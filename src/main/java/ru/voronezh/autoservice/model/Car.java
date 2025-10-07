package ru.voronezh.autoservice.model;

import lombok.Data;

@Data
public class Car {
    private Integer carId;
    private Integer ownerId;
    private String plateNumber;
    private String brand;
}