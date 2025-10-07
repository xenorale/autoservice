package ru.voronezh.autoservice.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Repair {
    private Integer repairId;
    private Integer carId;
    private Integer employeeId;
    private LocalDate appealDate;
    private String malfunctionDescription;
    private LocalDate repairDate;
    private String partName;
    private String partNumber;
}