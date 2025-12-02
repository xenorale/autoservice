package ru.voronezh.autoservice.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Инициализация приложения ===");
        DatabaseInitializer.init();
        System.out.println("=== База данных инициализирована ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Приложение остановлено ===");
    }
}