package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Owner;

public interface OwnerRepository {
    Owner findById(int id);
}