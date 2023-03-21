// Answers to Analysis Questions
// One interesting trend was how the presence of celebrity figures can often sway the popularity of a name. For instance, for Courtney, we see that between 1960 and 2000, the name moved 528 ranks (determined through a method I added). Though there are many reasons for this, it is likely that the emergence of actress Courtney Love and singer Courtney Cox influenced the popularity of this name.

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Names class looks at NameRecord objects and creates a variety of methods (like printing rank, printing trends in popularity, and more).
 *
 * @author Dhruv Patel
 * @version 12.15.2021
 */
public class Names
{
    // instance variables
    private Scanner scanThrough;
    private File newFile;
    private ArrayList<NameRecord> arrayList;

    /**
     * Constructor for objects of class Names
     * 
     * @throws FileNotFoundException
     */
    public Names() throws FileNotFoundException 
    {
        // initialise instance variables
        String line = "";
        newFile = new File("Names.txt");
        scanThrough = new Scanner(newFile); // scans through the file
        arrayList = new ArrayList<NameRecord>();

        while (scanThrough.hasNextLine()){
            line = scanThrough.nextLine();

            NameRecord a = new NameRecord(line);
            arrayList.add(a);
        }
    }

    /**
     * Method that will be accessed by other methods - finds the NameRecord object for the specific name
     * 
     * @param name
     * @return null
     */
    public NameRecord getRecordByName(String name) {
        for (NameRecord i : arrayList) {
            if (name.equalsIgnoreCase(i.getName())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Method to print the rank of a name in every decade
     * 
     * @param name
     * @return name
     */
    public ArrayList printRank(String name) {
        ArrayList<String> array = new ArrayList<>();  

        NameRecord nr = this.getRecordByName(name);
        if (nr == null) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error", JOptionPane.ERROR_MESSAGE);        
        } // if the name is not found in the data file, print this message

        for (int i = 0; i < 11; i++) {
            int year = 1900 + 10 * i;

            array.add("The popularity in " + year + " was " + nr.getData(i) + "\n");
        } // for each decade, print the popularity

        return array;
    }

    /**
     * Method to print the names that have been ranked in every decade
     * 
     * @return str
     * @throws IOException
     */
    public String allDecades() throws IOException {
        String str = "";

        for (NameRecord i : arrayList) {
            int count = 0;

            for (int t = 0; t < 11; t++) {
                if (i.getData(t) == 0) {
                    count += 1;
                }
            }

            if (count == 0) {
                str += i.getName() + "\n";
            }
        }

        str = str.trim();
        return str;
    }

    /**
     * Method to print the name that has been ranked in only one decade
     * 
     * @return str
     * @throws IOException
     */
    public String oneDecades() throws IOException {
        ArrayList<String> array = new ArrayList<>();
        String str = "";

        for (NameRecord i : arrayList) {
            int count = 0;

            for (int t = 0; t<11; t++) {
                if (i.getData(t) != 0) {
                    count += 1;
                }
            }

            if (count == 1) {
                array.add(i.getName());
                if (array.size() != 0) {
                    str += i.getName() + "\n";
                }
            }
        }

        str = str.trim();
        return str;
    }    

    /**
     * Method to print the rank of a name at a specific decade
     * 
     * @return output
     * @param name
     * @param decade
     */
    public String rankName(String name, int decade) {
        int index = (decade - 1900) / 10;
        
        if (index < 0 || index > 10) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot be calculated","Decade error",JOptionPane.ERROR_MESSAGE);        
        } // if the provided decade is not in the range given in the data set, return an error 

        NameRecord nr = this.getRecordByName(name);
        if (nr == null) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error",JOptionPane.ERROR_MESSAGE);        
        }

        String output = "" + nr.getData(index);

        if (output.equals("0")) {
            output = "Name is not ranked (after the 1000th most popular name)";
        } // if the rank is 0, print statement saying it is after the 1000th most popular name

        return output;
    }

    /**
     * Method to print the names whose popularity has consistently increased
     * 
     * @return str
     */
    public String popularityIncreasing() {
        String str = "";

        for (NameRecord i : arrayList) {
            int count = 0;
            int max = i.getData(0);

            for (int t = 1; t<11; t++) {
                if (i.getData(t) > max) {
                    max = i.getData(t);  
                    count += 1;
                }
            }

            if (count == 10) {
                str += i.getName() + "\n";
            }
        }

        str = str.trim();
        return str;
    }

    /**
     * Method to print the names whose popularity has consistently decreased
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @return str
     */
    public String popularityDecreasing() 
    {
        String str = "";

        for (NameRecord i : arrayList) {
            int count = 0;
            int min = i.getData(0);

            for (int t = 1; t<11; t++) {
                if (i.getData(t) < min) {
                    min = i.getData(t);  
                    count += 1;
                }
            }

            if (count == 10) {
                str += i.getName() + "\n";
            }
        }  

        str = str.trim();
        return str;
    }

    /**
     * Method to determine if one name was more popular than another in a specific decade
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @param name1
     * @param name2
     * @param decade
     * @return output
     */
    public String isOneNameMorePopular(String name1, String name2, int decade) // this is a method personally added (not required by directions) throws Exception throws Exception throws Exception
    {
        String output = "";

        if (name1.equalsIgnoreCase(name2)) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Please input different names","Name error",JOptionPane.ERROR_MESSAGE);
        }

        int modifiedYear = (decade-1900)/10;

        if (modifiedYear > 10 || modifiedYear < 0) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot be calculated","Decade error",JOptionPane.ERROR_MESSAGE);
        } // if the inputted decade is not between 1900 and 2000, print error message

        NameRecord nr = this.getRecordByName(name1);

        if (nr == null) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error",JOptionPane.ERROR_MESSAGE);
        } // if name not found, print error message

        int popYear = nr.getData(modifiedYear);

        NameRecord nr1 = this.getRecordByName(name2);

        if (nr1 == null) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error",JOptionPane.ERROR_MESSAGE);
        } // if name not found, print error message

        int popYear1 = nr1.getData(modifiedYear);
        
        if (popYear < popYear1) {
            output = name1 + " was more popular than " + name2 + " in " + decade;
        }
        else if (popYear > popYear1) {
            output = name2 + " was more popular than " + name1 + " in " + decade;            
        }
        else if (popYear == popYear1) {
            output = "The popularity was equal";
        }

        return output;
    }

    /**
     * Method to determine the most popular name in a specified decade
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @param decade
     * @return str
     */
    public String mostPopularName(int decade) {
        String str = "";

        int index = (decade - 1900) / 10;

        if (index > 10 || index < 0) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot be calculated","Decade error",JOptionPane.ERROR_MESSAGE);
        }

        for (NameRecord i : arrayList) {
            if (i.getData(index) == 1) {
                str += i.getName() + "\n";
            }
        }

        return str;
    }

    /**
     * Method to determine the least popular name in a specified decade
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @param decade
     * @return str
     */
    public String leastPopularName(int decade) {
        String str = "";

        int index = (decade - 1900) / 10;

        if (index > 10 || index < 0) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot be calculated","Decade error", JOptionPane.ERROR_MESSAGE);
        }

        for (NameRecord i : arrayList) {
            if (i.getData(index) == 999) {
                str += i.getName() + "\n";
            }
        }

        return str;
    }

    /**
     * Method to print the name at a specificied rank in any decade
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @param position
     * @param decade
     * @return str
     */
    public String positionReturnName(int position, int decade) {
        String str = "";

        int index = (decade - 1900) / 10; // modify the inputted decade to work with the indices set in NameRecord

        if (index > 10 || index < 0) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot Be Calculated","Decade Error",JOptionPane.ERROR_MESSAGE);    
        } // if the decade input is not between 1900 and 2000, print error message.

        if (position <= 0 || position >= 1000) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Inaccurate Position","Position Error",JOptionPane.ERROR_MESSAGE); 
        } // if a rank is not between 1-999, print error message.

        for (NameRecord i: arrayList) {
            if (position != 0) { // if the position is 0, do not run this statement
                if (i.getData(index) == position) {
                    str = i.getName();
                } 
            }
        }

        return str;
    }           

    /**
     * Method to determine the change in popularity (number of ranks moved)
     * 
     * NOTE: This is a method personally added (not required by directions)
     * 
     * @param name
     * @param decade1
     * @param decade2
     * @return str
     */
    public String changeInPopularity (String name, int decade1, int decade2) {
        String str = "";

        int index1 = (decade1-1900) / 10;
        int index2 = (decade2-1900) / 10;

        if ((index1 > 10 || index1 < 0) || (index2 > 10 || index2 < 0)) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Cannot Be Calculated","Decade Error",JOptionPane.ERROR_MESSAGE);    
        }

        if (decade1 == decade2) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Decades cannot be the same","Decade Error",JOptionPane.ERROR_MESSAGE);    
        }

        NameRecord nr = this.getRecordByName(name);
        int pop1 = nr.getData(index1);
        if (name == null) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error",JOptionPane.ERROR_MESSAGE);
        }

        int pop2 = nr.getData(index2);

        if (pop2 == 0) {
            pop2 = 1000;
        }
        int change = pop2-pop1;

        if(decade1 != decade2) {
            if (pop2 == 1000) {
                str = "The popularity decreased in popularity by at least " + change + " positions";
            }
            else if (change < 0) {
                str = "The popularity grew in popularity " + Math.abs(change) +  " positions";
            }
            else if (change >= 0) {
                str = "The popularity decreased in popularity by " + change + " positions";
            }  
        }

        return str;
    }
    
    //NOTE: Tweleve total method --- six are personally created and not required by directions.
}