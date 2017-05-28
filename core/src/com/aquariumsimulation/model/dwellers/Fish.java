package com.aquariumsimulation.model.dwellers;

import com.aquariumsimulation.model.objects.Aquarium;
import com.aquariumsimulation.model.objects.EdibleAquariumDweller;
import com.aquariumsimulation.model.objects.Food;

import java.util.HashSet;
import java.util.Set;

public final class Fish extends EdibleAquariumDweller {
    private static final int INITIAL_WIDTH = 128;
    private static final int INITIAL_HEIGHT = 128;
    private static final int MAX_WIDTH = 256;
    private static final int MAX_HEIGHT = 256;

    private static final String TYPE = "Fish";
    private static final String ICON_NAME = "fish";

    private static final int MAX_HEALTH = 200;
    private static final int MAX_ENERGY = 300;
    private static final int MAX_SATIETY = 200;
    private static final int MAX_AGE = 600;

    private static final int SPEED_PER_SECOND = 200;
    private static final float COEFFICIENT_OF_GROWTH_PER_SECOND = 0.1f;
    private static final int MAX_SATURATION_PER_SECOND = 10;
    private static final int MIN_TIME_BETWEEN_REPRODUCTION = 60;
    private static final int DURATION_OF_REPRODUCTION = 6;

    private static final Set<Class<? extends Food>> RATION = new HashSet<Class<? extends Food>>();
    static {
        RATION.add(SmallFish.class);
    }

    public Fish(float x, float y, Gender gender, Aquarium aquarium) {
        super(INITIAL_WIDTH, INITIAL_HEIGHT, MAX_WIDTH, MAX_HEIGHT, x, y, TYPE, ICON_NAME,
                SPEED_PER_SECOND, COEFFICIENT_OF_GROWTH_PER_SECOND, MAX_HEALTH, MAX_ENERGY, MAX_SATIETY,
                MAX_AGE, MAX_SATURATION_PER_SECOND, MIN_TIME_BETWEEN_REPRODUCTION, DURATION_OF_REPRODUCTION,
                gender, RATION, aquarium);
    }
}
