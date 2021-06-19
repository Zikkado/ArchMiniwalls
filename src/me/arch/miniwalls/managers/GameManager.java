package me.arch.miniwalls.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;


public class GameManager {
	
	private HashMap<Player, Arena> games = new HashMap<>();
	
	
	public GameManager() {
		
	}
	
	
	
	public void onJoin(Player p) {
		
		
		
		for(Player all : new Arena("RaNdOm").getPlayers()) {
			all.sendMessage(ChatColor.translateAlternateColorCodes('&', "§7" + p.getName() + " §eentrou (" + ""));
		}
	}
	
	public HashMap<Player, Arena> getGames() {
		return this.games;
	}
	
	
	
}
