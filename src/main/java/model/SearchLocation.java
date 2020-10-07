package model;

import java.util.*;

public class SearchLocation {

    private Map<String,PlaceLocation> locations = new HashMap<>();
    private static final double MIN_SIMILARITY = 0.4;
    private static final int LIST_SIZE = 10;

    public void addLocation(String name, double lat, double lng){
        locations.putIfAbsent(name,new PlaceLocation(name,lat,lng));
    }

    public List<PlaceLocation> search(String searchTerm){
        List<PlaceLocation> foundLocations = new ArrayList<>();
        double aux;
        for(PlaceLocation loc : locations.values()){
            aux = QGram.similarity(searchTerm.toUpperCase(), loc.getName().toUpperCase());
            if(aux - MIN_SIMILARITY >= 0){
                loc.setSimilarity(aux);
                foundLocations.add(loc);
                System.out.println(loc.getName()+ " " + loc.getSimilarity() + " aux: " + aux);
            }
        }
        foundLocations.sort((o1, o2) -> (o2.getSimilarity() - o1.getSimilarity())>0? 1:(o2.getSimilarity() - o1.getSimilarity())==0? 0:-1);
        return foundLocations.subList(0,LIST_SIZE);
    }
}
