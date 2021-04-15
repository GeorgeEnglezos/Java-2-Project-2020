import java.sql.*;
import java.util.ArrayList;

public class CitiesDB {
    static Connection db_con_obj = null; //A connection (session) with a specific database. SQL statements are executed and results are returned within the context
        //of a connection. A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures,
        //the capabilities of this connection, and so on. This information is obtained with the getMetaData method.
    static PreparedStatement db_prep_obj = null;//An object that represents a precompiled SQL statement.
        //A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.

    public static void makeJDBCConnection() {

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
                db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl","itHere","password edw");// Returns a connection to the URL.
                //Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
                if (db_con_obj != null) {
                    System.out.println(Text.ANSI_PURPLE+"Connection successful to the Cities' DB !"+Text.ANSI_RESET);

                } else {
                    System.out.println(Text.ANSI_RED+"Failed to make connection!"+ Text.ANSI_RESET);
                }
            } catch (SQLException e) {
                System.out.println(Text.ANSI_RED+"Oracle Connection Failed!"+Text.ANSI_RESET);
                e.printStackTrace();
                return;
            }

        }

    public static void ReadData(ArrayList<City> Cities) throws SQLException {
        System.out.println("City Country");
        System.out.println("______________");
        for(City C : Cities) {
            System.out.println(C.getCity()+"\t"+C.getCountry());
        }
        System.out.println(Cities.size());
    }

	public static void LoadCitiesFromDB(ArrayList<City> City) throws SQLException {
        db_prep_obj = db_con_obj.prepareStatement("select * from cities");
        ResultSet rs = db_prep_obj.executeQuery();
        //System.out.println("City Country Museums Cafes Restaurants Bars Weather");
        //System.out.println("_________________________________________________________");
        while (rs.next()){
            String cityName = rs.getString("cityName");
            String Country =rs.getString("countryName");
            int museums = rs.getInt("museums");
            int cafes = rs.getInt("cafes");
            int restaurants = rs.getInt("restaurants");
            int bars = rs.getInt("bars");
            double lat = rs.getDouble("lat");
            double lon = rs.getFloat("lon");
            String weather = rs.getString("weather");
            int wordCount = rs.getInt("wordCount");
            City.add(new City(cityName.toUpperCase(),Country.toUpperCase(),museums,cafes,restaurants,bars,weather,lat,lon));
        //public City(String city, String country, int museums, int cafes, int restaurants, int bars, String weather, double lat, double lon) {
        }
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
        try {
            String insertQueryStatement = "INSERT  INTO  cities  VALUES  (?,?,?,?,?,?,?,?,?,?)";
                //static Connection db_con_obj = null;
                //static PreparedStatement db_prep_obj = null;
            db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
            db_prep_obj.setString(1, C.getCity().toUpperCase());
            db_prep_obj.setString(2,C.getCountry().toUpperCase());
            db_prep_obj.setInt(3, C.getCafes());
            db_prep_obj.setInt(4, C.getMuseums());
            db_prep_obj.setInt(5, C.getRestaurants());
            db_prep_obj.setInt(6, C.getBars());
            db_prep_obj.setDouble(7, C.getLat());
            db_prep_obj.setDouble(8, C.getLon());
            db_prep_obj.setString(9, C.getWeather());
            db_prep_obj.setInt(10,wordCount);
                // execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
            int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
                //System.out.println("Rows "+numRowChanged+" changed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}