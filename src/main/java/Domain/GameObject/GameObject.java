package Domain.GameObject;

import Domain.GameConstants;
import Domain.GameVector;

public abstract class GameObject {
    protected GameVector position;

    public GameObject() {}

    public GameObject(int x, int y) {
        position = new GameVector(x,y);
    }

    public GameObject(GameVector vector){
        position = vector;
    }

    // public GameObject() {this(0,0);}
    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public void setX(int x) { position.setX(x); }

    public void setY(int y) {
        position.setY(y);
    }

    public GameVector getPosition(){
        return position;
    }

    public void setPosition(GameVector position) {
        this.position = position;
    }
}
