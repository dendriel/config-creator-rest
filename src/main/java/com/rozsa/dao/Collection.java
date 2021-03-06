package com.rozsa.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Collection {
    USER("user"),
    TEMPLATE("template"),
    PROJECT("project"),
    RESOURCE("resource"),
    CONFIGURATION("configuration"),
    ;

    private final String name;
}
