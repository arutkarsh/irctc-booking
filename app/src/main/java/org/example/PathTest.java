package org.example;

public class PathTest {
    public static void main(String[] args) {
        System.out.println("User dir: " + System.getProperty("user.dir"));
        System.out.println("Current dir: " + System.getProperty("user.dir") + "/app/src/main/java/org/example/localDb/trains.json");
        System.out.println("File exists: " + new java.io.File(System.getProperty("user.dir") + "/app/src/main/java/org/example/localDb/trains.json").exists());
    }
}