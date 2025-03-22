package com.app.test;

import com.app.properties.*;
import com.app.user.*;

public class AppTest {

    private static final boolean IS_TEST_ENABLED = AppPropertiesHandler.getAppProperty(TESTPROPERTIES.IS_TEST_ENABLED,
            Boolean.class);
    private static final boolean IS_ADVANCED_TEST_ENABLED = AppPropertiesHandler.getAppProperty(
            TESTPROPERTIES.IS_ADVANCED_TEST_ENABLED,
            Boolean.class);
    private static final boolean CAN_PRINT_TEST_LOGS = AppPropertiesHandler.getAppProperty(
            TESTPROPERTIES.CAN_PRINT_TEST_LOGS,
            Boolean.class);

    private static final String TC_IS_USER_VALID = "[1. IsUserValid]";
    private static final String TC_IS_USER_ADULT = "[2. IsUserAdult]";

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static void printTestFailed(Boolean canPrintMsg, String testcase, String msg) {
        printTestFailed(canPrintMsg, testcase, msg, new Object[0]);
    }

    private static void printTestFailed(Boolean canPrintMsg, String testcase, String msg, Object... args) {
        if (!CAN_PRINT_TEST_LOGS)
            return;
        if (!canPrintMsg)
            return;

        if (args.length > 0) {
            System.err
                    .println(ANSI_RED + "TESTCASE FAILED " + testcase + " : " + String.format(msg, args) + ANSI_RESET);
        } else {
            System.err.println(ANSI_RED + "TESTCASE FAILED " + testcase + " : " + msg + ANSI_RESET);
        }
    }

    private static void printTestSuccess(String testcase, String msg, Boolean canPrintMsg) {
        printTestSuccess(canPrintMsg, testcase, msg, new Object[0]);
    }

    private static void printTestSuccess(Boolean canPrintMsg, String testcase, String msg, Object... args) {
        if (!CAN_PRINT_TEST_LOGS)
            return;
        if (!canPrintMsg)
            return;

        if (args.length > 0) {
            System.out.println(
                    ANSI_GREEN + "TESTCASE PASSED " + testcase + " : " + String.format(msg, args) + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "TESTCASE PASSED " + testcase + " : " + msg + ANSI_RESET);
        }
    }

    // USER TESTS
    public static boolean runTestsForUsers(User[] users) {
        boolean result = true;
        for (User user : users) {
            result &= runTestsForUser(user);
        }
        return result;
    }

    public static boolean runTestsForUser(User user) {
        boolean result = true;
        result &= isUserValid(user);
        result &= result && isUserAdult(user);
        return result;
    }

    private static boolean isUserValid(User user) {
        return isUserValid(user, true, true);
    }

    private static boolean isUserValid(User user, boolean canPrintSuccess, boolean canPrintFailure) {
        if (!IS_TEST_ENABLED) {
            return true;
        }
        if (user == null || user.age == null || user.name == null || user.gender == null) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_VALID,
                    "User %s is either null or has null values", user);
            return false;
        }
        if (user.name.trim().isEmpty()) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_VALID, "User %s has an invalid name %s",
                    user, user.name);
            return false;
        }
        if (user.age <= 0) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_VALID, "User %s = %s has an invalid age %d",
                    user, user.name, user.age);
            return false;
        }
        if (!user.gender.equalsIgnoreCase("male") && !user.gender.equalsIgnoreCase("female")) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_VALID, "User %s = %s has an invalid gender %s",
                    user, user.name, user.gender);
            return false;
        }
        printTestSuccess(canPrintSuccess, AppTest.TC_IS_USER_VALID, "User %s = %s", user, user.name);
        return true;
    }

    private static boolean isUserAdult(User user) {
        return isUserAdult(user, true, true);
    }

    private static boolean isUserAdult(User user, boolean canPrintSuccess, boolean canPrintFailure) {
        if (!IS_TEST_ENABLED || !IS_ADVANCED_TEST_ENABLED) {
            return true;
        }

        if (!isUserValid(user, false, false)) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_ADULT,
                    "User %s = %s with is invalid", user,
                    user.name);
            return false;
        }

        if (user.age < 18) {
            AppTest.printTestFailed(canPrintFailure, AppTest.TC_IS_USER_ADULT,
                    "User %s = %s with age %d is not an adult", user,
                    user.name,
                    user.age);
            return false;
        }
        printTestSuccess(canPrintSuccess, AppTest.TC_IS_USER_ADULT, "User %s = %s", user, user.name);
        return true;
    }

}
