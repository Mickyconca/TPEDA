import model.BusDijkstra;
import model.SearchLocation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static spark.Spark.*;
import static utils.Json.json;

public class Start {

  public static void main(String[] args) throws IOException {

    // se lee el archivo de paradas
    String busStopsFile= "/paradas-de-colectivo.csv"; InputStream isBus =
            Start.class.getResourceAsStream(busStopsFile);

    Reader inBus = new InputStreamReader(isBus);
    Iterable<CSVRecord> recordsBus = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(inBus);

    // se crea el grafo
    BusDijkstra graph = new BusDijkstra(false);

    // voy agregando los nodos
    for (CSVRecord record : recordsBus) {
      graph.addNode(record.get("stop_id"),record.get("route_short_name"), Double.parseDouble(record.get("stop_lat")), Double.parseDouble(record.get("stop_lon")), Integer.parseInt(record.get("direction_id")));
    }

    inBus.close();

    // se lee el archivo de etaciones de subte
    String subwayFile= "/estaciones-de-subte.csv"; InputStream isSubway =
            Start.class.getResourceAsStream(subwayFile);
    Reader inSubway = new InputStreamReader(isSubway);
    Iterable<CSVRecord> recordsSubway = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(inSubway);

    for (CSVRecord record : recordsSubway) {
      graph.addNode(record.get("id"), record.get("linea"), Double.parseDouble(record.get("lat")), Double.parseDouble(record.get("long")), 1);  // no importa la direccion ya que va y vuelve por el mismo lado
    }

    inSubway.close();

    graph.addEdges();

    // se lee el archivo de centros culturales
    String culturalPlacesFile= "/espacios-culturales.csv"; InputStream isCulturalPlaces =
            Start.class.getResourceAsStream(culturalPlacesFile);

    Reader inCulturalPlaces = new InputStreamReader(isCulturalPlaces);
    Iterable<CSVRecord> recordsCulturalPlaces = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(inCulturalPlaces);

    SearchLocation locations = new SearchLocation();

    for (CSVRecord record : recordsCulturalPlaces) {
      locations.addLocation(record.get("establecimiento"), Double.parseDouble(record.get("latitud")), Double.parseDouble(record.get("longitud")));
    }
    inCulturalPlaces.close();


    Controller controller = new Controller(graph,locations);
    cors();
    after((req, res) -> res.type("application/json"));
    get("/path", (req, res) -> {
      double fromLat = Double.parseDouble(req.queryParams("fromLat"));
      double fromLng = Double.parseDouble(req.queryParams("fromLng"));
      double toLat = Double.parseDouble(req.queryParams("toLat"));
      double toLng = Double.parseDouble(req.queryParams("toLng"));
      return controller.findPath(fromLat, fromLng, toLat, toLng);
    }, json());
    get("/place", (req, res) -> {
      String searchTerm = req.queryParams("searchTerm");
      return controller.findPlaces(searchTerm);
    }, json());
  }

  private static void cors() {
    before((req, res) -> {
      res.header("Access-Control-Allow-Methods", "*");
      res.header("Access-Control-Allow-Origin", "*");
      res.header("Access-Control-Allow-Headers", "*");
      res.header("Access-Control-Allow-Credentials", "true");
    });
    options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }
      return "OK";
    });
  }
}
