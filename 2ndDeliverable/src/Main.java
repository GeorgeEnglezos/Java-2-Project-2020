import exception.WikipediaNoArcticleException;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClientProtocolException, IOException, WikipediaNoArcticleException, SQLException {
        CitiesDB.makeJDBCConnection();
        Scanner input = new Scanner(System.in);
        ArrayList<Traveller> travellerList = new ArrayList<Traveller>();
        ArrayList<City> Cities= new ArrayList<City>();
        ArrayList<CandidateCity> CC= new ArrayList<CandidateCity>();

        Text.CreateFile();
        String[]  Option={"cafe" , "museum" ,"restaurant" ,"bar"}; // Τα κριτήρια
        int i=0;
        while(true){
            // 0) Αντιγράφω μέσα στην πρώτη επανάληψη την βάση δεδομενων και το Travellers.txt μέσα σε ArrayList
            if (i==0){
                Text.extractTravellers(travellerList);
                CitiesDB.LoadCitiesFromDB(Cities);
                i++;
            }

            System.out.println("---------"+Text.ANSI_GREEN+"MENU"+ Text.ANSI_RESET+"---------");
            System.out.println(Text.ANSI_BLUE+"1)Make a new entry! "+Text.ANSI_RESET);
            System.out.println(Text.ANSI_BLUE+"2)View the list of cities users searched for! "+Text.ANSI_RESET);
            System.out.println(Text.ANSI_BLUE+"3)View the list of previous users! "+Text.ANSI_RESET);
            System.out.println(Text.ANSI_BLUE+"4)Get the free ticket results! "+Text.ANSI_RESET);
            System.out.println(Text.ANSI_BLUE+"5)Exit the program! "+Text.ANSI_RESET);
            System.out.printf(Text.ANSI_BLUE+"Insert Number: "+Text.ANSI_RESET);

            int choice;
            choice = input.nextInt();
            switch (choice) { //switch για απάντηση
                case 1:
                    // 1) Καλώ την μέθοδο για να πάρω τα στοιχεία του χρήστη
                    WhoAreYou(travellerList);

                    // 2) Βρίσκω το index του χρήστη επειδή είναι Backup απο Travellers.txt + 1
                    int index=travellerList.size()-1;

                    // 3) Αρχικοποιώ τις τιμές του πίνακα σε περίπτωση που ο χρήστης είναι business
                    String[] needs={"n","n","n","n",};

                    // 4) Αν είναι business δεν θα ρωτηθεί για τα κριτήρια
                    if (travellerList.get(index).getKindOftrip()==1){
                        needs =City.ChooseCriterions(Option);
                    }

                    // 5) Για να πάρω τις Candidate Cities
                    City.Destination(CC,Option,needs);

                    // 6) Με το iterator ελέγχω αν οι πόλεις υπάρχουν στο CitiesDB αν δεν υπάρχουν αποθηκεύεται και στο ArrayList
                    for(CandidateCity C : CC) {
                        boolean DoesCityExist= CandidateCity.CheckDBforCity(C,Cities);
                        //System.out.println(DoesCityExist);
                        if (Boolean.FALSE.equals(DoesCityExist)){
                            CitiesDB.addDataToDB(C,OpenDataRest.countTotalWords(OpenDataRest.RetrieveWikipedia(C.getCity())));
                            Cities.add(new City(C.getCity(),C.getCountry(),C.getMuseums(),C.getCafes(),C.getRestaurants(),C.getBars(),C.getWeather(),C.getLat(),C.getLon()));
                        }else{
                           System.out.println(Text.ANSI_PURPLE+"City already in the DB"+Text.ANSI_RESET);
                        }
                    }

                    // 7) Καλώ την συνάρτηση CompareCities και παίρνω ένα Array με την πόλη με το μεγαλύτερο Similarity και το Similarity
                    String[] maxSimCity=Traveller.CompareCities(CC,travellerList.get(index));

                    // 8) Βάζω στα objects τις τιμές ώστε να αποθηκευτούν στην συνέχεια και στο αρχείο .txt
                    travellerList.get(index).setVisit(maxSimCity[0]);
                    travellerList.get(index).setSim(Double.valueOf(maxSimCity[1]));
                    System.out.println(Text.ANSI_BLUE+"From the options and parameteres you gave the best destination for you is "+Text.ANSI_PURPLE+maxSimCity[0] +Text.ANSI_RESET);

                    // 9) Ελέγχω αν ο χρήστης υπάρχει στo ArrayList και αν δεν υπάρχει τον αποθηκεύω
                    boolean DoesHeExist =Text.CheckIfHeExists(travellerList.get(index).getName(),needs);
                    Text.save_user_if_doesnt_exist(travellerList.get(index),DoesHeExist,needs);

                    // 10)Σβήνω το περιεχόμενο του Arraylist με τις Candidate Cities
                    CC.removeAll(CC);
                    break;
                case 2:
                    // 11) Καλώ την ReadData για να εκτυπωσω όλες τις πόλεις στο Ιστορικό
                    CitiesDB.ReadData(Cities);
                    break;
                case 3:
                    // 12) Εκτυπώνω όλους τους χρήστες στο Ιστορικό
                    Text.printPreviousUsers(travellerList);
                    break;
                case 4:
                    // 13) Εκτυπώνω τον νικητή του free ticket και για τα δύο subclasses του Traveller
                    CallFreeTicket(travellerList);
                    break;
                case 5:
                    // 14) Κλείνω το πρόγραμμα
                    System.out.println(Text.ANSI_PURPLE+"Thank you for using our app!"+Text.ANSI_RESET);
                    System.exit(0);
                default:
                    // 15) Αν ο χρήστης δώσει Λάθος νούμερο
                    System.out.printf(Text.ANSI_RED+"Wrong input! \n"+Text.ANSI_RESET);
                    break;
            }
        }
    }
    private static List<Traveller> WhoAreYou(ArrayList<Traveller> traveller) throws IOException { //Aν θες να τα γυρνάς κάνεις το void String και γυρνάς κάποιο array
        OpenDataRest ODR = new OpenDataRest();
        Scanner input = new Scanner(System.in); //Καινούργιο Scanner
        String appid = ""; //Your openweathermap id.
        System.out.println("_____________________________________________");
        System.out.printf(Text.ANSI_BLUE+"Name:"+Text.ANSI_RESET);//Όνομα
        String Name = input.next();//εκχώρηση
        Name = Text.checkVariable(Name);//Έλεγχος για το αν είναι μόνο γράμματα ή όχι

        System.out.printf(Text.ANSI_BLUE+"Surname:"+Text.ANSI_RESET);//Επίθετο
        String Surname = input.next();
        Surname = Text.checkVariable(Surname);

        System.out.printf(Text.ANSI_BLUE+"Age:"+Text.ANSI_RESET);//Ηλικία
        int Age = input.nextInt();
        Age = Text.checkVariable(Age, 17, 100);// Περνάω την ηλικία και τα όρια της ηλικίας

        System.out.printf(Text.ANSI_BLUE+"Current City:"+Text.ANSI_RESET);// Σε ποια Πόλη μένει κανονικά
        String CurrentCity = input.next();
        CurrentCity = Text.checkVariable(CurrentCity);

        System.out.printf(Text.ANSI_BLUE+"Current Country:"+Text.ANSI_RESET);// Σε ποια Χώρα μένει κανονικά
        String CurrentCountry = input.next();
        CurrentCountry = Text.checkVariable(CurrentCountry);

        System.out.printf(Text.ANSI_BLUE+"Number of travellers:"+Text.ANSI_RESET);//Αριθμός ταξιδιωτών
        int NumberOfTtravellers = input.nextInt();
        NumberOfTtravellers = Text.checkVariable(NumberOfTtravellers, 0, 100);

        System.out.println("_____________________________________________");

        System.out.printf(Text.ANSI_BLUE+"Is the trip gonna be for:"+Text.ANSI_RESET +Text.ANSI_CYAN+"\n1)Tourism \n2)Bussines\n"+Text.ANSI_BLUE+"Insert 1 or 2: "+Text.ANSI_RESET);
        int kindOftrip = input.nextInt();
        Text.checkVariable(kindOftrip,0,2);

        double[] arr = ODR.RetrieveOpenWeatherMap(CurrentCity,CurrentCountry,appid); //Για να πάρω temperature ,lat,lon
        double temperature= arr[0];
        double lat= arr[1];
        double lon= arr[2];
        //Φτιάχνω το traveller object μέσα σε Arraylist για να είναι δυναμικό σχήμα
        traveller.add(new Traveller(Name+" "+Surname,Age,NumberOfTtravellers,CurrentCity,CurrentCountry,temperature,lat,lon,kindOftrip,"",0));
        return traveller;
    }

    private static void CallFreeTicket(ArrayList<Traveller> AllTravellers){
        ArrayList<Traveller> business= new ArrayList<Traveller>();
        ArrayList<Traveller> tourist= new ArrayList<Traveller>();
        for(Traveller T : AllTravellers) {
            if (T.getKindOftrip()==1){
                tourist.add(T);
            }else {
                business.add(T);
            }
        }
        Tourist.freeTicket(tourist);// Καλώ την μέθοδο free ticket του subclass tourist
        // και βρίσκει για την πόλη με τα περισσότερα Visit τον χρήστη με το μεγαλύτερο similarity
        Business.freeTicket(business);// Καλώ την μέθοδο free ticket του subclass business και επιστρέφει έναν τυχαίο χρήστη
        tourist.removeAll(tourist);
        business.removeAll(business);
    }
}
