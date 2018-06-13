package github.serjes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

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
        Long time = System.currentTimeMillis();
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
                    "TaskName TEXT, StartDate INTEGER, Duration INTEGER, Finished INTEGER, PersonId INTEGER, ProjId INTEGER);");

            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('ATM');");
            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('Project management system');");
            statement.executeUpdate("INSERT INTO Projects (ProjName) VALUES ('JabaChat');");

            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Валуев', '+7(905)1234567', 'valuev@mail.ru');");
            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Иванов', '+7(926)1234567', 'ivanov@mail.ru');");
            statement.executeUpdate("INSERT INTO Persons (Name, Phone, Mail) VALUES ('Сидоров', '+7(903)1234567', 'sidorov@mail.ru');");

            PreparedStatement preparedStatement;
//            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Finished, PersonId, ProjId) " +
//                    "VALUES ('Наполнение вкладки Операционист', 10000, 5000, 0, 1, 1);");
            preparedStatement = connection.prepareStatement("INSERT INTO Tasks (TaskName, StartDate, Duration, " +
                    "Finished, PersonId, ProjId) VALUES ('Наполнение вкладки Операционист', ?, ?, 0, 1, 1);");
            preparedStatement.setString(1, ((Long) (time - (Long) (Main.DAY_MSEC * 2))).toString());
            preparedStatement.setString(2, ((Long) (Main.DAY_MSEC * 5)).toString());
            preparedStatement.executeUpdate();

//            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Finished, PersonId, ProjId) " +
//                    "VALUES ('Маркировать текст для разных абонентов', 15000, 5000, 0, 2, 3);");
            preparedStatement = connection.prepareStatement("INSERT INTO Tasks (TaskName, StartDate, Duration, " +
                    "Finished, PersonId, ProjId) VALUES ('Маркировать текст для разных абонентов', ?, ?, 0, 2, 3);");
            preparedStatement.setString(1, ((Long) (time + (Long) (Main.DAY_MSEC * 7))).toString());
            preparedStatement.setString(2, ((Long) (Main.DAY_MSEC * 7)).toString());
            preparedStatement.executeUpdate();

//            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Finished, PersonId, ProjId) " +
//                    "VALUES ('Разные потоки для подключения', 5000, 5000, 1, 2, 3);");
            preparedStatement = connection.prepareStatement("INSERT INTO Tasks (TaskName, StartDate, Duration, " +
                    "Finished, PersonId, ProjId) VALUES ('Разные потоки для подключения', ?, ?, 1, 2, 3);");
            preparedStatement.setString(1, ((Long) (time - (Long) (Main.DAY_MSEC * 7))).toString());
            preparedStatement.setString(2, ((Long) (Main.DAY_MSEC * 3)).toString());
            preparedStatement.executeUpdate();

//            statement.executeUpdate("INSERT INTO Tasks (TaskName, StartDate, Duration, Finished, PersonId, ProjId) " +
//                    "VALUES ('Дополнительные кнопки', 15000, 5000, 0, 3, 3);");
            preparedStatement = connection.prepareStatement("INSERT INTO Tasks (TaskName, " +
                    "StartDate, Duration, Finished, PersonId, ProjId) VALUES ('Дополнительные кнопки', ?, ?, 0, " +
                    "3, 3);");
            preparedStatement.setString(1, time.toString());
            preparedStatement.setString(2, ((Long) (Main.DAY_MSEC * 7)).toString());
//            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public ArrayList<String> getProjectsInWork() {
        ArrayList<String> retArrayList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Books WHERE Amount = ?;");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Projects.ProjName FROM Projects join Tasks\n" +
                    "ON tasks.projid = projects.projid\n" +
                    "where Tasks.Finished = 0;");
            while (resultSet.next()) {
                String projName = resultSet.getString("ProjName");
                retArrayList.add(projName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retArrayList;
    }

    public ArrayList<String> getProjects() {
        ArrayList<String> retArrayList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Projects.ProjName FROM Projects ;");
            while (resultSet.next()) {
                String projName = resultSet.getString("ProjName");
                retArrayList.add(projName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retArrayList;
    }

    public int getAmountFinishedTask(String projName) {
        int ret = 0;
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tasks join Projects\n" +
                    "ON Tasks.ProjId = Projects.ProjId\n" +
                    "WHERE Projects.ProjName = ? and Tasks.Finished = 0;");
            statement.setString(1, projName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ret++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<String> getPersons() {
        ArrayList<String> retArrayList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Persons.Name FROM Persons ;");
            while (resultSet.next()) {
                String projName = resultSet.getString("Name");
                retArrayList.add(projName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retArrayList;
    }

    public ArrayList<String> getUnfinishedTasksOfPerson(String personName) {
        ArrayList<String> retArrayList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement statement = connection.prepareStatement("select Tasks.taskName from Tasks " +
                    "join Persons ON tasks.personid = persons.personid WHERE persons.name = ? and Tasks.Finished = 0;");
            statement.setString(1, personName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String taskName = resultSet.getString("TaskName");
                retArrayList.add(taskName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retArrayList;
    }

    public ArrayList<String> getTasksToday(long time) {
        ArrayList<String> retArrayList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Tasks.taskName, Tasks.StartDate, Tasks.Duration from Tasks;");
//            PreparedStatement statement = connection.prepareStatement("select Tasks.taskName, Tasks.StartDate, Tasks.Duration from Tasks " +
//                    "join Persons ON tasks.personid = persons.personid WHERE persons.name = ? and Tasks.Finished = 0;");
//            statement.setString(1, personName);
//            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String startDate = resultSet.getString("StartDate");
                long startDateNum = Long.parseLong(startDate);
                String duration = resultSet.getString("Duration");
                long durationNum = Long.parseLong(duration);
                Date date = new Date(time);
                Date dateStart = new Date(startDateNum);
                Date dateEnd = new Date(startDateNum + durationNum);

                if (time >= startDateNum && time <= (startDateNum + durationNum)) {
                    String taskName = resultSet.getString("TaskName");
                    System.out.println("текущее время: " + date + " Время начала: " + dateStart + " Время конца:" + dateEnd);
//                    System.out.println("текущее время: " + time + " Время начала: " + startDateNum + " Время конца:" + (startDateNum + durationNum));
                    retArrayList.add(taskName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return retArrayList;
    }
}
