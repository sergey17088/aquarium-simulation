package com.aquariumsimulation.view.screens;

import com.aquariumsimulation.AquariumSimulation;
import com.aquariumsimulation.view.ui.MainMenuPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class MenuScreen extends ScreenAdapter {
    private final AquariumSimulation aquariumSimulation;
    private final Texture background;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;                       

    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(WIDTH, HEIGHT, camera);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Stage stage = new Stage(viewport, spriteBatch);

    public MenuScreen(AquariumSimulation aquariumSimulation) {
        this.aquariumSimulation = aquariumSimulation;
        background = aquariumSimulation.getAssetManager().get("background.png");
    }

    public AquariumSimulation getAquariumSimulation() {
        return aquariumSimulation;
    }

    @Override
    public void show() {
        viewport.apply(true);

        stage.addActor(new MainMenuPanel(this));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, WIDTH, HEIGHT);
        spriteBatch.end();

        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }
}
