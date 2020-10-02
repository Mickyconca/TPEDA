import model.BusDijkstra;
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
    Controller controller = new Controller();
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


    // se lee el archivo
    String fileName= "/paradas-de-colectivo.csv"; InputStream is =
            Start.class.getResourceAsStream(fileName );

    Reader in = new InputStreamReader(is);
    Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in);

    // se crea el grafo
    BusDijkstra graph = new BusDijkstra(false); // para mi tendria que ser siempre dirigido

    // voy agregando los nodos
    for (CSVRecord record : records) {
      graph.addNode(record.get("stop_id"),record.get("route_short_name"), Double.parseDouble(record.get("stop_lat")), Double.parseDouble(record.get("stop_lon")), Integer.parseInt(record.get("direction_id")));
    }
    graph.addEdges();
    System.out.println(graph.getSize());
    graph.printAristas("204598");    // Aca esta el print Aristas
    in.close();


  }

  public static void cors() {
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
