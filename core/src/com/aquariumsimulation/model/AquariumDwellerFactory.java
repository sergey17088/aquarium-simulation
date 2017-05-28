package com.aquariumsimulation.model;

import com.aquariumsimulation.model.objects.AquariumDweller;

public interface AquariumDwellerFactory {
    AquariumDweller create(float x, float y, AquariumDweller.Gender gender);
}
