package com.aquariumsimulation.model.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AquariumDweller extends AquariumObjectImpl {
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
    private float durationOfCurrentReproduction = 0;

    private final Gender gender;
    private Action action;
    private final Set<Class<? extends Food>> ration;

    private boolean isAlive = true;

    private final List<Food> availableFoods = new ArrayList<Food>();
    private final List<AquariumDweller> potentialPartners = new ArrayList<AquariumDweller>();
    private AquariumDweller partner;

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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
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
    public boolean isAvailable() {
        return isAlive;
    }

    @Override
    public void setAvailable(boolean isAvailable) {
        isAlive = isAvailable;
    }

    @Override
    public void update(float delta) {
        updateIndicators(delta);
        if (isAlive) {
            growth(delta);
            if (action == null) calculateAction();
            action(delta);
        } else {
            aquarium.removeAquariumObject(this);
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

    private void calculateAction() {
        final float coefficientOfEnergy = energy / maxEnergy;
        final float coefficientOfSatiety = satiety / maxSatiety;

        searchAvailableFoods();
        final int amountOfAvailableFoods = availableFoods.size();

        final int amountOfPotentialPartners;
        if (timeAfterLasReproduction < minTimeBetweenReproduction) {
            amountOfPotentialPartners = 0;
        } else {
            searchPotentialPartners();
            amountOfPotentialPartners = potentialPartners.size();
        }

        if (amountOfPotentialPartners > 0 && coefficientOfSatiety > 0.5 &&
                coefficientOfEnergy > 0.5) {
            action = Action.REPRODUCE;
        } else if (coefficientOfSatiety <= coefficientOfEnergy && amountOfAvailableFoods > 0) {
            action = Action.EAT;
        } else {
            action = Action.SLEEP;
        }
    }

    private void searchAvailableFoods() {
        availableFoods.clear();
        for (Food food : aquarium.getFoods()) {
            if (food.isAvailable() && ration.contains(food.getClass()) && food != this) {
                availableFoods.add(food);
            }
        }
    }

    private void searchPotentialPartners() {
        potentialPartners.clear();
        for (AquariumDweller aquariumDweller : aquarium.getAquariumDwellers()) {
            if (aquariumDweller.isAlive && aquariumDweller.getClass() == getClass() &&
                    aquariumDweller.gender != gender && aquariumDweller.partner == null &&
                    aquariumDweller.timeAfterLasReproduction >=
                            aquariumDweller.minTimeBetweenReproduction) {
                potentialPartners.add(aquariumDweller);
            }
        }
    }

    private void action(float delta) {
        switch (action) {
            case EAT:
                eat(delta);
                break;
            case SLEEP:
                sleep(delta);
                break;
            case REPRODUCE:
                reproduce(delta);
                break;
        }
    }

    private void eat(float delta) {
        Food nearestFood = null;
        for (Food availableFood : availableFoods) {
            if (nearestFood == null || getDistance(nearestFood) > getDistance(availableFood)) {
                nearestFood = availableFood;
            }
        }

        if (nearestFood != null) {
            if (getDistance(nearestFood) > (getWidth() + nearestFood.getWidth()) / 2) {
                moveTo(nearestFood, delta);
                calculateRotation(nearestFood);
            } else {
                final float saturation = nearestFood.toBeEaten(maxSaturationPerSecond * delta);

                if (satiety + saturation > maxSatiety) satiety = maxSatiety;
                else satiety += saturation;

                if (health + saturation > maxHealth) health = maxHealth;
                else health += saturation;

                if (satiety / maxSatiety > 0.5) action = null;
            }
        }
    }

    private void sleep(float delta) {
        final float energyChange = ENERGY_RECOVERY_IN_DREAM_PER_SECOND * delta;
        
        if (energy + energyChange > maxEnergy) {
            energy = maxEnergy;
            action = null;
        } else {
            energy += energyChange;
        }

        if (health + energyChange > maxHealth) health = maxHealth;
        else health += energyChange;

        if (energy / maxEnergy > 0.5) {
            action = null;
            setRotation(0);
        }
    }

    private void reproduce(float delta) {
        if (partner == null) {
            for (AquariumDweller potentialPartner : potentialPartners) {
                if (partner == null || getDistance(partner) >
                        getDistance(potentialPartner)) {
                    partner = potentialPartner;
                    partner.partner = this;
                    partner.action = Action.REPRODUCE;
                }
            }
        } else {
            if (getDistance(partner) > (getWidth() + partner.getWidth()) / 2) {
                moveTo(partner, delta);
                calculateRotation(partner);
            } else {
                if ((timeAfterLasReproduction += delta) >= durationOfCurrentReproduction) {
                    final float x, y;
                    if (gender == Gender.FEMALE) {
                        x = getX();
                        y = getY();
                    } else {
                        x = partner.getX();
                        y = partner.getY();
                    }

                    aquarium.addAquariumDweller(getClass(), x, y,
                            Math.random() > 0.5 ? Gender.MALE : Gender.FEMALE);
                    timeAfterLasReproduction = partner.timeAfterLasReproduction = 0;
                    action = partner.action = null;
                    partner.partner = partner = null;
                    setRotation(0);
                }
            }
        }
    }

    private float getDistance(float x, float y) {
        return (float) Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
    }

    private float getDistance(AquariumObject aquariumObject) {
        return getDistance(aquariumObject.getX(), aquariumObject.getY());
    }

    private void moveTo(float x, float y, float delta) {
        final float distance = getDistance(x, y);
        if (distance != 0) {
            setX(getX() + speedPerSecond * delta * (x - getX()) / distance);
            setY(getY() + speedPerSecond * delta * (y - getY()) / distance);
        }
    }

    private void moveTo(AquariumObject aquariumObject, float delta) {
        moveTo(aquariumObject.getX(), aquariumObject.getY(), delta);
    }

    private void calculateRotation(float x, float y) {
        setRotation(90 - (float) Math.toDegrees(Math.atan((x - getX()) / (y - getY()))));
        if (y < getY() || (y == getY() && x < getX())) setRotation(getRotation() + 180);
    }

    private void calculateRotation(AquariumObject aquariumObject) {
        calculateRotation(aquariumObject.getX(), aquariumObject.getY());
    }
}
