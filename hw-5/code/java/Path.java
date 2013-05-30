/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcr.homework5;

import java.util.ArrayList;

/**
 *
 * @author Iuri Andrade, Oscar Lima
 */
public class Path {

    private static double[][] distanceDictionary;
    private ArrayList<Integer> indexedTour;
    private ArrayList<City> citiesList;

    public Path(ArrayList<City> citiesList, int indexOfStartCity) {
        this.citiesList = citiesList;
        Path.distanceDictionary = createDistanceDictionary();
        this.indexedTour = createInitialPath(indexOfStartCity);
    }

    private Path(ArrayList<City> citiesList) {
        this.citiesList = citiesList;
        this.indexedTour = new ArrayList();
    }

    public Path(ArrayList<City> citiesList, String startCityName) {
        this.citiesList = citiesList;
        Path.distanceDictionary = createDistanceDictionary();
        this.indexedTour = createInitialPath(indexOf(startCityName));
    }

    private double[][] createDistanceDictionary() {
        int numCities = this.citiesList.size();
        City cityI;
        double[][] distances = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            cityI = this.citiesList.get(i);
            for (int j = 0; j < numCities; j++) {
                distances[i][j] = cityI.getDistanceKm(this.citiesList.get(j));
            }
        }
        return distances;
    }

    private ArrayList<Integer> createInitialPath(int indexOfStartCity) {
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(indexOfStartCity);
        double minDistance;
        int indexToAdd = 0;
        int i = indexOfStartCity;
        while (tour.size() < this.citiesList.size()) {
            minDistance = Double.MAX_VALUE;
            for (int j = 0; j < distanceDictionary[i].length; j++) {
                if (i != j && !tour.contains(j)) {
                    if (minDistance > distanceDictionary[i][j]) {
                        minDistance = distanceDictionary[i][j];
                        indexToAdd = j;
                    }
                }
            }
            i = indexToAdd;
            tour.add(indexToAdd);
        }
        tour.add(indexOfStartCity);
        return tour;
    }

    private int indexOf(String cityName) {
        for (int i = 0; i < citiesList.size(); i++) {
            if (citiesList.get(i).getName().equalsIgnoreCase(cityName)) {
                return i;
            }
        }
        System.out.println("Starting city " + cityName + " not found");
        return citiesList.size() + 1;
    }

    public double totalDistance() {
        double distance = 0;
        for (int i = 1; i < this.getPathSize(); i++) {
            distance += this.distanceDictionary[this.indexedTour.get(i - 1)][this.indexedTour.get(i)];
        }
        return distance + this.distanceDictionary[this.indexedTour.get(indexedTour.size() - 1)][indexedTour.get(0)];
    }

    public Path clonePathAndSwapCities(int index1, int index2) {
        Path newPath = new Path(this.citiesList);
        for (int i = 0; i < this.indexedTour.size(); i++) {
            newPath.indexedTour.add(this.indexedTour.get(i));
        }

        newPath.indexedTour.set(index1, this.indexedTour.get(index2));
        newPath.indexedTour.set(index2, this.indexedTour.get(index1));
        return newPath;
    }

    public int getPathSize() {
        return this.indexedTour.size();
    }

    @Override
    public String toString() {
        String str = "Tour {\n";
        for (int i = 0; i < indexedTour.size(); i++) {
            str += indexedTour.get(i) + "\t" + citiesList.get(indexedTour.get(i)).getName() + "->\n";
        }
        str += indexedTour.get(0) + "\t" + citiesList.get(indexedTour.get(0)).getName() + " }";
        str += "\nTour total distance:\t" + totalDistance();
        return str;
    }

    public double[] getCitiesX() {
        double[] x = new double[this.citiesList.size() + 1];
        for (int i = 0; i < this.citiesList.size(); i++) {
            x[i] = this.citiesList.get(this.indexedTour.get(i)).getLonX();
        }
        x[this.citiesList.size()] = this.citiesList.get(this.indexedTour.get(0)).getLonX();
        return x;
    }

    public double[] getCitiesY() {
        double[] y = new double[this.citiesList.size() + 1];
        for (int i = 0; i < this.citiesList.size(); i++) {
            y[i] = this.citiesList.get(this.indexedTour.get(i)).getLatY();
        }
        y[this.citiesList.size()] = this.citiesList.get(this.indexedTour.get(0)).getLatY();
        return y;
    }

    public String distanceDictionaryToString() {
        String ret = "";
        for (int i = 0; i < this.citiesList.size(); i++) {
            ret += "\n";
            for (int j = 0; j < this.citiesList.size(); j++) {
                ret += "[" + i + "]" + "[" + j + "]" + " " + this.distanceDictionary[i][j] + "\t";
            }
        }
        return ret;
    }
}
