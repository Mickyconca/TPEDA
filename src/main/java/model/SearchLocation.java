package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLocation {

    private Map<String,PlaceLocation> locations = new HashMap<>();
    private static final int MIN_SIMILARITY = 1;

    public void addLocation(String name, double lat, double lng){
        locations.putIfAbsent("name",new PlaceLocation(name,lat,lng));
    }

    public List<PlaceLocation> search(String searchTerm){
        List<PlaceLocation> foundLocations = new ArrayList<>();
        for(PlaceLocation loc : locations.values()){
            if(QGram.similarity(searchTerm, loc.getName()) < MIN_SIMILARITY){
                foundLocations.add(loc);
            }
        }
        return foundLocations;
    }
}
