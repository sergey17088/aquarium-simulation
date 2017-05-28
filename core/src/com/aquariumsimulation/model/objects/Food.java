package com.aquariumsimulation.model.objects;

public interface Food extends AquariumObject {
    float toBeEaten(float biteForceOfAquariumDweller);
}
