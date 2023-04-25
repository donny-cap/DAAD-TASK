package org.example.util;

import org.example.exception.NodeNotFound;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileReader {

    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> setNodesToLines(Set<Map<String, List<String>>> lines) {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> nodeLines = new LinkedHashMap<>();
        int counter = 0;
        Set<String> checkedCrossroads = new HashSet<>();
        for (Map<String, List<String>> line : lines) {
            for (String lineName : line.keySet()) {
                LinkedHashMap<String, Integer> nodeStations = new LinkedHashMap<>();
                for (String station : line.get(lineName)) {
                    if (station.contains("-")) {
                        if (checkedCrossroads.contains(station)) {
                        int index = -1;
                            for (Map<String, Integer> st : nodeLines.values()) {
                                if (st.get(station) != null) {
                                    index = st.get(station);
                                }
                            }

                            if (index == -1) {
                                throw new NodeNotFound("No index found for node: " + station);
                            }
                            nodeStations.put(station, index);
                        } else {
                            nodeStations.put(station, counter);
                            counter++;
                            checkedCrossroads.add(station);
                        }
                    } else {
                        nodeStations.put(station, counter);
                        counter++;
                    }

                }

                nodeLines.put(lineName, nodeStations);
            }
        }
        return nodeLines;
    }

    public static Set<Map<String, List<String>>> readLinesFromFiles() throws IOException {
        Set<Map<String, List<String>>> lines = new HashSet<>();

        File dir = new File("lines2");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt") && name.contains(" Line");
            }
        });

        for(File file : files) {
            List<String> line = new ArrayList<>();
            BufferedReader br
                    = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String st;

            while ((st = br.readLine()) != null)
                line.add(st);

            lines.add(Collections.singletonMap(getLineName(file.getName()), line));
            br.close();
        }

        return lines;
    }

    private static String getLineName(String fileName) {
        return fileName.replace(".txt", "").replace(" Line", "").trim();
    }

}
