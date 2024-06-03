package com.eltonls.chess.view;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
    private static Camera instance;
    private final OrthographicCamera camera;

    private Camera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
    }

    public static Camera getInstance() {
        if(instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
