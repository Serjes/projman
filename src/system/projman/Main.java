package system.projman;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ManagSystem managSystem = new ManagSystem();
        managSystem.init();
        userMenu(managSystem);
    }

    private static void userMenu(ManagSystem mSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберете запрос (0 - выход):");
        System.out.println("1. Какие проекты в работе");
        System.out.println("2. Сколько по проекту незавершенных задач");

        int anInt = scanner.nextInt();
        switch (anInt) {
            case 1:
                ArrayList<String> projs = mSystem.getProjectsInWork();
                System.out.println(projs);
                break;
            case 2:
                break;
            default:
                break;
        }

    }
}
