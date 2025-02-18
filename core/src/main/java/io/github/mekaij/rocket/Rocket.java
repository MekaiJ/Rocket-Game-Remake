package io.github.mekaij.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Rocket extends Sprite {
    private float velocityY = 0; 
    private float gravity = -700; 
    private float lift = 300; 

    public Rocket() {
        super(new Texture("rocket.png")); 
        setPosition(175, 0); 
        setSize(128, 128); 
    }

    /*
     * Used for physics and upward movement
     */
    public void update(float delta, boolean isUpPressed) {
        velocityY += gravity * delta;

        // Apply lift if the up arrow is pressed
        if (isUpPressed) {
            velocityY = lift;
        }
        // Update position based on velocity
        setY(getY() + velocityY * delta);

        float maxVelocity = 1000;
        velocityY = Math.max(-maxVelocity, Math.min(maxVelocity, velocityY));
        if (getY() < 0) {
            setY(0);
            velocityY = 0;
        }
    }

    /*
     * Used for horizontal movement
     */
    public void moveHorizontal(float delta, boolean isLeftPressed, boolean isRightPressed) {
        float horizontalSpeed = 300; // Horizontal movement speed
        if (isLeftPressed) {
            setX(getX() - horizontalSpeed * delta); // Move left
        }
        if (isRightPressed) {
            setX(getX() + horizontalSpeed * delta); // Move right
        }

        // Keep within screen bounds
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + getWidth() > Gdx.graphics.getWidth()) {
            setX(Gdx.graphics.getWidth() - getWidth());
        }
    }

    public void dispose() {
        getTexture().dispose(); // Dispose of the texture
    }
}
