package com.aquariumsimulation.view.screens;

import com.aquariumsimulation.AquariumSimulation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class LoadingScreen extends ScreenAdapter {
    private final AquariumSimulation aquariumSimulation;
    private final AssetManager assetManager;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int PROGRESS_BAR_WIDTH = WIDTH - 50;
    private static final int PROGRESS_BAR_HEIGHT = 100;
    private static final int PROGRESS_BAR_X = (WIDTH - PROGRESS_BAR_WIDTH) / 2;
    private static final int PROGRESS_BAR_Y = (HEIGHT - PROGRESS_BAR_HEIGHT) / 2;
    private float progress;

    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(WIDTH, HEIGHT, camera);
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public LoadingScreen(AquariumSimulation aquariumSimulation) {
        this.aquariumSimulation = aquariumSimulation;
        assetManager = aquariumSimulation.getAssetManager();
    }

    @Override
    public void show() {
        assetManager.load("background.png", Texture.class);
        assetManager.load("aquariumBackground.png", Texture.class);
        final String textureAtlasName = "textures.atlas";
        assetManager.load(textureAtlasName, TextureAtlas.class);
        final BitmapFontLoader.BitmapFontParameter bitmapFontParameter = new BitmapFontLoader.BitmapFontParameter();
        bitmapFontParameter.atlasName = textureAtlasName;
        assetManager.load("font20.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("font22.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("font30.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("font50.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("clickSound.wav", Sound.class);
        assetManager.load("music.mp3", Music.class);

        viewport.apply(true);
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
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(PROGRESS_BAR_X, PROGRESS_BAR_Y, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(PROGRESS_BAR_X, PROGRESS_BAR_Y, PROGRESS_BAR_WIDTH * progress, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }

    private void update(float delta) {
        if (progress == 1) {
            dispose();
            aquariumSimulation.setScreen(new MenuScreen(aquariumSimulation));
        } else {
            assetManager.update();
            progress = assetManager.getProgress();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
