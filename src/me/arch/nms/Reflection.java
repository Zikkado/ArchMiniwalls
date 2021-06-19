package me.arch.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Reflection {
	  private static final Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
	  
	  private static final String OBC_PREFIX;
	  
	  private static final String NMS_PREFIX;
	  
	  private static final String NM_PREFIX;
	  
	  private static final String VERSION;
	  
	  public static final Class<?> craftServerClass;
	  
	  public static final Class<?> craftPlayerClass;
	  
	  public static final Class<?> entityPlayerClass;
	  
	  public static final Class<?> playerConnectionClass;
	  
	  public static final MethodInvoker getHandleMethod;
	  
	  public static final MethodInvoker sendPacketMethod;
	  
	  public static final FieldAccessor<?> playerConnectionField;
	  
	  static {
	    try {
	      OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
	      NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
	      NM_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft");
	      VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
	      craftServerClass = getOBC(new String[] { "CraftServer" });
	      craftPlayerClass = getOBC(new String[] { "entity.CraftPlayer" });
	      entityPlayerClass = getNMS("EntityPlayer", "level.EntityPlayer");
	      playerConnectionClass = getNMS("PlayerConnection", "network.PlayerConnection");
	      getHandleMethod = getMethod(craftPlayerClass, "getHandle", new Class[0]);
	      sendPacketMethod = getMethod(playerConnectionClass, "sendPacket", new Class[] { getNMSNM("Packet", "network.protocol.Packet") });
	      playerConnectionField = getField(entityPlayerClass, playerConnectionClass, 0);
	    } catch (Throwable e) {
	      e.printStackTrace();
	      throw new RuntimeException(e);
	    } 
	  }
	  
	  public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
	    return getField(target, name, fieldType, 0);
	  }
	  
	  public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
	    return getField(getClass(new String[] { className }), name, fieldType, 0);
	  }
	  
	  public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
	    return getField(target, null, fieldType, index);
	  }
	  
	  public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
	    return getField(getClass(new String[] { className }), fieldType, index);
	  }
	  
	  private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
	    for (Field field : target.getDeclaredFields()) {
	      if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
	        field.setAccessible(true);
	        return new FieldAccessor<T>() {
	            @SuppressWarnings("unchecked")
				public T get(Object target) {
	              try {
	                return (T)field.get(target);
	              } catch (IllegalAccessException e) {
	                throw new RuntimeException("Cannot access reflection.", e);
	              } 
	            }
	            
	            public void set(Object target, Object value) {
	              try {
	                field.set(target, value);
	              } catch (IllegalAccessException e) {
	                throw new RuntimeException("Cannot access reflection.", e);
	              } 
	            }
	            
	            public boolean hasField(Object target) {
	              return field.getDeclaringClass().isAssignableFrom(target.getClass());
	            }
	          };
	      } 
	    } 
	    if (target.getSuperclass() != null)
	      return getField(target.getSuperclass(), name, fieldType, index); 
	    throw new IllegalArgumentException("Cannot find field with type " + fieldType);
	  }
	  
	  public static interface FieldAccessor<T> {
	    T get(Object param1Object);
	    
	    void set(Object param1Object1, Object param1Object2);
	    
	    boolean hasField(Object param1Object);
	  }
	  
	  public static interface MethodInvoker {
	    Object invoke(Object param1Object, Object... param1VarArgs);
	  }
	  
	  public static interface ConstructorInvoker {
	    Object invoke(Object... param1VarArgs);
	  }
	  
	  public static MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
	    return getTypedMethod(getClass(new String[] { className } ), methodName, null, params);
	  }
	  
	  public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
	    return getTypedMethod(clazz, methodName, null, params);
	  }
	  
	  public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
	    for (Method method : clazz.getDeclaredMethods()) {
	      if ((methodName == null || method.getName().equals(methodName)) && (returnType == null || method
	        .getReturnType().equals(returnType)) && 
	        Arrays.equals((Object[])method.getParameterTypes(), (Object[])params)) {
	        method.setAccessible(true);
	        return (target, arguments) -> {
	            try {
	              return method.invoke(target, arguments);
	            } catch (Exception e) {
	              throw new RuntimeException("Cannot invoke method " + method, e);
	            } 
	          };
	      } 
	    } 
	    if (clazz.getSuperclass() != null)
	      return getMethod(clazz.getSuperclass(), methodName, params); 
	    throw new IllegalStateException(String.format("Unable to find method %s (%s).", new Object[] { methodName, Arrays.asList(params) }));
	  }
	  
	  public static ConstructorInvoker getConstructor(String className, Class<?>... params) {
	    return getConstructor(getClass(new String[] { className } ), params);
	  }
	  
	  public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
	    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
	      if (Arrays.equals((Object[])constructor.getParameterTypes(), (Object[])params)) {
	        constructor.setAccessible(true);
	        return arguments -> {
	            try {
	              return constructor.newInstance(arguments);
	            } catch (Exception e) {
	              throw new RuntimeException("Cannot invoke constructor " + constructor, e);
	            } 
	          };
	      } 
	    } 
	    throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", new Object[] { clazz, Arrays.asList(params) }));
	  }
	  
	  @SuppressWarnings("unchecked")
	public static Class<Object> getUntypedClass(String... names) {
	    Class<Object> clazz = (Class<Object>) getClass(names);
	    return clazz;
	  }
	  
	  public static Class<?> getClass(String... names) {
	    for (int i = 0; i < names.length; i++)
	      names[i] = expandVariables(names[i]); 
	    return getCanonicalClass(names);
	  }
	  
	  public static Class<?> getNMSV(String... names) {
	    for (int i = 0; i < names.length; i++)
	      names[i] = NMS_PREFIX + "." + names[i]; 
	    return getCanonicalClass(names);
	  }
	  
	  public static Class<?> getNMS(String oldClass, String newClass) {
	    return getNMSNM(oldClass, "server." + newClass);
	  }
	  
	  public static Class<?> getNMSNM(String oldClass, String newClass) {
	    return getCanonicalClass(new String[] { NMS_PREFIX + "." + oldClass, "net.minecraft." + newClass });
	  }
	  
	  public static Class<?> getOBC(String... names) {
	    for (int i = 0; i < names.length; i++)
	      names[i] = OBC_PREFIX + "." + names[i]; 
	    return getCanonicalClass(names);
	  }
	  
	  private static Class<?> getCanonicalClass(String... names) {
	    if (names.length == 0)
	      throw new IllegalArgumentException("Empty canonical names!"); 
	    for (int i = 0; i < names.length - 1; i++) {
	      try {
	        return Class.forName(names[i]);
	      } catch (ClassNotFoundException classNotFoundException) {}
	    } 
	    String canonicalName = names[names.length - 1];
	    try {
	      return Class.forName(canonicalName);
	    } catch (ClassNotFoundException e) {
	      throw new IllegalArgumentException("Cannot find " + canonicalName, e);
	    } 
	  }
	  
	  private static String expandVariables(String name) {
	    StringBuffer output = new StringBuffer();
	    Matcher matcher = MATCH_VARIABLE.matcher(name);
	    while (matcher.find()) {
	      String variable = matcher.group(1);
	      String replacement = "";
	      switch (variable) {
	        case "nms":
	          replacement = NMS_PREFIX;
	          break;
	        case "nm":
	          replacement = NM_PREFIX;
	          break;
	        case "obc":
	          replacement = OBC_PREFIX;
	          break;
	        case "version":
	          replacement = VERSION;
	          break;
	        default:
	          throw new IllegalArgumentException("Unknown variable: " + variable);
	      } 
	      if (!replacement.isEmpty() && matcher.end() < name.length() && name.charAt(matcher.end()) != '.')
	        replacement = replacement + "."; 
	      matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
	    } 
	    matcher.appendTail(output);
	    return output.toString();
	  }
	  
	  public static void sendPacket(Player player, Object packet) {
	    if (packet == null)
	      throw new IllegalArgumentException("Packet cannot be null!"); 
	    if (player == null || !player.isOnline())
	      return; 
	    try {
	      Object entityPlayer = getHandleMethod.invoke(player, new Object[0]);
	      Object playerConnection = playerConnectionField.get(entityPlayer);
	      sendPacketMethod.invoke(playerConnection, new Object[] { packet });
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	  }
	}
