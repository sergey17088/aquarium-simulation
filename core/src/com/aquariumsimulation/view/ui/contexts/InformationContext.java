package com.aquariumsimulation.view.ui.contexts;

import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public final class InformationContext extends Context {
    private final GameScreen gameScreen;
    private final ScrollPane scrollPane = new ScrollPane(null);

    public InformationContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        scrollPane.setSize(getWidth(), getHeight());
        scrollPane.setPosition(0, 0);
        scrollPane.setFadeScrollBars(false);
        addActor(scrollPane);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        scrollPane.setWidget(gameScreen.getController().getCurrentInformationAboutAquariumObjectPanel());
    }
}
