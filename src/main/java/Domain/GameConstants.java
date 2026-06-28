package Domain;

public class GameConstants {
    public static final int WALL_THICKNESS = 1;

    public static final int ROOM_GRID_HEIGHT = 3;
    public static final int ROOM_GRID_WIDTH = 3;

    public static final int ROOM_SPACING = 2;

    public static final int ROOM_MIN_HEIGHT = 3 + 2 * WALL_THICKNESS;
    public static final int ROOM_MIN_WIDTH = 4 + 2 * WALL_THICKNESS;

    public static final int ROOM_MAX_HEIGHT = 5 + 2 * WALL_THICKNESS;
    public static final int ROOM_MAX_WIDTH = 8 + 2 * WALL_THICKNESS;

    public static final int CELL_HEIGHT = ROOM_MAX_HEIGHT + ROOM_SPACING;
    public static final int CELL_WIDTH = ROOM_MAX_WIDTH + ROOM_SPACING;

    public static final int MAP_HEIGHT = CELL_HEIGHT * ROOM_GRID_HEIGHT;
    public static final int MAP_WIDTH = CELL_WIDTH * ROOM_GRID_WIDTH;

    public static final int LOG_CAPACITY = 10;
    public static final int MAX_STACK = 9;

    public static final int MAX_LEVEL = 21;

    public static final int POTION_DURATION = 30;
    public static final int STRENGTH_POTION_DIFFERENCE = 5;
    public static final int AGILITY_POTION_DIFFERENCE = 5;
    public static final int HEALTH_POTION_DIFFERENCE = 10;

    public static final class Player{

        public static final int MAX_HEALTH = 100;
        public static final int AGILITY = 10;
        public static final int STRENGTH = 10;
    }

    public static final class Zombie{
        public static final int HEALTH = 40;
        public static final int AGILITY = 4;
        public static final int STRENGTH = 7;
        public static final int HOSTILITY = 5;
    }

    public static final class Vampire{
        public static final int HEALTH = 30;
        public static final int AGILITY = 12;
        public static final int STRENGTH = 7;
        public static final int HOSTILITY = 7;

        public static final int DEBUFF_DURATION = 4;
        public static final int HP_DECREASE = -10;
    }

    public static final class Ghost{
        public static final int HEALTH = 15;
        public static final int AGILITY = 15;
        public static final int STRENGTH = 4;
        public static final int HOSTILITY = 3;
    }

    public static final class Ogr{
        public static final int HEALTH = 120;
        public static final int AGILITY = 4;
        public static final int STRENGTH = 15;
        public static final int HOSTILITY = 3;
    }

    public static final class SnakeMage{
        public static final int HEALTH = 20;
        public static final int AGILITY = 16;
        public static final int STRENGTH = 7;
        public static final int HOSTILITY = 5;

        public static final double STUN_CHANCE = 0.1;
    }

    public static final class Mimic{
        public static final int HEALTH = 45;
        public static final int AGILITY = 12;
        public static final int STRENGTH = 5;
        public static final int HOSTILITY = 1;
    }



}
