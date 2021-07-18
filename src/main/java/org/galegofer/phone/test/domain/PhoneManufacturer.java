package org.galegofer.phone.test.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

public class PhoneManufacturer {
    public interface PhoneInformation {
        int getId();

        String getManufactureName();

        String getModelName();
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum Samsung implements PhoneInformation {
        GALAXY_S9(1, "Samsung", "Galaxy S9"),
        GALAXY_S8(2, "Samsung", "Galaxy S8"),
        GALAXY_S7(3, "Samsung", "Galaxy S7");

        int id;
        String manufactureName;
        String modelName;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum Motorola implements PhoneInformation {
        NEXUS_S6(4, "Motorola", "Nexus S6");

        int id;
        String manufactureName;
        String modelName;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum LG implements PhoneInformation {
        NEXUS_5X(5, "LG", "Nexus 5X");

        int id;
        String manufactureName;
        String modelName;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum Huawei implements PhoneInformation {
        HONOR_7X(6, "Huawei", "Honor 7X");

        int id;
        String manufactureName;
        String modelName;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum Apple implements PhoneInformation {
        IPHONE_X(7, "Apple", "iPhone X"),
        IPHONE_8(8, "Apple", "iPhone 8"),
        IPHONE_4S(9, "Apple", "iPhone 4S");

        int id;
        String manufactureName;
        String modelName;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = PRIVATE)
    public enum Nokia implements PhoneInformation {
        _3310(10, "Nokia", "3310");

        int id;
        String manufactureName;
        String modelName;
    }
}
