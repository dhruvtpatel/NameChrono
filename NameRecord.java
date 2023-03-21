import java.io.*;
import java.util.*;

/**
 * Class to score each line into a name and an ArrayList record
 *
 * @author Dhruv Patel
 * @version 12.15.2021
 */
public class NameRecord
{
    // instance variables
    private String name;
    private ArrayList<Integer> arrayList;
    
    /**
     * Constructor for the NameRecord class
     */
    public NameRecord(String line) {
        arrayList = new ArrayList<Integer>();
        
        String[] parsedData = line.split(" "); // splits between the name and the data
        
        name = parsedData[0];
        
        for (int i = 1; i < parsedData.length; i++) {
            arrayList.add(Integer.parseInt(parsedData[i])); // creates an ArrayList of the data points
        }
    }
    
    /**
     * Gets the name of the inputted line
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the data associated with the provided index
     * 
     * @param index
     * @return arrayList.get(index)
     */
    public int getData(int index) {
        return arrayList.get(index);
    }
    
    /**
     * Sets the name provided a new name (through parameter)
     * 
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }
    
    /**
     * Sets the data with a specific index and value in the arrayList
     * 
     * @param index
     * @param value
     */
    public void setData(int index, int value) {
        arrayList.set(index, value);
    }
}