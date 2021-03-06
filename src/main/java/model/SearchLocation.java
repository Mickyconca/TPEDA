package model;

import java.util.*;

public class SearchLocation {

    private static Map<String,PlaceLocation> locations = new HashMap<>();
    private static final double MIN_SIMILARITY = 0.4;
    private static final int LIST_SIZE = 10;

    public void addLocation(String name, double lat, double lng){
        locations.putIfAbsent(name,new PlaceLocation(name,lat,lng));
    }

    public static List<PlaceLocation> search(String searchTerm){                                                                                         // O(l + m + n + l * log(l) + 1)
        List<PlaceLocation> foundLocations = new ArrayList<>();
        double aux;
        for(PlaceLocation loc : locations.values()){                                                                                              // O(l)
            aux = QGram.similarity(searchTerm.toUpperCase(), loc.getName().toUpperCase());                                                        // O(m + n)
            if(aux - MIN_SIMILARITY >= 0){
                loc.setSimilarity(aux);
                foundLocations.add(loc);
            }
        }
        foundLocations.sort((o1, o2) -> (o2.getSimilarity() - o1.getSimilarity())>0? 1:(o2.getSimilarity() - o1.getSimilarity())==0? 0:-1);     // O(l * log(l))
        if(foundLocations.size() > LIST_SIZE){
            foundLocations.subList(0,LIST_SIZE);                                                                                               // O(1)
        }
        return foundLocations;
    }
}
