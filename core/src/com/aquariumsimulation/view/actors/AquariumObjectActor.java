package com.aquariumsimulation.view.actors;

import com.aquariumsimulation.model.objects.AquariumObject;
import com.aquariumsimulation.view.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

public class AquariumObjectActor extends Actor {
    private final GameScreen gameScreen;
    private final AquariumObject aquariumObject;
    private final Sprite sprite;

    public AquariumObjectActor(GameScreen gameScreen, AquariumObject aquariumObject) {
        this.gameScreen = gameScreen;
        this.aquariumObject = aquariumObject;

        final TextureAtlas textureAtlas = gameScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        sprite = new Sprite(textureAtlas.findRegion(aquariumObject.getIconName()));

        updateSizes();
        updatePositions();

        addTapListener();

        setDebug(true);
    }

    private void addTapListener() {
        addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gameScreen.getController().selectAquariumObject(aquariumObject);
            }
        });
    }

    private void updateSizes() {
        setSize(aquariumObject.getWidth(), aquariumObject.getHeight());
        sprite.setSize(getWidth(), getHeight());
    }

    private void updatePositions() {
        setPosition(aquariumObject.getX(), aquariumObject.getY(), Align.center);
        sprite.setPosition(getX(Align.bottomLeft), getY(Align.bottomLeft));
    }

    private void updateRotations() {
        setOrigin(Align.center);
        sprite.setOriginCenter();

        final float rotation = getAquariumObject().getRotation();

        setRotation(rotation);
        sprite.setRotation(rotation);

        if ((rotation > 90 && rotation <= 270 && !sprite.isFlipY()) ||
                ((rotation <= 90 || rotation > 270) && sprite.isFlipY())) {
            sprite.flip(false, true);
        }
    }

    public AquariumObject getAquariumObject() {
        return aquariumObject;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        updateSizes();
        updatePositions();
        updateRotations();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
}
