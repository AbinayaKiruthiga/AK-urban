package Dashboard;

import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Dashboard extends javax.swing.JFrame {

    JPanel[] php;                          //panel that hold all the subpanels (panel holding panels)
    JPanel[][] paneltitle, panelvalue;       //paneltitle - holds subtitle label and panel value, panelvalue - hold value label
    JLabel[][] labeltitle, labelvalue;      //labeltitle - hold subtitle name, labelvalue - hold value of the respected subtitle
    static String title_in_title[];         //title_in_title - refers to the title name referred in the db table 'title'
    static int id_in_title[];               //id_in_title - refers to the id referred in the db table 'title' for the respective title
    static int count_of_subtitle[];         //count_of_subtitle - refers to the no of subtitle which is in access
    java.util.List<String>[] subtitlename;  //title in the subpanel
    java.util.List<String>[] value;         //value in the subpanel
    java.util.List<String>[] titlecolor;    //color of the title
    java.util.List<String>[] valuecolor;    //color of the value
    int count = 0;
    Timer timer;
    java.sql.Connection con;

    /*
    
    *   ADDITIONALY THERE IS A SCR0LLPANE AND JPANEL SET THROUGH PALETTE 
    *   SCROLLPANE - named as scroll
    *   JPANEL     - named as frame
    
     */
    public Dashboard() {
        initComponents();
        
        Fetch_Data_from_DataBase();

        // Set up the timer to fetch data every 0.5 seconds
        timer = new Timer(25000, new ActionListener() {
            int sec = 6;

            @Override
            public void actionPerformed(ActionEvent e) {
                sec--;
                System.out.println("sec :" + sec);
                if (sec == 0) {
                    final Timer time = (Timer) e.getSource();
                    sec = 5;
                    this.actionPerformed(e);
                    //Fetch_Data_from_DataBase();

                } else if (sec == 1) {

                    Fetch_Data_from_DataBase();
                    for (int i = 0; i < count; i++) {
                        php[i].setBackground(Color.WHITE);
                    }
                }
            }
        });
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        frame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        scroll.setBackground(new java.awt.Color(255, 255, 255));

        frame.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout frameLayout = new javax.swing.GroupLayout(frame);
        frame.setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1356, Short.MAX_VALUE)
        );
        frameLayout.setVerticalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1205, Short.MAX_VALUE)
        );

        scroll.setViewportView(frame);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Fetch_Data_from_DataBase() {

        frame.removeAll();

        try {

            //ESTABLISHING THE CONNECTION
            Class.forName("java.sql.DriverManager");
            con = (java.sql.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/documents", "root", "abi@2106");
        } catch (Exception ex) {

            System.err.println("An exception occurred: " + ex.getMessage());
        }
        try {
            //   extracting the COUNT of the NO OF TITLES which is ACCESSIBLE 
            Statement stmttiltecount = (Statement) con.createStatement();
            String titlecountqurry = "select count(title) from title where access=1";
            System.out.println("Get extracting the title count -->" + titlecountqurry);
            ResultSet rstitlecount = stmttiltecount.executeQuery(titlecountqurry);

            while (rstitlecount.next()) {
                count = rstitlecount.getInt(1);
            }
            System.out.println("count =" + count);
            int height = (700 / count);
            System.out.println("height : " + height);

            //  extracting the TITLE NAME
            Statement stmttilte = (Statement) con.createStatement();
            String titlequrry = "select title from title where access=1";
            System.out.println("Get extracting the title name -->" + titlequrry);
            ResultSet rstitle = stmttilte.executeQuery(titlequrry);
            title_in_title = new String[count];
            int h = 0;
            while (rstitle.next()) {
                title_in_title[h] = rstitle.getString("title");
                h++;
            }
            System.out.println("TITLE NAME : " + title_in_title[0] + " " + title_in_title[1]);
        } catch (Exception e) {

            System.err.println("An exception occurred: " + e.getMessage());
        }

        try {
            // extracting the  ID of the TITLE 1 AND 2
            Statement stmttilteid = (Statement) con.createStatement();
            String titleidqurry = "select id from title where access=1";
            System.out.println("Get extracting the titles id -->" + titleidqurry);
            ResultSet rstitleid = stmttilteid.executeQuery(titleidqurry);
            id_in_title = new int[count];
            int l = 0;
            while (rstitleid.next()) {
                id_in_title[l] = rstitleid.getInt("id");
                l++;
            }
            System.out.println("TITLE ID : " + id_in_title[0] + " " + id_in_title[1]);
        } catch (Exception e) {

            System.err.println("An exception occurred: " + e.getMessage());
        }

        try {
            //   extracting the COUNT of the NO OF SUBTITLE of TITLE 1,2,3,..... which is ACCESSIBLE 
            count_of_subtitle = new int[count];
            for (int i = 0; i < count; i++) {
                Statement stmtsubtilt1ecount = (Statement) con.createStatement();
                String subtitlecountqurry = "select count(subtitle) from subtitle where access=1 AND title=" + id_in_title[i];
                System.out.println("Get extracting the subtitle count  -->" + subtitlecountqurry);
                ResultSet rssubtitlecount = stmtsubtilt1ecount.executeQuery(subtitlecountqurry);
                while (rssubtitlecount.next()) {
                    count_of_subtitle[i] = rssubtitlecount.getInt(1);
                }
                System.out.println("count of subtitle : " + count_of_subtitle[i]);
            }

        } catch (Exception e) {

            System.err.println("An exception occurred: " + e.getMessage());
        }

        try {
            //   extracting the NAME of SUBTITLES  
            subtitlename = new ArrayList[count];
            for (int i = 0; i < count; i++) {

                Statement stmtsubtilt1e1name = (Statement) con.createStatement();
                String subtitle1namequrry = "select subtitle from subtitle where access=1 AND title=" + id_in_title[i];
                System.out.println("Get extracting the name of subtitles   -->" + subtitle1namequrry);
                ResultSet rssubtitle1name = stmtsubtilt1e1name.executeQuery(subtitle1namequrry);
                subtitlename[i] = new ArrayList<>();
                int k = 0;
                while (rssubtitle1name.next()) {
                    System.out.println("first k : " + k);
                    subtitlename[i].add(rssubtitle1name.getString("subtitle"));
                    k++;
//                        
                }
            }
            for (int i = 0; i < count; i++) {
                System.out.println("name of subtitle of titles : " + " " + subtitlename[i]);
            }
        } catch (Exception e) {

            System.err.println("An exception occurred: " + e.getMessage());
        }

        try {
            //		extracing the 	VALUES,		TITLE COLOR,	VALUE COLOR     of TITLE 1,2,....
            value = new ArrayList[count];
            titlecolor = new ArrayList[count];
            valuecolor = new ArrayList[count];

            for (int i = 0; i < count; i++) {

                Statement stmtvalueoftitle = (Statement) con.createStatement();
                String valueoftitlequrry = "select h.value,h.titlecolor,h.valuecolor from head as h,subtitle as s where h.subtitle=s.id AND s.access=1 AND h.title=" + id_in_title[i];
                System.out.println("Get extracting the value,titlecolor,valuecolor of title 1,2,,, \n   -->" + valueoftitlequrry);
                ResultSet valueoftitlename = stmtvalueoftitle.executeQuery(valueoftitlequrry);

                value[i] = new ArrayList<>();
                titlecolor[i] = new ArrayList<>();
                valuecolor[i] = new ArrayList<>();
                System.out.println("count_of_subtitle : " + count_of_subtitle[i]);
                int k = 0;
                while (valueoftitlename.next()) {
                    value[i].add(Integer.toString(valueoftitlename.getInt("value")));
                    titlecolor[i].add(valueoftitlename.getString("titlecolor"));
                    valuecolor[i].add(valueoftitlename.getString("valuecolor"));
                    k++;
                }
            }

            for (int i = 0; i < count; i++) {
                System.out.println("values : " + value[i] + " \ntitle color : " + titlecolor[i] + "\nvalue color : " + valuecolor[i]);
            }
        } catch (Exception e) {

            System.err.println("An exception occurred: " + e.getMessage());
        }

        //              END OF SQL                      
        int max = count_of_subtitle[0];
        for (int u = 0; u < count; u++) {
            if (count_of_subtitle[u] > max) {
                max = count_of_subtitle[u];
            }
        }
        System.out.println("\nmax : " + max);

        //              INSERTION OF LABELS AND PANELS      
        php = new JPanel[count];
        paneltitle = new JPanel[count][max];
        panelvalue = new JPanel[count][max];
        labeltitle = new JLabel[count][max];
        labelvalue = new JLabel[count][max];
        int x = 10;
        int y = 5;
        int initiallWidth = 1320;

        for (int i = 0; i < count; i++) {

            //Alligning all subpanels to the left of the mainpanal php
            php[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));

            //setting the size and location of each main panel(php)
            int r = (count_of_subtitle[i] / 5) + 1;
            if (count_of_subtitle[i] % 5 == 0) {
                r--;
            }
            System.out.println("row : " + r);
            php[i].setBounds(x, y, initiallWidth, 170 * r + 10);
            System.out.println("170 * r + 10 : " + (170 * r + 10));
            y += 170 * r + 10;

            //setting the white color to the php
            String panelcolor = "#FFFFFF";
            php[i].setBackground(Color.decode(panelcolor));

            for (int j = 0; j < count_of_subtitle[i]; j++) {

                paneltitle[i][j] = new JPanel();
                panelvalue[i][j] = new JPanel();
                //adding color to the title panel and value panel 
                paneltitle[i][j].setBackground(Color.decode(titlecolor[i].get(j)));
                panelvalue[i][j].setBackground(Color.decode(valuecolor[i].get(j)));

                //adding subtitlename into the label
                labeltitle[i][j] = new JLabel(subtitlename[i].get(j));
                //  MIDDLE ALLIGNMENT of labelvalue in panelvalue
                panelvalue[i][j].setLayout(new GridBagLayout());
                labelvalue[i][j] = new JLabel(value[i].get(j), SwingConstants.LEFT);
                //  CENTER ALLIGNMENT of title label
                labeltitle[i][j].setAlignmentX(Component.CENTER_ALIGNMENT);
                // Create a custom font
                Font customFontForTitle = new Font("Times New Roman", Font.PLAIN, 22); // Change "Arial" to your desired font family  - WORKING PERFECTLY
                Font customFontForValue = new Font("Times New Roman", Font.PLAIN, 48); // Change "Arial" to your desired font family
                // Set the custom font for the JLabel
                labeltitle[i][j].setFont(customFontForTitle);
                labelvalue[i][j].setFont(customFontForValue);
                // Add the label,panel to the respective panels 
                panelvalue[i][j].add(labelvalue[i][j]);
                paneltitle[i][j].add(labeltitle[i][j]);
                paneltitle[i][j].add(panelvalue[i][j]);

                //creating margin and adding in to the paneltitle
                int marginSize = 5;
                Border margin = BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode(valuecolor[i].get(j))), // Margin color
                        BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize) // Margin size
                );

                paneltitle[i][j].setBorder(margin);
                //fixing the size of the subpanels
                paneltitle[i][j].setPreferredSize(new Dimension(250, 150));
                //  CENTER ALLIGNMENT of title label
                paneltitle[i][j].setLayout(new BoxLayout(paneltitle[i][j], BoxLayout.Y_AXIS));
                //create title border 
                TitledBorder subPanelBorder = BorderFactory.createTitledBorder(title_in_title[i]);
                //insert that created title border into php panel
                php[i].setBorder(subPanelBorder);
                // Create a custom font for the title
                Font customFont = new Font("Times New Roman", Font.PLAIN, 20);
                // Set the custom font to the titled border
                subPanelBorder.setTitleFont(customFont);

                //adding the sub panel into the panels holding panel  (php)
                php[i].add(paneltitle[i][j]);

            }
            //adding all the main panels (php) to the static panel set through palete 
            frame.add(php[i]);

        }
        
        // Apply the custom ScrollBarUI to the vertical scrollbar
        scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scroll.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        
        //setting the size of the panel (frame) to the size of array of mainpanel (php)     
        frame.setPreferredSize(new Dimension(initiallWidth, y + 20));
        System.out.println("height y : " + (y + 20));

        // Revalidate and repaint the frame to reflect the changes
        frame.revalidate();
        frame.repaint();

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    
   // Custom ScrollBarUI class
    static class CustomScrollBarUI extends BasicScrollBarUI {
        private Color thumbColor = Color.BLACK;
        private Color trackColor = Color.WHITE;
        private Color thumbRolloverColor = Color.BLACK;
        private Color thumbBorderColor = Color.ORANGE;

        @Override
        protected void configureScrollBarColors() {
            // Not necessary to override this method
            super.configureScrollBarColors();
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            // Not necessary to override this method
            return super.createDecreaseButton(orientation);
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            // Not necessary to override this method
            return super.createIncreaseButton(orientation);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            // Custom painting of the scrollbar thumb
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            JScrollBar sb = (JScrollBar) c;
            Color color;
            if (isDragging) {
                color = thumbBorderColor;
            } else if (isThumbRollover()) {
                color = thumbRolloverColor;
            } else {
                color = thumbColor;
            }

            g2.setPaint(color);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 30, 30);
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel frame;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
