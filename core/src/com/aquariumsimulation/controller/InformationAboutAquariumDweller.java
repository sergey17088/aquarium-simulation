package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumDweller;

public class InformationAboutAquariumDweller implements InformationAboutAquariumObject {
    private final AquariumDweller aquariumDweller;

    private static final String[][] TEMPLATE = {
            {"Type: ", "type"},
            {"Width: ", "width", " / ", "maxWidth"},
            {"Height: ", "height", " / ", "maxHeight"},
            {"Health: ", "health", " / ", "maxHealth"},
            {"Satiety: ", "satiety", " / ", "maxSatiety"},
            {"Energy: ", "energy", " / ", "maxEnergy"},
            {"Age: ", "age", " / ", "maxAge"}
    };

    private final String[][] description = new String[TEMPLATE.length][];

    public InformationAboutAquariumDweller(AquariumDweller aquariumDweller) {
        this.aquariumDweller = aquariumDweller;

        createDescription();
    }

    private void createDescription() {
        for (int i = 0; i < TEMPLATE.length; i++) {
            description[i] = new String[TEMPLATE[i].length];
            for (int j = 0; j < TEMPLATE[i].length; j++) {
                description[i][j] = TEMPLATE[i][j];
            }
        }

        description[0][1] = aquariumDweller.getType();
        description[1][3] = Integer.toString(aquariumDweller.getMaxWidth());
        description[2][3] = Integer.toString(aquariumDweller.getMaxHeight());
        description[3][3] = Integer.toString(aquariumDweller.getMaxHealth());
        description[4][3] = Integer.toString(aquariumDweller.getMaxSatiety());
        description[5][3] = Integer.toString(aquariumDweller.getMaxEnergy());
        description[6][3] = Integer.toString(aquariumDweller.getMaxAge());
    }

    @Override
    public void update() {
        description[1][1] = Integer.toString((int) aquariumDweller.getWidth());
        description[2][1] = Integer.toString((int) aquariumDweller.getHeight());
        description[3][1] = Integer.toString((int) aquariumDweller.getHealth());
        description[4][1] = Integer.toString((int) aquariumDweller.getSatiety());
        description[5][1] = Integer.toString((int) aquariumDweller.getEnergy());
        description[6][1] = Integer.toString((int) aquariumDweller.getAge());
    }

    @Override
    public AquariumDweller getAquariumObject() {
        return aquariumDweller;
    }

    @Override
    public String getIconName() {
        return aquariumDweller.getIconName();
    }

    @Override
    public String[][] getDescription() {
        return description;
    }
}
