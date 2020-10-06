package model;

public class PlaceLocation {

  private double lat;
  private double lng;
  private String name;
  private double similarity;

  public PlaceLocation(String name, double lat, double lng) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getName() {
    return name;
  }

  public double getSimilarity() {
    return similarity;
  }

  public void setSimilarity(double similarity) {
    this.similarity = similarity;
  }
}
