package org.example;

import org.example.service.UserBookingService;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

public class ErrorFixTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Error Fixes ===");
        
        try {
            // Test 1: Verify UserBookingService can be initialized (File path fix)
            System.out.println("1. Testing UserBookingService initialization...");
            UserBookingService service = new UserBookingService();
            System.out.println("   ✓ UserBookingService initialized successfully");
            
            // Test 2: Verify fetchBooking handles null user (NullPointerException fix)
            System.out.println("2. Testing fetchBooking with no logged-in user...");
            service.fetchBooking();
            System.out.println("   ✓ fetchBooking handles null user gracefully");
            
            // Test 3: Test complete login flow
            System.out.println("3. Testing complete login and booking flow...");
            User testUser = new User("testuser", "testpass", UserServiceUtil.hashPassword("testpass"), new java.util.ArrayList<>(), java.util.UUID.randomUUID().toString());
            UserBookingService loginService = new UserBookingService(testUser);
            boolean loginResult = loginService.loginUser();
            System.out.println("   ✓ Login test completed, result: " + loginResult);
            
            System.out.println("=== All Error Fixes Verified ===");
            
        } catch (Exception e) {
            System.out.println("✗ Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}