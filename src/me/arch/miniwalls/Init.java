package me.arch.miniwalls;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Init {
	
	private Plugin plugin;
	
	public List<Listener> listeners = new ArrayList<>();
	
	public Init(Plugin plugin, String pluginName) {
		setPlugin(plugin);
		
		
		registerEvents();
		
		
		Bukkit.getConsoleSender().sendMessage("§aPlugin: §e" + pluginName + " §aIniciado Com Sucesso!");
		Bukkit.getConsoleSender().sendMessage("§aSite: §ehttps://archstore.xyz/plugins");
		Bukkit.getConsoleSender().sendMessage("§aAuthor: §eArch");
	}
	
	
	public Plugin getPlugin() {
		return this.plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void AddListener(Listener e) {
		getListeners().add(e);
	}
	
	public List<Listener> getListeners() {
		return this.listeners;
	}
	
	
	public void registerEvents() {
		for(Listener e : getListeners()) {
			Bukkit.getPluginManager().registerEvents(e, main.pl);
			Bukkit.getConsoleSender().sendMessage("§aRegistrando: " + e.getClass());
		}
		
	}
	
	
}
