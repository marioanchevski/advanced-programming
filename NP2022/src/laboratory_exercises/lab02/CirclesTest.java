package laboratory_exercises.lab02;

import java.util.*;
import java.util.stream.Collectors;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();

    TYPE getObjectType();
}

class MovablePoint implements Movable {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (getCurrentYPosition() + getySpeed() > MovablesCollection.Y)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition(), getCurrentYPosition() + getySpeed()));
        setY(getCurrentYPosition() + getySpeed());
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (getCurrentXPosition() - getxSpeed() < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition() - getxSpeed(), getCurrentYPosition()));
        setX(getCurrentXPosition() - getxSpeed());
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (getCurrentXPosition() + getxSpeed() > MovablesCollection.X)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition() + getxSpeed(), getCurrentYPosition()));
        setX(getCurrentXPosition() + getxSpeed());
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (getCurrentYPosition() - getySpeed() < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition(), getCurrentYPosition() - getySpeed()));
        setY(getCurrentYPosition() - getySpeed());
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public TYPE getObjectType() {
        return TYPE.POINT;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }
}

class MovableCircle implements Movable {
    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (getCurrentYPosition() + center.getySpeed() > MovablesCollection.Y)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition(), getCurrentYPosition() + center.getySpeed()));
        center.setY(getCurrentYPosition() + center.getySpeed());
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (getCurrentXPosition() - center.getxSpeed() < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition() - center.getxSpeed(), getCurrentYPosition()));
        center.setX(getCurrentXPosition() - center.getxSpeed());
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (getCurrentXPosition() + center.getxSpeed() > MovablesCollection.X)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition() + center.getxSpeed(), getCurrentYPosition()));
        center.setX(getCurrentXPosition() + center.getxSpeed());
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (getCurrentYPosition() - center.getySpeed() < 0)
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", getCurrentXPosition(), getCurrentYPosition() - center.getySpeed()));
        center.setY(getCurrentYPosition() - center.getySpeed());
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    public int getRadius() {
        return radius;
    }

    public MovablePoint getCenter() {
        return center;
    }

    public TYPE getObjectType() {
        return TYPE.CIRCLE;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d",
                center.getCurrentXPosition(), center.getCurrentYPosition(), radius);
    }

}

class MovablesCollection {
    private Movable[] movable;
    private int numberOfObjects;
    private int currentMaxSizeOfMovable;

    public static int X;
    public static int Y;

    public MovablesCollection(int x_MAX, int y_MAX) {
        MovablesCollection.setxMax(x_MAX);
        MovablesCollection.setyMax(y_MAX);
        numberOfObjects = 0;
        currentMaxSizeOfMovable = 10;
        movable = new Movable[currentMaxSizeOfMovable];

    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (objectInBounds(m)) {
            if (numberOfObjects == currentMaxSizeOfMovable) {
                movable = Arrays.copyOf(movable, 2 * currentMaxSizeOfMovable);
                currentMaxSizeOfMovable *= 2;
            }
            movable[numberOfObjects++] = m;
        }
    }

    private boolean objectInBounds(Movable m) throws MovableObjectNotFittableException {
/*        if (m.getObjectType().equals(TYPE.POINT)) {
            MovablePoint point = (MovablePoint) m;
            if (point.getCurrentXPosition() < 0 || point.getCurrentXPosition() > X ||
                    point.getCurrentYPosition() < 0 || point.getCurrentYPosition() > Y)
                throw new MovableObjectNotFittableException(String.format("test"));

        }*/
        if (m.getObjectType().equals(TYPE.CIRCLE)) {
            MovableCircle circle = (MovableCircle) m;
            if (circle.getCurrentXPosition() + circle.getRadius() > X || circle.getCurrentXPosition() - circle.getRadius() < 0 ||
                    circle.getCurrentYPosition() + circle.getRadius() > Y || circle.getCurrentYPosition() - circle.getRadius() < 0)
                throw new MovableObjectNotFittableException(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection",
                        circle.getCurrentXPosition(), circle.getCurrentYPosition(), circle.getRadius()));
        }
        return true;
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (Movable m : movable) {
            if (m != null && m.getObjectType().equals(type)) {
                try {
                    if (direction.equals(DIRECTION.UP))
                        m.moveUp();
                    if (direction.equals(DIRECTION.DOWN))
                        m.moveDown();
                    if (direction.equals(DIRECTION.LEFT))
                        m.moveLeft();
                    if (direction.equals(DIRECTION.RIGHT))
                        m.moveRight();
                } catch (ObjectCanNotBeMovedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void setxMax(int x) {
        X = x;
    }

    public static void setyMax(int y) {
        Y = y;
    }

    @Override
    public String toString() {
        String result = Arrays.stream(movable).filter(Objects::nonNull).map(s -> String.format("%s", s.toString())).collect(Collectors.joining("\n"));
        return String.format("Collection of movable objects with size %d:\n%s\n", numberOfObjects, result);
    }
}

public class CirclesTest {

    public static void main(String[] args) throws ObjectCanNotBeMovedException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                try {
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                try {
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());


    }


}
