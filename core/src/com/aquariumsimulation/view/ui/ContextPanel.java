package com.aquariumsimulation.view.ui;

import com.aquariumsimulation.view.ui.contexts.Context;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public final class ContextPanel extends Group {
    private final TextureRegion contextPanel;

    private static final int TOP_PADDING = 56;
    private static final int BOTTOM_PADDING = 72;
    private static final int LEFT_PADDING = 32;
    private static final int RIGHT_PADDING = 68;
    public static final int CONTEXT_X = LEFT_PADDING;
    public static final int CONTEXT_Y = BOTTOM_PADDING;
    public static final int CONTEXT_WIDTH = Sidebar.CONTEXT_PANEL_WIDTH - LEFT_PADDING - RIGHT_PADDING;
    public static final int CONTEXT_HEIGHT = Sidebar.CONTEXT_PANEL_HEIGHT - TOP_PADDING - BOTTOM_PADDING;

    private Context context;

    public ContextPanel(TextureAtlas textureAtlas) {
        setSize(Sidebar.CONTEXT_PANEL_WIDTH, Sidebar.CONTEXT_PANEL_HEIGHT);
        setPosition(Sidebar.CONTEXT_PANEL_X, Sidebar.CONTEXT_PANEL_Y);

        contextPanel = textureAtlas.findRegion("contextPanel");
    }

    public void setContext(Context context) {
        if (this.context != null) {
            removeActor(this.context);
        }
        this.context = context;
        addActor(context);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(contextPanel, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}