package com.aquariumsimulation.model;

import com.aquariumsimulation.model.objects.AquariumObject;

public interface AquariumObjectFactory {
    AquariumObject create(float x, float y);
}
