package com.utility;

import com.level.Board;
import com.level.Field;
import com.model.AdjacencyList;
import com.model.Vertex;

import java.util.*;

public class Utility {

    public static ArrayList<Field> getFieldsInRange(Board board, int id, int range) {
        Field origin = board.getFieldById(id);
        ArrayDeque<Field> deque = new ArrayDeque<>();
        deque.add(origin);

        ArrayList<Field> list;
        HashSet<Field> fieldsInRange = new HashSet<>();

        int neighboursCount = 1;
        int added;
        while (range-- > 0) {
            added = 0;
            while (neighboursCount-- > 0) {
                list = board.getGraph().getNeighbourFields(deque.getFirst());
                deque.addAll(list);
                fieldsInRange.addAll(list);
                added += list.size();
                deque.pop();
            }
            neighboursCount = added;
        }


        return new ArrayList<>(fieldsInRange);
    }

    public static void getShortestPath(AdjacencyList<Field> graph, Field start, Field goal) throws NoSuchElementException {
        if (existingStartGoal(graph, start, goal)) {
            throw new NoSuchElementException("Start or goal field not found");
        }


        Vertex<Field> k = null;
        AdjacencyList<Field> spf = new AdjacencyList<>();

        for (Vertex<Field> v : graph.getAllVertices()) {
            v.setDistance(Integer.MAX_VALUE);
            if (v.same(start)) {
                v.setDistance(0);
                k = v;
                spf.addVertex(v);
            }
        }


        while (!spf.getAllVertices().containsAll(graph.getAllVertices())) {
            assert k != null;

            ArrayList<Vertex<Field>> list = k.getNeighbours();
            for (int i = 0; i < list.size(); i++) {
                if (k.getDistance() != Integer.MAX_VALUE) {
                    if (list.get(i).getDistance() > k.getDistance() + 1) {
                        list.get(i).setDistance(k.getDistance() + 1);
                    }
                }
                list.get(i).setPredecessor(k);
            }

            /*for (Vertex<Field> neighbour : k.getNeighbours()) {
                if (k.getDistance() != Integer.MAX_VALUE) {
                    if (neighbour.getDistance() > k.getDistance() + 1) {
                        neighbour.setDistance(k.getDistance() + 1);
                    }
                }
                neighbour.setPredecessor(k);
            }*/
            k = getNewVertex(graph, spf);
            spf.addVertex(k);
        }
        System.out.println();
    }

    private static boolean existingStartGoal(AdjacencyList<Field> graph, Field start, Field goal) {
        HashSet<Vertex<Field>> set = graph.getAllVertices();
        return set.contains(new Vertex<>(start)) && set.contains(new Vertex<>(goal));
    }

    private static Vertex<Field> getNewVertex(AdjacencyList<Field> original, AdjacencyList<Field> building) {
        Vertex<Field> min = new Vertex<>();
        for (Vertex<Field> vertex: original.getAllVertices()) {
            if (!building.getAllVertices().contains(vertex) && vertex.getDistance() < min.getDistance()) {
                min = vertex;
            }
        }
        return min;
    }
}
