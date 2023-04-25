package org.example.util;

import java.util.List;

public class Printer {
    public static void printPath(List<String> path) {
        System.out.println("--------Path--------");
        for (String vertex : path) {
            System.out.println(vertex);
        }
        System.out.println("---------------------");
    }
}
