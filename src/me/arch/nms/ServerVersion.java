package me.arch.nms;

import org.bukkit.Bukkit;

public enum ServerVersion {
	  v1_18("1.18"),
	  v1_17("1.17"),
	  v1_16("1.16"),
	  v1_15("1.15"),
	  v1_14("1.14"),
	  v1_13("1.13"),
	  v1_12("1.12"),
	  v1_11("1.11"),
	  v1_10("1.10"),
	  v1_9("1.9"),
	  v1_8("1.8"),
	  v1_7("1.7"),
	  v1_6("1.6"),
	  v1_5("1.5"),
	  DESCONHECIDA("???");
	  
	  ServerVersion(String check) {
	    this.check = check;
	  }
	  
	  private final String check;
	  
	  private static ServerVersion serverVersion;
	  
	  public String getCheck() {
	    return this.check;
	  }
	  
	  public static ServerVersion getServerVersion() {
	    return serverVersion;
	  }
	  
	  static {
	    serverVersion = DESCONHECIDA;
	  }
	  
	  public static void load() {
	    String ver = Bukkit.getVersion();
	    for (ServerVersion serverVersion : values()) {
	      if (ver.contains(serverVersion.check)) {
	        ServerVersion.serverVersion = serverVersion;
	        break;
	      } 
	    } 
	  }
	}
