package com.rozsa.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListTemplateDto {
    private List<String> data;

    public ListTemplateDto() {
        data = new ArrayList<>();
    }
}
