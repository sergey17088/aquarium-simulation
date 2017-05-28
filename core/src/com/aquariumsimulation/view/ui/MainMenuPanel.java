package com.aquariumsimulation.view.ui;

import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.screens.MenuScreen;
import com.aquariumsimulation.view.ui.buttons.ButtonWithThreeState;
import com.aquariumsimulation.view.ui.buttons.ButtonWithThreeStateAndText;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

public final class MainMenuPanel extends Group {
    private final MenuScreen menuScreen;
    private final Skin skin;
    private final TextureRegion mainMenuPanel;

    private static final int WIDTH = 927;
    private static final int HEIGHT = 655;
    private static final int X = MenuScreen.WIDTH / 2;
    private static final int Y = MenuScreen.HEIGHT / 2;
    private static final int PADDING_TOP = 89;
    private static final int PADDING_BOTTOM = 117;
    private static final int PADDING_LEFT = 50;
    private static final int PADDING_RIGHT = 75;
    private static final int BUTTON_WIDTH = 427;
    private static final int BUTTON_HEIGHT = 190;
    private static final int BUTTON_X = WorkingSpace.WIDTH / 2;
    private static final int DISTANCE_BETWEEN_BUTTONS = 25;
    private static final int AMOUNT_OF_BUTTONS = 2;
    private static final String[] BUTTON_NAMES = {
            "New Game", "Exit"
    };

    private final Map<String, ButtonWithThreeState> buttons =
            new HashMap<String, ButtonWithThreeState>(AMOUNT_OF_BUTTONS);

    private final class WorkingSpace extends Group {
        private static final int X = PADDING_RIGHT;
        private static final int Y = PADDING_BOTTOM;
        private static final int WIDTH = MainMenuPanel.WIDTH - PADDING_LEFT - PADDING_RIGHT;
        private static final int HEIGHT = MainMenuPanel.HEIGHT - PADDING_TOP - PADDING_BOTTOM;

        WorkingSpace() {
            setSize(WIDTH, HEIGHT);
            setPosition(X, Y);

            createButtons();
            setupButtons();
        }

        void createButtons() {
            final float[] yCoordinates = PositionHelper.getCoordinatesForCentralLocationOfElements(HEIGHT,
                    BUTTON_HEIGHT, AMOUNT_OF_BUTTONS, DISTANCE_BETWEEN_BUTTONS);
            int counter = AMOUNT_OF_BUTTONS - 1;
            for (String buttonName : BUTTON_NAMES) {
                final ButtonWithThreeState button = createButton(yCoordinates[counter--], buttonName);
                buttons.put(buttonName, button);
                addActor(button);
            }
        }

        ButtonWithThreeState createButton(float y, String text) {
            return new ButtonWithThreeStateAndText(BUTTON_X, y, BUTTON_WIDTH, BUTTON_HEIGHT, Align.center,
                    text, skin, "button190", "label50");
        }

        void setupButtons() {
            buttons.get("New Game").addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    menuScreen.dispose();
                    menuScreen.getAquariumSimulation().setScreen(new GameScreen(menuScreen.getAquariumSimulation()));
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

    public MainMenuPanel(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
        TextureAtlas textureAtlas = menuScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        skin = new Skin(Gdx.files.internal("styles.json"), textureAtlas);
        mainMenuPanel = textureAtlas.findRegion("mainMenuPanel");

        setSize(WIDTH, HEIGHT);
        setPosition(X, Y, Align.center);

        addActor(new WorkingSpace());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mainMenuPanel, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
