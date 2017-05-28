package com.aquariumsimulation.model.objects;

public abstract class AquariumObjectImpl implements ChangeableAquariumObject {
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

    public AquariumObjectImpl(int initialWidth, int initialHeight, int maxWidth, int maxHeight,
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

    @Override
    public int getInitialWidth() {
        return initialWidth;
    }

    @Override
    public int getInitialHeight() {
        return initialHeight;
    }

    @Override
    public int getMaxWidth() {
        return maxWidth;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIconName() {
        return iconName;
    }

    @Override
    public String toString() {
        return type;
    }
}
