package com.aquariumsimulation.view.screens;

import com.aquariumsimulation.AquariumSimulation;
import com.aquariumsimulation.controller.Controller;
import com.aquariumsimulation.view.actors.AquariumActor;
import com.aquariumsimulation.view.ui.Sidebar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class GameScreen extends ScreenAdapter {
    private final AquariumSimulation aquariumSimulation;
    private final AquariumActor aquariumActor;
    private final Sidebar sidebar;
    private final Controller controller = new Controller(this);

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    public static final int SIDEBAR_WIDTH = 427;
    public static final int SIDEBAR_HEIGHT = HEIGHT;
    public static final int SIDEBAR_X = WIDTH - SIDEBAR_WIDTH;
    public static final int SIDEBAR_Y = 0;
    private static final int AQUARIUM_WIDTH = WIDTH - SIDEBAR_WIDTH;
    private static final int AQUARIUM_HEIGHT = HEIGHT;
    private static final int AQUARIUM_SCALE = 10;
    public static final int AQUARIUM_WORLD_WIDTH = AQUARIUM_WIDTH * AQUARIUM_SCALE;
    public static final int AQUARIUM_WORLD_HEIGHT = AQUARIUM_HEIGHT * AQUARIUM_SCALE;
    public static final int AQUARIUM_X = 0;
    public static final int AQUARIUM_Y = 0;

    private static final float ZOOM_SPEED_FOR_MOUSE_WHEEL = 0.05f;
    private static final float ZOOM_SPEED_FOR_KEYBOARD = 0.01f;
    private static final float ZOOM_SPEED_FOR_GESTURES = 0.005f;
    private static final int SHIFT_OF_CAMERA_FOR_KEYBOARD = 100;

    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(WIDTH, HEIGHT, camera);
    private final OrthographicCamera aquariumCamera = new OrthographicCamera();
    private final Viewport aquariumViewport =
            new FitViewport(AQUARIUM_WORLD_WIDTH, AQUARIUM_WORLD_HEIGHT, aquariumCamera);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Stage stage = new Stage(viewport, spriteBatch);
    private final Stage aquariumStage = new Stage(aquariumViewport, spriteBatch);

    public GameScreen(AquariumSimulation aquariumSimulation) {
        this.aquariumSimulation = aquariumSimulation;
        aquariumActor = new AquariumActor(this);
        sidebar = new Sidebar(this);
    }

    public AquariumSimulation getAquariumSimulation() {
        return aquariumSimulation;
    }

    public AquariumActor getAquariumActor() {
        return aquariumActor;
    }

    public Controller getController() {
        return controller;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    @Override
    public void show() {
        viewport.apply(true);
        aquariumViewport.apply(true);

        stage.addActor(sidebar);
        aquariumStage.addActor(aquariumActor);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, aquariumStage));

        setupAquariumStage();
    }

    private void setupAquariumStage() {
        aquariumStage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                shiftCamera(-deltaX * 0.5f, -deltaY * 0.5f);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                if (distance - initialDistance > 0) zoomIn(ZOOM_SPEED_FOR_GESTURES);
                else zoomOut(ZOOM_SPEED_FOR_GESTURES);
            }
        });

        aquariumStage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (amount < 0) zoomIn(ZOOM_SPEED_FOR_MOUSE_WHEEL);
                else zoomOut(ZOOM_SPEED_FOR_MOUSE_WHEEL);
                return true;
            }
        });
    }

    private void shiftCamera(float deltaX, float deltaY) {
        aquariumCamera.position.set(getNewPositionOfCamera(aquariumCamera.position.x, deltaX, AQUARIUM_WORLD_WIDTH),
                getNewPositionOfCamera(aquariumCamera.position.y, deltaY, AQUARIUM_WORLD_HEIGHT), 0);
    }

    private float getNewPositionOfCamera(float position, float deltaPosition, int viewportSize) {
        if (position + viewportSize / 2f * aquariumCamera.zoom + deltaPosition > viewportSize) {
            return viewportSize - viewportSize / 2f * aquariumCamera.zoom;
        } else if (position - viewportSize / 2f * aquariumCamera.zoom + deltaPosition < 0) {
            return viewportSize / 2f * aquariumCamera.zoom;
        }
        return position + deltaPosition;
    }

    private void zoomIn(float deltaZoom) {
        if (aquariumCamera.zoom > 0.1) {
            if (aquariumCamera.zoom - deltaZoom > 0.1) {
                aquariumCamera.zoom -= deltaZoom;
            } else {
                aquariumCamera.zoom = 0.1f;
            }
//            final Vector2 position = aquariumViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
//            aquariumCamera.position.set(position.x, position.y, 0);
            shiftCamera(0, 0);
        }
    }

    private void zoomOut(float deltaZoom) {
        if (aquariumCamera.zoom < 1) {
            if (aquariumCamera.zoom + deltaZoom < 1) {
                aquariumCamera.zoom += deltaZoom;
            } else {
                aquariumCamera.zoom = 1;
            }
            shiftCamera(0, 0);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        aquariumViewport.update((int) viewport.project(new Vector2(AQUARIUM_WIDTH, AQUARIUM_HEIGHT)).x, height);
        aquariumViewport.setScreenPosition(viewport.getScreenX(), viewport.getScreenY());
    }

    @Override
    public void render(float delta) {
        clearScreen();
        draw();
        update(delta);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        viewport.apply();
        stage.draw();

        aquariumViewport.apply();
        aquariumStage.draw();
    }

    private void update(float delta) {
        controller.update(delta);
        stage.act(delta);
        aquariumStage.act(delta);

        handleKeyboardInput();
    }

    private void handleKeyboardInput() {
        float deltaX = 0, deltaY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            deltaX = -SHIFT_OF_CAMERA_FOR_KEYBOARD * aquariumCamera.zoom;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            deltaX = SHIFT_OF_CAMERA_FOR_KEYBOARD * aquariumCamera.zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            deltaY = SHIFT_OF_CAMERA_FOR_KEYBOARD * aquariumCamera.zoom;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            deltaY = -SHIFT_OF_CAMERA_FOR_KEYBOARD * aquariumCamera.zoom;
        }
        if (deltaX != 0 || deltaY != 0) {
            shiftCamera(deltaX, deltaY);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) {
            zoomIn(ZOOM_SPEED_FOR_KEYBOARD);
        } else if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            zoomOut(ZOOM_SPEED_FOR_KEYBOARD);
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
        aquariumStage.dispose();
    }
}
