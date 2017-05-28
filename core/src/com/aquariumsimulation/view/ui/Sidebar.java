package com.aquariumsimulation.view.ui;

import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public final class Sidebar extends Group {
    private final GameScreen gameScreen;
    private final TextureRegion sidebar;

    private static final int PADDING = 10;
    static final int CONTEXT_PANEL_WIDTH = GameScreen.SIDEBAR_WIDTH;
    static final int CONTEXT_PANEL_HEIGHT = 547;
    static final int CONTEXT_PANEL_X = 0;
    static final int CONTEXT_PANEL_Y = PADDING;
    static final int TAB_PANEL_WIDTH = GameScreen.SIDEBAR_WIDTH;
    static final int TAB_PANEL_HEIGHT = 141;
    static final int TAB_PANEL_X = 0;
    static final int TAB_PANEL_Y = GameScreen.SIDEBAR_HEIGHT - TAB_PANEL_HEIGHT - PADDING;

    private final ContextPanel contextPanel;
    private final TabPanel tabPanel;

    public Sidebar(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        final TextureAtlas textureAtlas = gameScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        sidebar = textureAtlas.findRegion("sidebar");

        setSize(GameScreen.SIDEBAR_WIDTH, GameScreen.SIDEBAR_HEIGHT);
        setPosition(GameScreen.SIDEBAR_X, GameScreen.SIDEBAR_Y);

        contextPanel = new ContextPanel(textureAtlas);
        tabPanel = new TabPanel(gameScreen, contextPanel);
        addActor(contextPanel);
        addActor(tabPanel);
    }

    public ContextPanel getContextPanel() {
        return contextPanel;
    }

    public TabPanel getTabPanel() {
        return tabPanel;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sidebar, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
