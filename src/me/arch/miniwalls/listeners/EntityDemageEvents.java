package me.arch.miniwalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDemageEvents implements Listener {
	
	@EventHandler
	private void onDemage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
}
