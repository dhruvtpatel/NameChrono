import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 * Creates the graphical user interface (GUI) of the program and also codes the graph which plots changes in popularity of a specified name
 *
 * @author Dhruv Patel
 * @version 12.15.2021
 */
public class Drawing
{
    // instance variables - replace the example below with your own
    private SimpleCanvas canvas;
    private int width;
    private int height;
    private int plotW;
    private int plotH;
    private int plotX0;
    private int plotY0;
    private Names names;

    /**
     * Method to create the GUI for the program (uses a grid layout, creates buttons, and adds action listeners to the buttons)
     * 
     * @throws FileNotFoundException
     */
    public Drawing() throws FileNotFoundException {
        names = new Names();
        width = 1000;
        height = 500;
        plotW = (int)(width * 0.8);
        plotH = (int)(height * 0.8);
        plotX0 = (int)(width * 0.1);
        plotY0 = (int)(height * 0.9);
    }

    /**
     * Method to draw the grid lines and tick marks for each graphical quantity
     */
    public void drawGridLines() {
        JFrame frame = new JFrame();
        String name = JOptionPane.showInputDialog(frame, "Enter name:");
        name = name.substring(0,1).toUpperCase() + name.substring(1, name.length());

        if (names.getRecordByName(name) == null) {
            JFrame frame1 = new JFrame();
            JOptionPane.showMessageDialog(frame,"Name not found","Name error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        canvas = new SimpleCanvas("Graph of Name Popularity", 1000, 500);

        Font bigFont = new Font("Calibri", Font.PLAIN, 28);
        canvas.setFont(bigFont);
        canvas.drawString("'" + name + "' " + " Popularity Through the Decades", 300, 40);

        canvas.drawLine(plotX0, plotY0, plotX0 + plotW, plotY0);
        canvas.drawLine(plotX0, plotY0, plotX0, plotY0 - plotH);

        Font normalFont = new Font("Calibri", Font.PLAIN, 12);
        canvas.setFont(normalFont);

        canvas.drawString("Popularity", 7, 250);
        canvas.drawString("Decade", 500, 497);

        int xtick = 1900;
        int ytick = 0;

        for (int i=plotX0; i <= plotW + plotX0; i = i + plotW/10) {
            canvas.drawLine(i, plotY0-10, i, plotY0+10);

            String xTickLabel = Integer.toString(xtick);
            canvas.drawString(xTickLabel, i, plotY0+28);
            xtick = xtick + 10;
        }

        for (int i=plotY0-plotH; i <= plotY0; i+=(plotH/10)) {
            canvas.drawLine(plotX0-5, i, plotX0+5, i);

            String yTickLabel = Integer.toString(ytick);
            canvas.drawString(yTickLabel, plotX0-35, i);
            ytick = ytick + 100;
        }

        double scalingFactor = 0.4;

        int xCurrent = 0;
        int yCurrent = 0;
        int xOld = plotX0 + 0;
        int yOld = (int) ((names.getRecordByName(name).getData(0) * scalingFactor)+(height*0.1));
        int lastRank = names.getRecordByName(name).getData(0);

        NameRecord nr = names.getRecordByName(name);

        ArrayList<Integer> yearsWithoutData = new ArrayList<>();

        canvas.setForegroundColour(Color.BLUE);

        for (int i = 0; i < 11; i++) {
            int curRank = names.getRecordByName(name).getData(i);

            xCurrent =  plotX0 + ((plotW/10) * i);
            if (curRank == 0) {
                curRank = 1000;
                canvas.drawString("NR", xCurrent, plotY0-(plotY0/40));
            }

            yCurrent = (int) ((curRank * scalingFactor)+(height*0.1));

            if (lastRank == 1000) {
                canvas.drawLine(xOld, yOld, xCurrent, yOld);
                canvas.drawLine(xCurrent, yOld, xCurrent, yCurrent);
            } else {
                canvas.drawLine(xOld, yOld, xCurrent, yCurrent);
            }
            canvas.drawString("" + curRank, xCurrent, yCurrent);

            xOld = xCurrent;
            yOld = yCurrent; 
            lastRank = curRank;
        } 
    }  
}