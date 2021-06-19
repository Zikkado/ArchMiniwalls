package me.arch.miniwalls.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board {
    private static int count = 0;

    private static final List<ChatColor> COLORS = Arrays.asList(ChatColor.values());

    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    private final Objective objective;

    private String title;

    private final List<Line> lines = new ArrayList<>();

    public Board(String title) {
        this.objective = this.scoreboard.registerNewObjective("scorelib" + count++, "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setTitle(title);
        for (int i = 0; i < COLORS.size(); i++) {
            ChatColor color = COLORS.get(i);
            Team team = this.scoreboard.registerNewTeam("line" + i);
            this.lines.add(i, new Line(color.toString(), team, i));
        }
    }

    public void setLines(List<String> lines) {
        int start = lines.size();
        for (String line : lines)
            setLine(start--, line);
    }

    public void setLine(int line, String value) {
        Line boardLine = getLine(line);
        Validate.notNull(boardLine, "The index should be between 0 and 21");
        updateLine(boardLine, value);
        if (!boardLine.isSet()) {
            this.objective.getScore(boardLine.getEntry()).setScore(line);
            boardLine.setSet(true);
        }
    }

    private void updateLine(Line boardLine, String value) {
        value = ChatColor.translateAlternateColorCodes('&', value);
        if (value.length() <= 16) {
            boardLine.getTeam().setPrefix(value);
            return;
        }
        String prefix = value.substring(0, 16);
        String suffix = value.substring(16);
        String lastColor = ChatColor.getLastColors(prefix);
        if (!boardLine.getFixColor().equals(lastColor)) {
            removeLine(boardLine);
            boardLine.fixColor(lastColor);
        }
        if (suffix.length() > 16)
            suffix = suffix.substring(0, 16);
        boardLine.getTeam().setPrefix(prefix);
        boardLine.getTeam().setSuffix(suffix);
    }

    public void removeLine(int line) {
        Line boardLine = getLine(line);
        Validate.notNull(boardLine, "The index should be between 0 and 21");
        removeLine(boardLine);
    }

    private void removeLine(Line line) {
        this.scoreboard.resetScores(line.getEntry());
        line.setSet(false);
    }

    private Line getLine(int index) {
        return this.lines.stream().filter(line -> (line.getLine() == index)).findFirst().orElse(null);
    }

    public void show(Player player) {
        player.setScoreboard(this.scoreboard);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
}