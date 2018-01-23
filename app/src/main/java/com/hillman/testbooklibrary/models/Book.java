package com.hillman.testbooklibrary.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

public class Book extends RealmObject {

    @PrimaryKey
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String author;
    @Getter
    @Setter
    private String imageUrl;

}