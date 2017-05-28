package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumObject;

public interface InformationAboutAquariumObject {
    AquariumObject getAquariumObject();

    String getIconName();

    String[][] getDescription();

    void update();
}
