package ru.mipt.bit.platformer.util.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.enums.Direction;
import ru.mipt.bit.platformer.util.movement.Movement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements BaseObject {
    private final float MOVEMENT_SPEED = 7f;
    private final GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private final float rotation;
    private Movement direction;
    private float movementProgress = 1f;

    private final Player tank;



    public Bullet(GridPoint2 coords, float rotation, Player tank) {
        this.coordinates = new GridPoint2(coords);
        this.destinationCoordinates = new GridPoint2(coords);
        this.rotation = rotation;
        this.direction = getDirectionFromRotation();

        this.tank = tank;
    }

    private Movement getDirectionFromRotation() {
        if (rotation == Direction.UP.rotation) {
            return new Movement(Direction.UP.vector, Direction.UP.rotation);
        }
        if (rotation == Direction.RIGHT.rotation) {
            return new Movement(Direction.RIGHT.vector, Direction.RIGHT.rotation);
        }
        if (rotation == Direction.DOWN.rotation) {
            return new Movement(Direction.DOWN.vector, Direction.DOWN.rotation);
        }

        return new Movement(Direction.LEFT.vector, Direction.LEFT.rotation);
    }

    public GridPoint2 tryMovement() {
        GridPoint2 newCoordinates = new GridPoint2();
        newCoordinates.x = destinationCoordinates.x + direction.direction.x;
        newCoordinates.y = destinationCoordinates.y + direction.direction.y;
        return newCoordinates;
    }




    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Player getTank() {
        return tank;
    }

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public boolean hasFinishedMovement() {
        return isEqual(movementProgress, 1f);
    }

    public void makeMovement() {
        destinationCoordinates.add(direction.direction);
    }

    public void finishMovement() {
        movementProgress = 0f;
    }

    public void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
    }

    public void move() {
        if (hasFinishedMovement()) {
            makeMovement();
            finishMovement();
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateMovementProgress(deltaTime, MOVEMENT_SPEED);
        if (hasFinishedMovement()) {
            coordinates.set(destinationCoordinates);
        }
    }
}