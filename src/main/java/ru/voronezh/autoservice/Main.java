package ru.voronezh.autoservice;

import ru.voronezh.autoservice.util.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Автосервис запущен ===");
        DatabaseInitializer.init();
        System.out.println("Программа работает!");
    }
}