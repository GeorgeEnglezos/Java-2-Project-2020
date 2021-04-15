import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
public class CitiesDB {
    static Connection db_con_obj = null; //A connection (session) with a specific database. SQL statements are executed and results are returned within the context
        //of a connection. A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures,
        //the capabilities of this connection, and so on. This information is obtained with the getMetaData method.
    static PreparedStatement db_prep_obj = null;//An object that represents a precompiled SQL statement.
        //A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.

    public static void makeJDBCConnection(JFrame f) {

            try {//We check that the DB Driver is available in our project.
                Class.forName("oracle.jdbc.driver.OracleDriver"); //This code line is to check that JDBC driver is available. Or else it will throw an exception. Check it with 2.
                System.out.println(Text.ANSI_PURPLE+"Found JDBC driver! "+Text.ANSI_RESET);
            } catch (ClassNotFoundException e) {
                System.out.println(Text.ANSI_RED+"Sorry, couldn't find JDBC driver. Make sure you have added JDBC Maven Dependency Correctly"+Text.ANSI_RESET);
                e.printStackTrace();
                return;
            }

            try {
                // DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
                db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl","it here","password here");// Returns a connection to the URL.
                //Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
               /* if (db_con_obj != null) {
                    System.out.println(Text.ANSI_PURPLE+"Connection successful to the Cities' DB !"+Text.ANSI_RESET);
                }

                */
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(f,"Please open the Hua VPN \nand restart the program!","Alert",JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
                return;
            }

        }

    public static void PrintCities(JFrame f, JPanel menuPanel) throws SQLException {
        ArrayList<City> Cities=new ArrayList<>();
        //Cities.removeAll(Cities);
        LoadCitiesFromDB(Cities);
        String[][] data= new String[Cities.size()][7];
        f.setBounds(10, 10, 1150, 900);
        JPanel P = new JPanel();
        JTable j;
        menuPanel.setVisible(false);
        JButton Menu=new JButton("Go Back");
        Menu.setBounds(20,20,100,30);

        int i=0;
        for(City C : Cities) {
            data[i]= new String[]{
                    C.getCity()
                    ,C.getCountry()
                    ,String.valueOf(C.getMuseums())
                    ,String.valueOf(C.getParks())
                    ,String.valueOf(C.getMonuments())
                    ,String.valueOf(C.getViewpoints())
            };

            i++;
        }

        // Column Names
        String[] columnNames = {"City","Country","Museums","Parks","Monuments","Viewpoints"};


        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(40, 100, 1000, 500);
        JScrollPane CitiesPanel = new JScrollPane(j);
        CitiesPanel.setBounds(40, 70, 1050, 700);
        CitiesPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        P.add(CitiesPanel);
        P.setLayout(null);
        P.add(Menu);
        f.setContentPane(P);
        Menu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Cities.removeAll(Cities);//Για να μην δείχνει τον πίνακα από προηγούμενες χρήσεις
                CitiesPanel.setVisible(false);
                menuPanel.setVisible(true);
                f.setContentPane(menuPanel);
            }});

    }


	public static void LoadCitiesFromDB(ArrayList<City> City) throws SQLException {
        City.removeAll(City);
        System.out.println(Text.ANSI_YELLOW+"LoadCitiesFromDB"+Text.ANSI_RESET);
        db_prep_obj = db_con_obj.prepareStatement("select * from CitiesForGui");
        ResultSet rs = db_prep_obj.executeQuery();
        System.out.println(Text.ANSI_PURPLE+"Extracting Cities"+Text.ANSI_RESET);
        while (rs.next()){
            String cityName = rs.getString("cityName");
            String Country =rs.getString("countryName");
            int Museums = rs.getInt("Museums");
            int Cafes = rs.getInt("Cafes");
            int Restaurants = rs.getInt("Restaurants");
            int Bars = rs.getInt("Bars");
            int Zoos=rs.getInt("Zoos");
            int Monuments=rs.getInt("Monuments");
            int Parks=rs.getInt("Parks");
            int Viewpoints=rs.getInt("Viewpoints");
            int Beaches=rs.getInt("Beaches");
            double lat = rs.getDouble("lat");
            double lon = rs.getFloat("lon");
            double Temperarture=rs.getDouble("temperature");
            String weather = rs.getString("weather");
            int wordCount = rs.getInt("wordCount");
            City.add(new City(cityName,Country,weather,lat,lon,Cafes,Museums,Restaurants,Bars,Parks,Zoos,Monuments,Viewpoints,Beaches,Temperarture));
    //public City(String City, String Country, String weather, double lat, double lon, int Cafes, int Museums, int Restaurants, int Bars, int Parks, int Zoos, int Monuments, int Viewpoints, int Beaches,double Temperature) {
        }
        System.out.println(Text.ANSI_PURPLE+"Finished Extracting Cities : "+City.size()+Text.ANSI_RESET);
    }

    private static void deleteDataFromDB(String City) {
        try {
            String deleteQueryStatement = "DELETE FROM dt_date WHERE year = "+City+";";
            db_prep_obj = db_con_obj.prepareStatement(deleteQueryStatement);
            int numRowChanged = db_prep_obj.executeUpdate();
            System.out.println("Rows "+numRowChanged+" changed.");
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public static void addDataToDB(CandidateCity C,int wordCount) {
        System.out.println(Text.ANSI_YELLOW+"addDataToDB"+Text.ANSI_RESET);
        try {
            String insertQueryStatement = "INSERT  INTO  CitiesForGui  VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                //static Connection db_con_obj = null;
                //static PreparedStatement db_prep_obj = null;
            db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
            db_prep_obj.setString(1, C.getCity().toUpperCase());
            db_prep_obj.setString(2,C.getCountry().toUpperCase());
            db_prep_obj.setInt(3, C.getCafes());
            db_prep_obj.setInt(4, C.getMuseums());
            db_prep_obj.setInt(5, C.getRestaurants());
            db_prep_obj.setInt(6, C.getBars());
            db_prep_obj.setInt(7, C.getParks());
            db_prep_obj.setInt(8, C.getZoos());
            db_prep_obj.setInt(9, C.getMonuments());
            db_prep_obj.setInt(10, C.getViewpoints());
            db_prep_obj.setInt(11, C.getBeaches());
            db_prep_obj.setDouble(12, C.getLat());
            db_prep_obj.setDouble(13, C.getLon());
            db_prep_obj.setDouble(14, C.getTemperature());
            db_prep_obj.setString(15, C.getWeather());
            db_prep_obj.setInt(16,wordCount);
                // execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
            int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            //System.out.println("Rows "+numRowChanged+" changed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(Text.ANSI_PURPLE+"City Saved In DB"+Text.ANSI_RESET);

    }
}

