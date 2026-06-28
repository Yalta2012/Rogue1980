package Domain.Map;

import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Corridor implements Containable{
    @JsonIgnore
    private int size = 4;
    private boolean isVisited = false;

    public Corridor() {}

    private GameVector[] points = new GameVector[size];

    public GameVector[] getPoints(){
        return points;
    }

    public void setPoints(GameVector[] points) {
        this.points = points;
    }

    @JsonIgnore
    public boolean isVisited() {return isVisited;}
    public void setVisited(boolean val){ isVisited = val;}
    @JsonIgnore
    public  GameVector getPoint(int index){
        return points[index];
    }

    @JsonIgnore
    public GameVector getFirstPoint(){
        return points[0];
    }

    @JsonIgnore
    public GameVector getLastPoint(){
        return points[size-1];
    }

    @Override
    public boolean contains(GameVector point) {
        for(int i = 1; i < 4; i++){

            GameVector p1 = points[i - 1];
            GameVector p2 = points[i];

            if (p1.getX() == p2.getX()) {
                if (point.getX() != p1.getX()) continue;
                int minY = Math.min(p1.getY(), p2.getY());
                int maxY = Math.max(p1.getY(), p2.getY());
                if (point.getY() >= minY && point.getY() <= maxY)
                    return true;
            }
            else if (p1.getY() == p2.getY()) {
                if (point.getY() != p1.getY()) continue;
                int minX = Math.min(p1.getX(), p2.getX());
                int maxX = Math.max(p1.getX(), p2.getX());
                if (point.getX() >= minX && point.getX() <= maxX)
                    return true;
            }
        }

        return false;
    }

    public Corridor(GameVector startPoint, GameVector endPoint, GameVector direction){
        if(direction != GameVector.DOWN && direction != GameVector.RIGHT) throw new RuntimeException("Wrong direction");

        points[0] = startPoint;
        points[3] = endPoint;

        if(direction == GameVector.DOWN){

            int midY = Generator.nextInt(startPoint.getY()+1, endPoint.getY()-1);
            points[1] = new GameVector(startPoint.getX(), midY);
            points[2] = new GameVector(endPoint.getX(), midY);

        }
        if (direction == GameVector.RIGHT){

            int midX = Generator.nextInt( startPoint.getX()+1, endPoint.getX()-1);
            points[1] = new GameVector(midX, startPoint.getY());
            points[2] = new GameVector(midX, endPoint.getY());
        }
    }
}
