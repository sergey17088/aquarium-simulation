package com.aquariumsimulation.view.actors;

import com.aquariumsimulation.model.objects.AquariumObjectImpl;
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
    private final AquariumObjectImpl aquariumObjectImpl;
    private final Sprite sprite;

    public AquariumObjectActor(GameScreen gameScreen, AquariumObjectImpl aquariumObjectImpl) {
        this.gameScreen = gameScreen;
        this.aquariumObjectImpl = aquariumObjectImpl;

        final TextureAtlas textureAtlas = gameScreen.getAquariumSimulation().getAssetManager().get("textures.atlas");
        sprite = new Sprite(textureAtlas.findRegion(aquariumObjectImpl.getIconName()));

        updateSizes();
        updatePositions();

        addTapListener();
    }

    private void addTapListener() {
        addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gameScreen.getController().selectAquariumObject(aquariumObjectImpl);
            }
        });
    }

    private void updateSizes() {
        setSize(aquariumObjectImpl.getWidth(), aquariumObjectImpl.getHeight());
        sprite.setSize(getWidth(), getHeight());
    }

    private void updatePositions() {
        setPosition(aquariumObjectImpl.getX(), aquariumObjectImpl.getY(), Align.center);
        sprite.setPosition(getX(Align.bottomLeft), getY(Align.bottomLeft));
    }

    private void updateRotations() {
        setOrigin(Align.center);
        sprite.setOriginCenter();

        final float rotation = getAquariumObjectImpl().getRotation();

        setRotation(rotation);
        sprite.setRotation(rotation);

        if ((rotation > 90 && rotation <= 270 && !sprite.isFlipY()) ||
                ((rotation <= 90 || rotation > 270) && sprite.isFlipY())) {
            sprite.flip(false, true);
        }
    }

    public AquariumObjectImpl getAquariumObjectImpl() {
        return aquariumObjectImpl;
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
