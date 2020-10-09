package model;

import java.util.*;

public class BusDijkstra {

    private final boolean isDirected;
    private final Map<String, StopNode> nodes;            // como organizamos el mapa? Long por ahora seria el id de la parada
    private static final double RADIO = 0.007;         // radio de 5 cuadras
    public BusDijkstra(boolean isDirected) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
    }

    public void addNode(String stopId, String shortName, double latitude, double longitude, int direction){ //lo cree public para poder acceder del Start
        nodes.putIfAbsent(stopId, new StopNode(stopId, shortName, latitude, longitude, direction));
    }

    public StopNode getNode(String id){
        return nodes.get(id);
    }

    private double distance(StopNode stop1, StopNode stop2){
        return Math.abs(stop1.latitude - stop2.latitude) + Math.abs(stop1.longitude - stop2.longitude);
    }

    public void addEdges(){
        StopNode[] vector = nodes.values().toArray(new StopNode[0]);
        for (int i = 0; i < vector.length-1; i++) {
            for (int j = i+1; j < vector.length; j++){
                double dist = distance(vector[i], vector[j]);
                if(vector[i].equals(vector[j])){
                    addEdge(vector[i], vector[j], dist);
                }
                else if(dist < RADIO){
                    addEdge(vector[i], vector[j], dist + 2);
                }
            }
        }
    }

    private void addEdge(StopNode stop1, StopNode stop2, double weight){
        if(stop1 == null || stop2 == null){
            return;
        }
        stop1.edges.add(new Edge(stop2, weight));
        if (!isDirected) {                                      // No es dirigido pero se podria implementar en caso de querer que sea dirigido
            stop2.edges.add(new Edge(stop1, weight));
        }
    }

    private void deleteEdges(StopNode node){    // O(e*k)
        Edge toRemove = null;
        for(Edge edge:node.edges){                          // O(e)

            for(Edge edgeTarget : edge.target.edges){       // O(k)
                if(edgeTarget.target.equals(node)){
                    toRemove = edgeTarget;
                }
            }
            if(toRemove != null){
                edge.target.edges.remove(toRemove);
            }
        }
        node.edges=null;
    }

    private StopNode addMapNode(String mapPoint, double latitude, double longitude){            // O(n)
        int direction = 0;                                                          // No me importa la direccion
        StopNode returnNode = new StopNode(mapPoint,mapPoint,latitude,longitude,direction);

        for (StopNode stopNode : nodes.values()) {                          // O(n)
            double dist = distance(returnNode, stopNode);
            if (dist < RADIO) {
                double weight = dist + 1;
                returnNode.edges.add(new Edge(stopNode, weight));
                if (!isDirected) {                                      // No es dirigido pero se podria implementar en caso de querer que sea dirigido
                    stopNode.edges.add(new Edge(returnNode, weight));
                }
            }
        }

        return returnNode;
    }

    public List<BusInPath> path(double fromLat, double fromLng, double toLat, double toLng){    // O((n+e) * log(n) + 2m + 3n + 2e*k + x)
        StopNode begin = addMapNode("begin",fromLat, fromLng);                         // O(n)
        StopNode finish = addMapNode("finish",toLat, toLng);                           // O(n)
        List<StopNode> stopNodeList = pathDijkstra(begin, finish);                              // O((n+e) * log(n) + 2m + n)
        List<BusInPath> toReturn = new ArrayList<>();

        deleteEdges(begin);                                                                     // O(e*k)
        deleteEdges(finish);                                                                    // O(e*k)

        for(int i=1; i< stopNodeList.size()-2; i+=2){
            toReturn.add(new BusInPath(stopNodeList.get(i).shortName, stopNodeList.get(i).latitude, stopNodeList.get(i).longitude, stopNodeList.get(i+1).latitude, stopNodeList.get(i+1).longitude));
        }
        // O(x) , x es un numero chico => no se si habria que tenerlo en cuenta

        return toReturn;
    }

    // pathDijsktra recibe el punto de inicio y final como nodos StopNode
    public List<StopNode> pathDijkstra(StopNode startStop, StopNode endStop){                    // O((n+e) * log(n) + 2m + n)
        nodes.values().forEach(node -> {node.cost = Double.MAX_VALUE;
                                        node.visited = false;
                                        node.previousNode = null;});                              // O(n)

        startStop.cost = 0;
        startStop.visited = false;
        startStop.previousNode = null;

        endStop.cost = Double.MAX_VALUE;
        endStop.visited = false;
        endStop.previousNode = null;


        PriorityQueue<StopNode> queue = new PriorityQueue<>();
        queue.add(startStop);

        while(!queue.isEmpty()){                                                               // O((n+e) * log(n)
            StopNode node = queue.remove();

            if(node.visited) continue;
            node.visited = true;

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

        List<StopNode> list = new ArrayList<>();
        if(endStop.previousNode == null){
            return list;
        }

        for(StopNode current = endStop; current != null; current = current.previousNode){       // O(m)
            list.add(current);
        }

        Collections.reverse(list);                                                             // O(m)

        return list;
    }

    public static class StopNode implements Comparable<StopNode>{
        String shortName;
        String stopId;
        Set<Edge> edges;
        double latitude;
        double longitude;
        int direction;

        double cost;
        boolean visited;
        StopNode previousNode;

        StopNode(String stopId, String shortName, double latitude, double longitude, int direction) {
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

    static class Edge{
        StopNode target;
        double weight;

        Edge(StopNode target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            Edge edge = (Edge) o;
            return Objects.equals(target, edge.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(target, weight);
        }

        @Override
        public String toString() {
            return target.toString();
        }
    }
}
