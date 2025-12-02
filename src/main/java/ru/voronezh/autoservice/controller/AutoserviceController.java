package ru.voronezh.autoservice.controller;

import ru.voronezh.autoservice.model.Car;
import ru.voronezh.autoservice.model.Employee;
import ru.voronezh.autoservice.model.Owner;
import ru.voronezh.autoservice.model.Repair;
import ru.voronezh.autoservice.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AutoserviceController {
    private final CarRepository carRepository = CarRepositoryImpl.getInstance();
    private final OwnerRepository ownerRepository = OwnerRepositoryImpl.getInstance();
    private final EmployeeRepository employeeRepository = EmployeeRepositoryImpl.getInstance();
    private final RepairRepository repairRepository = RepairRepositoryImpl.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Показать все автомобили");
            System.out.println("2. Показать всех сотрудников");
            System.out.println("3. Показать все ремонты");
            System.out.println("4. Добавить ремонт");
            System.out.println("5. Удалить ремонт");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showCars();
                case 2 -> showEmployees();
                case 3 -> showRepairs();
                case 4 -> addRepair();
                case 5 -> deleteRepair();
                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void showCars() {
        List<Car> cars = carRepository.findAll();
        System.out.println("\n=== Список автомобилей ===");
        for (Car car : cars) {
            Owner owner = ownerRepository.findById(car.getOwnerId());
            System.out.printf("ID: %d | Владелец: %s %s | Номер: %s | Марка: %s%n",
                    car.getCarId(),
                    owner != null ? owner.getFirstName() : "?",
                    owner != null ? owner.getLastName() : "?",
                    car.getPlateNumber(),
                    car.getBrand());
        }
    }

    private void showEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        System.out.println("\n=== Список сотрудников ===");
        for (Employee emp : employees) {
            System.out.printf("ID: %d | %s %s | Должность: %s | Зарплата: %s | Стаж: %d лет%n",
                    emp.getEmployeeId(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getPosition(),
                    emp.getSalary(),
                    emp.getExperience());
        }
    }

    private void showRepairs() {
        List<Repair> repairs = repairRepository.findAll();
        System.out.println("\n=== Список ремонтов ===");
        for (Repair repair : repairs) {
            System.out.printf("ID: %d | Авто ID: %d | Сотрудник ID: %d | Дата: %s | Описание: %s%n",
                    repair.getRepairId(),
                    repair.getCarId(),
                    repair.getEmployeeId(),
                    repair.getAppealDate(),
                    repair.getMalfunctionDescription());
        }
    }

    private void addRepair() {
        System.out.print("ID автомобиля: ");
        int carId = scanner.nextInt();
        System.out.print("ID сотрудника: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Описание неисправности: ");
        String description = scanner.nextLine();

        Repair repair = new Repair();
        repair.setCarId(carId);
        repair.setEmployeeId(employeeId);
        repair.setAppealDate(LocalDate.now());
        repair.setMalfunctionDescription(description);

        repairRepository.create(repair);
        System.out.println("Ремонт добавлен!");
    }

    private void deleteRepair() {
        System.out.print("ID ремонта для удаления: ");
        int id = scanner.nextInt();
        repairRepository.delete(id);
        System.out.println("Ремонт удалён!");
    }
}