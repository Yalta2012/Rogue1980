package Domain;

import java.util.Random;

public class Generator {
    private static Random random = new Random();

    public static int nextInt(int a, int b){
        if (a > b){
            return random.nextInt(b, a+1);
        }
        if (a == b){
            return a;
        }
        return random.nextInt(a,b+1);
    }

    public static int roll(int dices, int sides){
        int result = 0;
        for(int i = 0; i < dices; i ++){
            result += nextInt(1, sides+1);
        }
        return result;
    }

    public static GameVector randomPointBetween(GameVector a, GameVector b){
        return new GameVector(
                nextInt(a.getX(), b.getX()),
                nextInt(a.getY(), b.getY())
        );
    }
}
