package com.aquariumsimulation.view.ui;

public final class PositionHelper {
    private PositionHelper() {
    }

    public static float[] getCoordinatesForCentralLocationOfElements(float parentSize, float elementSize,
                                                                     int amountOfElements) {
        return getCoordinatesForCentralLocationOfElements(parentSize, elementSize, amountOfElements, 0);
    }

    public static float[] getCoordinatesForCentralLocationOfElements(float parentSize, float elementSize,
                                                                     int amountOfElements, float distanceBetweenElements) {
        return getCoordinatesForCentralLocationOfElements(parentSize, elementSize,
                amountOfElements, distanceBetweenElements, 0, 0);
    }

    public static float[] getCoordinatesForCentralLocationOfElements(float parentSize, float elementSize,
                                                                     int amountOfElements, float distanceBetweenElements,
                                                                     int indentFromBeginning, int indentFromEnd) {
        final float[] coordinates = new float[amountOfElements];
        final float elementsSize = elementSize * amountOfElements + distanceBetweenElements * (amountOfElements - 1) +
                indentFromBeginning + indentFromEnd;
        final float initialPosition = (parentSize - elementsSize + elementSize) / 2 + indentFromBeginning;
        for (int i = 0; i < amountOfElements; i++) {
            coordinates[i] = initialPosition + i * (elementSize + distanceBetweenElements);
        }
        return coordinates;
    }
}
