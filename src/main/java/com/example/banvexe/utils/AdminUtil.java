package com.example.banvexe.utils;

import java.util.Set;

public class AdminUtil {

    private static final Set<String> ADMINS = Set.of(
            "Phanthuky12@gmail.com",
            "Honglamtoan0124@gmail.com"
    );

    public static boolean isAdmin(String email) {
        return ADMINS.contains(email);
    }

    public static String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }

    public static String getRoleBadge(String role) {
        if ("ADMIN".equals(role)) return "bg-danger";
        return "bg-primary";
    
}
}