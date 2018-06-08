package github.serjes;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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
            anInt = scanner.nextInt();
            switch (anInt) {
                case 1:
                    ArrayList<String> projsInWork = mSystem.getProjectsInWork();
                    System.out.println(projsInWork);
                    break;
                case 2:
                    ArrayList<String> projs = mSystem.getProjects();
                    System.out.println("Выберете проект из следующих:");
//                System.out.println(projs);
//                Iterator iter = projs.iterator();
//                for( String s = (String)iter.next(); iter.hasNext();) {
//                    System.out.println(s);
//                    iter.next();
//                }

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
//                    int amountFinishedTask = mSystem.getAmountFinishedTask(projs.get(--i));
                    ArrayList<String> tasks = mSystem.getUnfinishedTasksOfPerson(persons.get(--i));
                    if (tasks.isEmpty()){
                        System.out.println("Нет незавершенных задач");
                    } else {
                        for (String task : tasks) {
                            System.out.printf("%d. %s %n", ++i, task);
                        }
                    }
                    break;
                default:
                    break;
            }
        } while (anInt != 0);

    }
}
