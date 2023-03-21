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
public class GUI
{
    // instance variables - replace the example below with your own
    private Names names;
    private JFrame frame;
    private JTextArea textLabel;
    private Drawing draw;
        
    /**
     * Method to create the GUI for the program (uses a grid layout, creates buttons, and adds action listeners to the buttons)
     * 
     * @throws FileNotFoundException
     */
    public GUI() throws FileNotFoundException {
        names = new Names(); 
        draw = new Drawing();
        
        frame = new JFrame("Dhruv's Population Analysis Tool");
        frame.setLayout(new BorderLayout(1,5));
        makeMenuBar(frame);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout(1, 5));

        JPanel bigPanel = new JPanel(new GridLayout(1,2));

        JPanel sideTopPanel = new JPanel(new GridLayout(1,1));
        sideTopPanel.setForeground(Color.BLACK);
        sideTopPanel.setFont(new Font("Calibri", Font.PLAIN, 28));

        textLabel = new JTextArea();
        contentPane.add(textLabel, BorderLayout.NORTH);
        contentPane.add(sideTopPanel, BorderLayout.CENTER);

        textLabel.setBackground(new Color(192,194,201));
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
        textLabel.setEditable(false);

        JScrollPane scroll = new JScrollPane (textLabel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(700, 200));

        textLabel.setLineWrap(true);

        sideTopPanel.add(scroll);
            
        JLabel imageLabel = new JLabel(new ImageIcon(this.getClass().getResource("baby.gif")));
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(192,194,201));
        imagePanel.add(imageLabel);

        bigPanel.add(imagePanel);
        bigPanel.add(sideTopPanel);

        JPanel sideButtonPanel = new JPanel(new GridLayout(4,3));
        sideButtonPanel.setForeground(Color.WHITE);
        sideButtonPanel.setFont(new Font("Calibri", Font.PLAIN, 28));

        JButton buttonOne = new JButton("<html><p>Print names ranked only <b>once</b></p></html>");
        JButton buttonTwo = new JButton("<html><p>Find all names that <b>increase</b> in popularity</p></html>");
        JButton buttonThree = new JButton("<html><p>Find all the names that <b>decrease</b> in popularity</p></html>");
        JButton buttonFour = new JButton("<html><p>Display names that are <b>ranked every decade</b></p></html>");
        JButton buttonFive = new JButton("<html><p>Display the <b>rank</b> of a name <b>in given decade</b></p></html>");
        JButton buttonSix = new JButton("<html><p>Print <b>rank in every decade</b> of given name</p></html>");
        JButton buttonSeven = new JButton("<html><p>Print <b>graph with popularity</b></p></html>");
        JButton buttonEight = new JButton("<html><b>Compare two name</b> popularities</html>");
        JButton buttonNine = new JButton("<html><p>Print <b>most popular name(s)</b> in decade</p></html>");
        JButton buttonTen = new JButton("<html><p>Print <b>name at specific position</b> in specific decade</p></html>");
        JButton buttonEleven = new JButton("<html><p>Print <b>least popular name(s)</b> in decades</p></html>");
        JButton buttonTwelve = new JButton("<html><p>Print <b>change in popularity</b></p></html>");
        JButton buttonThirteen = new JButton("<html><p><b> Quit</b> this tool</p></html>");

        JButton[] buttons = new JButton[13];
        buttons[0] = buttonOne;
        buttons[1] = buttonTwo;
        buttons[2] = buttonThree;
        buttons[3] = buttonFour;
        buttons[4] = buttonFive;
        buttons[5] = buttonSix;
        buttons[6] = buttonSeven;
        buttons[7] = buttonEight;
        buttons[8] = buttonNine;
        buttons[9] = buttonTen;
        buttons[10] = buttonEleven;
        buttons[11] = buttonTwelve;
        buttons[12] = buttonThirteen;

        for (JButton i : buttons) {

            i.setBackground(new Color(30, 61, 89));
            i.setForeground(Color.WHITE);
            i.setFont(new Font("Lucida", Font.PLAIN, 12));

            i.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        i.setBackground(new Color(255, 193, 59));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        i.setBackground(new Color(30, 61, 89));
                    }
                });        

        }

        sideButtonPanel.add(buttonOne);

        buttonOne.addActionListener(e ->
            {
                try
                {
                    printPopularity();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            });

        sideButtonPanel.add(buttonTwo);
        buttonTwo.addActionListener(e -> printPopularityIncreasing());

        sideButtonPanel.add(buttonThree);
        buttonThree.addActionListener(e -> printPopularityDecreasing());

        sideButtonPanel.add(buttonFour);
        buttonFour.addActionListener(e ->
            {
                try
                {
                    printAllDecades();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            });

        sideButtonPanel.add(buttonFive);
        buttonFive.addActionListener(e -> printRankName());

        sideButtonPanel.add(buttonSix);
        buttonSix.addActionListener(e -> printRank());

        sideButtonPanel.add(buttonSeven);
        buttonSeven.addActionListener(e -> printDrawGridLines());

        sideButtonPanel.add(buttonEight);
        buttonEight.addActionListener(e ->printPopularityComparison());

        sideButtonPanel.add(buttonNine);
        buttonNine.addActionListener(e -> printMostPopularName());

        sideButtonPanel.add(buttonTen);
        buttonTen.addActionListener(e -> printPositionReturnName());

        sideButtonPanel.add(buttonEleven);
        buttonEleven.addActionListener(e -> printLeastPopularName());

        sideButtonPanel.add(buttonTwelve);
        buttonTwelve.addActionListener(e -> printChangeInPopularity());

        sideButtonPanel.add(buttonThirteen);
        buttonThirteen.addActionListener(e -> quit());

        bigPanel.add(sideButtonPanel);

        frame.add(bigPanel);
        frame.pack();  
        frame.setVisible(true);
    }

    /**
     * Method to make the menu bar with the quit option
     * 
     * @param frame
     */
    private void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu quitMenu = new JMenu("Quit");
        menubar.add(quitMenu);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> quit());
        quitMenu.add(quitItem);
    }

    /**
     * Method to quit the program
     */
    public void quit() {
        JOptionPane.showMessageDialog(frame,"Thank you for working with this tool!");
        System.exit(0);
    }

    /**
     * Method to set the textLabel to the output of the oneDecades() method in the Names class
     * 
     * @throws IOException
     */
    public void printPopularity() throws IOException {
        textLabel.setText("");
        textLabel.setText(names.oneDecades());
    }

    /**
     * Method to set the textLabel to the output of the popularityIncreasing() method in the Names class
     */
    public void printPopularityIncreasing() {
        textLabel.setText("");
        textLabel.setText(names.popularityIncreasing());
    }

    /**
     * Method to set the textLabel to the ouput of the popularityDecreasing() method in the Names class
     */
    public void printPopularityDecreasing() {
        textLabel.setText("");
        textLabel.setText(names.popularityDecreasing());
    }

    /**
     * Method to set the textLabel to the output of the allDecades() method in the Names class
     *
     * @throws IOException
     */
    public void printAllDecades() throws IOException {
        textLabel.setText("");
        textLabel.setText(names.allDecades());
    }

    /**
     * Method to set the textLabel to the output of the rankName() method in the Names class
     */
    public void printRankName() {
        textLabel.setText("");

        JFrame frame = new JFrame();
        String name = JOptionPane.showInputDialog(frame, "Enter name:"); // creates an input dialog to ask the user for name
        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter decade:")); // creates an input dialog to ask the user for decade

        textLabel.setText(names.rankName(name, decade));
    }

    /**
     * Method to set the textLabel to the output of the printRank() method in Names (with each element expressed as a string)
     */
    public void printRank() {
        textLabel.setText("");

        JFrame frame = new JFrame();
        String name = JOptionPane.showInputDialog(frame, "Enter name:"); // asks user for a name

        for (Object i: names.printRank(name)) {
            textLabel.setText(textLabel.getText() + "" + i); 
        }
    }

    /**
     * Method to set the textLabel to the output of the isOneNameMorePopular() method
     */
    public void printPopularityComparison() {
        textLabel.setText("");

        JFrame frame = new JFrame();
        String name1 = JOptionPane.showInputDialog(frame, "Enter First Name:"); // asks user for a name
        String name2 = JOptionPane.showInputDialog(frame, "Enter Second Name:"); // asks user for a second name
        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Decade:")); // asks user for a decade of choice 

        textLabel.setText(names.isOneNameMorePopular(name1, name2, decade));
    }

    /**
     * Method to set the textLabel to the output of the mostPopularName() method
     */
    public void printMostPopularName() {
        textLabel.setText("");

        JFrame frame = new JFrame();

        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Decade:")); // asks user for a decade of choice

        textLabel.setText(names.mostPopularName(decade));        
    }

    /**
     * Method to set the textLabel to the output of the leastPopularName() method
     */
    public void printLeastPopularName() {
        textLabel.setText("");

        JFrame frame = new JFrame();

        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Decade:")); // asks user for a decade of choice

        textLabel.setText(names.leastPopularName(decade));        
    }

    /**
     * Method to set the textLabel to the output of the positionReturnName() method
     */
    public void printPositionReturnName() {
        textLabel.setText("");

        JFrame frame = new JFrame();
        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Decade:")); // asks user for decade
        int position = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Rank of Name:")); // asks user for rank of name

        textLabel.setText(names.positionReturnName(position, decade));        
    }

    /**
     * Method to set the textLabel to the output of changeInPopularity() method
     */
    public void printChangeInPopularity() {
        textLabel.setText("");

        JFrame frame = new JFrame();
        String name1 = JOptionPane.showInputDialog(frame, "Enter Name:"); // asks user for a name
        int decade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Earlier (Starting) Decade:")); // asks user for the starting decade
        int decade1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Later (Ending) Decade:")); // asks user for an ending decade

        textLabel.setText(names.changeInPopularity(name1, decade, decade1));
    }
    
    public void printDrawGridLines() {
        draw.drawGridLines();
    }
    
    /**
     * Main method for the class -- creates an object of the class.
     * 
     * @throws FileNotFoundException
     * @param args
     */
    public static void main(String args[]) throws FileNotFoundException {
        GUI object = new GUI();
    }
}