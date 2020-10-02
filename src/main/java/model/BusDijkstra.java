package model;

import java.util.*;

public class BusDijkstra {

    private final boolean isDirected;
    private final Map<String, StopNode> nodes;            // como organizamos el mapa? Long por ahora seria el id de la parada
    private static final double RADIO = 0.005;

    public BusDijkstra(boolean isDirected) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
    }

    public void addNode(String stopId, String shortName, Float latitude, Float longitude, int direction){ //lo cree public para poder acceder del Start
        nodes.putIfAbsent(stopId, new StopNode(stopId, shortName, latitude, longitude, direction));
    }

//    public boolean getNode(String bus){
//        if(nodes.get(bus) != null){
//            return true;
//        }
//        return false;
//    }

    public int getSize(){
        return nodes.size();
    }

    public void printAristas(String stopId){
        System.out.println(String.format("Edges size: " + nodes.get(stopId).edges.size()));     // Para que quede bonito
        nodes.get(stopId).edges.forEach(System.out::println);
    }

    private double distance(StopNode stop1, StopNode stop2){
        return Math.abs(stop1.latitude - stop2.latitude) + Math.abs(stop1.longitude - stop2.longitude);
    }

    private double distanceWalked(StopNode stop1, StopNode stop2){
        return distance(stop1,stop2) * 2;
    }

    public void addEdges(){
        StopNode[] vector = nodes.values().toArray(new StopNode[0]);
        for (int i = 0; i < vector.length-1; i++) {
            for (int j = i+1; j < vector.length; j++){
                if(vector[i].equals(vector[j])){
                    addEdge(vector[i].stopId, vector[j].stopId, distance(vector[i], vector[j]));
                }
                else if(distance(vector[i], vector[j]) < RADIO){
                    addEdge(vector[i].stopId, vector[j].stopId, distanceWalked(vector[i], vector[j]));
                }
            }
        }
    }

    public void addEdge(String stop1, String stop2, double weight){
        StopNode node1 = nodes.get(stop1);
        StopNode node2 = nodes.get(stop2);

        if(node1 == null || node2 == null){
            return;
        }
        node1.edges.add(new Edge(node2, weight));
        if (!isDirected) {                                      // No es dirigido pero se podria implementar en caso de querer que sea dirigido
            node2.edges.add(new Edge(node1, weight));
        }
    }

    // pathDijsktra recibe el punto de inicio y final como nodos StopNode
    public List<StopNode> pathDijkstra(StopNode startStop, StopNode endStop){     // O((n+e) * log(n) + m)
        nodes.values().forEach(node -> {node.cost = Double.MAX_VALUE;
                                        node.visited = false;
                                        node.previousNode = null;});

        startStop.cost = 0;
        PriorityQueue<StopNode> queue = new PriorityQueue<>();
        queue.add(startStop);

        while(!queue.isEmpty()){
            StopNode node = queue.remove();

            if(node.visited) continue;
            node.visited = true;
            System.out.println(node.shortName + ": " + node.cost);

            for(Edge edge : node.edges){
                double newCost = node.cost + edge.weight;
                StopNode nextNode = edge.target;
                if(newCost < nextNode.cost) {
                    nextNode.cost = newCost;
                    nextNode.previousNode = node;
                    queue.add(nextNode);
                }
            }
        }

        // la menor dist a endStop
        List<StopNode> list = new ArrayList<>();
        for(StopNode current = endStop; current != null; current = current.previousNode){       // O(m)
            list.add(current);
        }

        Collections.reverse(list);          // se puede usar???     // O(m)?

        return list;
    }


    class StopNode implements Comparable<StopNode>{
        String shortName;
        String stopId;
        Set<Edge> edges;
        Float latitude;
        Float longitude;
        int direction;

        double cost;
        boolean visited;
        StopNode previousNode;

        StopNode(String stopId, String shortName, Float latitude, Float longitude, int direction) {
            this.stopId = stopId;
            this.shortName = shortName;     // nombre de la linea
            this.latitude = latitude;
            this.longitude = longitude;
            this.direction = direction;     // si es 1 o 0
            this.visited = false;
            edges = new HashSet<>();
        }

        @Override
        public int hashCode(){
            return Objects.hash(shortName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StopNode)) return false;
            StopNode node = (StopNode) o;
            return Objects.equals(shortName, node.shortName) && Objects.equals(direction, node.direction);      // compara por nombre y direccion
        }

        @Override
        public int compareTo(StopNode o) {
            return 0;
        }       // aca tendriamos que comparar distancias??

        @Override
        public String toString() {
            return shortName;
        }
    }

    class Edge{
        StopNode target;
        double weight;

        public Edge(StopNode target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return target.toString();
        }
    }
}
