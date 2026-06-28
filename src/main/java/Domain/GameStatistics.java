package Domain;

import java.util.ArrayList;
import java.util.List;

public class GameStatistics {
    private String playerName;
    private int treasure;
    private int level;
    private int kills;
    private int foods;
    private int potions;
    private int scrolls;
    private int hits;
    private int missHits;
    private int steps;

    public GameStatistics(String name) {
        this.playerName = name;
        treasure = 0;
        level = 1;
        kills = 0;
        foods = 0;
        potions = 0;
        scrolls = 0;
        hits = 0;
        missHits = 0;
        steps = 0;
    }

    public GameStatistics() {}

    public String getPlayerName() {
        return playerName;
    }

    public int getTreasure() {
        return treasure;
    }

    public int getLevel() {
        return level;
    }

    public int getKills() {
        return kills;
    }

    public int getFoods() {
        return foods;
    }

    public int getPotions() {
        return potions;
    }

    public int getScrolls() {
        return scrolls;
    }

    public int getHits() {
        return hits;
    }

    public int getMissHits() {
        return missHits;
    }

    public int getSteps() {
        return steps;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setFoods(int foods) {
        this.foods = foods;
    }

    public void setPotions(int potions) {
        this.potions = potions;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setMissHits(int missHits) {
        this.missHits = missHits;
    }

    public void setScrolls(int scrolls) {
        this.scrolls = scrolls;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    // игнор json
    public List<String> getSummary() {
        List<String> summary = new ArrayList<>();
        summary.add("=== FINAL STATISTICS ===");
        summary.add("Player: " + playerName);
        summary.add("Deepest Level: " + level);
        summary.add("Steps taken: " + steps);
        summary.add("Gold collected: " + treasure);
        summary.add("--- Combat ---");
        summary.add("Kills: " + kills);
        summary.add("Successful hits: " + hits);
        summary.add("Misses: " + missHits);
        summary.add("--- Consumption ---");
        summary.add("Food eaten: " + foods);
        summary.add("Potions drunk: " + potions);
        summary.add("Scrolls read: " + scrolls);
        summary.add("========================");
        return summary;
    }
}
