package com.aquariumsimulation.view.ui;

import com.aquariumsimulation.controller.InformationAboutAquariumObject;
import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public final class InformationAboutAquariumObjectPanel extends Table {
    private final GameScreen gameScreen;
    private final TextureAtlas textureAtlas;
    private final Skin skin;

    private final Label[][] labels;

    private final InformationAboutAquariumObject informationAboutAquariumObject;

    public InformationAboutAquariumObjectPanel(GameScreen gameScreen,
                                               InformationAboutAquariumObject informationAboutAquariumObject) {
        this.gameScreen = gameScreen;
        final AssetManager assetManager = gameScreen.getAquariumSimulation().getAssetManager();
        textureAtlas = assetManager.get("textures.atlas");
        this.informationAboutAquariumObject = informationAboutAquariumObject;
        this.skin = new Skin(Gdx.files.internal("styles.json"), textureAtlas);
        labels = new Label[informationAboutAquariumObject.getDescription().length][];

        createTable();
    }

    private void createTable() {
        top();
        pad(5);

        createIconPanel();
        createLabels();
    }

    private void createIconPanel() {
        row().colspan(4).padBottom(15);
        add(new Table() {
            {
                pad(10);

                add(new Actor() {
                    final TextureRegion background =
                            textureAtlas.findRegion(informationAboutAquariumObject.getIconName());

                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        batch.draw(background, getX(), getY(), getWidth(), getHeight());
                    }
                }).grow();
            }

            final TextureRegion background = textureAtlas.findRegion("iconPanel");

            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(background, getX(), getY(), getWidth(), getHeight());
                super.draw(batch, parentAlpha);
            }
        }).size(200);
    }

    private void createLabels() {
        row().colspan(2);
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label[informationAboutAquariumObject.getDescription()[i].length];
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j] = new Label(null, skin, "label22");
                add(labels[i][j]);
            }
            row();
        }

    }

    @Override
    public void act(float delta) {
        updateLabels();
        super.act(delta);
    }

    private void updateLabels() {
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j].setText(informationAboutAquariumObject.getDescription()[i][j]);
            }
        }
    }
}
