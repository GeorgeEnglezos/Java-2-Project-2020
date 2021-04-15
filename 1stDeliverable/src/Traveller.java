import exception.WikipediaNoArcticleException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Traveller {
    String name;
    int age;
    int numberOfTravellers;
    String City;
    String Country;
    double current_lat, current_lon, current_Temperature;
    int KindOftrip;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public int getNumberOfTravellers() { return numberOfTravellers; }

    public void setNumberOfTravellers(int numberOfTravellers) { this.numberOfTravellers = numberOfTravellers; }

    public String getCity() { return City; }

    public void setCity(String city) { City = city; }

    public String getCountry() { return Country; }

    public void setCountry(String country) { Country = country; }

    public double getCurrent_lat() { return current_lat; }

    public void setCurrent_lat(double current_lat) { this.current_lat = current_lat; }

    public double getCurrent_lon() { return current_lon; }

    public void setCurrent_lon(double current_lon) { this.current_lon = current_lon; }

    public double getCurrent_Temperature() { return current_Temperature; }

    public void setCurrent_Temperature(double current_Temperature) { this.current_Temperature = current_Temperature; }

    public int getKindOftrip() { return KindOftrip; }

    public void setKindOftrip(double current_Temperature) { this.KindOftrip = KindOftrip; }

    public Traveller(String name, int age, int numberOfTravellers, String city, String country, double current_lat, double current_lon, double current_Temperature,int KindOftrip) {
        this.name = name;
        this.age = age;
        this.numberOfTravellers = numberOfTravellers;
        City = city;
        Country = country;
        this.current_lat = current_lat;
        this.current_lon = current_lon;
        this.current_Temperature = current_Temperature;
        this.KindOftrip=KindOftrip;
    }

    public static int Similarity(City thisCity) throws IOException, WikipediaNoArcticleException {
        int count=0;
        if (thisCity.getBars()!=0){ count++; }
        if (thisCity.getRestaurants()!=0){ count++; }
        if (thisCity.getCafes()!=0){ count++; }
        if (thisCity.getMuseums()!=0){ count++; }
        return count;
    }
    public static String CompareCities(ArrayList<City> Cities,Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
        boolean iWantRain=false;
            boolean flag=true;
            Scanner input= new Scanner(System.in);
            System.out.printf("\nDo you want to include cities with rain or not ? (Y/n)");
            while(flag){
                String answer2=input.next();
                if ((answer2.equals("y"))||(answer2.equals("Y"))){
                    System.out.printf("\nYou are in the first option \n");
                    iWantRain=true;
                    flag=false;
                }else if((answer2.equals("N"))||(answer2.equals("n"))){
                    System.out.printf("\nYou are in the second option\n ");
                    iWantRain=false;
                    flag=false;
                }else{
                        System.out.printf("Wrong input, try again: (Y/n)");
                }
            }
            String maxSimCity =CompareCities(iWantRain,Cities,newTraveller);
        System.out.println("Back to first Compare cities");
       // System.out.println("The city with the biggest similarity is " + maxSimCity);
        return maxSimCity;

    }

    public static String CompareCities(boolean iWantRain, ArrayList<City> Cities,Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
        System.out.println("You are in the second CompareCities");
        String BestOption = "";
        int kindOfTrip = newTraveller.getKindOftrip();
        int j=0;
        double[] Sim = new double[Cities.size()];
        double max=Sim[0]; //Βρίσκω το Max Similarity

        for (int i=0;i<Cities.size();i++){ //Για κάθε πόλη
            if (kindOfTrip==1){
                Sim[i]=Tourist.Similarity(Cities.get(i),newTraveller);
            }else{
                Sim[i]=Business.Similarity(Cities.get(i),newTraveller);
            }
        }
        if (kindOfTrip==2){
            double min;
            min=Sim[0];
            String closestCity=Cities.get(0).getCity();
            for (int i=0;i<Cities.size();i++){
                String Weather = Cities.get(i).getWeather();
                if (Boolean.TRUE.equals(iWantRain)) {
                    if (Sim[i] < min) {
                        min = Sim[i];
                        closestCity = Cities.get(i).getCity();
                    }
                }else if ((Boolean.FALSE.equals(iWantRain)) && (Weather.equals("Sunny"))) {
                    j++;
                    if (j==1){
                        min=Sim[i];
                        closestCity = Cities.get(i).getCity();
                    }
                    if (Sim[i] < min) {
                        min = Sim[i];
                        closestCity = Cities.get(i).getCity();
                    }
                }
                BestOption= closestCity;
            }
        }else {
            String maxSimCity=Cities.get(0).getCity();
            for (int i=0;i<Cities.size();i++){
                String Weather = Cities.get(i).getWeather();
                if (Boolean.TRUE.equals(iWantRain)) {
                    if (Sim[i] >max) {
                        max = Sim[i];
                        maxSimCity = Cities.get(i).getCity();
                    }
                }else if ((Boolean.FALSE.equals(iWantRain)) && (Weather.equals("Sunny"))) {
                    j++; //Για την πρώτη εκχώρηση και επειδή δεν μπορώ να ξέρω ποια θα είναι η πρώτη πόλη χωρίς βροχή
                    if (j==1){
                        max=Sim[i];
                        maxSimCity = Cities.get(i).getCity();
                    }
                    if (Sim[i] > max) {
                        max = Sim[i];
                        maxSimCity = Cities.get(i).getCity();
                    }
                }
            }
            BestOption= maxSimCity;
        }
        return  BestOption;
    }
}
    class Business extends Traveller {

        public Business(String name, int age, int numberOfTravellers, String city, String country, double current_lat, double current_lon, double current_Temperature, int KindOftrip) {
            super(name, age, numberOfTravellers, city, country, current_lat, current_lon, current_Temperature, KindOftrip);
        }
        public static double Similarity(City DestinationCity,Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
                double DestinationLat=DestinationCity.getLat();
                double DestinationLon=DestinationCity.getLon();
                double CurrentLat=newTraveller.getCurrent_lat();
                double CurrentLon=newTraveller.getCurrent_lon();
                if ((CurrentLat == DestinationLat) && (CurrentLon == DestinationLon)) {
                    return 0;
                } else {
                    double theta = DestinationLon - CurrentLon;
                    double dist = Math.sin(Math.toRadians(DestinationLat)) * Math.sin(Math.toRadians(CurrentLat)) + Math.cos(Math.toRadians(DestinationLat)) * Math.cos(Math.toRadians(CurrentLat)) * Math.cos(Math.toRadians(theta));
                    dist = Math.acos(dist);
                    dist = Math.toDegrees(dist);
                    dist = dist * 60 * 1.1515;
                    dist = dist * 1.609344;
                    //System.out.println("Hello ,at least you are in ");
                    return (dist);
                    /*Παίρνω το distance μεταξύ της πόλης μου και του προορισμού και θα τα κρατήσω
                    σε έναν πίνακα για να κάνω μετά την σύγκριση*/
                }
        }

    }
    class Tourist extends Traveller{

        public Tourist(String name, int age, int numberOfTravellers, String city, String country, double current_lat, double current_lon, double current_Temperature, int KindOftrip) {
            super(name, age, numberOfTravellers, city, country, current_lat, current_lon, current_Temperature, KindOftrip);
        }
        public static double Similarity(City DestinationCity,Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
            OpenDataRest ODR = new OpenDataRest();
            String article = ODR.RetrieveWikipedia(DestinationCity.getCity());
            int Total = ODR.countTotalWords(article);
            double sum=DestinationCity.getBars()+DestinationCity.getRestaurants()+DestinationCity.getCafes()+DestinationCity.getMuseums();
            sum=sum/Total;
            return sum;
        }
    }


