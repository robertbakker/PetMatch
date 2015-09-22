package com.example.robert.petmatch.models;

import java.io.Serializable;

/**
 * Created by Robert on 12-09-15.
 */
public class Pet implements Serializable {
    private String name;
    private String species;
    private String description;
    private String image;

    public Pet(String name, String species, String description, String image) {
        this.name = name;
        this.species = species;
        this.image = image;

        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecies() {
        return species;
    }

    public String getImage() {
        return image;
    }
}
