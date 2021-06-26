package com.rozsa.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Collection {
    USER("user"),
    TEMPLATE("template"),
    PROJECT("project")
    ;


    private final String name;
}
