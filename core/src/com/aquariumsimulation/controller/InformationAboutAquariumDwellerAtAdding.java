package com.aquariumsimulation.controller;

import com.aquariumsimulation.model.objects.AquariumDweller;
import com.aquariumsimulation.model.objects.Food;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class InformationAboutAquariumDwellerAtAdding implements InformationAboutAquariumObjectAtAdding {
    private static final Map<String, String> NEEDED_FIELDS = new LinkedHashMap<String, String>(15);

    static {
        addNeededFields();
    }

    private static void addNeededFields() {
        NEEDED_FIELDS.put("TYPE", "Type: ");

        NEEDED_FIELDS.put("INITIAL_WIDTH", "Initial Width: ");
        NEEDED_FIELDS.put("INITIAL_HEIGHT", "Initial Height: ");
        NEEDED_FIELDS.put("MAX_WIDTH", "Max Width: ");
        NEEDED_FIELDS.put("MAX_HEIGHT", "Max Height: ");

        NEEDED_FIELDS.put("MAX_HEALTH", "Max Health: ");
        NEEDED_FIELDS.put("MAX_ENERGY", "Max Energy: ");
        NEEDED_FIELDS.put("MAX_SATIETY", "Max Satiety: ");
        NEEDED_FIELDS.put("MAX_AGE", "Max Age: ");

        NEEDED_FIELDS.put("SPEED_PER_SECOND", "Speed per\nSecond: ");
        NEEDED_FIELDS.put("COEFFICIENT_OF_GROWTH_PER_SECOND", "Coefficient of\nGrowth per\nSecond: ");
        NEEDED_FIELDS.put("MAX_SATURATION_PER_SECOND", "Max Saturation\nper Second:\n");
        NEEDED_FIELDS.put("MIN_TIME_BETWEEN_REPRODUCTION", "Min Time Between\nReproduction:\n");
        NEEDED_FIELDS.put("DURATION_OF_REPRODUCTION", "Duration of\nReproduction:\n");
    }

    private final Class<? extends AquariumDweller> kind;
    private final String iconName;
    private final String description;

    private AquariumDweller.Gender gender;

    public InformationAboutAquariumDwellerAtAdding(Class<? extends AquariumDweller> kind) {
        this.kind = kind;

        try {
            iconName = initializeIconName();
            description = initializeDescription();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String initializeIconName() throws NoSuchFieldException, IllegalAccessException {
        final Field field = kind.getDeclaredField("ICON_NAME");
        field.setAccessible(true);
        return (String) field.get(null);
    }

    private String initializeDescription() throws NoSuchFieldException, IllegalAccessException {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> pair : NEEDED_FIELDS.entrySet()) {
            final Field field = kind.getDeclaredField(pair.getKey());
            field.setAccessible(true);

            stringBuilder.append(pair.getValue()).append(field.get(null)).append('\n');
        }

        stringBuilder.append("RATION:\n");
        final Field rationField = kind.getDeclaredField("RATION");
        rationField.setAccessible(true);
        @SuppressWarnings("unchecked")
        final Set<Class<? extends Food>> foodTypes = ((Set<Class<? extends Food>>) rationField.get(null));
        for (Class<? extends Food> foodType : foodTypes) {
            final Field typeField = foodType.getDeclaredField("TYPE");
            typeField.setAccessible(true);
            stringBuilder.append(typeField.get(null)).append(",\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }

    @Override
    public Class<? extends AquariumDweller> getKind() {
        return kind;
    }

    @Override
    public String getIconName() {
        return iconName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public AquariumDweller.Gender getGender() {
        return gender;
    }

    public void setGender(AquariumDweller.Gender gender) {
        this.gender = gender;
    }

    public void clear() {
        gender = null;
    }
}
