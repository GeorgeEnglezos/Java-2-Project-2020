import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

// Έβαλα στις κλάσεις Cities , Candidates την παράμετρο double Temperature
// Στην βάση προστέθηκε το Temperature
//Constructor Από Κάτω
//Συναρτήσεις για προσπέλαση του αρχείου
public class Exams implements Comparable<Exams>{
    String City,Country;
    double Temperature;
    public Exams(String City, String Country,double Temperature) {
        this.City = City;
        this.Country = Country;
        this.Temperature=Temperature;
    }
    public String getCity(){return City;}
    public void setCity(String City){this.City=City;}

    public String getCountry(){return Country;}
    public void setCountry(){this.Country=Country;}

    public double getTemperature(){return Temperature;}
    public void setTemperature(double Temperature){this.Temperature=Temperature;}

    public static void printCities(ArrayList<Exams> History)//Cities the user searched for in this session
    {

        JFrame f2 = new JFrame("City List");
        f2.setBounds(10, 10, 350, 500);
        //f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setVisible(true);
        JPanel P = new JPanel();
        JTable j;
        String[][] data= new String[History.size()][3];
        Collections.sort(History);
        System.out.println(History.size());
        int i=0;
        for (Exams H : History) {
            System.out.println(H.getCity()+" "+H.getCountry()+" "+H.getTemperature());
            data[i]= new String[]{H.getCity(),H.getCountry(),Double.toString(H.getTemperature())};
            i++;
        }
        String[] columnNames = {"City","Country","Temperature"};
        j = new JTable(data, columnNames);
        j.setBounds(0, 0, 300, 500);
        JScrollPane UsersPanel = new JScrollPane(j);
        UsersPanel.setBounds(0, 0, 300, 500);
        UsersPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        P.add(UsersPanel);
        P.setLayout(null);
        f2.setContentPane(P);
    }


    public static void SaveCities(ArrayList<Exams> History) throws IOException {
        File f = new File("Cities.txt");
        if (f.createNewFile()) {
            System.out.println("File created: " + f.getName());
        } else {
            System.out.println("File already exists.");
        }
        for (Exams H : History){
            if (Boolean.FALSE.equals(CheckIfCityExists(H.getCity(),H.getCountry()))){//Check if city is in the file
                    File file = new File("Cities.txt");
                    FileWriter fr = new FileWriter(file, true);//append
                    fr.write(H.getCity()+","+H.getCountry()+","+H.getTemperature()+"\n");
                    fr.close();
            }
        }
    }



    private static boolean CheckIfCityExists (String City, String Country) throws IOException {//Έλεγχος για το αν υπάρχει η πόλη
        int nl=getNumberLinesInText();
        boolean CityFound =false;
        for (int i = 0; i < nl; i++) {//Τρέχω για όλες τις γραμμές
            String Line = getLine(i);//Παίρνω την γραμμή
            String[] arr = extractTextFromLine(Line);//Σπάω το String σε ένα Array
            //Έλεγχος
            CityFound=City.toLowerCase().equals(arr[0].toLowerCase());
            if (CityFound){
                System.out.println("Found City");
                if (Country.toUpperCase().equals(arr[1].toUpperCase())){
                    System.out.println("Found Country");
                    CityFound=true;
                    return CityFound;
                    }else{
                    System.out.println(Country.toUpperCase()+"="+arr[1]);
                    CityFound=false;
                }
            }
        }
        return CityFound;
    }
    private static int getNumberLinesInText() throws  IOException{
        int lines=0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Cities.txt"))) {
            while (reader.readLine() != null) lines++;
        }catch (IOException ex){
            return 0;
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
        try (Stream<String> lines = Files.lines(Paths.get("Cities.txt"))){
            Line = lines.skip(i).findFirst().get();
        }
        return Line;
    }

    @Override
    public int compareTo(@NotNull Exams o) {
            return Double.compare(o.Temperature, this.Temperature);
    }
    //https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html#compare-double-double-

}
