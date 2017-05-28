package com.aquariumsimulation.model.objects;

public abstract class AquariumObject {
    public abstract void update(float delta);

    private final int initialWidth;
    private final int initialHeight;
    private final int maxWidth;
    private final int maxHeight;

    private float width;
    private float height;
    private float x;
    private float y;

    private float rotation;

    private final String type;
    private final String iconName;

    public AquariumObject(int initialWidth, int initialHeight, int maxWidth, int maxHeight,
                          float x, float y, String type, String iconName) {
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.x = x;
        this.y = y;
        this.type = type;
        this.iconName = iconName;

        width = initialWidth;
        height = initialHeight;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getType() {
        return type;
    }

    public String getIconName() {
        return iconName;
    }

    @Override
    public String toString() {
        return type;
    }
}
