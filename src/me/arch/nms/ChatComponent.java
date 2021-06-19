package me.arch.nms;

import java.lang.reflect.Method;

public class ChatComponent {
  public static final Class<?> icbc;
  
  private static Method a;
  
  static {
    try {
      icbc = Reflection.getNMSNM("IChatBaseComponent", "network.chat.IChatBaseComponent");
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } 
  }
  
  public static Object chatComponentFromText(String text) {
    if (a != null)
      try {
        return a.invoke(null, new Object[] { "{\"text\":\"" + text + "\"}" });
      } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
        e.printStackTrace();
      }  
    return null;
  }
  
  public static void load() {
    try {
      if ((icbc.getDeclaredClasses()).length > 0) {
        a = icbc.getDeclaredClasses()[0].getMethod("a", new Class[] { String.class });
      } else {
        a = Reflection.getNMSV(new String[] { "ChatSerializer" }).getMethod("a", new Class[] { String.class });
      } 
    } catch (Throwable throwable) {}
  }
}