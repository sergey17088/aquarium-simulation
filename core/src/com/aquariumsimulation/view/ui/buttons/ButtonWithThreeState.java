package com.aquariumsimulation.view.ui.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonWithThreeState extends Group {
    protected final ButtonWithThreeStateStyle style;
    protected Drawable background;

    public static class ButtonWithThreeStateStyle {
        protected Drawable normalButton, hoverButton, pressedButton;
    }

    public ButtonWithThreeState(float x, float y, float width, float height, int align, Skin skin, String styleName) {
        style = skin.get(styleName, getStyleClass());
        background = style.normalButton;

        setSize(width, height);
        setPosition(x, y, align);

        addListener(createInputListener());
    }

    protected Class<? extends ButtonWithThreeStateStyle> getStyleClass() {
        return ButtonWithThreeStateStyle.class;
    }

    protected InputListener createInputListener() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                background = style.pressedButton;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                background = style.normalButton;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (background != style.pressedButton) background = style.hoverButton;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                background = style.normalButton;
            }
        };
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}