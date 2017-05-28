package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumObjectImpl;

public interface InformationAboutAquariumObject {
    AquariumObjectImpl getAquariumObject();

    String getIconName();

    String[][] getDescription();

    void update();
}
