package io.github.mekaij.rocket;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    private float width;
    private float height;

    public Camera(float width, float height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

}
