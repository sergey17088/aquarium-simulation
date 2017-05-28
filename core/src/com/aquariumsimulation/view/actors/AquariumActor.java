package com.aquariumsimulation.view.actors;

import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public final class AquariumActor extends Group {
    private final Texture background;

    private final List<AquariumObjectActor> aquariumObjectActors = new ArrayList<AquariumObjectActor>();

    public AquariumActor(GameScreen gameScreen) {
        background = gameScreen.getAquariumSimulation().getAssetManager().get("aquariumBackground.png");

        setPosition(GameScreen.AQUARIUM_X, GameScreen.AQUARIUM_Y);
        setSize(GameScreen.AQUARIUM_WORLD_WIDTH, GameScreen.AQUARIUM_WORLD_HEIGHT);
    }

    public List<AquariumObjectActor> getAquariumObjectActors() {
        return aquariumObjectActors;
    }

    public void addActor(AquariumObjectActor aquariumObjectActor) {
        aquariumObjectActors.add(aquariumObjectActor);
        super.addActor(aquariumObjectActor);
    }

    public void removeActor(AquariumObjectActor aquariumObjectActor) {
        aquariumObjectActors.remove(aquariumObjectActor);
        super.removeActor(aquariumObjectActor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
