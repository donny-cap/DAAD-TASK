package org.example.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResultHandler {

    public static void addLineInfoToPath(LinkedHashMap<String, LinkedHashMap<String, Integer>> lines,
                                         LinkedList<String> path) {
        String oldLineName = "";
        String currentLineName;
        for(int i = 0; i < path.size(); i++) {
            String stationName = path.get(i);
            String lineInfo;
            currentLineName = getLineName(lines, oldLineName, stationName,
                    i == path.size() - 1 ? null : path.get(i + 1));
            if(!oldLineName.isEmpty() && !oldLineName.equals(currentLineName)) {
                lineInfo = "Change to " + currentLineName + " Line";
            } else {
                lineInfo = currentLineName + " Line";
            }

            path.set(i, stationName + " (" + lineInfo + ")");

            oldLineName = currentLineName;
        }
    }

    public static LinkedList<String> convertNodeIntValues(List<String> nodes, List<Integer> path) {
        LinkedList<String> stringPath = new LinkedList<>();

        for (Integer intVal : path) {
            stringPath.add(nodes.get(intVal));
        }

        return stringPath;
    }

    private static String getLineName(LinkedHashMap<String, LinkedHashMap<String, Integer>> lines,
                                      String latLineName, String currentStationName, String nextStationName) {
        Set<String> keySet = lines.keySet();
        String[] lineNames = keySet.toArray(new String[keySet.size()]);

        if(currentStationName.contains("-")) {
            if(nextStationName == null) {
                return latLineName;
            } else if(nextStationName.contains("-")) {
                Map<String, Integer> stationLines = new HashMap<>();
                int i = 0;
                for(LinkedHashMap<String, Integer> station : lines.values()) {
                    for(String stationName : station.keySet()) {
                        if(stationName.equals(currentStationName) || stationName.equals(nextStationName)) {
                            String lineName = lineNames[i];
                            if(stationLines.get(lineName) != null) {
                                stationLines.put(lineName, stationLines.get(lineName) + 1);
                            } else {
                                stationLines.put(lineName, 1);
                            }
                        }
                    }
                    i++;
                }

                int max = -1;
                String lineName = null;
                for(String key : stationLines.keySet()) {
                    int currentVal = stationLines.get(key);
                    if(currentVal > max) {
                        max = currentVal;
                        lineName = key;
                    }
                }

                return lineName;
            }
        }

        int i = 0;
        for(LinkedHashMap<String, Integer> stations : lines.values()) {
            for(String station : stations.keySet()) {
                if(station.equals(currentStationName)) {
                    return lineNames[i];
                }
            }
            i++;
        }

        return null;
    }

}
