package com.rozsa.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private String id;
    private String data;

    public BaseDto(String data) {
        this.data = data;
    }
}
