package com.aquariumsimulation.model.objects;

public interface AquariumObject {
    void update(float delta);

    float getWidth();

    float getHeight();

    float getX();

    float getY();

    String getType();

    String getIconName();

    boolean isAvailable();

    void setAvailable(boolean isAvailable);
}
