package com.aquariumsimulation.model.objects;

import java.util.Set;

public abstract class AquariumDweller extends AquariumObject {
    public enum Gender {
        MALE, FEMALE
    }

    private enum Action {
        EAT, SLEEP, REPRODUCE
    }

    private static final int ENERGY_RECOVERY_IN_DREAM_PER_SECOND = 5;

    private final int maxHealth;
    private final int maxEnergy;
    private final int maxSatiety;
    private final int maxAge;

    private float health;
    private float energy;
    private float satiety;
    private float age = 0;

    private final int speedPerSecond;
    private final float coefficientOfGrowthPerSecond;
    private final int maxSaturationPerSecond;
    private final int minTimeBetweenReproduction;
    private final int durationOfReproduction;

    private float timeAfterLasReproduction = 0;

    private final Gender gender;
    private final Set<Class<? extends Food>> ration;

    private boolean isAlive = true;

    private final Aquarium aquarium;

    public AquariumDweller(int initialWidth, int initialHeight, int maxWidth, int maxHeight, float x, float y,
                           String type, String iconName, int speedPerSecond, float coefficientOfGrowthPerSecond,
                           int maxHealth, int maxEnergy, int maxSatiety, int maxAge, int maxSaturationPerSecond,
                           int minTimeBetweenReproduction, int durationOfReproduction, Gender gender,
                           Set<Class<? extends Food>> ration, Aquarium aquarium) {
        super(initialWidth, initialHeight, maxWidth, maxHeight, x, y, type, iconName);
        this.speedPerSecond = speedPerSecond;
        this.coefficientOfGrowthPerSecond = coefficientOfGrowthPerSecond;
        this.maxHealth = maxHealth;
        this.maxEnergy = maxEnergy;
        this.maxSatiety = maxSatiety;
        this.maxAge = maxAge;
        this.maxSaturationPerSecond = maxSaturationPerSecond;
        this.minTimeBetweenReproduction = minTimeBetweenReproduction;
        this.durationOfReproduction = durationOfReproduction;
        this.gender = gender;
        this.ration = ration;
        this.aquarium = aquarium;

        health = maxHealth;
        energy = maxEnergy;
        satiety = maxSatiety;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getMaxSatiety() {
        return maxSatiety;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public float getEnergy() {
        return energy;
    }

    public float getSatiety() {
        return satiety;
    }

    public float getAge() {
        return age;
    }

    @Override
    public void update(float delta) {
        updateIndicators(delta);
        if (isAlive) {
            growth(delta);
            calculateTheNeed();
        }
    }

    private void updateIndicators(float delta) {
        if (energy - delta > 0) {
            energy -= delta;
        } else {
            health += energy;
            energy = 0;
            health -= delta;
        }

        if (satiety - delta > 0) {
            satiety -= delta;
        } else {
            health += satiety;
            satiety = 0;
            health -= delta;
        }

        age += delta;
        timeAfterLasReproduction += delta;

        isAlive = health > 0 && age <= maxAge;
    }

    private void growth(float delta) {
        if (getWidth() < getMaxWidth() && getHeight() < getMaxHeight()) {
            final float newWidth = getWidth() + getInitialWidth() * coefficientOfGrowthPerSecond * delta;
            final float newHeight = getHeight() + getInitialHeight() * coefficientOfGrowthPerSecond * delta;
            setWidth(newWidth < getMaxWidth() ? newWidth : getMaxWidth());
            setHeight(newHeight < getMaxHeight() ? newHeight : getMaxHeight());
        }
    }

    private void calculateTheNeed() {

    }

    private void moveTo(float x, float y, float delta) {
        final float distance = getDistance(x, y);
        if (distance != 0) {
            setX(getX() + speedPerSecond * delta * (x - getX()) / distance);
            setY(getY() + speedPerSecond * delta * (y - getY()) / distance);
        }
    }

    private float getDistance(float x, float y) {
        return (float) Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
    }

    private void calculateRotation(float x, float y) {
        setRotation(90 - (float) Math.toDegrees(Math.atan((x - getX()) / (y - getY()))));
        if (y < getY() || (y == getY() && x < getX())) setRotation(getRotation() + 180);
    }
}
