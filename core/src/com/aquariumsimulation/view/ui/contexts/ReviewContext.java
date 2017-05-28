package com.aquariumsimulation.view.ui.contexts;

import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class ReviewContext extends Context {
    private final GameScreen gameScreen;
    private final Table table = new Table();
    private final ScrollPane scrollPane = new ScrollPane(table);

    public ReviewContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        table.setWidth(getWidth());
        scrollPane.setSize(getWidth(), getHeight());
        scrollPane.setPosition(0, 0);
        scrollPane.setFadeScrollBars(false);
        super.addActor(scrollPane);
    }

    @Override
    public void addActor(Actor actor) {
        table.add(actor);
        table.row();
    }
}
