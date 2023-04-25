package org.example.util;

import org.example.exception.PathNotFound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BFS {
    public static List<List<Integer>> getShortestDistance (
            ArrayList<ArrayList<Integer>> adj,
            int s, int dest, int v)
    {
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (!findShortestPath(adj, s, dest, v, pred, dist)) {
            throw new PathNotFound("Given source and destination are not connected");
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        Collections.reverse(path);
        return new ArrayList<>(){{
            add(Collections.singletonList(dist[dest]));
            add(path);
        }};
    }
    public static boolean findShortestPath(ArrayList<ArrayList<Integer>> adj, int src,
                                            int dest, int v, int pred[], int dist[])
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[v];

        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        throw new PathNotFound("Given source and destination are not connected");
    }

}
