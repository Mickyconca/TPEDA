package model;

import java.util.Objects;

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

  public String getName() {
    return name;
  }

  public double getSimilarity() {
    return similarity;
  }

  public void setSimilarity(double similarity) {
    this.similarity = similarity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PlaceLocation)) return false;
    PlaceLocation that = (PlaceLocation) o;
    return Objects.equals(getName(), that.getName());
  }

  @Override
  public String toString() {
    return name;
  }
}
