package model;

import java.util.*;

public class BusDijkstra {

    private final boolean isDirected;
    private Map<String, StopNode> nodes;            // como organizamos el mapa? String por ahora seria el nombre de la linea

    public BusDijkstra(boolean isDirected) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
    }

    public void addNode(String shortName, Float latitude, Float longitude, int direction){ //lo cree public para poder acceder del Start
        nodes.putIfAbsent(shortName, new StopNode(shortName, latitude, longitude, direction));
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

    void addEdge(String label1, String label2, double weight){          //IMPORTANTE
        StopNode node1 = nodes.get(label1);
        StopNode node2 = nodes.get(label2);

        if(node1 == null || node2 == null){
            return;
        }
        //TODO
    }

    public void printDijkstra(String startingLabel){                        // O((N+E) * log(n))
        nodes.values().forEach(node -> node.distance = Double.MAX_VALUE);       //HAY QUE CAMBIAR PRINT DIJKSTRA

        StopNode startingNode = nodes.get(startingLabel);
        startingNode.distance = 0;
        PriorityQueue<StopNode> queue = new PriorityQueue<>();
        queue.add(startingNode);

        while(!queue.isEmpty()){
            StopNode node = queue.remove();

            if(node.visited) continue;
            node.visited = true;

            for(Edge edge : node.edges){
                double newCost = node.distance + edge.weigth;       //distance seria distancia caminada, weight distancia en el mapa
                StopNode nextNode = edge.target;
                if(newCost < nextNode.distance){
                    nextNode.distance = newCost;
                    queue.add(nextNode);
                }
            }
        }
    }
    
    private 

    class StopNode implements Comparable<StopNode>{
        String shortName;
        Set<Edge> edges;
        Float latitude;
        Float longitude;
        int direction;
        double distance = Double.MAX_VALUE;
        boolean visited;

        StopNode(String shortName, Float latitude, Float longitude, int direction) {
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
    }

    class Edge{
        StopNode target;
        double weigth;

        public Edge(StopNode target, double weigth) {
            this.target = target;
            this.weigth = weigth;
        }
    }
}
