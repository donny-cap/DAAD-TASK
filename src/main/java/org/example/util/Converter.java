package org.example.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Converter {
    private final Set<String> checkedConnections;
    private final LinkedHashMap<String, LinkedHashMap<String, Integer>> lines;
    private final List<String> nodes;
    private final ArrayList<ArrayList<Integer>> edges;

    public Converter(LinkedHashMap<String, LinkedHashMap<String, Integer>> lines) {
        this.lines = lines;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.checkedConnections = new HashSet<>();
    }

    public ArrayList<ArrayList<Integer>> convertToMap() {
        init();
        Set<String> keySet = lines.keySet();
        String[] lineNames = keySet.toArray(new String[keySet.size()]);
        int index = 0;
        for(LinkedHashMap<String, Integer> stations : lines.values()) {
            for(String stationName : stations.keySet()) {
                if(!checkedConnections.contains(stationName)) {
                    String[] arrayLine = getKeyArrayFromLinkedMap(stations);
                    List<String> nearNodes = getNearNodeNames(arrayLine, findIndex(arrayLine, stationName));
                    if(stationName.contains("-")) {
                        for(int i = index + 1; i < lineNames.length; i++) {
                            LinkedHashMap<String, Integer> stationsOfOtherLine = lines.get(lineNames[i]);
                            for(String name : stationsOfOtherLine.keySet()) {
                                if(name.equals(stationName)) {
                                    arrayLine = getKeyArrayFromLinkedMap(stationsOfOtherLine);
                                    nearNodes.addAll(getNearNodeNames(arrayLine, findIndex(arrayLine, name)));
                                }
                            }
                        }
                    }
                    checkedConnections.add(stationName);
                    setNodes(nearNodes, stations.keySet(), stationName);
                }
            }
            index++;
        }
        return edges;
    }

    private void init() {
        Set<Integer> checkedNodes = new HashSet<>();
        for (LinkedHashMap<String, Integer> stations : lines.values()) {
            for(String name : stations.keySet()) {
                if(!checkedNodes.contains(stations.get(name))) {
                    nodes.add(name);
                    checkedNodes.add(stations.get(name));
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            edges.add(new ArrayList<>());
        }
    }

    private void setNodes(List<String> nodeNames, Set<String> line, String currentNodeName) {
        for(String nodeName : nodeNames) {
            if(true) {
                int source = getNodeNumByNodeName(currentNodeName);
                int target = getNodeNumByNodeName(nodeName);
                edges.get(source).add(target);
            }
        }
    }
    private String[] getKeyArrayFromLinkedMap(LinkedHashMap<String, Integer> linkedHashMap) {
        Set<String> keySet = linkedHashMap.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }

    private int findIndex(String[] array, String nodeName)
    {
        if (array == null) {
            return -1;
        }
        int len = array.length;
        int i = 0;

        while (i < len) {
            if (array[i].equals(nodeName)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

    private int getNodeNumByNodeName(String name) {
        for(Map<String, Integer> stations : lines.values()) {
            if(stations.containsKey(name)) {
                return stations.get(name);
            }
        }
        return -1;
    }

    private List<String> getNearNodeNames(String[] stations, int index) {
        List<String> result = new ArrayList<>();

        Integer leftNodeIndex = index - 1 < 0 || index - 1 >= stations.length ? null : index - 1;
        Integer rightNodeIndex = index + 1 < 0 || index + 1 >= stations.length ? null : index + 1;

        if(leftNodeIndex != null) {
            result.add(stations[leftNodeIndex]);
        } if(rightNodeIndex != null) {
            result.add(stations[rightNodeIndex]);
        }

        return result;
    }

    public List<String> getNodes() {
        return nodes;
    }

}
