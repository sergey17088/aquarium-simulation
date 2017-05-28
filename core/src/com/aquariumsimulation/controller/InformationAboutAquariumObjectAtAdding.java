package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumObject;

public interface InformationAboutAquariumObjectAtAdding {
    Class<? extends AquariumObject> getKind();

    String getIconName();

    String getDescription();
}
