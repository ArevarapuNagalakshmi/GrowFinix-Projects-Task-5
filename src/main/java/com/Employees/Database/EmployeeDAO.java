package com.Employees.Database;

import com.Employees.Model.Employees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Add new employee
    public void addEmployee(Employees employee) throws SQLException {
        String sql = "INSERT INTO employees (name, email, phone, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPhone());
            ps.setString(4, employee.getDepartment());

            ps.executeUpdate();
        }
    }

    // Get all employees
    public List<Employees> getAllEmployees() throws SQLException {
        List<Employees> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setDepartment(rs.getString("department"));

                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    // Update existing employee
    public void updateEmployee(Employees employee) throws SQLException {
        String sql = "UPDATE employees SET name=?, email=?, phone=?, department=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPhone());
            ps.setString(4, employee.getDepartment());
            ps.setInt(5, employee.getId());

            ps.executeUpdate();
        }
    }

    // Delete employee by ID
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
