package com.aquariumsimulation.view.ui.contexts;

import com.aquariumsimulation.controller.Controller;
import com.aquariumsimulation.controller.InformationAboutAquariumDwellerAtAdding;
import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.ui.AddingPanel;
import com.aquariumsimulation.view.ui.ContextPanel;

public final class AddingContext extends ScrollingContext {
    public static final int ADDING_PANEL_WIDTH = ContextPanel.CONTEXT_WIDTH;
    public static final int ADDING_PANEL_HEIGHT = 1125;

    public AddingContext(GameScreen gameScreen) {
        for (InformationAboutAquariumDwellerAtAdding information :
                Controller.getInformationAboutAllAquariumDwellersAtAdding()) {
            addActor(new AddingPanel(gameScreen, information));
        }
    }
}
