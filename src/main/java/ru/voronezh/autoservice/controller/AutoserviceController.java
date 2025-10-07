package ru.voronezh.autoservice.controller;

import ru.voronezh.autoservice.model.Repair;
import ru.voronezh.autoservice.service.RepairService;
import ru.voronezh.autoservice.service.RepairServiceImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class AutoserviceController {
    private final RepairService service = new RepairServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== Автосервис ===");
            System.out.println("1. Показать ремонты");
            System.out.println("2. Добавить ремонт");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showRepairs();
                case 2 -> addRepair();
                case 0 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    private void showRepairs() {
        System.out.println("\n--- Список ремонтов ---");
        service.getAllRepairs().forEach(System.out::println);
    }

    private void addRepair() {
        System.out.print("ID автомобиля: ");
        int carId = scanner.nextInt();
        System.out.print("ID сотрудника: ");
        int empId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Описание: ");
        String desc = scanner.nextLine();

        Repair repair = new Repair();
        repair.setCarId(carId);
        repair.setEmployeeId(empId);
        repair.setAppealDate(LocalDate.now());
        repair.setMalfunctionDescription(desc);

        service.addRepair(repair);
    }
}