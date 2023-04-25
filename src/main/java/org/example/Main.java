package org.example;

import org.example.util.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> lines = FileReader.setNodesToLines(FileReader.readLinesFromFiles());
        Converter converter = new Converter(lines);
        String[] inputs = getInputs();

        List<List<Integer>> result = BFS.getShortestDistance(converter.convertToMap(), converter.getNodes().indexOf(inputs[0]), converter.getNodes().indexOf(inputs[1]), converter.getNodes().size());
        LinkedList<String> path = ResultHandler.convertNodeIntValues(converter.getNodes(), result.get(1));
        ResultHandler.addLineInfoToPath(lines, path);

        Printer.printPath(path);
    }

    private static String[] getInputs() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Please enter start and end points");
        System.out.print("Start : ");
        String sourceStation = sc.nextLine();
        System.out.print("Destination: ");
        String targetStation = sc.nextLine();

        return new String[]{sourceStation, targetStation};
    }
}
