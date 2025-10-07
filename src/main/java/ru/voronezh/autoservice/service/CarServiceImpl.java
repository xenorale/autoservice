package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Car;
import ru.voronezh.autoservice.repository.CarRepository;
import ru.voronezh.autoservice.repository.CarRepositoryImpl;

import java.util.List;

public class CarServiceImpl implements CarService {
    private final CarRepository repository = new CarRepositoryImpl();

    @Override
    public List<Car> getAllCars() {
        return repository.findAll();
    }
}