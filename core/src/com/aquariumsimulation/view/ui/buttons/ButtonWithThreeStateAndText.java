package com.aquariumsimulation.view.ui.buttons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class ButtonWithThreeStateAndText extends ButtonWithThreeState {
    public ButtonWithThreeStateAndText(float x, float y, float width, float height, int align, String text,
                                       Skin skin, String styleName, String labelStyleName, Sound clickSound) {
        super(x, y, width, height, align, skin, styleName, clickSound);

        final Label label = new Label(text, skin.get(labelStyleName, Label.LabelStyle.class));
        label.setPosition(width / 2, height / 2, Align.center);
        addActor(label);
    }
}
