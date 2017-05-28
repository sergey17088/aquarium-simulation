package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.dwellers.BigFish;
import com.aquariumsimulation.model.dwellers.Fish;
import com.aquariumsimulation.model.dwellers.SmallFish;
import com.aquariumsimulation.model.objects.Aquarium;
import com.aquariumsimulation.model.objects.AquariumDweller;
import com.aquariumsimulation.model.objects.AquariumObject;
import com.aquariumsimulation.view.actors.AquariumObjectActor;
import com.aquariumsimulation.view.screens.GameScreen;
import com.aquariumsimulation.view.ui.InformationAboutAquariumObjectPanel;

import java.util.*;

public final class Controller {
    private static final InformationAboutAquariumDwellerAtAdding[] INFORMATION_ABOUT_ALL_AQUARIUM_DWELLERS_AT_ADDING = {
            new InformationAboutAquariumDwellerAtAdding(BigFish.class),
            new InformationAboutAquariumDwellerAtAdding(Fish.class),
            new InformationAboutAquariumDwellerAtAdding(SmallFish.class)
    };

    public static InformationAboutAquariumDwellerAtAdding[] getInformationAboutAllAquariumDwellersAtAdding() {
        return INFORMATION_ABOUT_ALL_AQUARIUM_DWELLERS_AT_ADDING;
    }

    private final GameScreen gameScreen;
    private final Aquarium aquarium = new Aquarium(GameScreen.AQUARIUM_WORLD_WIDTH, GameScreen.AQUARIUM_WORLD_HEIGHT);

    private final Map<InformationAboutAquariumObject, InformationAboutAquariumObjectPanel> informationAboutAquariumObjects =
            new LinkedHashMap<InformationAboutAquariumObject, InformationAboutAquariumObjectPanel>();
    private InformationAboutAquariumObjectPanel currentInformationAboutAquariumObjectPanel;

    public Controller(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public InformationAboutAquariumObjectPanel getCurrentInformationAboutAquariumObjectPanel() {
        return currentInformationAboutAquariumObjectPanel;
    }

    public void update(float delta) {
        aquarium.update(delta);

        createAquariumObjects();
        destroyAquariumObjects();

        updateInformationAboutAquariumObjects();
    }

    private void createAquariumObjects() {
        if (aquarium.isAvailableAquariumObjectsForCreating()) {
            for (AquariumObject aquariumObject : aquarium.createAquariumObjects()) {
                gameScreen.getAquariumActor().addActor(new AquariumObjectActor(gameScreen, aquariumObject));
                if (aquariumObject instanceof AquariumDweller) {
                    final InformationAboutAquariumDweller informationAboutAquariumDweller =
                            new InformationAboutAquariumDweller((AquariumDweller) aquariumObject);
                    final InformationAboutAquariumObjectPanel informationAboutAquariumObjectPanel =
                            new InformationAboutAquariumObjectPanel(gameScreen, informationAboutAquariumDweller);
                    informationAboutAquariumObjects.put(informationAboutAquariumDweller,
                            informationAboutAquariumObjectPanel);

                    gameScreen.getSidebar().getTabPanel().getReviewContext().addActor(informationAboutAquariumObjectPanel);
                }
            }
            aquarium.clearAquariumObjectsForCreating();
        }
    }

    private void destroyAquariumObjects() {
        if (aquarium.isAvailableAquariumObjectsForDestroy()) {
            for (AquariumObject aquariumObject : aquarium.destroyAquariumObjects()) {
                for (AquariumObjectActor aquariumObjectActor : gameScreen.getAquariumActor().getAquariumObjectActors()) {
                    if (aquariumObjectActor.getAquariumObject() == aquariumObject) {
                        gameScreen.getAquariumActor().removeActor(aquariumObjectActor);
                        break;
                    }
                }

                for (InformationAboutAquariumObject informationAboutAquariumObject :
                        informationAboutAquariumObjects.keySet()) {
                    if (informationAboutAquariumObject.getAquariumObject() == aquariumObject) {
                        final InformationAboutAquariumObjectPanel informationAboutAquariumObjectPanel =
                                informationAboutAquariumObjects.remove(informationAboutAquariumObject);
                        if (currentInformationAboutAquariumObjectPanel == informationAboutAquariumObjectPanel) {
                            currentInformationAboutAquariumObjectPanel = null;
                        }
                        break;
                    }
                }
            }
            aquarium.clearAquariumObjectsForDestroy();
        }
    }

    private void updateInformationAboutAquariumObjects() {
        for (InformationAboutAquariumObject informationAboutAquariumObject : informationAboutAquariumObjects.keySet()) {
            informationAboutAquariumObject.update();
        }
    }

    public void createAquariumObject(InformationAboutAquariumObjectAtAdding informationAboutAquariumObjectAtAdding) {
        if (informationAboutAquariumObjectAtAdding instanceof InformationAboutAquariumDwellerAtAdding) {
            final InformationAboutAquariumDwellerAtAdding information =
                    ((InformationAboutAquariumDwellerAtAdding) informationAboutAquariumObjectAtAdding);
            aquarium.addAquariumDweller(information.getKind(), aquarium.getWidth() / 2, aquarium.getHeight() / 2,
                    information.getGender());
            information.clear();
        } else {
            aquarium.addAquariumObject(informationAboutAquariumObjectAtAdding.getKind(),
                    aquarium.getWidth() / 2, aquarium.getHeight() / 2);
        }
    }

    public void removeAquariumObject(AquariumObject aquariumObject) {
        aquarium.removeAquariumObject(aquariumObject);
    }

    public void selectAquariumObject(AquariumObject aquariumObject) {
        for (InformationAboutAquariumObject informationAboutAquariumObject : informationAboutAquariumObjects.keySet()) {
            if (informationAboutAquariumObject.getAquariumObject() == aquariumObject) {
                /*if (currentInformationAboutAquariumObjectPanel != null) {
                    gameScreen.getSidebar().getTabPanel().getReviewContext().addActor(currentInformationAboutAquariumObjectPanel);
                }

                currentInformationAboutAquariumObjectPanel =
                        informationAboutAquariumObjects.get(informationAboutAquariumObject);*/
                return;
            }
        }
    }
}
