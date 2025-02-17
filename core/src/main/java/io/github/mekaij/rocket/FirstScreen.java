package io.github.mekaij.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Rocket rocket;
    private Texture backgroundTexture;
    private Sprite background;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        rocket = new Rocket(); // Initialize the rocket

        backgroundTexture = new Texture(Gdx.files.internal("cloudsbackground.jpg"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update rocket movement
        boolean isUpPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP);
        boolean isLeftPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT);
        boolean isRightPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT);

        rocket.update(delta, isUpPressed); // Update vertical movement
        rocket.moveHorizontal(delta, isLeftPressed, isRightPressed); // Update horizontal movement

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
        rocket.dispose();
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
