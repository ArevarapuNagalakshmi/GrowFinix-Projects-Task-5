package com.Employees;

import com.Employees.Database.EmployeeDAO;
import com.Employees.Model.Employees;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    private TextField nameInput;
    private TextField emailInput;
    private TextField phoneInput;
    private TextField departmentInput;  // renamed from courseInput for clarity
    private Label messageLabel;
    private ObservableList<Employees> employeeList;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Name
        Label nameLabel = new Label("Name:");
        nameInput = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameInput, 1, 0);

        // Email
        Label emailLabel = new Label("Email:");
        emailInput = new TextField();
        grid.add(emailLabel, 0, 1);
        grid.add(emailInput, 1, 1);

        // Phone
        Label phoneLabel = new Label("Phone:");
        phoneInput = new TextField();
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneInput, 1, 2);

        // Department
        Label departmentLabel = new Label("Department:");
        departmentInput = new TextField();
        grid.add(departmentLabel, 0, 3);
        grid.add(departmentInput, 1, 3);

        // Buttons
        Button addButton = new Button("Add Employee");
        Button updateButton = new Button("Update Employee");
        Button deleteButton = new Button("Delete Employee");

        grid.add(addButton, 0, 4);
        grid.add(updateButton, 1, 4);
        grid.add(deleteButton, 2, 4);

        messageLabel = new Label();
        grid.add(messageLabel, 1, 5, 2, 1);

        // TableView
        TableView<Employees> employeeTable = new TableView<>();
        employeeTable.setPrefWidth(700);

        TableColumn<Employees, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employees, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employees, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employees, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Employees, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        employeeTable.getColumns().addAll(idCol, nameCol, emailCol, phoneCol, departmentCol);

        grid.add(employeeTable, 0, 6, 3, 1);

        // ObservableList for TableView
        employeeList = FXCollections.observableArrayList();
        refreshTable();

        employeeTable.setItems(employeeList);

        // Populate fields on row selection
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameInput.setText(newSelection.getName());
                emailInput.setText(newSelection.getEmail());
                phoneInput.setText(newSelection.getPhone());
                departmentInput.setText(newSelection.getDepartment());
            }
        });

        // Add button action
        addButton.setOnAction(e -> {
            if (!validateInputs()) return;

            Employees employee = new Employees(
                    nameInput.getText(),
                    emailInput.getText(),
                    phoneInput.getText(),
                    departmentInput.getText());

            try {
                employeeDAO.addEmployee(employee);
                messageLabel.setText("Employee added successfully!");
                refreshTable();
                clearForm();
            } catch (SQLException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            }
        });

        // Update button action
        updateButton.setOnAction(e -> {
            Employees selected = employeeTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                messageLabel.setText("Select an employee to update.");
                return;
            }
            if (!validateInputs()) return;

            selected.setName(nameInput.getText());
            selected.setEmail(emailInput.getText());
            selected.setPhone(phoneInput.getText());
            selected.setDepartment(departmentInput.getText());

            try {
                employeeDAO.updateEmployee(selected);
                messageLabel.setText("Employee updated successfully.");
                refreshTable();
                clearForm();
            } catch (SQLException ex) {
                messageLabel.setText("Update failed: " + ex.getMessage());
            }
        });

        // Delete button action
        deleteButton.setOnAction(e -> {
            Employees selected = employeeTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                messageLabel.setText("Select an employee to delete.");
                return;
            }
            try {
                employeeDAO.deleteEmployee(selected.getId());
                messageLabel.setText("Employee deleted successfully.");
                refreshTable();
                clearForm();
            } catch (SQLException ex) {
                messageLabel.setText("Delete failed: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateInputs() {
        if (nameInput.getText().isEmpty() || emailInput.getText().isEmpty()) {
            messageLabel.setText("Name and Email are required.");
            return false;
        }
        if (!emailInput.getText().matches("^(.+)@(.+)$")) {
            messageLabel.setText("Invalid email format.");
            return false;
        }
        return true;
    }

    private void refreshTable() {
        try {
            employeeList.clear();
            employeeList.addAll(employeeDAO.getAllEmployees());
        } catch (SQLException e) {
            messageLabel.setText("Error loading employees: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameInput.clear();
        emailInput.clear();
        phoneInput.clear();
        departmentInput.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
