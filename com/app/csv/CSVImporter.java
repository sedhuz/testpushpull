package com.app.csv;

import com.app.user.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.app.properties.AppPropertiesHandler;
import com.app.test.TESTPROPERTIES;

public class CSVImporter {

    private static final String DEFAULT_FILE_PATH = AppPropertiesHandler.getAppProperty(CSVPROPERTIES.PATH,
            String.class);
    private static final String DEFAULT_FILE_NAME = AppPropertiesHandler.getAppProperty(CSVPROPERTIES.FILENAME,
            String.class);

    public static User[] importUsersFromDefaultPath() {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DEFAULT_FILE_PATH))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] values = line.split(",");
                String name = values[0].trim();
                Integer age = Integer.parseInt(values[1]);
                String gender = values[2].trim();
                User user = new User(name, age, gender);
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println(
                    "Error while loading csv file! The default csv file must be located in project's root folder with name - \"users.csv\"");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println(
                    "Error while importing csv file! Check if the csv is in proper format name, age, gender & has proper values");
            e.printStackTrace();
            System.exit(1);
        }
        return users.toArray(new User[users.size()]);
    }
}
