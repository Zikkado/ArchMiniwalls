package me.arch.miniwalls.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {
	
	private String ID;
	private List<Player> players = new ArrayList<>();
	private Location center;
	
	public Arena(String ID) {
		setID(ID);
	}
	
	public void setCenter(Location loc) {
		this.center = loc;
	}
	
	public Location getCenter() {
		return this.center;
	}


	public List<Player> getPlayers() {
		return players;
	}


	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

}
