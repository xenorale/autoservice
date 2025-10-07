package ru.voronezh.autoservice.model;

import lombok.Data;

@Data
public class Owner {
    private Integer ownerId;
    private String firstName;
    private String lastName;
    private String phone;
}