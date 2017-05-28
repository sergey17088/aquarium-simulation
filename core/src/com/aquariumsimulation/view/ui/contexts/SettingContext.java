package com.aquariumsimulation.view.ui.contexts;

import com.aquariumsimulation.AquariumSimulation;
import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.screens.MenuScreen;
import com.aquariumsimulation.view.ui.ContextPanel;
import com.aquariumsimulation.view.ui.PositionHelper;
import com.aquariumsimulation.view.ui.buttons.ButtonWithThreeState;
import com.aquariumsimulation.view.ui.buttons.ButtonWithThreeStateAndText;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

public final class SettingContext extends Context {
    private final GameScreen gameScreen;
    private final AquariumSimulation aquariumSimulation;
    private final Skin skin;

    private static final int BUTTON_WIDTH = 225;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_X = ContextPanel.CONTEXT_WIDTH / 2;
    private static final int AMOUNT_OF_BUTTONS = 3;
    private static final int DISTANCE_BETWEEN_BUTTONS = 20;
    private static final String[] BUTTON_NAMES = {
            "New Game", "Main Menu", "Exit"
    };

    private final Map<String, ButtonWithThreeState> buttons =
            new HashMap<String, ButtonWithThreeState>(AMOUNT_OF_BUTTONS);

    public SettingContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.aquariumSimulation = gameScreen.getAquariumSimulation();
        final TextureAtlas textureAtlas = aquariumSimulation.getAssetManager().get("textures.atlas");
        skin = new Skin(Gdx.files.internal("styles.json"), textureAtlas);

        createButtons();
        setupButtons();
    }

    private void createButtons() {
        final float[] yCoordinates = PositionHelper.getCoordinatesForCentralLocationOfElements(getHeight(),
                BUTTON_HEIGHT, AMOUNT_OF_BUTTONS, DISTANCE_BETWEEN_BUTTONS);
        int counter = AMOUNT_OF_BUTTONS - 1;
        for (String buttonName : BUTTON_NAMES) {
            final ButtonWithThreeState button = createButton(yCoordinates[counter--], buttonName);
            buttons.put(buttonName, button);
            addActor(button);
        }
    }

    private ButtonWithThreeState createButton(float y, String text) {
        return new ButtonWithThreeStateAndText(BUTTON_X, y, BUTTON_WIDTH, BUTTON_HEIGHT, Align.center,
                text, skin, "button100", "label30");
    }

    private void setupButtons() {
        buttons.get("New Game").addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gameScreen.dispose();
                aquariumSimulation.setScreen(new GameScreen(aquariumSimulation));
            }
        });

        buttons.get("Main Menu").addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gameScreen.dispose();
                aquariumSimulation.setScreen(new MenuScreen(aquariumSimulation));
            }
        });

        buttons.get("Exit").addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Gdx.app.exit();
            }
        });
    }
}
