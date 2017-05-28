package com.aquariumsimulation.model.objects;

public interface ChangeableAquariumObject extends AquariumObject {
    int getInitialWidth();

    int getInitialHeight();

    int getMaxWidth();

    int getMaxHeight();

    float getRotation();

    void setRotation(float rotation);

    void setX(float x);

    void setY(float y);

    void setWidth(float width);

    void setHeight(float height);
}
