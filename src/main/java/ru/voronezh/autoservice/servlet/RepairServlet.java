package ru.voronezh.autoservice.servlet;

import ru.voronezh.autoservice.model.Repair;
import ru.voronezh.autoservice.service.RepairService;
import ru.voronezh.autoservice.service.RepairServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/repairs")
public class RepairServlet extends HttpServlet {
    private final RepairService repairService = new RepairServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Repair> repairs = repairService.getAllRepairs();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < repairs.size(); i++) {
            Repair r = repairs.get(i);
            json.append("{")
                    .append("\"repairId\":").append(r.getRepairId()).append(",")
                    .append("\"carId\":").append(r.getCarId()).append(",")
                    .append("\"employeeId\":").append(r.getEmployeeId()).append(",")
                    .append("\"appealDate\":\"").append(r.getAppealDate()).append("\",")
                    .append("\"malfunctionDescription\":\"")
                    .append(escapeJson(r.getMalfunctionDescription())).append("\"")
                    .append("}");
            if (i < repairs.size() - 1) json.append(",");
        }

        json.append("]");
        resp.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int carId = Integer.parseInt(req.getParameter("carId"));
        int employeeId = Integer.parseInt(req.getParameter("employeeId"));
        String description = req.getParameter("description");

        Repair repair = new Repair();
        repair.setCarId(carId);
        repair.setEmployeeId(employeeId);
        repair.setAppealDate(LocalDate.now());
        repair.setMalfunctionDescription(description);

        repairService.addRepair(repair);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                repairService.deleteRepair(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}