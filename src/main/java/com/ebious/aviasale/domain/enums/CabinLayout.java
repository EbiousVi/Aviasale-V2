package com.ebious.aviasale.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum CabinLayout {
    ABC_773_ECONOMY(Arrays.asList("A", "B", "C", "AISLE", "D", "E", "F", "G", "AISLE", "H", "J", "K")),
    ABC_773_COMFORT(Arrays.asList("A", "C", "AISLE", "D", "E", "F", "G", "AISLE", "H", "K")),
    ABC_773_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "G", "AISLE", "H", "K")),

    ABC_763_ECONOMY(Arrays.asList("A", "B", "AISLE", "D", "E", "F", "AISLE", "G", "H")),
    ABC_763_BUSINESS(Arrays.asList("A", "B", "AISLE", "C", "F", "AISLE", "G", "H")),

    ABC_733_ECONOMY(Arrays.asList("A", "B", "C", "AISLE", "D", "E", "F")),
    ABC_733_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "F")),

    ABC_321_ECONOMY(Arrays.asList("A", "B", "C", "AISLE", "D", "E", "F")),
    ABC_321_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "F")),

    ABC_320_ECONOMY(Arrays.asList("A", "B", "C", "AISLE", "D", "E", "F")),
    ABC_320_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "F")),

    ABC_319_ECONOMY(Arrays.asList("A", "B", "C", "AISLE", "D", "E", "F")),
    ABC_319_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "F")),

    ABC_SU9_ECONOMY(Arrays.asList("A", "C", "AISLE", "D", "E", "F")),
    ABC_SU9_BUSINESS(Arrays.asList("A", "C", "AISLE", "D", "F")),

    ABC_CR2_ECONOMY(Arrays.asList("A", "B", "AISLE", "C", "D")),

    ABC_CN1_ECONOMY(Arrays.asList("A", "AISLE", "B"));

    public List<String> layout;

    CabinLayout(List<String> layout) {
        this.layout = layout;
    }
}
