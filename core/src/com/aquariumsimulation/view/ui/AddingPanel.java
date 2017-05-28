package com.aquariumsimulation.view.ui;

import com.aquariumsimulation.controller.InformationAboutAquariumDwellerAtAdding;
import com.aquariumsimulation.model.objects.AquariumDweller;
import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.ui.buttons.ButtonWithFourState;
import com.aquariumsimulation.view.ui.buttons.ButtonWithThreeStateAndText;
import com.aquariumsimulation.view.ui.contexts.AddingContext;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

public final class AddingPanel extends Group {
    private final GameScreen gameScreen;
    private final InformationAboutAquariumDwellerAtAdding informationAboutAquariumDwellerAtAdding;
    private final TextureAtlas textureAtlas;
    private final Skin skin;

    private static final int INDENT = 10;
    private static final int ICON_PANEL_WIDTH = 200;
    private static final int ICON_PANEL_HEIGHT = 200;
    private static final int ICON_PANEL_X = AddingContext.ADDING_PANEL_WIDTH / 2;
    private static final int ICON_PANEL_Y = AddingContext.ADDING_PANEL_HEIGHT - ICON_PANEL_HEIGHT / 2 - INDENT;
    private static final int PROPERTIES_LABEL_X = 0;
    private static final int BUTTON_WIDTH = 225;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_X = AddingContext.ADDING_PANEL_WIDTH / 2;
    private static final int BUTTON_Y = BUTTON_HEIGHT / 2 + INDENT;
    private static final int RADIO_BUTTON_WIDTH = 123;
    private static final int RADIO_BUTTON_HEIGHT = 128;
    private static final int RADIO_BUTTON_Y = BUTTON_Y + BUTTON_HEIGHT / 2 + INDENT + RADIO_BUTTON_HEIGHT / 2;
    private static final int ICON_ON_RADIO_BUTTON_SIZE = 90;

    private final ButtonWithFourState male, female;
    private Boolean isMale;

    private final class IconPanel extends Group {
        private static final int PADDING = 10;

        private final TextureRegion iconPanel;
        private final TextureRegion icon;

        IconPanel() {
            setSize(ICON_PANEL_WIDTH, ICON_PANEL_HEIGHT);
            setPosition(ICON_PANEL_X, ICON_PANEL_Y, Align.center);

            iconPanel = textureAtlas.findRegion("iconPanel");
            icon = textureAtlas.findRegion(informationAboutAquariumDwellerAtAdding.getIconName());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(iconPanel, getX(), getY(), getWidth(), getHeight());
            batch.draw(icon, getX() + PADDING, getY() + PADDING, getWidth() - PADDING * 2, getHeight() - PADDING * 2);
            super.draw(batch, parentAlpha);
        }
    }

    public AddingPanel(final GameScreen gameScreen,
                       InformationAboutAquariumDwellerAtAdding informationAboutAquariumDwellerAtAdding) {
        this.gameScreen = gameScreen;
        this.informationAboutAquariumDwellerAtAdding = informationAboutAquariumDwellerAtAdding;
        textureAtlas = gameScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        skin = new Skin(Gdx.files.internal("styles.json"), textureAtlas);

        setSize(AddingContext.ADDING_PANEL_WIDTH, AddingContext.ADDING_PANEL_HEIGHT);

        addActor(new IconPanel());
        createPropertiesLabel();
        final float[] yCoordinates = PositionHelper.getCoordinatesForCentralLocationOfElements(getWidth(),
                RADIO_BUTTON_WIDTH, 2, 25);
        male = createRadioButton("male", yCoordinates[0], true);
        female = createRadioButton("female", yCoordinates[1], false);
        createAddingButton();
    }

    private void createPropertiesLabel() {
        final Label label = new Label(informationAboutAquariumDwellerAtAdding.getDescription(), skin, "label30");
        label.setAlignment(Align.center);
        label.setPosition(PROPERTIES_LABEL_X, getHeight() - ICON_PANEL_HEIGHT - label.getHeight() - INDENT * 2);
        addActor(label);
    }

    private ButtonWithFourState createRadioButton(final String iconName, float x, final boolean isMale) {
        final ButtonWithFourState radioButton = new ButtonWithFourState(x, RADIO_BUTTON_Y, RADIO_BUTTON_WIDTH,
                RADIO_BUTTON_HEIGHT, Align.center, skin, "squareButton128");

        radioButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                AddingPanel.this.isMale = isMale;
                male.unblockButton();
                female.unblockButton();
                ((ButtonWithFourState) event.getListenerActor()).blockButton();
            }
        });

        radioButton.addActor(new Actor() {
            final TextureRegion icon = textureAtlas.findRegion(iconName);
            {
                setSize(ICON_ON_RADIO_BUTTON_SIZE, ICON_ON_RADIO_BUTTON_SIZE);
                setPosition(RADIO_BUTTON_WIDTH / 2, RADIO_BUTTON_HEIGHT / 2, Align.center);
            }

            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(icon, getX(), getY(), getWidth(), getHeight());
            }
        });
        addActor(radioButton);
        return radioButton;
    }

    private void createAddingButton() {
        final ButtonWithThreeStateAndText buttonWithThreeStateAndText =
                new ButtonWithThreeStateAndText(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, Align.center,
                        "Add", skin, "button100", "label30");
        addActor(buttonWithThreeStateAndText);

        buttonWithThreeStateAndText.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (isMale != null) {
                    informationAboutAquariumDwellerAtAdding.setGender(isMale ?
                            AquariumDweller.Gender.MALE: AquariumDweller.Gender.FEMALE);
                    gameScreen.getController().createAquariumObject(informationAboutAquariumDwellerAtAdding);
                    male.unblockButton();
                    female.unblockButton();
                    isMale = null;
                }
            }
        });
    }
}
