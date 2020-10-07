import model.BusDijkstra;
import model.BusInPath;
import model.PlaceLocation;
import model.SearchLocation;

import java.util.List;

public class Controller {

  BusDijkstra bus;
  SearchLocation location;

  public Controller(BusDijkstra bus, SearchLocation location) {
    this.bus = bus;
    this.location = location;
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {   // O((n+e) * log(n))    Orden de la complejidad de findPath
    return bus.path(fromLat, fromLng, toLat, toLng);
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {                                     // O(l * log(l))    Orden de la complejidad de findPlaces
    return location.search(searchTerm);
  }
}
