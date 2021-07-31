package com.rozsa.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseDto {
    private String id;
    private String data;

    public BaseDto(String data) {
        this.data = data;
    }
}
