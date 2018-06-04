package system.projman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagSystem {
    public static final String URL = "jdbc:sqlite:projects.db";

    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("Not found JDBC: " + e.toString());
        }
        createTables();
    }

    private void createTables() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Projects");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Projects (ProjId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "ProjName TEXT);");

            statement.executeUpdate("DROP TABLE IF EXISTS Persons");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Persons (PersonId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "Name TEXT, Phone TEXT, Mail TEXT);");

            statement.executeUpdate("DROP TABLE IF EXISTS Tasks");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Tasks (TaskId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "TaskName TEXT, StartDate INTEGER, Duration INTEGER, Complete INTEGER, PersonId INTEGER, ProjId INTEGER);");

            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('ATM');");
            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('Project management system');");
            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('JabaChat');");

            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Валуев', '+7(905)1234567', 'valuev@mail.ru');");
            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Иванов', '+7(926)1234567', 'ivanov@mail.ru');");
            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Сидоров', '+7(903)1234567', 'sidorov@mail.ru');");

            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Complete, PersonId, ProjId) " +
                    "VALUES ('Наполнение вкладки Операционист', 10000, 5000, 0, 1, 1);");
            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Complete, PersonId, ProjId) " +
                    "VALUES ('Маркировать текст для разных абонентов', 15000, 5000, 0, 2, 3);");
            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Complete, PersonId, ProjId) " +
                    "VALUES ('разные потоки для подключения', 5000, 5000, 1, 2, 3);");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getProjectsInWork() {
        ArrayList<String> retArrayList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Books WHERE Amount = ?;");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Projects.ProjName FROM Projects join Tasks\n" +
                    "ON tasks.projid = projects.projid\n" +
                    "where Tasks.Complete = 0;");
            while (resultSet.next()) {
                String projName = resultSet.getString("ProjName");
                retArrayList.add(projName);
//                int amount = resultSet.getInt("Amount");
//                System.out.printf("book: %s, amount: %d%n", name, amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retArrayList;
    }

//    public void userMenu() {
//    }
}
