package me.arch.miniwalls.scoreboard;


import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;


public class ScoreManager {
	
	
	private HashMap<Player, Board> scores = new HashMap<>();
	
	
	public ScoreManager(Player p) {
		if(!getScores().containsKey(p)) {
			getScores().put(p, new Board(p.getName()));
		}
	}


	public HashMap<Player, Board> getScores() {
		return scores;
	}


	public void getBoard(Player p, String title, List<String> lines) {
		getScores().get(p).setTitle(title);
		getScores().get(p).setLines(lines);
	}
	
	public void removeBoard(Player p) {
		getScores().remove(p);
	}
	
	
	public void ShowBoard(Player p) {
		getScores().get(p).show(p);
	}
	
	
	
	
}
