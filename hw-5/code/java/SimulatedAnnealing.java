/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcr.homework5;

import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author Iuri Andrade, Oscar Lima
 */
public class SimulatedAnnealing {

    private double startTemperature = 100d;
    private double coolingRate = 0.000001d;

    public SimulatedAnnealing() {
    }

    public SimulatedAnnealing(double startTemperature, double coolingRate) {
        this.startTemperature = startTemperature;
        this.coolingRate = coolingRate;
    }

    public Path search(Path initialTour) {
        Path bestTour = initialTour;
        double temperature = this.getStartTemperature();
        while (temperature > 1) {
            Random number = new Random();
            int randomIndex1 = number.nextInt(bestTour.getPathSize());
            int randomIndex2 = number.nextInt(bestTour.getPathSize());
            while (randomIndex1 == randomIndex2 || randomIndex1 == 0 || randomIndex2 == 0) {
                randomIndex1 = number.nextInt(bestTour.getPathSize());
                randomIndex2 = number.nextInt(bestTour.getPathSize());
            }
            Path newTour = bestTour.clonePathAndSwapCities(randomIndex1, randomIndex2);
            if (acceptance(bestTour.totalDistance(), newTour.totalDistance(), temperature) > Math.random()) {
                bestTour = newTour;
            }
            temperature *= (1 - this.getCoolingRate());
        }
        return bestTour;
    }

    
    public Path search(Path initialTour, ArrayList<Double> costFunction) {
        Path bestTour = initialTour;
        double temperature = this.getStartTemperature();
        while (temperature > 1) {
            Random number = new Random();
            int randomIndex1 = number.nextInt(bestTour.getPathSize());
            int randomIndex2 = number.nextInt(bestTour.getPathSize());
            while (randomIndex1 == randomIndex2 || randomIndex1 == 0 || randomIndex2 == 0) {
                randomIndex1 = number.nextInt(bestTour.getPathSize());
                randomIndex2 = number.nextInt(bestTour.getPathSize());
            }
            Path newTour = bestTour.clonePathAndSwapCities(randomIndex1, randomIndex2);
            double currentDistance = bestTour.totalDistance();
            double newDistance = newTour.totalDistance();
            if (acceptance(currentDistance, newDistance, temperature) > Math.random()) {
                bestTour = newTour;
                costFunction.add(newDistance);
            }
            temperature *= (1 - this.getCoolingRate());
        }
        return bestTour;
    }
    
    /*
     public Path recursiveSearchImprove(Path initialTour){
     Path bestTour = initialTour;
     Path newTour;
     for(int i = 0; i < 1000; i++){
     newTour = search(bestTour);
     if(newTour.totalDistance() < bestTour.totalDistance())
     bestTour = newTour;
     }
     return bestTour;
     }
     */

    private double acceptance(double currentTourDistance, double newTourDistance, double temperature) {
        if (newTourDistance < currentTourDistance) {
            return 1.0d;
        }
        return Math.exp((currentTourDistance - newTourDistance) / temperature);
    }

    /**
     * @return the startTemperature
     */
    public double getStartTemperature() {
        return startTemperature;
    }

    /**
     * @param startTemperature the startTemperature to set
     */
    public void setStartTemperature(double startTemperature) {
        this.startTemperature = startTemperature;
    }

    /**
     * @return the coolingRate
     */
    public double getCoolingRate() {
        return coolingRate;
    }

    /**
     * @param coolingRate the coolingRate to set
     */
    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }
}
