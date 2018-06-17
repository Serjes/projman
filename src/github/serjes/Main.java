package github.serjes;

import java.util.*;

public class Main {
//    public static int DAY_MSEC = 86400000; // 24 * 60 * 60
    public static Long DAY_MSEC = (Long)(24L * 60 * 60 * 1000);
    public static void main(String[] args) {
        ManagSystem managSystem = new ManagSystem();
        managSystem.init();
        userMenu(managSystem);
    }

    private static void userMenu(ManagSystem mSystem) {

        long time = System.currentTimeMillis();
        Date date;
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
            System.out.println("5. Ответственные с просроченными задачами");
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
                        i = 0;
                        for (String task : tasks) {
                            System.out.printf("%d. %s %n", ++i, task);
                        }
                    }
                    break;
                case 4:
                    date = new Date(time);
                    System.out.println(date);
                    HashMap<String, String> tasksTodayForPerson = mSystem.getTasksToday(time);
                    System.out.println("Актуальные задачи на сегодня:");
                    if (!tasksTodayForPerson.isEmpty()) {
                        i = 0;
                        for (String task : tasksTodayForPerson.keySet()) {
//                            System.out.println(task);
                            System.out.printf("%d. %s (ответственный - %s) %n", ++i, task, tasksTodayForPerson.get(task));
                        }
                    } else {
                        System.out.println("Нет задач на сегодня");
                    }
                    break;
                case 5:
                    date = new Date(time);
                    System.out.println(date);
                    HashMap<String,ArrayList<String>> personsWithExpiredTasks = mSystem.getPersonsWithExpiredTasks(time);
                    if (!personsWithExpiredTasks.isEmpty()) {
                        i = 0;
                        for (String person : personsWithExpiredTasks.keySet()) {
                            System.out.printf("%d. %s  %s %n", ++i, person, personsWithExpiredTasks.get(person).toString());
                        }
                    } else {
                        System.out.println("Нет просроченных задач");
                    }
                    break;
                default:
                    break;
            }
        } while (anInt != 0);

    }
}
