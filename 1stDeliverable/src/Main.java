import exception.WikipediaNoArcticleException;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws ClientProtocolException, IOException, WikipediaNoArcticleException {
        Scanner input = new Scanner(System.in);
        String answer;
        int i=0;
        while(true){ //
            int stop=1;
            System.out.printf("Do you want to make a new entry ? (Y/n)");
            while (stop == 1) {  // Ένας έλεγχος για το αν έδωσε σωστή απάντηση ο χρήστης
                answer = input.next();
                switch (answer) { //switch για απάντηση
                    case "Y":
                    case "y":
                        ArrayList<Traveller> travellerList = new ArrayList<Traveller>();
                        ArrayList<City> Cities= new ArrayList<City>();
                        WhoAreYou(travellerList); //Καλώ την μέθοδο για να πάρω τα στοιχεία του χρήστη
                        City.Destination(Cities); // Για να πάρω τις πόλεις
                        System.out.println("Back to main"); //Δισγνωστικό μήνυμα
                        String maxSimCity=Traveller.CompareCities(Cities,travellerList.get(i));
                        System.out.println("From the options and parameteres you gave the best destination for you is "+maxSimCity);
                        stop++;
                        i++;
                        break;
                    case "n":
                    case "N":
                        System.out.println("Thank you for using our app!");
                        stop++;
                        System.exit(0);
                    default:
                        System.out.printf("Wrong input, try again: (Y/n)");
                        break;
                }
            }
        }

    }
    public static List<Traveller> WhoAreYou(ArrayList<Traveller> traveller) throws IOException { //Aν θες να τα γυρνάς κάνεις το void String και γυρνάς κάποιο array
        OpenDataRest ODR = new OpenDataRest();
        Scanner input = new Scanner(System.in); //kainourgio Scanner
        String appid = ""; //Your openweathermap id.

        System.out.printf("Name:");//Όνομα
        String Name = input.next();//εκχώρηση
        Name = ODR.checkVariable(Name);//Έλεγχος για το αν είναι μόνο γράμματα ή όχι

        System.out.printf("Surname:");//Επίθετο
        String Surname = input.next();
        Surname = ODR.checkVariable(Surname);

        System.out.printf("Age:");//Ηλικία
        int Age = input.nextInt();
        Age = ODR.checkVariable(Age, 18, 100);// Περνάω την ηλικία και τα όρια της ηλικίας

        System.out.printf("Current City:");// Σε ποια Πόλη μένει κανονικά
        String CurrentCity = input.next();
        CurrentCity = ODR.checkVariable(CurrentCity);

        System.out.printf("Current Country:");// Σε ποια Χώρα μένει κανονικά
        String CurrentCountry = input.next();
        CurrentCountry = ODR.checkVariable(CurrentCountry);

        System.out.printf("Number of travellers:");//Αριθμός ταξιδιωτών
        int NumberOfTtravellers = input.nextInt();
        NumberOfTtravellers = ODR.checkVariable(NumberOfTtravellers, 1, 100);

        System.out.println("_____________________________________________");

        System.out.printf("Is the trip gonna be for: \n1)Tourism \n2)Bussines\nInsert 1 or 2: ");
        int kindOftrip = input.nextInt();
        OpenDataRest.checkVariable(kindOftrip,0,2);

        double[] arr = OpenDataRest.RetrieveOpenWeatherMap(CurrentCity,CurrentCountry,appid); //Για να πάρω temperature ,lat,lon
        double temperature= arr[0];
        double lat= arr[1];
        double lon= arr[2];
        //Φτιάχνω το traveller object μέσα σε Arraylist για να είναι δυναμικό σχήμα
        traveller.add(new Traveller(Name+" "+Surname,Age,NumberOfTtravellers,CurrentCity,CurrentCountry,temperature,lat,lon,kindOftrip));
        return traveller;
    }
}
