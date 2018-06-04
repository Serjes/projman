package system.projman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ManagSystem managSystem = new ManagSystem();
        managSystem.init();
        userMenu(managSystem);
    }

    private static void userMenu(ManagSystem mSystem) {

        int anInt;
        Scanner scanner;
        do {
            scanner = new Scanner(System.in);
            System.out.println("Выберете запрос (0 - выход):");
            System.out.println("1. Какие проекты в работе");
            System.out.println("2. Сколько по проекту незавершенных задач");
            anInt = scanner.nextInt();
            switch (anInt) {
                case 1:
                    ArrayList<String> projsInWork = mSystem.getProjectsInWork();
                    System.out.println(projsInWork);
                    break;
                case 2:
                    ArrayList<String> projs = mSystem.getProjects();
                    System.out.println("Выберет проект из следующих:");
//                System.out.println(projs);
//                Iterator iter = projs.iterator();
//                for( String s = (String)iter.next(); iter.hasNext();) {
//                    System.out.println(s);
//                    iter.next();
//                }
                    int i = 0;
                    for (String name : projs) {
                        System.out.printf("%d. %s %n", ++i, name);
                    }
                    i = scanner.nextInt();
                    int amountFinishedTask = mSystem.getAmountFinishedTask(projs.get(--i));
                    System.out.println("Количество незавершенных задач по этому проекту: " + amountFinishedTask);
                    break;
                default:
                    break;
            }
        } while (anInt != 0);

    }
}
