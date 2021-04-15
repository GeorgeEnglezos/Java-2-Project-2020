import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                travellerList.add(new Traveller(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3], arr[4], Double.valueOf(arr[5]), Double.valueOf(arr[6]), Double.valueOf(arr[7]), Integer.parseInt(arr[8]), arr[9],Double.valueOf(arr[10])));
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
        private static int getNumberLinesInText() throws  IOException{
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
        public static boolean CheckIfHeExists (String Name, String[] Needs) throws IOException {
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
                    for(int j=0;j<8;j++){
                        if (!(Needs[j].toLowerCase().equals(arr[11+j].toLowerCase()))){ // Αν δεν ταιριάζουν τα κριτήρια τον κρατάει ως άλλο χρήστη
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
                        +User.getKindOftrip()+","+User.getVisit()+","+User.getSim()+","
                        +needs[0]+","+needs[1]+","+needs[2]+","+needs[3]+","+needs[4]+","+needs[5]+","+needs[6]+","+needs[7]+","+needs[8];
                Text.WriteToFile(Buffer);
            }else{System.out.println(Text.ANSI_RED+"User already in backup, with same info"+Text.ANSI_RESET);}
        }
        public static void printPreviousUsers(ArrayList<Traveller> Users,JFrame f,JPanel menuPanel) throws IOException {
            Users.removeAll(Users);
            Text.extractTravellers(Users);
            String[][] data= new String[Users.size()][7];
            f.setBounds(10, 10, 1150, 900);
            JPanel P = new JPanel();
            JTable j;
            menuPanel.setVisible(false);
            JButton Menu=new JButton("Go Back");
            Menu.setBounds(20,20,100,30);
            Collections.sort(Users);
            String reasonOfTrip;

            int i=0;
            for(Traveller U : Users) {
                if (U.getKindOftrip()==1){
                    reasonOfTrip="Tourist";
                }else{
                    reasonOfTrip="Business";
                }
                data[i]= new String[]{U.getName(),Integer.toString(U.getAge()),U.getCity(),U.getCountry(),reasonOfTrip,U.getVisit()};

                i++;
            }

            // Column Names
            String[] columnNames = {"Name/Surname","Age","City","Country","Kind Of Traveller","Recommended City"};


            // Initializing the JTable
            j = new JTable(data, columnNames);
            j.setBounds(40, 100, 1000, 500);
            JScrollPane UsersPanel = new JScrollPane(j);
            UsersPanel.setBounds(40, 70, 1050, 700);
            UsersPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            P.add(UsersPanel);
            P.setLayout(null);
            P.add(Menu);
            f.setContentPane(P);
            Menu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    Users.removeAll(Users);//Για να μην δείχνει τον πίνακα από προηγούμενες χρήσεις
                    UsersPanel.setVisible(false);
                    menuPanel.setVisible(true);
                    f.setContentPane(menuPanel);
                }});

        }







    public static boolean checkVariable( String value,int min, int max){//Έλεγχος για το νούμερο
        boolean correct=true;
        if (value.matches("[0-9]+")){
            if(Integer.parseInt(value)<min || Integer.parseInt(value)>max) {
                correct = false;
            }
        } else{
            correct=false;
        }
        return correct; //Επιστρέφω το σωστό νούμερο
    }
    public static boolean checkVariable(String word ){// if the names contain only letters
        boolean correct=true;

        if (!((word != null) && (!word.equals("")) && ((word.matches("^[a-zA-Z]*$")))))
        {
            correct=false;
        }
        return correct;


    }
}