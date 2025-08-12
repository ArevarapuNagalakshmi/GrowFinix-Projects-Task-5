package com.Employees.Services;

public class Validation {
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^(.+)@(.+)$");
    }
}
