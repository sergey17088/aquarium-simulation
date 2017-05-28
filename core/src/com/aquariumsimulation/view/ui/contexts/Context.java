package com.aquariumsimulation.view.ui.contexts;

import com.aquariumsimulation.view.ui.ContextPanel;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class Context extends Group {
    public Context() {
        setSize(ContextPanel.CONTEXT_WIDTH, ContextPanel.CONTEXT_HEIGHT);
        setPosition(ContextPanel.CONTEXT_X, ContextPanel.CONTEXT_Y);
    }
}
