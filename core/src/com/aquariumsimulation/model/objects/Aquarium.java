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
    private final Map<Class<? extends AquariumObjectImpl>, AquariumObjectFactory> aquariumObjectFactories =
            new HashMap<Class<? extends AquariumObjectImpl>, AquariumObjectFactory>();
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

    private final List<AquariumObjectImpl> aquariumObjectImpls = new ArrayList<AquariumObjectImpl>();
    private final List<AquariumDweller> aquariumDwellers = new ArrayList<AquariumDweller>();
    private final List<Food> foods = new ArrayList<Food>();

    private final List<AquariumObjectImpl> aquariumObjectsForCreatingImpl = new ArrayList<AquariumObjectImpl>();
    private final List<AquariumObjectImpl> aquariumObjectsForDestroyImpl = new ArrayList<AquariumObjectImpl>();

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

    public List<AquariumDweller> getAquariumDwellers() {
        return aquariumDwellers;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void update(float delta) {
        for (AquariumObjectImpl aquariumObjectImpl : aquariumObjectImpls) {
            aquariumObjectImpl.update(delta);
        }
    }

    public void addAquariumObject(Class<? extends AquariumObjectImpl> aquariumObjectType, float x, float y) {
        if (aquariumObjectFactories.containsKey(aquariumObjectType)) {
            aquariumObjectsForCreatingImpl.add(aquariumObjectFactories.get(aquariumObjectType).create(x, y));
        }
    }

    public void addAquariumDweller(Class<? extends AquariumDweller> aquariumDwellerType, float x, float y,
                                   AquariumDweller.Gender gender) {
        if (aquariumDwellerFactories.containsKey(aquariumDwellerType)) {
            aquariumObjectsForCreatingImpl.add(aquariumDwellerFactories.get(aquariumDwellerType).create(x, y, gender));
        }
    }

    public void removeAquariumObject(AquariumObjectImpl aquariumObjectImpl) {
        if (aquariumObjectImpls.contains(aquariumObjectImpl)) {
            aquariumObjectsForDestroyImpl.add(aquariumObjectImpl);
        }
    }

    public boolean isAvailableAquariumObjectsForCreating() {
        return aquariumObjectsForCreatingImpl.size() > 0;
    }

    public List<AquariumObjectImpl> createAquariumObjects() {
        for (AquariumObjectImpl aquariumObjectImpl : aquariumObjectsForCreatingImpl) {
            aquariumObjectImpls.add(aquariumObjectImpl);
            checkAquariumObjectWhenAdding(aquariumObjectImpl);
        }
        return aquariumObjectsForCreatingImpl;
    }

    public void clearAquariumObjectsForCreating() {
        aquariumObjectsForCreatingImpl.clear();
    }

    public boolean isAvailableAquariumObjectsForDestroy() {
        return aquariumObjectsForDestroyImpl.size() > 0;
    }

    public List<AquariumObjectImpl> destroyAquariumObjects() {
        for (AquariumObjectImpl aquariumObjectImpl : aquariumObjectsForDestroyImpl) {
            aquariumObjectImpls.remove(aquariumObjectImpl);
            checkAquariumObjectWhenRemoving(aquariumObjectImpl);
        }
        return aquariumObjectsForDestroyImpl;
    }

    public void clearAquariumObjectsForDestroy() {
        aquariumObjectsForDestroyImpl.clear();
    }

    private void checkAquariumObjectWhenAdding(AquariumObjectImpl aquariumObjectImpl) {
        if (aquariumObjectImpl instanceof AquariumDweller) {
            aquariumDwellers.add((AquariumDweller) aquariumObjectImpl);
        }
        if (aquariumObjectImpl instanceof Food) {
            foods.add((Food) aquariumObjectImpl);
        }
    }

    private void checkAquariumObjectWhenRemoving(AquariumObjectImpl aquariumObjectImpl) {
        if (aquariumObjectImpl instanceof AquariumDweller) {
            aquariumDwellers.remove(aquariumObjectImpl);
        }
        if (aquariumObjectImpl instanceof Food) {
            foods.remove(aquariumObjectImpl);
        }
    }
}
