package com.example.robert.petmatch.models;

import java.io.Serializable;

/**
 * Created by Robert on 12-09-15.
 */
public class Pet implements Serializable {
    private String name;
    private String family;
    private String description;
    private String image;

    public Pet(String name, String family, String description, String image) {
        this.name = name;
        this.family = family;
        this.image = image;

        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFamily() {
        return family;
    }

    public String getImage() {
        return image;
    }
}
