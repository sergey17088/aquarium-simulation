package com.aquariumsimulation.view.ui;


import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.ui.buttons.ButtonWithFourState;
import com.aquariumsimulation.view.ui.contexts.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

public final class TabPanel extends Group {
    private final GameScreen gameScreen;
    private final ContextPanel contextPanel;
    private final Skin skin;
    private final TextureRegion tabPanel;

    private static final int PADDING_BOTTOM = 20;
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 100;
    private static final int AMOUNT_OF_BUTTONS = 4;
    private static final int BUTTON_Y = PADDING_BOTTOM + (Sidebar.TAB_PANEL_HEIGHT - PADDING_BOTTOM) / 2;
    private static final int DISTANCE_BETWEEN_BUTTONS = 8;
    private static final String[] BUTTON_NAMES = {
            "addingButton", "reviewButton", "informationButton", "settingButton"
    };

    private final Map<String, ButtonWithFourState> tabs = new HashMap<String, ButtonWithFourState>(AMOUNT_OF_BUTTONS);
    private ButtonWithFourState currentTab;
    private final Map<ButtonWithFourState, Context> contexts =
            new HashMap<ButtonWithFourState, Context>(AMOUNT_OF_BUTTONS);

    private final Context settingContext;
    private final Context reviewContext;
    private final Context addingContext;
    private final Context informationContext;

    public TabPanel(GameScreen gameScreen, ContextPanel contextPanel) {
        this.gameScreen = gameScreen;
        this.contextPanel = contextPanel;
        final TextureAtlas textureAtlas = gameScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        skin = new Skin(Gdx.files.internal("styles.json"), textureAtlas);
        tabPanel = textureAtlas.findRegion("tabPanel");

        setSize(Sidebar.TAB_PANEL_WIDTH, Sidebar.TAB_PANEL_HEIGHT);
        setPosition(Sidebar.TAB_PANEL_X, Sidebar.TAB_PANEL_Y);

        settingContext = new SettingContext(gameScreen);
        reviewContext = new ReviewContext(gameScreen);
        addingContext = new AddingContext(gameScreen);
        informationContext = new InformationContext(gameScreen);

        createButtons();
        addContexts();
        setup();
    }

    public Context getReviewContext() {
        return reviewContext;
    }

    private void createButtons() {
        final float[] xCoordinates = PositionHelper.getCoordinatesForCentralLocationOfElements(Sidebar.TAB_PANEL_WIDTH,
                BUTTON_WIDTH, AMOUNT_OF_BUTTONS, DISTANCE_BETWEEN_BUTTONS);
        int counter = 0;
        for (String buttonName : BUTTON_NAMES) {
            final ButtonWithFourState button = createButton(xCoordinates[counter++], buttonName);
            tabs.put(buttonName, button);
            addActor(button);
        }
    }

    private ButtonWithFourState createButton(float x, String styleName) {
        return new ButtonWithFourState(x, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, Align.center, skin,
                styleName, ((Sound) gameScreen.getAquariumSimulation().getAssetManager().get("clickSound.wav")));
    }

    private void addContexts() {
        contexts.put(tabs.get("settingButton"), settingContext);
        contexts.put(tabs.get("reviewButton"), reviewContext);
        contexts.put(tabs.get("addingButton"), addingContext);
        contexts.put(tabs.get("informationButton"), informationContext);
    }

    private void setup() {
        currentTab = tabs.get(BUTTON_NAMES[0]);
        currentTab.blockButton();
        contextPanel.setContext(contexts.get(currentTab));

        final EventListener eventListener = new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                final ButtonWithFourState newCurrentTab = ((ButtonWithFourState) event.getListenerActor());
                if (newCurrentTab != currentTab) {
                    currentTab.unblockButton();
                    currentTab = newCurrentTab;
                    currentTab.blockButton();
                    contextPanel.setContext(contexts.get(currentTab));
                }
            }
        };
        for (ButtonWithFourState buttonWithFourState : tabs.values()) {
            buttonWithFourState.addListener(eventListener);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tabPanel, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
