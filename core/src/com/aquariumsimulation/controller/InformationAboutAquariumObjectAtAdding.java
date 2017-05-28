package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumObjectImpl;

public interface InformationAboutAquariumObjectAtAdding {
    Class<? extends AquariumObjectImpl> getKind();

    String getIconName();

    String getDescription();
}
