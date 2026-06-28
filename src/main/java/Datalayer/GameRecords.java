package Datalayer;

public class GameRecords {
    public String name;
    public int score;
    public int levelReached;

    public GameRecords() {}

    public GameRecords(String name, int score, int levelReached) {
        this.name = name;
        this.score = score;
        this.levelReached = levelReached;
    }
}
