package github.serjes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
//    public static int DAY_MSEC = 86400000; // 24 * 60 * 60
    public static Long DAY_MSEC = (Long)(24L * 60 * 60 * 1000);
    public static void main(String[] args) {
        ManagSystem managSystem = new ManagSystem();
        managSystem.init();
        userMenu(managSystem);
    }

    private static void userMenu(ManagSystem mSystem) {

        int anInt;
        int i;
        Scanner scanner;
        do {
            scanner = new Scanner(System.in);
            System.out.printf("%nВыберете запрос (0 - выход):%n");
            System.out.println("1. Какие проекты в работе");
            System.out.println("2. Сколько по проекту незавершенных задач");
            System.out.println("3. Какие незавершенные задачи есть у ответственного");
            System.out.println("4. Какие задачи на сегодня и ответственный");
            anInt = scanner.nextInt();
            switch (anInt) {
                case 1:
                    ArrayList<String> projsInWork = mSystem.getProjectsInWork();
                    System.out.println(projsInWork);
                    break;
                case 2:
                    ArrayList<String> projs = mSystem.getProjects();
                    System.out.println("Выберете проект из следующих:");
                    i = 0;
                    for (String name : projs) {
                        System.out.printf("%d. %s %n", ++i, name);
                    }
                    i = scanner.nextInt();
                    int amountFinishedTask = mSystem.getAmountFinishedTask(projs.get(--i));
                    System.out.println("Количество незавершенных задач по этому проекту: " + amountFinishedTask);
                    break;
                case 3:
                    System.out.println("Выберете ответственного:");
                    ArrayList<String> persons = mSystem.getPersons();
                    i = 0;
                    for (String name : persons) {
                        System.out.printf("%d. %s %n", ++i, name);
                    }
                    i = scanner.nextInt();
                    ArrayList<String> tasks = mSystem.getUnfinishedTasksOfPerson(persons.get(--i));
                    if (tasks.isEmpty()){
                        System.out.println("Нет незавершенных задач");
                    } else {
                        for (String task : tasks) {
                            System.out.printf("%d. %s %n", ++i, task);
                        }
                    }
                    break;
                case 4:
//                    Calendar calendar = Calendar.getInstance();
//
//                    System.out.println("Время: " + calendar.getTime());
                    long time = System.currentTimeMillis();
                    Date date =  new Date(time); // +1 day
//                    Date date =  new Date(time + DAY_MSEC); // +1 day
                    System.out.println(date);


                    ArrayList<String> tasksToday = mSystem.getTasksToday(time);
                    System.out.println("Задачи на сегодня:");
                    if (!tasksToday.isEmpty()) {
                        i = 0;
                        for (String task : tasksToday) {
                            System.out.println(task);
//                            System.out.printf("%d. %s %n", ++i, task);
                        }
                    } else {
                        System.out.println("Нет задач на сегодня");
                    }
                    break;
                default:
                    break;
            }
        } while (anInt != 0);

    }
}
