package me.arch.miniwalls;

import org.bukkit.plugin.java.JavaPlugin;

import me.arch.miniwalls.listeners.EntityDemageEvents;
import me.arch.miniwalls.listeners.PlayerDeath;



public class main extends JavaPlugin {
	
	public static main pl;
	

	
	@Override
	public void onEnable() {
		pl = this;
		
		Init initializtion = new Init(this, "ArchMiniwalls");
		
		initializtion.AddListener(new PlayerDeath());
		initializtion.AddListener(new EntityDemageEvents());
		
		initializtion.registerEvents();
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
