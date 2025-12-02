package ru.voronezh.autoservice.servlet;

import ru.voronezh.autoservice.model.Car;
import ru.voronezh.autoservice.service.CarService;
import ru.voronezh.autoservice.service.CarServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/cars")
public class CarServlet extends HttpServlet {
    private final CarService carService = new CarServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Car> cars = carService.getAllCars();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            json.append("{")
                    .append("\"carId\":").append(c.getCarId()).append(",")
                    .append("\"ownerId\":").append(c.getOwnerId()).append(",")
                    .append("\"plateNumber\":\"").append(c.getPlateNumber()).append("\",")
                    .append("\"brand\":\"").append(c.getBrand()).append("\"")
                    .append("}");
            if (i < cars.size() - 1) json.append(",");
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }
}