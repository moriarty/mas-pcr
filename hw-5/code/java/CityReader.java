/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcr.homework5;

/**
 *
 * @author Iuri Andrade, Oscar Lima
 */
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CityReader {

    public CityReader() {
    }

    public ArrayList<City> readCitiesFromFile(String fileName) {
        ArrayList<City> citiesList = new ArrayList<>();
        try {
            BufferedReader inputBuffer = new BufferedReader(new FileReader(fileName));
            String line = inputBuffer.readLine();
            line = inputBuffer.readLine();//Skip the 1st line
            while (line != null) {
                String[] words = line.split("\t");
                String name = words[0];
                double latitude = Double.parseDouble(words[2]);
                double longitude = Double.parseDouble(words[3]);
                citiesList.add(new City(name, latitude, longitude));
                line = inputBuffer.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return citiesList;
    }
}
