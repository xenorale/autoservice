package ru.voronezh.autoservice.controller;

import ru.voronezh.autoservice.model.Car;
import ru.voronezh.autoservice.model.Employee;
import ru.voronezh.autoservice.model.Owner;
import ru.voronezh.autoservice.model.Repair;
import ru.voronezh.autoservice.repository.OwnerRepository;
import ru.voronezh.autoservice.repository.OwnerRepositoryImpl;
import ru.voronezh.autoservice.service.CarService;
import ru.voronezh.autoservice.service.CarServiceImpl;
import ru.voronezh.autoservice.service.EmployeeService;
import ru.voronezh.autoservice.service.EmployeeServiceImpl;
import ru.voronezh.autoservice.service.RepairService;
import ru.voronezh.autoservice.service.RepairServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AutoserviceController {
    private final RepairService repairService = new RepairServiceImpl();
    private final EmployeeService employeeService = new EmployeeServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n╔════════════════════════════╗");
            System.out.println("║      АВТОСЕРВИС МЕНЮ       ║");
            System.out.println("╚════════════════════════════╝");
            System.out.println("1. Показать все ремонты");
            System.out.println("2. Добавить ремонт");
            System.out.println("3. Удалить ремонт");
            System.out.println("4. Показать всех сотрудников");
            System.out.println("0. Выход");
            System.out.print("➤ Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showRepairs();
                case 2 -> addRepair();
                case 3 -> deleteRepair();
                case 4 -> showEmployees();
                case 0 -> {
                    System.out.println("\n✓ До свидания!");
                    return;
                }
                default -> System.out.println("❌ Неверный выбор");
            }
        }
    }

    private void showRepairs() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                    СПИСОК РЕМОНТОВ                          │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        List<Repair> repairs = repairService.getAllRepairs();
        if (repairs.isEmpty()) {
            System.out.println("  Ремонтов пока нет.");
        } else {
            for (Repair r : repairs) {
                System.out.printf("  ID: %-3d | Авто: %-3d | Сотрудник: %-3d | Дата: %s%n",
                        r.getRepairId(), r.getCarId(), r.getEmployeeId(), r.getAppealDate());
                System.out.printf("  Описание: %s%n", r.getMalfunctionDescription());
                System.out.println("  ─────────────────────────────────────────────────────");
            }
        }
    }

    private void addRepair() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                   ДОБАВИТЬ РЕМОНТ                           │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        // Показываем список автомобилей
        System.out.println("\nДоступные автомобили:");
        CarService carService = new CarServiceImpl();
        OwnerRepository ownerRepo = new OwnerRepositoryImpl();
        List<Car> cars = carService.getAllCars();

        if (cars.isEmpty()) {
            System.out.println("  ⚠ Автомобилей нет в базе. Сначала добавьте автомобиль.");
            return;
        }

        for (Car car : cars) {
            Owner owner = ownerRepo.findById(car.getOwnerId());
            String ownerName = owner != null ? owner.getFirstName() + " " + owner.getLastName() : "Неизвестен";
            System.out.printf("  ID: %-3d | %s %s | Владелец: %s%n",
                    car.getCarId(), car.getBrand(), car.getPlateNumber(), ownerName);
        }

        System.out.print("\n➤ ID автомобиля: ");
        int carId = scanner.nextInt();

        // Показываем сотрудников
        System.out.println("\nДоступные сотрудники:");
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("  ⚠ Сотрудников нет в базе.");
            return;
        }
        for (Employee e : employees) {
            System.out.printf("  ID: %-3d | %s %s (%s)%n",
                    e.getEmployeeId(), e.getFirstName(), e.getLastName(), e.getPosition());
        }

        System.out.print("\n➤ ID сотрудника: ");
        int empId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("➤ Описание неисправности: ");
        String desc = scanner.nextLine();

        Repair repair = new Repair();
        repair.setCarId(carId);
        repair.setEmployeeId(empId);
        repair.setAppealDate(LocalDate.now());
        repair.setMalfunctionDescription(desc);

        repairService.addRepair(repair);
    }

    private void deleteRepair() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                   УДАЛИТЬ РЕМОНТ                            │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        showRepairs();

        System.out.print("\n➤ Введите ID ремонта для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repairService.deleteRepair(id);
    }

    private void showEmployees() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                  СПИСОК СОТРУДНИКОВ                         │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("  Сотрудников пока нет.");
        } else {
            for (Employee e : employees) {
                System.out.printf("  ID: %-3d | %s %s%n", e.getEmployeeId(),
                        e.getFirstName(), e.getLastName());
                System.out.printf("  Должность: %-15s | Оклад: %,.2f₽ | Стаж: %d лет%n",
                        e.getPosition(), e.getSalary(), e.getExperience());
                System.out.println("  ─────────────────────────────────────────────────────");
            }
        }
    }
}