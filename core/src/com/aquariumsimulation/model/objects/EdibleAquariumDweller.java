package com.aquariumsimulation.model.objects;

import java.util.Set;

public abstract class EdibleAquariumDweller extends AquariumDweller implements Food {
    public EdibleAquariumDweller(int initialWidth, int initialHeight, int maxWidth, int maxHeight, float x, float y,
                                 String type, String iconName, int speedPerSecond, float coefficientOfGrowthPerSecond,
                                 int maxHealth, int maxEnergy, int maxSatiety, int maxAge, int maxSaturationPerSecond,
                                 int minTimeBetweenReproduction, int durationOfReproduction, Gender gender,
                                 Set<Class<? extends Food>> ration, Aquarium aquarium) {
        super(initialWidth, initialHeight, maxWidth, maxHeight, x, y, type, iconName, speedPerSecond,
                coefficientOfGrowthPerSecond, maxHealth, maxEnergy, maxSatiety, maxAge, maxSaturationPerSecond,
                minTimeBetweenReproduction, durationOfReproduction, gender, ration, aquarium);
    }

    @Override
    public float toBeEaten(int biteForceOfAquariumDweller) {
        final float lossOfHealth;
        if (getHealth() > biteForceOfAquariumDweller) {
            setHealth(getHealth() - biteForceOfAquariumDweller);
            lossOfHealth = biteForceOfAquariumDweller;
        } else {
            lossOfHealth = getHealth();
            setHealth(0);
            setAlive(false);
        }
        return lossOfHealth;
    }
}
