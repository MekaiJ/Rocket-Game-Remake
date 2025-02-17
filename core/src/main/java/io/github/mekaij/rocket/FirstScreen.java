package io.github.mekaij.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Texture rocketTexture;
    private Sprite rocket;

    private Texture backgroundTexture;
    private Sprite background;

    private float velocity = 0f;
    private float gravity = 0f;
    private float lift = 400f;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        rocketTexture = new Texture(Gdx.files.internal("rocket.png")); // Load the texture
        rocket = new Sprite(rocketTexture);
        rocket.setPosition(175, 0); // Set the sprite's position
        rocket.setSize(128, 128);

        backgroundTexture = new Texture(Gdx.files.internal("cloudsbackground.jpg"));
        background = new Sprite(backgroundTexture);
        background.setSize(background.getWidth(), background.getHeight());
    }

    //TODO: Refactor this into several different methods
    //TODO: Make rocket sprite its own class
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Constants for movement
        float gravity = -500; // Increased gravity for faster falling (pixels per second squared)
        float lift = 400; // Lift force when the up arrow is pressed (pixels per second squared)
        float horizontalSpeed = 200; // Horizontal movement speed (pixels per second)

        // Apply gravity to the rocket's velocity
        velocity += gravity * delta;

        // If the up arrow is pressed, apply lift to counteract gravity
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            velocity = lift; // Set velocity directly for instant response
        } else {
            velocity += gravity * delta; // Apply gravity when up arrow is not pressed
        }

        // Clamp the velocity to prevent excessive speed
        float maxVelocity = 1000; // Increased maximum vertical speed
        velocity = Math.max(-maxVelocity, Math.min(maxVelocity, velocity));

        // Update the rocket's vertical position based on velocity
        rocket.setY(rocket.getY() + velocity * delta);

        // Handle horizontal movement
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            rocket.setX(rocket.getX() - horizontalSpeed * delta); // Move left
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            rocket.setX(rocket.getX() + horizontalSpeed * delta); // Move right
        }

        // Keep the rocket within screen bounds
        if (rocket.getX() < 0) {
            rocket.setX(0); // Prevent moving past the left edge
        }
        if (rocket.getX() + rocket.getWidth() > Gdx.graphics.getWidth()) {
            rocket.setX(Gdx.graphics.getWidth() - rocket.getWidth()); // Prevent moving past the right edge
        }
        if (rocket.getY() < 0) {
            rocket.setY(0); // Prevent moving past the bottom edge
            velocity = 0; // Stop downward velocity when hitting the ground
        }

        // Begin drawing
        spriteBatch.begin();

        // Draw the background
        background.draw(spriteBatch);

        // Draw the rocket
        rocket.draw(spriteBatch);

        // End drawing
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        background.setSize(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        rocketTexture.dispose();
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
