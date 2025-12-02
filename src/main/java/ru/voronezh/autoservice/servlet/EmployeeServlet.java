package ru.voronezh.autoservice.servlet;

import ru.voronezh.autoservice.model.Employee;
import ru.voronezh.autoservice.service.EmployeeService;
import ru.voronezh.autoservice.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/employees")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Employee> employees = employeeService.getAllEmployees();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < employees.size(); i++) {
            Employee e = employees.get(i);
            json.append("{")
                    .append("\"employeeId\":").append(e.getEmployeeId()).append(",")
                    .append("\"firstName\":\"").append(e.getFirstName()).append("\",")
                    .append("\"lastName\":\"").append(e.getLastName()).append("\",")
                    .append("\"position\":\"").append(e.getPosition()).append("\",")
                    .append("\"salary\":").append(e.getSalary()).append(",")
                    .append("\"experience\":").append(e.getExperience())
                    .append("}");
            if (i < employees.size() - 1) json.append(",");
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }
}