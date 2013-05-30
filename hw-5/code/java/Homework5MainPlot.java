/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcr.homework5;

import org.math.plot.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author Iuri Andrade, Oscar Lima
 */
public class Homework5MainPlot {

    public static void main(String args[]) {
        CityReader reader = new CityReader();
        ArrayList<Double> costFunc = new ArrayList<>();
        ArrayList<City> citiesList = reader.readCitiesFromFile("100_biggest_cities_germany.txt");
        Path tour = new Path(citiesList, "Bonn");
        System.out.println(tour.totalDistance());
        //System.out.println(tour.distanceDictionaryToString());
        SimulatedAnnealing sa = new SimulatedAnnealing();
        tour = sa.search(tour, costFunc);
        System.out.println(tour);
        //tour = sa.recursiveSearchImprove(tour);
        //System.out.println(tour.totalDistance());
        
        double[] x = tour.getCitiesX();
        double[] y = tour.getCitiesY();
        
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // define the legend position
        plot.addLegend("SOUTH");

        // add a line plot to the PlotPanel
        plot.addScatterPlot("Cities", x, y);
        plot.addLinePlot("Tour Path", x, y);

        // put the PlotPanel in a JFrame like a JPanel
        JFrame frame = new JFrame("Plot tour " + tour.totalDistance() + " km");
        frame.setSize(800, 800);
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        long size = costFunc.size();
        double[] costArray = listToArray(costFunc);
        double[] y2 = new double[costArray.length];
        for(int i = 0; i < costArray.length; i++)
            y2[i]=i;
        Plot2DPanel cost = new Plot2DPanel();

        // define the legend position
        cost.addLegend("SOUTH");

        // add a line plot to the PlotPanel
        cost.addLinePlot("Cost x Iterations", y2, costArray);

        // put the PlotPanel in a JFrame like a JPanel
        JFrame frame2 = new JFrame("Plot cost function " + tour.totalDistance() + " km");
        frame2.setSize(800, 800);
        frame2.setContentPane(cost);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static double[] listToArray(ArrayList<Double> list){
        double[] ret = new double[list.size()];
        for(int i = 0; i < list.size(); i++){
            ret[i] = list.get(i);
        }
        return ret;
    }
    
}
