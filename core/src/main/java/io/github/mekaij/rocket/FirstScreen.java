package io.github.mekaij.rocket;

import javax.swing.plaf.ViewportUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FirstScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Rocket rocket;
    private Texture backgroundTexture;
    private Sprite background;
    private Array<Sprite> asteroids;
    private Texture asteroidTexture;

    private Sound bgmusic;

    private Camera camera;

    private Viewport viewport;


    /*
     * Initialize and set up all components for the screen
     */
    //TODO: Tilemapping for background
    @Override
    public void show() {
        //music credit to my 8 year old brother
        bgmusic = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
        bgmusic.play();
        bgmusic.setLooping(0, true);

        spriteBatch = new SpriteBatch();
        rocket = new Rocket();
        asteroids = new Array<>();
        createAsteroids();

        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("cloudsbackground.jpg"));
        background = new Sprite(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the camera
        float viewportWidth = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
        camera = new Camera(viewportWidth, viewportHeight);
        camera.position.set(viewportWidth / 2, viewportHeight / 2, 0); // Center the camera initially
        camera.update();
    }

    private void createAsteroids() {
        float width = 1f;
        float height = 1f;
        float worldWidth = Gdx.graphics.getWidth();
        float worldHeight = Gdx.graphics.getHeight();

        //create asteroid sprite
        Sprite asteroidSprite = new Sprite();
        asteroidTexture = new Texture(Gdx.files.internal("asteroid.png"));
        asteroidSprite.setSize(width, height);
        asteroidSprite.setY(worldHeight);
        asteroids.add(asteroidSprite);
    }

    /*
     * Main game loop
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update rocket movement
        boolean isUpPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W);
        boolean isLeftPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A);
        boolean isRightPressed = Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D);

        rocket.update(delta, isUpPressed); 
        rocket.moveHorizontal(delta, isLeftPressed, isRightPressed);

        float cameraX = camera.viewportWidth / 2; 
        float cameraY = rocket.getY() + 100 + rocket.getHeight() / 2; 
        camera.position.set(cameraX, cameraY, 0);
        camera.update();

        
        spriteBatch.begin();

        background.setPosition(0, 0); 
        background.draw(spriteBatch);

        // Apply the camera to the SpriteBatch for the rocket and other objects
        spriteBatch.setProjectionMatrix(camera.combined);

        rocket.draw(spriteBatch);
        spriteBatch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        // Update the camera's viewport when the screen is resized
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        bgmusic.dispose();
        rocket.dispose();
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
