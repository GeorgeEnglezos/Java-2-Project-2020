import exception.WikipediaNoArcticleException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class City {
    int  museums, cafes ,Restaurants ,Bars;
    String weather,City,Country;
    double lat, lon;
    public int getMuseums() {
        return museums;
    }

    public void setMuseums(int museums) {
        this.museums = museums;
    }

    public int getCafes() {
        return cafes;
    }

    public void setCafes(int cafes) {
        this.cafes = cafes;
    }

    public int getRestaurants() {
        return Restaurants;
    }

    public void setRestaurants(int restaurants) {
        Restaurants = restaurants;
    }

    public int getBars() {
        return Bars;
    }

    public void setBars(int bars) {
        Bars = bars;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public City(String city, String country, int museums, int cafes, int restaurants, int bars, String weather, double lat, double lon) {
        this.museums = museums;
        this.cafes = cafes;
        Restaurants = restaurants;
        Bars = bars;
        this.weather = weather;
        City = city;
        Country = country;
        this.lat = lat;
        this.lon = lon;
    }
    public static List<City> Destination(ArrayList<City> Destination) throws IOException, WikipediaNoArcticleException { //Aν θες να τα γυρνάς κάνεις το void String και γυρνάς κάποιο array
        String appid = ""; //Your openweathermap id.
        int[] criterionFound= new int[4];
        String[]  Option={"cafe" , "museum" ,"Restaurant" ,"Bar"}; // Τα κριτήρια
        String[] needs =ChooseCriterions(Option);//System.out.printf("\n");
        System.out.printf("\nHow many cities do you want to compare? ");
        OpenDataRest ODR = new OpenDataRest();
        Scanner input = new Scanner(System.in);
        int numberOfCities = input.nextInt();
        System.out.printf("\n");
        for (int i=1; i<=numberOfCities ;i++){
            System.out.println("_____________________________________________");
            System.out.printf("City "+i+": ");
            String City,Country;
            ODR.checkVariable(City = input.next());
            System.out.printf("Country of City "+i+": ");
            ODR.checkVariable(Country = input.next());
            criterionFound=countCriterion (City, needs, Option );
            System.out.println("Back to Destination ");
            for ( int j=0; j<4; j++){
                System.out.println(j + " appeared " + criterionFound[j]);
            }

            String Weather="Sunny";
            double[] arr = ODR.RetrieveOpenWeatherMap(City,Country,appid); //Για να πάρω temperature ,lat,lon
            double temperature= arr[0];
            double lat= arr[1];
            double lon= arr[2];
            Destination.add(new City(City,Country,criterionFound[0],criterionFound[1],criterionFound[2],criterionFound[3],Weather,lat,lon));
            System.out.println("_____________________________________________");
        }

        return Destination;


    }
    public static String[] ChooseCriterions (String[] Options) throws IOException, WikipediaNoArcticleException {
        Scanner input = new Scanner(System.in);
        String[] Need=new String[4];
        System.out.println("_____________________________________________");
        for (int i=0; i<4; i++){
            boolean stop=true;
            System.out.println("Do you want to check for " + Options[i] + "?");
            Need[i] = input.next();
            while(stop) {
                switch (Need[i]) { //switch για απάντηση
                    case "Y":
                    case "y":
                        stop = false;
                        break;
                    case "N":
                    case "n":
                        stop = false;
                        break;
                    default:
                        stop = true;
                        System.out.printf("Wrong input , please try again: ");
                        Need[i]=input.next();
                        break;
                }
            }
        }
        System.out.println("_____________________________________________");
        return Need; // Το επιστρέφω στο destination
    }
    public static int[] countCriterion (String City, String[] Need, String[] Option ) throws IOException, WikipediaNoArcticleException {
        OpenDataRest ODR = new OpenDataRest();
        int[] Found=new int[4]; // Τα αποτελέσματα
        for (int i=0; i<4; i++) {
            if ((Need[i].equals("y")) || (Need[i].equals("Y"))) {
                String article = ODR.RetrieveWikipedia(City); // παίρνω το άρθρο
                Found[i] = ODR.countCriterionfCity(article, Option[i]); // παίρνω το κριτήριο
                System.out.println(Option[i] + " appeared " + Found[i] + " in city " + City);
            } else {
                Found[i] = 0; //αφού δεν μας ενδιαφέρει
            }
        }
        return Found;
    }
}
