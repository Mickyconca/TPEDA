package controller;

import model.SearchLocation;

public class TestSearchFactory {

    public static SearchLocation createLocations(){
        SearchLocation locations = new SearchLocation();
        locations.addLocation("cafe palacio", 1.0, 1.0);
        locations.addLocation("ruby", 1.0, 1.0);
        locations.addLocation("rub", 1.0, 1.0);
        locations.addLocation("ruvy", 1.0, 1.0);
        locations.addLocation("rubi", 1.0, 1.0);
        locations.addLocation("vale", 1.0, 1.0);
        locations.addLocation("alale", 1.0, 1.0);
        locations.addLocation("salale", 1.0, 1.0);

        return locations;
    }

}
