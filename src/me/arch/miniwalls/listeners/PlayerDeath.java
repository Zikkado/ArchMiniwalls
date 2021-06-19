package me.arch.miniwalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

	
	@EventHandler
	private void onDeath(PlayerDeathEvent e) {
		//Player p = e.getEntity();
		e.setDeathMessage(null);
		
	}
	
}
