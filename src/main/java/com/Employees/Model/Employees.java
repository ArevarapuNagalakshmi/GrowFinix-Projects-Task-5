package com.Employees.Model;

public class Employees {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String course;
    private String department;

    public Employees(int id, String name, String email, String phone, String course, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.department = department;
    }

    public Employees(String name, String email, String phone, String course, String department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.department = department;
    }

    public Employees() {
        
    }

    public Employees(String text, String text1, String text2, String text3) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
