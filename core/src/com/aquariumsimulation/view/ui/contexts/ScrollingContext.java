package com.aquariumsimulation.view.ui.contexts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class ScrollingContext extends Context {
    private final Table table = new Table();

    public ScrollingContext() {
        final ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setSize(getWidth(), getHeight());
        scrollPane.setPosition(0, 0);
        scrollPane.setFadeScrollBars(false);
        super.addActor(scrollPane);
    }

    @Override
    public void addActor(Actor actor) {
        table.row();
        table.add(actor);
    }
}