package com.example.robert.petmatch.models;

import java.io.Serializable;

/**
 * Created by Robert on 12-09-15.
 */
public class Pet implements Serializable {
    private int id;
    private String name;
    private String species;
    private String description;
    private String image;
    private String createdAt;

    public static final int STATUS_UNDECIDED = 0;
    public static int STATUS_LIKED = 1;
    public static int STATUS_DISLIKED = 2;

    private int status = STATUS_UNDECIDED;

    public Pet(int id, String name, String species, String description, String image, String createdAt) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
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

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void like() {
        status = STATUS_LIKED;
    }

    public void dislike() {
        status = STATUS_DISLIKED;
    }

    public void removeStatus() {
        status = STATUS_UNDECIDED;
    }

    @Override
    public String toString() {
        return name;
    }
}
