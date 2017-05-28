package com.aquariumsimulation.model.objects;

import com.aquariumsimulation.model.AquariumDwellerFactory;
import com.aquariumsimulation.model.AquariumObjectFactory;
import com.aquariumsimulation.model.dwellers.BigFish;
import com.aquariumsimulation.model.dwellers.Fish;
import com.aquariumsimulation.model.dwellers.SmallFish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Aquarium {
    private final Map<Class<? extends AquariumObject>, AquariumObjectFactory> aquariumObjectFactories =
            new HashMap<Class<? extends AquariumObject>, AquariumObjectFactory>();
    {

    }

    private final Map<Class<? extends AquariumDweller>, AquariumDwellerFactory> aquariumDwellerFactories =
            new HashMap<Class<? extends AquariumDweller>, AquariumDwellerFactory>();
    {
        aquariumDwellerFactories.put(SmallFish.class, new AquariumDwellerFactory() {
            @Override
            public AquariumDweller create(float x, float y, AquariumDweller.Gender gender) {
                return new SmallFish(x, y, gender, Aquarium.this);
            }
        });
        aquariumDwellerFactories.put(Fish.class, new AquariumDwellerFactory() {
            @Override
            public AquariumDweller create(float x, float y, AquariumDweller.Gender gender) {
                return new Fish(x, y, gender, Aquarium.this);
            }
        });
        aquariumDwellerFactories.put(BigFish.class, new AquariumDwellerFactory() {
            @Override
            public AquariumDweller create(float x, float y, AquariumDweller.Gender gender) {
                return new BigFish(x, y, gender, Aquarium.this);
            }
        });
    }

    private final List<AquariumObject> aquariumObjects = new ArrayList<AquariumObject>();
    private final List<AquariumDweller> aquariumDwellers = new ArrayList<AquariumDweller>();
    private final List<Food> foods = new ArrayList<Food>();

    private final List<AquariumObject> aquariumObjectsForCreating = new ArrayList<AquariumObject>();
    private final List<AquariumObject> aquariumObjectsForDestroy = new ArrayList<AquariumObject>();

    private final int width;
    private final int height;

    public Aquarium(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(float delta) {
        for (AquariumObject aquariumObject : aquariumObjects) {
            aquariumObject.update(delta);
        }
    }

    public void addAquariumObject(Class<? extends AquariumObject> aquariumObjectType, float x, float y) {
        if (aquariumObjectFactories.containsKey(aquariumObjectType)) {
            aquariumObjectsForCreating.add(aquariumObjectFactories.get(aquariumObjectType).create(x, y));
        }
    }

    public void addAquariumDweller(Class<? extends AquariumDweller> aquariumDwellerType, float x, float y,
                                      AquariumDweller.Gender gender) {
        if (aquariumDwellerFactories.containsKey(aquariumDwellerType)) {
            aquariumObjectsForCreating.add(aquariumDwellerFactories.get(aquariumDwellerType).create(x, y, gender));
        }
    }

    public void removeAquariumObject(AquariumObject aquariumObject) {
        if (aquariumObjects.contains(aquariumObject)) {
            aquariumObjectsForDestroy.add(aquariumObject);
        }
    }

    public boolean isAvailableAquariumObjectsForCreating() {
        return aquariumObjectsForCreating.size() > 0;
    }

    public List<AquariumObject> createAquariumObjects() {
        for (AquariumObject aquariumObject : aquariumObjectsForCreating) {
            aquariumObjects.add(aquariumObject);
            checkAquariumObjectWhenAdding(aquariumObject);
        }
        return aquariumObjectsForCreating;
    }

    public void clearAquariumObjectsForCreating() {
        aquariumObjectsForCreating.clear();
    }

    public boolean isAvailableAquariumObjectsForDestroy() {
        return aquariumObjectsForDestroy.size() > 0;
    }

    public List<AquariumObject> destroyAquariumObjects() {
        for (AquariumObject aquariumObject : aquariumObjectsForDestroy) {
            aquariumObjects.remove(aquariumObject);
            checkAquariumObjectWhenRemoving(aquariumObject);
        }
        return aquariumObjectsForDestroy;
    }

    public void clearAquariumObjectsForDestroy() {
        aquariumObjectsForDestroy.clear();
    }

    private void checkAquariumObjectWhenAdding(AquariumObject aquariumObject) {
        if (aquariumObject instanceof AquariumDweller) {
            aquariumDwellers.add((AquariumDweller) aquariumObject);
        }
        if (aquariumObject instanceof Food) {
            foods.add((Food) aquariumObject);
        }
    }

    private void checkAquariumObjectWhenRemoving(AquariumObject aquariumObject) {
        if (aquariumObject instanceof AquariumDweller) {
            aquariumDwellers.remove(aquariumObject);
        }
        if (aquariumObject instanceof Food) {
            foods.remove(aquariumObject);
        }
    }
}
