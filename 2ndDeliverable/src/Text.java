import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;
public class Text {

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

    public static void CreateFile() throws IOException {
        File BackUP = new File("Travellers.txt");
        if (BackUP.exists()) {
            System.out.println(ANSI_CYAN+"Backup file of previous users found!\nIt will load up now!"+ANSI_RESET);
        } else {
            System.out.println(ANSI_PURPLE+"No backup of previous users was found!\n"+ANSI_CYAN+ "A new one will be made now!"+ANSI_RESET);
            FileWriter fw = new FileWriter("Travellers.txt");
        }
    }
    public static ArrayList<Traveller> extractTravellers(ArrayList<Traveller> travellerList) throws IOException {
        Scanner input=new Scanner(System.in);
        //ArrayList<Traveller> travellerList = new ArrayList<Traveller>();
        int nl=getNumberLinesInText();
        for (int i = 0; i < nl; i++) {
            String Line = getLine(i);
            String[] arr = extractTextFromLine(Line);
            travellerList.add(new Traveller(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3], arr[4], Double.valueOf(arr[5]), Double.valueOf(arr[6]), Double.valueOf(arr[7]), Integer.parseInt(arr[8]), arr[13],Double.valueOf(arr[14])));
            //    public Traveller(String name, int age, int numberOfTravellers, String City, String Country, double current_lat, double current_lon, double current_Temperature, int KindOftrip, String visit,double Sim) {
        }
        return travellerList;
    }
    public static void WriteToFile(String buffer) {
        try(FileWriter fw = new FileWriter("Travellers.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(buffer);
            System.out.println(ANSI_PURPLE+"Successfully saved user's info for future references."+ANSI_RESET);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    private static int getNumberLinesInText() throws FileNotFoundException, IOException{
        int lines=0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Travellers.txt"))) {
            while (reader.readLine() != null) lines++;
        }
            return lines;
    }
    private static String[] extractTextFromLine(String Line){
        String[] tokens = null;
        tokens = Line.split(",");
        return tokens;
    }
    private static String getLine(int i) throws IOException{
        String Line= "";
        try (Stream<String> lines = Files.lines(Paths.get("Travellers.txt"))){
            Line = lines.skip(i).findFirst().get();
        }
        return Line;
    }
    public static boolean CheckIfHeExists (String Name,String[] Needs) throws IOException {
        int[] Found=new int[4]; // Τα αποτελέσματα
        int nl=getNumberLinesInText();
        boolean userFound =false;
        for (int i = 0; i < nl; i++) {
            //System.out.println("Searching for user");
            String Line = getLine(i);
            String[] arr = extractTextFromLine(Line);
            //System.out.println("Is "+Name+" = "+arr[0]);
            userFound=Name.toLowerCase().equals(arr[0].toLowerCase());
            if (userFound){
                //System.out.println("Found User");
                //System.out.println("Searching Criterions");
                for(int j=0;j<4;j++){
                    if (!(Needs[j].toLowerCase().equals(arr[9+j].toLowerCase()))){ // Αν δεν ταιριάζουν τα κριτήρια τον κρατάει ως άλλο χρήστη
                        userFound=false;
                        //System.out.println("found different criterion");
                    }else{
                        //System.out.println("Criterion match");
                    }
                }
            }
            if (userFound){
                i=nl;
            }
        }
        //System.out.println(userFound);
        return userFound;
    }
    public static void save_user_if_doesnt_exist(Traveller User,boolean heExists,String[] needs){
        if(Boolean.FALSE.equals(heExists)){
            System.out.println(Text.ANSI_PURPLE+"saving your info"+Text.ANSI_RESET);
            String Buffer=User.getName()+","+User.getAge()+","
                    +User.getNumberOfTravellers()+","+User.getCity()+","
                    +User.getCountry()+","+User.getCurrent_Temperature()+","
                    +User.getCurrent_lat()+","+User.getCurrent_lon()+","
                    +User.getKindOftrip()+","+needs[0]+","+needs[1]+","+needs[2]+","+needs[3]+","+User.getVisit()+","+User.getSim();
            Text.WriteToFile(Buffer);
        }else{System.out.println(Text.ANSI_RED+"User already in backup, with same info"+Text.ANSI_RESET);}
    }
    public static void printPreviousUsers(ArrayList<Traveller> Users) throws IOException {
        //userList=extractTravellers(userList);
        System.out.println(ANSI_PURPLE + "If you see your name more than once it is\n because you chose different criterions"+ANSI_RESET);

        System.out.println(ANSI_PURPLE + "The number of previous users: " + getNumberLinesInText() + ANSI_RESET);
        if (getNumberLinesInText() != 0){
            System.out.println(ANSI_BLUE + "NAME,SURNAME,AGE,CITY,COUNTRY,KIND_OF_TRIP,RECOMENDED_CITY" + ANSI_RESET);
            Collections.sort(Users);
            String reasonOfTrip="";
            for (int i = 0; i < getNumberLinesInText(); i++) {
                if (Users.get(i).getKindOftrip()==1){
                     reasonOfTrip="Tourist";
                }else{
                     reasonOfTrip="Bussines";
                }
                System.out.println(Users.get(i).getName()+" "+Users.get(i).getAge()+" "+Users.get(i).getCity()+" "+Users.get(i).getCountry()+" "+reasonOfTrip+" "+Users.get(i).getVisit());
            }
        }else{
            System.out.println(ANSI_RED + "No users saved yet!" + ANSI_RESET);
        }
    }
    public static int checkVariable(int n ,int min, int max){//Έλεγχος εγκυρότητας μεταβλητών , περνάω το νούμερο ,το ελάχιστο για το νούμερο και το μέγιστο
        Scanner input = new Scanner(System.in);  // Created a Scanner object

        while(n<=min | n>max){// Μια επανάληψη που τερματίζει όταν δωθεί σωστό νούμερο
            System.out.printf(Text.ANSI_RED+"Wrong input, try again:"+Text.ANSI_RESET);
            n = input.nextInt();

        }

        return n; //Επιστρέφω το σωστό νούμερο
    }
    public static String checkVariable(String word ){// if the names contain only letters
        Scanner input = new Scanner(System.in);  // Created a Scanner object
        while (!((word != null) && (!word.equals("")) && (word.matches("^[a-zA-Z]*$"))))
        {
            System.out.printf(Text.ANSI_RED+"Wrong input, try again:"+Text.ANSI_RESET);
            word=input.next();
        }
        return word;
    }
}