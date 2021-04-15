import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
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
        private static int innerDot(int[] currentTraveller, int[] candidateTraveller) {
            int sum=0;
            for (int i=0; i<currentTraveller.length;i++){
                sum+=currentTraveller[i]*candidateTraveller[i];
            }

            return sum;

        }

        public static String GetRecomendation(String[] Criterion) throws IOException {//STREAMS
            ArrayList<CritAndCity> C=new ArrayList<>();//CritAndCity κλάση για το ερώτημα
            extractCriterions(C);//για να πάρω από το Travellers.txt τα κριτήρια σε μορφή y ή n
             int[] CritInt=new int[10];//Για τα Ranks
             for (int i=0;i<9;i++){
                 if (Criterion[i]=="y"){
                     CritInt[i]=1;
                 }
                 else{
                     CritInt[i]=0;
                 }
             }

            Optional<CritAndCity> maxRank= C.stream().map(i-> new CritAndCity //βρίσκω πιο έχει το μεγαλύτερο Rank που υπάρχει
                            (i.getCity(),i.getSim(),0,0,0,0,0,0,0,0,0,innerDot(i.getCriterions(),CritInt)))
                            .max(Comparator.comparingInt(CritAndCity::getRank));
            C.stream().forEach(i->i.setRank(innerDot(i.getCriterions(),CritInt)));//Φτιάχνω το Rank για όλες τις πόλεις
            List <CritAndCity> Candidates=C.stream().filter(i->i.getRank()==maxRank.get().getRank()).collect(Collectors.toList());//Αποθηκεύω όλες τις πόλεις με max Rank
            Optional<CritAndCity> SuggestedCity=Candidates.stream().max(Comparator.comparingDouble(CritAndCity::getSim));//Βρίσκω την πόλη με το μεγαλύτερο Similarity
            return(SuggestedCity.get().getCity());
        }
        public static ArrayList<CritAndCity>  extractCriterions(ArrayList<CritAndCity> C) throws IOException {
            int nl=getNumberLinesInText();
            for (int i = 0; i < nl; i++) {
                String Line = getLine(i);
                String[] Crit = extractTextFromLine(Line);
                int[] arr=new int[10];
                for (int j=11;j<20;j++){
                    if (Crit[j].equals("y")){
                        arr[j-11]=1;
                    }else{
                        arr[j-11]=0;
                    }
                }
                C.add(new CritAndCity(Crit[9],Double.valueOf(Crit[10]),arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8],0));
            }
            return C;
        }
        public static ArrayList<Traveller> extractTravellers(ArrayList<Traveller> travellerList) throws IOException {
            int nl=getNumberLinesInText();
            for (int i = 0; i < nl; i++) {
                String Line = getLine(i);
                String[] arr = extractTextFromLine(Line);
                travellerList.add(new Traveller(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3], arr[4], Double.valueOf(arr[5]), Double.valueOf(arr[6]), Double.valueOf(arr[7]), Integer.parseInt(arr[8]), arr[9],Double.valueOf(arr[10])));
                //public Traveller(String name, int age, int numberOfTravellers, String City, String Country, double current_lat, double current_lon, double current_Temperature, int KindOftrip, String visit,double Sim) {
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
        public static boolean CheckIfHeExists (String Name, String[] Needs) throws IOException {//Έλεγχος για το αν υπάρχει ο χρήστης
            int[] Found=new int[4]; // Τα αποτελέσματα
            int nl=getNumberLinesInText();
            boolean userFound =false;
            for (int i = 0; i < nl; i++) {
                String Line = getLine(i);
                String[] arr = extractTextFromLine(Line);
                userFound=Name.toLowerCase().equals(arr[0].toLowerCase());
                if (userFound){
                    for(int j=0;j<8;j++){
                        if (!(Needs[j].toLowerCase().equals(arr[11+j].toLowerCase()))){ // Αν δεν ταιριάζουν τα κριτήρια τον κρατάει ως άλλο χρήστη
                            userFound=false;
                        }
                    }
                }
                if (userFound){
                    i=nl;
                }
            }
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

    static class CritAndCity{//Κλάση για τα streams
        String City;
        double Sim;
        int[] Crit=new int[10];
        int Rank;
            CritAndCity(String City,double Sim,int C0,int C1,int C2,int C3,int C4,int C5,int C6,int C7,int C8,int rank) {
                this.Crit[0]=C0;
                this.Crit[1]=C1;
                this.Crit[2]=C2;
                this.Crit[3]=C3;
                this.Crit[4]=C4;
                this.Crit[5]=C5;
                this.Crit[6]=C6;
                this.Crit[7]=C7;
                this.Crit[8]=C8;
                this.City=City;
                this.Sim=Sim;
                this.Rank=rank;
            }

        public String getCity() {return City;
        }
        public double getSim() {return Sim;}
        public int getRank(){return Rank;}
        public void setRank(int rank){this.Rank=rank;}
        public int[] getCriterions() {return Crit;}
    }
}