import exception.WikipediaNoArcticleException;
import org.jetbrains.annotations.NotNull;//Το χρειαζόταν για να τρέξει

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

class Traveller implements Comparable<Traveller> {
    String name;
    int age;
    double sim;
    int numberOfTravellers;
    String City;
    String Country;
    String visit;
    double current_lat, current_lon, current_Temperature;
    int KindOftrip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSim() {
        return sim;
    }

    public void setSim(double sim) {
        this.sim = sim;
    }

    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public double getCurrent_lat() {
        return current_lat;
    }

    public void setCurrent_lat(double current_lat) {
        this.current_lat = current_lat;
    }

    public double getCurrent_lon() {
        return current_lon;
    }

    public void setCurrent_lon(double current_lon) {
        this.current_lon = current_lon;
    }

    public double getCurrent_Temperature() {
        return current_Temperature;
    }

    public void setCurrent_Temperature(double current_Temperature) {
        this.current_Temperature = current_Temperature;
    }

    public int getKindOftrip() {
        return KindOftrip;
    }

    public void setKindOftrip(int KindOftrip) {
        this.KindOftrip = KindOftrip;
    }

    public Traveller(String name, int age, int numberOfTravellers, String City, String Country, double current_lat, double current_lon, double current_Temperature, int KindOftrip, String visit, double sim) {
        this.name = name;
        this.age = age;
        this.sim = sim;
        this.numberOfTravellers = numberOfTravellers;
        this.City = City;
        this.Country = Country;
        this.visit = visit;
        this.current_lat = current_lat;
        this.current_lon = current_lon;
        this.current_Temperature = current_Temperature;
        this.KindOftrip = KindOftrip;
    }

    public static double Similarity(CandidateCity thisCity) throws IOException, WikipediaNoArcticleException {
        System.out.println(Text.ANSI_YELLOW+"You are in the Traveller/Similarity"+Text.ANSI_RESET);
        int count=0;
        if (thisCity.getBars()!=0){ count++; }//1
        if (thisCity.getRestaurants()!=0){ count++; }//2
        if (thisCity.getCafes()!=0){ count++; }//3
        if (thisCity.getMuseums()!=0){ count++; }//4
        if (thisCity.getMonuments()!=0){ count++; }//5
        if (thisCity.getZoos()!=0){ count++; }//6
        if (thisCity.getViewpoints()!=0){ count++; }//7
        if (thisCity.getBeaches()!=0){ count++; }//8
        if (thisCity.getParks()!=0){ count++; }//9
        return Double.valueOf(count);
    }
    public static String[] CompareCities(ArrayList<CandidateCity> CC, String[] needs, Traveller traveller, JRadioButton NoRain, JRadioButton Rain) throws IOException, WikipediaNoArcticleException {
        System.out.println(Text.ANSI_PURPLE+"You are in the CompareCities #1"+Text.ANSI_RESET);
        boolean iWantRain=false;
        boolean flag=true;
            if (Rain.isSelected()){
                iWantRain=true;
            }else{
                iWantRain=false;
            }

        for (CandidateCity C :CC){
            if (needs[0].equals("n")){
                System.out.println("Douleuei");
                C.setCafes(0);
            }
            if (needs[1].equals("n")){
                C.setMuseums(0);
            }
            if (needs[2].equals("n")){
                C.setRestaurants(0);
            }
            if (needs[3].equals("n")){
                C.getBars();
            }
            if (needs[4].equals("n")){
                C.setParks(0);
            }
            if (needs[5].equals("n")){
                C.setZoos(0);
            }
            if (needs[6].equals("n")){
                C.setMonuments(0);
            }
            if (needs[7].equals("n")){
                C.setViewpoints(0);
            }
            if (needs[8].equals("n")){
                C.setBeaches(0);
            }
        }
        String[] maxSimCity =CompareCities(iWantRain,CC,traveller);//Καλεί το δεύτερο CompareCities
        System.out.println(Text.ANSI_YELLOW+"You are back in CompareCities #1"+Text.ANSI_RESET);
        return maxSimCity;
    }

    private static String[] CompareCities(boolean iWantRain, ArrayList<CandidateCity> CC, Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
        System.out.println(Text.ANSI_PURPLE+"You are in the CompareCities #2"+Text.ANSI_RESET);
        String BestOption = "";
        double BestSim=0;
        int kindOfTrip = newTraveller.getKindOftrip();
        int j=0;
        double[] Sim = new double[CC.size()];
        double max=Sim[0]; //Βρίσκω το Max Similarity
        System.out.println("Cities: "+CC.size());

        for (int i=0;i<CC.size();i++){ //Για κάθε πόλη
            if (kindOfTrip==1){ // Παίρνω τον πίνακα similarity από τo subclass Tourist
                Sim[i]=Tourist.Similarity(CC.get(i));
                System.out.println("Finding Similarity for "+CC.get(i));
            }else{ // Παίρνω τον πίνακα similarity από τo subclass Bussines
                Sim[i]=Business.Similarity(CC.get(i),newTraveller);
            }
        }
        System.out.println("Finished collecting Similarities");
        if (kindOfTrip==2){//Αν είναι Business
            double min;
            min=Sim[0]; //Ορίζω ως αρχική τιμή το πρώτο
            String closestCity=CC.get(0).getCity();
            for (int i=0;i<CC.size();i++){

                String Weather = CC.get(i).getWeather(); //Παίρνω τι καιρό έχει στην πόλη

                if (Boolean.TRUE.equals(iWantRain)) { //Αν ο χρήστης θέλει και βροχή
                    if (Sim[i] < min) { // Βλέπω πιο Distance Είναι πιο κοντά
                        min = Sim[i];
                        closestCity = CC.get(i).getCity();
                    }
                }else if ((Boolean.FALSE.equals(iWantRain)) && (Weather.equals("Sunny"))) {//Αν ο χρήστης δεν θέλει και βροχή
                    j++;
                    if (j==1){ //Για την πρώτη φορά που μπαίνει μέσα στην else if ,αφού δεν ξέρω πότε θα γίνει αυτό
                        min=Sim[i];
                        closestCity = CC.get(i).getCity();
                    }else if (Sim[i] < min) {
                        min = Sim[i];
                        closestCity = CC.get(i).getCity();
                    }
                }
                BestSim=min;
                BestOption= closestCity;
            }
        }else {//Αν είναι Tourist
            System.out.println("You are a tourist");
            for (int i=0;i<CC.size();i++){//Για κάθε πόλη

                String Weather = CC.get(i).getWeather(); //Παίρνω τι καιρό έχει στην πόλη
                if (Boolean.TRUE.equals(iWantRain)) { //Αν ο χρήστης θέλει βροχή
                    if (Sim[i] > BestSim) { // Βλέπω πιο Distance Είναι πιο κοντά
                        BestSim = Sim[i];
                        BestOption = CC.get(i).getCity();
                    }
                }else if ((Boolean.FALSE.equals(iWantRain)) && (Weather.equals("Sunny"))) {//Αν ο χρήστης δεν θέλει βροχή
                    j++;
                    if (j==1){ //Για την πρώτη φορά που μπαίνει μέσα στην else if ,αφού δεν ξέρω πότε θα γίνει αυτό
                        BestSim=Sim[i];
                        BestOption = CC.get(i).getCity();
                    }else if (Sim[i] > BestSim) {
                        BestSim = Sim[i];
                        BestOption = CC.get(i).getCity();
                    }
                }
            }
        }
        //System.out.println(BestOption);
        String[] BestOptionAndSim = new String[2];
        BestOptionAndSim[0]=BestOption;
        BestOptionAndSim[1]=Double.toString(BestSim);
        return  BestOptionAndSim;
    }

    @Override
    public int compareTo(@NotNull Traveller o) {
        return this.age - o.age;
    }
}
class Business extends Traveller {


    public Business(String name, int age, int numberOfTravellers, String City, String Country, double current_lat, double current_lon, double current_Temperature, int KindOftrip, String visit, double sim) {
        super(name, age, numberOfTravellers, City, Country, current_lat, current_lon, current_Temperature, KindOftrip, visit, sim);
    }

    public static double Similarity(CandidateCity DestinationCity, Traveller newTraveller) throws IOException, WikipediaNoArcticleException {
        double lat1=DestinationCity.getLat();
        double lon1=DestinationCity.getLon();
        double lat2=newTraveller.getCurrent_lat();
        double lon2=newTraveller.getCurrent_lon();

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;

            return (dist);
        }
}
            /*H RetrieveOpenWeatherMap πιστεύω δίνει λάθος lat lon στις πόλεις
            *Παίρνω το distance μεταξύ της πόλης μου και του προορισμού και θα τα κρατήσω
             σε έναν πίνακα για να κάνω μετά την σύγκριση*/

    public static String freeTicket(ArrayList<Traveller> travellers){
        String Winner;
        int x = (int)(Math.random()*(((travellers.size()-1)-0)+1))+0;
        Winner=travellers.get(x).getName();
        System.out.println("The winner of the business free ticket is : "+Winner);
        return Winner;
    }
}

class Tourist extends Traveller{

    public Tourist(String name, int age, int numberOfTravellers, String city, String country, double current_lat, double current_lon, double current_Temperature, int KindOftrip,String visit,double sim) {
        super(name, age, numberOfTravellers, city, country, current_lat, current_lon, current_Temperature, KindOftrip,visit,sim);
    }
    public static double Similarity(CandidateCity DestinationCity) throws IOException, WikipediaNoArcticleException {
        System.out.println(Text.ANSI_YELLOW+"You are in Tourist/Similarity"+Text.ANSI_RESET);
        OpenDataRest ODR = new OpenDataRest();
        String article = ODR.RetrieveWikipedia(DestinationCity.getCity());
        int Total = ODR.countTotalWords(article);
        double DifferentCritFound=Traveller.Similarity(DestinationCity);
        double temp=Double.valueOf(DifferentCritFound);
        double sum=DestinationCity.getBars()+DestinationCity.getRestaurants()+DestinationCity.getCafes()+DestinationCity.getMuseums()
                +DestinationCity.getParks()+DestinationCity.getBeaches()+DestinationCity.getZoos()+DestinationCity.getViewpoints()+DestinationCity.getMonuments();
        //System.out.println("For city :"+DestinationCity.getCity());

        sum=sum*temp/Total;
        //System.out.println(sum);
        System.out.println("Returning to Compare Cities #2");

        return sum;
    }
    public static String freeTicket(ArrayList<Traveller> travellers){
        System.out.println(Text.ANSI_YELLOW+"You are in Tourist/freeTicket"+Text.ANSI_RESET);
        String Winner;
        double max=0;
        int i=0;
        int Candidates=0;
        String mostShownCity="";
        Boolean Found=false;
        int j=0;

        ArrayList<CityList> Cities= new ArrayList<CityList>();
        for(Traveller T : travellers) {//Όποτε πρωτοεμφανίζεται μια πόλη την βάζω σε ένα ArrayList
            if(!(j==0)){
                Cities.add(new CityList(T.getVisit(),0));
            }else {
                for (CityList C : Cities) {
                    if (T.getVisit().equals(C.getCity())) {
                        Found = true;
                        break;

                    }
                }
            }
            j++;
            if (Boolean.FALSE.equals(Found)){
                Cities.add(new CityList(T.getVisit(),0));
            }
        }

        for(Traveller T : travellers) {// όποτε βλέπω μια πόλη αυξάνω ένα counter για να βρω ποια πόλη πρότεινα περισσότερες φορές
            for(CityList C : Cities) {
                if(T.getVisit().equals(C.getCity())){
                    C.count++;
                }
            }
        }

        for(CityList C : Cities) { //Βρίσκω την πόλη που προτάθηκε στον χρήστη περισσότερο
            if(C.getCount()>max){
                max=C.getCount();
                mostShownCity=C.getCity();
            }
        }
        for (Traveller T:travellers){//Βρίσκω τον αριθμό των ατόμων με αυτλη την πόλη
            if (T.getVisit().equals(mostShownCity)){
                Candidates++;
            }
        }

        String[][] Sim = new String[Candidates+1][2];

        for (Traveller T:travellers){//Φτιάχνω τον πίνακα 2x2 με τους candidate
            if (T.getVisit().equals(mostShownCity)){
                Sim[i][0]=T.getName();
                Sim[i][1]=String.valueOf(T.getSim());
                i++;
            }
        }
        max=Double.valueOf(Sim[0][1]);
        Winner=Sim[0][0];
        for(i=1;i<Candidates;i++){
            if (Double.valueOf(Sim[i][1])>max){
                max=Double.valueOf(Sim[i][1]);
                Winner=Sim[i][0];
            }
        }

        System.out.println("The winner for the Tourist free Ticket is : "+ Winner);
        return Winner;
    }
    private static class CityList {
        String city;
        int count;

        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
        public String getCity() { return city; }
        public void setName(String name) { this.city = city; }

        public CityList(String city, int count) {
            this.city = city;
            this.count = count;
        }
    }
}