/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcr.homework5;

/**
 *
 * @author Iuri Andrade, Oscar Lima
 */
public class City {

    private String name;
    //City localization (latitude and longitude)
    private double latY;
    private double lonX;

    public City() {
    }

    /**
     * @param name The name of the city
     * @param latitude The latitude of the city
     * @param longitude The longitude of the city
     */
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latY = latitude;
        this.lonX = longitude;
    }

    /**
     * @param targetCity The reference to get distance to
     * @return straight XY distance between this city and the target city
     */
    public double getDistanceXY(City targetCity) {
        return Math.sqrt(Math.pow(targetCity.getLatY() - this.latY, 2) + Math.pow(targetCity.getLonX() - this.lonX, 2));
    }

    /**
     * @param targetCity The reference to get distance to
     * @return Real Km distance between this city and the target city
     */
    public double getDistanceKm(City targetCity) {
        double earthRadius = 6378.1370;
        double latitudeDist = Math.toRadians(targetCity.getLatY() - this.getLatY());
        double longitudeDist = Math.toRadians(targetCity.getLonX() - this.getLonX());
        double sinLatitudeDist = Math.sin(latitudeDist / 2);
        double sinLongitudeDist = Math.sin(longitudeDist / 2);
        double a = Math.pow(sinLatitudeDist, 2) + Math.pow(sinLongitudeDist, 2)
                * Math.cos(Math.toRadians(this.getLatY())) * Math.cos(Math.toRadians(targetCity.getLatY()));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = earthRadius * c;
        return distanceKm;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the latY
     */
    public double getLatY() {
        return latY;
    }

    /**
     * @param latY the latY to set
     */
    public void setLatY(double latY) {
        this.latY = latY;
    }

    /**
     * @return the lonX
     */
    public double getLonX() {
        return lonX;
    }

    /**
     * @param lonX the lonX to set
     */
    public void setLonX(double lonX) {
        this.lonX = lonX;
    }

    @Override
    /**
     * @return a nice print of the City object
     */
    public String toString() {
        return "\n" + this.getName() + " at: latitude " + this.getLatY() + ", longitude " + this.getLonX();
    }
}