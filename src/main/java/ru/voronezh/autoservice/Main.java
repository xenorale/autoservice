package ru.voronezh.autoservice;

import ru.voronezh.autoservice.controller.AutoserviceController;
import ru.voronezh.autoservice.util.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Автосервис запущен ===");
        DatabaseInitializer.init();

        AutoserviceController controller = new AutoserviceController();
        controller.run();
    }
}