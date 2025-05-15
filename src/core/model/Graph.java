/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author scues
 */
public class Graph {

    public final Map<Integer, Node> nodes = new HashMap<>();
    public final Map<Integer, Person> people = new HashMap<>();

    class PQNode {

        Node node;
        int saturation;
        int degree;

        PQNode(Node node, int saturation) {
            this.node = node;
            this.saturation = saturation;
            this.degree = node.adj.size();
        }
    }

    public void coloringBrelaz() {
        Map<Node, Integer> saturation = new HashMap<>();
        PriorityQueue<PQNode> pq = new PriorityQueue<>((a, b) -> {
            // Ordenar por saturación descendente, luego grado descendente
            if (b.saturation != a.saturation) {
                return b.saturation - a.saturation;
            }
            return b.degree - a.degree;
        });

        // Inicializar
        for (Node node : nodes.values()) {
            saturation.put(node, 0);
            node.color = 0;
        }

        // Ordenar nodos por grado y seleccionar el primero
        Node first = Collections.max(nodes.values(), Comparator.comparingInt(n -> n.adj.size()));
        first.color = 1;

        // Actualizar saturación de sus vecinos
        for (Node neighbor : first.adj) {
            saturation.put(neighbor, 1); // Ya ve un color
        }

        // Insertar nodos restantes en la cola
        for (Node node : nodes.values()) {
            if (node.color == 0) {
                pq.add(new PQNode(node, saturation.get(node)));
            }
        }

        // Bucle principal
        while (!pq.isEmpty()) {
            // Obtener el nodo con mayor saturación
            PQNode pqNode = pq.poll();
            Node nextNode = pqNode.node;

            // Encontrar el menor color posible
            Set<Integer> usedColors = new HashSet<>();
            for (Node neighbor : nextNode.adj) {
                if (neighbor.color != 0) {
                    usedColors.add(neighbor.color);
                }
            }

            int color = 1;
            while (usedColors.contains(color)) {
                color++;
            }
            nextNode.color = color;

            // Actualizar saturación de sus vecinos
            for (Node neighbor : nextNode.adj) {
                if (neighbor.color == 0) {
                    Set<Integer> neighborColors = new HashSet<>();
                    for (Node n2 : neighbor.adj) {
                        if (n2.color != 0) {
                            neighborColors.add(n2.color);
                        }
                    }
                    saturation.put(neighbor, neighborColors.size());
                }
            }

            // Limpiar y reconstruir la cola de prioridad con nuevas saturaciones
            pq.clear();
            for (Node node : nodes.values()) {
                if (node.color == 0) {
                    pq.add(new PQNode(node, saturation.get(node)));
                }
            }
        }
    }

    public void addClass(int code) {
        nodes.put(code, new Node());
    }

    public void addPerson(int code) {
        people.put(code, new Person());
    }

    public void addPerson(int personCode, int classCode) {
        Person p = people.get(personCode);
        if (p == null) {
            p = new Person();
            people.put(personCode, p);
        }
        Node n = nodes.get(classCode);
        if (n != null) {
            p.classes.add(n);
            for (Node c : p.classes) {
                if (c != n && !n.adj.contains(c)) {
                    n.adj.add(c);
                    c.adj.add(n);
                }
            }
        }
    }
}
