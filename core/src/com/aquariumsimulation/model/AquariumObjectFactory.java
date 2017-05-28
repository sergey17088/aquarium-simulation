package com.aquariumsimulation.model;

import com.aquariumsimulation.model.objects.AquariumObjectImpl;

public interface AquariumObjectFactory {
    AquariumObjectImpl create(float x, float y);
}
