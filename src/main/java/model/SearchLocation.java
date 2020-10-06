package model;

import java.util.*;

public class SearchLocation {

    private Map<String,PlaceLocation> locations = new HashMap<>();
    private static final double MIN_SIMILARITY = 0.9;

    public void addLocation(String name, double lat, double lng){
        locations.putIfAbsent(name,new PlaceLocation(name,lat,lng));
    }

    public List<PlaceLocation> search(String searchTerm){
        List<PlaceLocation> foundLocations = new ArrayList<>();
        double aux;
        for(PlaceLocation loc : locations.values()){
            aux = QGram.similarity(searchTerm, loc.getName());
            if(aux > MIN_SIMILARITY){
                loc.setSimilarity(aux);
                foundLocations.add(loc);
            }
        }
        foundLocations.sort((o1, o2) -> (int)(o1.getSimilarity() - o2.getSimilarity()));
        return foundLocations;
    }
}
