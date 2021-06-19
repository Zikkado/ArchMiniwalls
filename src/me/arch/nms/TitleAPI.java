package me.arch.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleAPI {
	  private static boolean available = true;
	  
	  private static Method a;
	  
	  private static Object enumTIMES;
	  
	  private static Object enumTITLE;
	  
	  private static Object enumSUBTITLE;
	  
	  private static Constructor<?> timeTitleConstructor;
	  
	  private static Constructor<?> textTitleConstructor;
	  
	  private static Reflection.MethodInvoker sendTitleMethod;
	  
	  public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
	    if (available)
	      if (sendTitleMethod == null) {
	        try {
	          Object chatTitle = a.invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	          Object chatSubtitle = a.invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
	          Object timeTitlePacket = timeTitleConstructor.newInstance(new Object[] { enumTIMES, null, Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
	          Object titlePacket = textTitleConstructor.newInstance(new Object[] { enumTITLE, chatTitle });
	          Object subtitlePacket = textTitleConstructor.newInstance(new Object[] { enumSUBTITLE, chatSubtitle });
	          Reflection.sendPacket(player, timeTitlePacket);
	          Reflection.sendPacket(player, titlePacket);
	          Reflection.sendPacket(player, subtitlePacket);
	        } catch (Exception e) {
	          try {
	            try {
	              Player.class.getMethod("sendTitle", new Class[] { String.class, String.class }).invoke(player, new Object[] { title, subtitle });
	            } catch (NoSuchMethodError|NoSuchMethodException ex) {
	              Player.class.getMethod("sendTitle", new Class[] { String.class, String.class, int.class, int.class, int.class }).invoke(player, new Object[] { title, subtitle, Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
	            } 
	          } catch (Exception ex) {
	            available = false;
	            ex.printStackTrace();
	          } 
	        } 
	      } else {
	        sendTitleMethod.invoke(player, new Object[] { title, subtitle, Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
	      }  
	  }
	  
	  public static void broadcastTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle) {
	    if (available)
	      Bukkit.getServer().getOnlinePlayers().forEach(player -> sendTitle(player, fadeIn, stay, fadeOut, title, subtitle)); 
	  }
	  
	  public static void load() {
	    try {
	      load116();
	    } catch (Throwable e) {
	      try {
	        load117();
	      } catch (Throwable e2) {
	        available = false;
	      } 
	    } 
	  }
	  
	  private static void load116() throws Throwable {
	    Class<?> enumClass, ppot = Reflection.getNMSV(new String[] { "PacketPlayOutTitle" });
	    if ((ppot.getDeclaredClasses()).length > 0) {
	      enumClass = ppot.getDeclaredClasses()[0];
	    } else {
	      enumClass = Reflection.getNMSV(new String[] { "EnumTitleAction" });
	    } 
	    if ((ChatComponent.icbc.getDeclaredClasses()).length > 0) {
	      a = ChatComponent.icbc.getDeclaredClasses()[0].getMethod("a", new Class[] { String.class });
	    } else {
	      a = Reflection.getNMSV(new String[] { "ChatSerializer" }).getMethod("a", new Class[] { String.class });
	    } 
	    enumTIMES = enumClass.getField("TIMES").get(null);
	    enumTITLE = enumClass.getField("TITLE").get(null);
	    enumSUBTITLE = enumClass.getField("SUBTITLE").get(null);
	    timeTitleConstructor = ppot.getConstructor(new Class[] { enumClass, ChatComponent.icbc, int.class, int.class, int.class });
	    textTitleConstructor = ppot.getConstructor(new Class[] { enumClass, ChatComponent.icbc });
	  }
	  
	  private static void load117() throws Throwable {
	    sendTitleMethod = Reflection.getMethod(Reflection.craftPlayerClass, "sendTitle", new Class[] { String.class, String.class, int.class, int.class, int.class });
	  }
	}
