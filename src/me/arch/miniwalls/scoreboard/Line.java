package me.arch.miniwalls.scoreboard;

import org.bukkit.scoreboard.Team;

class Line {
    private final String defaultEntry;
    private String fixColor = "";
    private Team team;
    private final int line;
    private boolean isSet = false;

    Line(String entry, Team team, int line) {
        this.team = team;
        this.line = line;
        this.defaultEntry = entry;
        updateEntry();
    }
    private void updateEntry() {
        if (this.defaultEntry != null)
            this.team.removeEntry(getEntry());
        this.team.addEntry(getEntry());
    }
    void fixColor(String color) {
        this.fixColor = color;
        updateEntry();
    }
    String getFixColor() {
        return this.fixColor;
    }
    String getEntry() {
        return this.defaultEntry + this.fixColor;
    }
    Team getTeam() {
        return this.team;
    }
    int getLine() {
        return this.line;
    }
    boolean isSet() {
        return this.isSet;
    }
    void setSet(boolean set) {
        this.isSet = set;
    }
}