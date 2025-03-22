package com.app;

import com.app.csv.CSVImporter;
import com.app.test.AppTest;
import com.app.user.*;

public class Main {
    public static void main(String[] args) {
        User[] users = CSVImporter.importUsersFromDefaultPath();
        boolean testResult = AppTest.runTestsForUsers(users);

        if (testResult == true) {
            System.out.println("App good to go 🚀 ...");
        } else {
            System.out.println("App crashed 💥 ...");
        }
    }
}
