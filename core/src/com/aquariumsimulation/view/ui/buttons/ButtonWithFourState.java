package com.aquariumsimulation.view.ui.buttons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonWithFourState extends ButtonWithThreeState {
    private final ButtonWithFourStateStyle style;

    public static class ButtonWithFourStateStyle extends ButtonWithThreeStateStyle {
        private Drawable blockedButton;
    }

    public ButtonWithFourState(float x, float y, float width, float height, int align, Skin skin,
                               String styleName, Sound clickSound) {
        super(x, y, width, height, align, skin, styleName, clickSound);
        style = ((ButtonWithFourStateStyle) super.style);
    }

    @Override
    protected Class<? extends ButtonWithThreeStateStyle> getStyleClass() {
        return ButtonWithFourStateStyle.class;
    }

    @Override
    protected InputListener createInputListener() {
        final InputListener inputListener = super.createInputListener();
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return background != style.blockedButton && inputListener.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (background != style.blockedButton) inputListener.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (background != style.blockedButton) inputListener.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (background != style.blockedButton) inputListener.exit(event, x, y, pointer, toActor);
            }
        };
    }

    public void blockButton() {
        background = style.blockedButton;
    }

    public void unblockButton() {
        background = style.normalButton;
    }
}
