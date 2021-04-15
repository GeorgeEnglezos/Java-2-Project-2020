import exception.WikipediaNoArcticleException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
     static JPanel MenuPanel;
     static JPanel UserInfoPanel;

    public static void main(String[] args) throws IOException{
        menu();
    }
    private static void menu() throws IOException{
        final JFrame f=new JFrame("Destination App");

        System.out.println(Text.ANSI_BLUE+"You are in menu"+Text.ANSI_RESET);
        Text.CreateFile();
        CitiesDB.makeJDBCConnection(f);
        ArrayList<Traveller> travellerList = new ArrayList<Traveller>();


        //Φτιάχνω το frame που θα βρίσκονται όλα τα panel πάνω
        MenuPanel = new JPanel();
        f.setResizable(false);// Για να μην μπορεί να αλλάξει το μέγεθος
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //~1 Επιλογή να συγκρίνει πόλεις
        JButton b1=new JButton("Find your next destination!");
        b1.setBounds(330,100,500,100);
        b1.setFont(new Font("Arial", Font.PLAIN, 40));
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                UserInfo(f,MenuPanel,travellerList);
                travellerList.removeAll(travellerList);
            }
        });
        //  ~2 Επιλογή να δείξει τις προηγούμενες πόλεις
        JButton b2=new JButton("Previous searched cities!");
        b2.setBounds(330,250,500,100);
        b2.setFont(new Font("Arial", Font.PLAIN, 40));
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Καλώ την ReadData για να εκτυπωσω όλες τις πόλεις στο Ιστορικό
                try {
                    CitiesDB.PrintCities(f,MenuPanel);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //  ~3 Επιλογή να δείξει τους προηγούμενους χρήστες
        JButton b3=new JButton("List of previous users!");
        b3.setBounds(330,400,500,100);
        b3.setFont(new Font("Arial", Font.PLAIN, 40));
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 12) Εκτυπώνω όλους τους χρήστες στο Ιστορικό

                try {
                    Text.printPreviousUsers(travellerList,f,MenuPanel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        //  ~4 Να εμφανίσει σε μήνυμα τους νικητές των διαγωνισμών
        JButton b4=new JButton("Free ticket results!");
        b4.setBounds(330,550,500,100);
        b4.setFont(new Font("Arial", Font.PLAIN, 40));
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 13) Εκτυπώνω τον νικητή του free ticket και για τα δύο subclasses του Traveller
                try {
                    CallFreeTicket(f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //  ~5 Επιλογή Να κλείσει το πρόγραμμα (Αχρείαστη αλλά γιατί όχι )
        JButton b5=new JButton("Exit the program!");
        b5.setBounds(330,700,500,100);
        b5.setFont(new Font("Arial", Font.PLAIN, 40));
        b5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                System.exit(0);
            }
        });

        f.setContentPane(MenuPanel);
        MenuPanel.setBackground(Color.lightGray);
        MenuPanel.add(b1);//MenuPanel.add(tf);
        MenuPanel.add(b2);
        MenuPanel.add(b3);
        MenuPanel.add(b4);
        MenuPanel.add(b5);
        MenuPanel.setLayout(null);
        f.setSize(1150, 900);
        f.setLayout(null);
        f.setVisible(true);





    }
    //Το panel για να πάρει τα στοιχεία του Χρήστη
    private static void UserInfo(JFrame f, JPanel MenuPanel, ArrayList<Traveller> traveller){
        System.out.println(Text.ANSI_BLUE+"You are in UserInfo"+Text.ANSI_RESET);
        ArrayList<JLabel> wrong=new ArrayList<>();
        OpenDataRest ODR = new OpenDataRest();
        String appid = ""; //Your openweathermap id.
    
        UserInfoPanel = new JPanel();
        MenuPanel.setVisible(false);
        f.setContentPane(UserInfoPanel);
        //Φτιάχνω labels gia όλα ώστε να εμφανίζει " * " δίπλα από κάθε wrong input
        double j=1;
        for (int i=0;i<6;i++){
            wrong.add(new JLabel("*"));
            wrong.get(i).setForeground(Color.RED);
            wrong.get(i).setBounds(760,(int)(j*60+10),50,30);
            wrong.get(i).setFont(new Font("Arial",Font.PLAIN,30));
            wrong.get(i).setVisible(false);
            UserInfoPanel.add(wrong.get(i));
            j+=1.5;
        }

        JLabel NameLabel=new JLabel("Name:");//Βάζω ένα label για να φαίνεται το "Όνομα"
        setFont(NameLabel,0,1);
        final JTextField NameText = new JTextField(); //  Ορίζω πλαίσιο για να γράψει ο χρήστης
        setFont(NameText,0,1);

        JLabel SurnameLabel=new JLabel("Surname:");
        setFont(SurnameLabel,0,  2.5);
        final JTextField SurnameText = new JTextField();
        setFont(SurnameText,0,2.5);

        JLabel AgeLabel=new JLabel("Age:");
        setFont(AgeLabel,0,4);
        final JTextField AgeText = new JTextField();
        setFont(AgeText,0,4);

        JLabel CurrentCityLabel=new JLabel("Current City:");
        setFont(CurrentCityLabel,0,5.5);
        final JTextField CurrentCityText = new JTextField();
        setFont(CurrentCityText,0,5.5);


        JLabel CurrentCountryLabel=new JLabel("Current Country:");
        setFont(CurrentCountryLabel,0,7);
        final JTextField CurrentCountryText = new JTextField();
        setFont(CurrentCountryText,0,7);


        JLabel NumberOfTtravellersLabel=new JLabel("Travellers:");
        setFont(NumberOfTtravellersLabel,0,8.5);
        final JTextField NumberOfTtravellersText = new JTextField();
        setFont(NumberOfTtravellersText,0,8.5);


        JLabel TouristOrBusinessLabel;
        JRadioButton TouristRadio,BusinessRadio;
        TouristOrBusinessLabel=new JLabel("Reason of the trip:");
        TouristOrBusinessLabel.setBounds(295,11*55,300,30);
        TouristOrBusinessLabel.setFont(new Font("Arial",Font.PLAIN,30));
        TouristRadio=new JRadioButton("Tourism");
        TouristRadio.setBounds(560,10*60-10,200,50);
        TouristRadio.setFont(new Font("Arial", Font.PLAIN, 30));
        BusinessRadio=new JRadioButton("Business");
        BusinessRadio.setBounds(560,11*60-30,200,50);
        BusinessRadio.setFont(new Font("Arial", Font.PLAIN, 30));
        BusinessRadio.setBackground(Color.lightGray);
        TouristRadio.setBackground(Color.lightGray);
        TouristRadio.setSelected(true);

        ButtonGroup bg=new ButtonGroup();
        bg.add(TouristRadio);bg.add(BusinessRadio);

        JButton confirm = new JButton("NEXT");
        confirm.setBounds(550,12*60, 150,40);
        setFont(confirm);
        JButton Menu = new JButton("MENU");
        Menu.setBounds(350,12*60, 150,40);
        setFont(Menu);

        UserInfoPanel.add(NameLabel);UserInfoPanel.add(SurnameLabel);UserInfoPanel.add(AgeLabel);UserInfoPanel.add(TouristOrBusinessLabel);//panel1.add(label);
        UserInfoPanel.add(CurrentCityLabel);UserInfoPanel.add(CurrentCountryLabel);UserInfoPanel.add(NumberOfTtravellersLabel);
        UserInfoPanel.add(CurrentCityText);UserInfoPanel.add(CurrentCountryText);UserInfoPanel.add(NumberOfTtravellersText);
        UserInfoPanel.add(NameText);UserInfoPanel.add(SurnameText);UserInfoPanel.add(AgeText);

        UserInfoPanel.add(BusinessRadio);UserInfoPanel.add(TouristRadio);

        UserInfoPanel.add(confirm);
        UserInfoPanel.add(Menu);
        UserInfoPanel.setSize(300,300);

        UserInfoPanel.setLayout(null);
        UserInfoPanel.setVisible(true);
        UserInfoPanel.setBackground(Color.lightGray);



        Menu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                UserInfoPanel.setVisible(false);
                MenuPanel.setVisible(true);
                f.setContentPane(MenuPanel);
        }});
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (Boolean.FALSE.equals(Text.checkVariable(NameText.getText()))){
                    wrong.get(0).setVisible(true);
                }else{
                    wrong.get(0).setVisible(false);
                }
                if (Boolean.FALSE.equals(Text.checkVariable(SurnameText.getText()))){
                    wrong.get(1).setVisible(true);
                }else{
                    wrong.get(1).setVisible(false);
                }
                if (Boolean.FALSE.equals(Text.checkVariable(AgeText.getText(),18,100))){
                    wrong.get(2).setVisible(true);
                }else{
                    wrong.get(2).setVisible(false);
                }
                if (Boolean.FALSE.equals(Text.checkVariable(CurrentCityText.getText()))){
                    wrong.get(3).setVisible(true);
                }else{
                    wrong.get(3).setVisible(false);
                }
                if (Boolean.FALSE.equals(Text.checkVariable(CurrentCountryText.getText()))){
                    wrong.get(4).setVisible(true);
                }else{
                    wrong.get(4).setVisible(false);
                }
                if (Boolean.FALSE.equals(Text.checkVariable(NumberOfTtravellersText.getText(),1,50))){
                    wrong.get(5).setVisible(true);
                }else{
                    wrong.get(5).setVisible(false);
                }

                int kindOfTrip=0;
                if(TouristRadio.isSelected()){
                    kindOfTrip=1;
                } else {
                    kindOfTrip = 2;
                }
                boolean ReadyToGo=true;
                for (JLabel W : wrong) {// Ελέγχω όλο το ArrayList wrong για να δω αν είναι όλα εντάξει
                    if (Boolean.TRUE.equals(W.isVisible())){
                        //Αν βρει εμφανισμένο wrong σταματάει
                        ReadyToGo=false;
                        break;
                    }
                }
                if (ReadyToGo){
                    double[] arr = new double[0]; //Για να πάρω temperature ,lat,lon
                    try {
                        ExecutorService service = Executors.newFixedThreadPool(3);
                        arr = Threads.getCords(CurrentCityText.getText(),CurrentCountryText.getText(),service);
                        service.shutdown();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (ExecutionException ex) {
                        ex.printStackTrace();
                    }
                    double temperature= arr[0];
                    double lat= arr[1];
                    double lon= arr[2];
                    //Φτιάχνω το traveller object μέσα σε Arraylist για να είναι δυναμικό σχήμα
                    traveller.add(new Traveller(NameText.getText()+" "+SurnameText.getText(),Integer.parseInt(AgeText.getText()),Integer.parseInt(NumberOfTtravellersText.getText()),CurrentCityText.getText(),CurrentCountryText.getText(),temperature,lat,lon,kindOfTrip,"",0));
                    try {
                        City.ChooseCriterions(traveller.get(traveller.size()-1),f,UserInfoPanel,MenuPanel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (WikipediaNoArcticleException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(f,"Missing Arguments or Wrong Arguments","Alert",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private static void CallFreeTicket(JFrame frame) throws IOException {
        System.out.println(Text.ANSI_YELLOW+"You are in CallFreeTicket"+Text.ANSI_RESET);
        ArrayList<Traveller> AllTravellers=new ArrayList<>();
        Text.extractTravellers(AllTravellers);
        ArrayList<Traveller> business= new ArrayList<Traveller>();
        ArrayList<Traveller> tourist= new ArrayList<Traveller>();
        for(Traveller T : AllTravellers) {
            if (T.getKindOftrip()==1){
                tourist.add(T);
            }else {
                business.add(T);
            }
        }
        if (tourist.size()!=0 &&business.size()!=0){
            String Winner1=Tourist.freeTicket(tourist).toUpperCase();// Καλώ την μέθοδο free ticket του subclass tourist
            String part1="Winner of the tourist Free Ticket is "+Winner1+"\n";
            String Winner2=Business.freeTicket(business).toUpperCase();// Καλώ την μέθοδο free ticket του subclass business και επιστρέφει έναν τυχαίο χρήστη
            String part2="Winner of the Business Free Ticket is "+Winner2;
            JOptionPane.showMessageDialog(frame,part1+part2,"                                   ~~~~WINNERS~~~~",JOptionPane.INFORMATION_MESSAGE);

        }else
        if (tourist.size()!=0){
            String Winner1=Tourist.freeTicket(tourist).toUpperCase();// Καλώ την μέθοδο free ticket του subclass tourist
            String part1="Winner of the tourist Free Ticket is "+Winner1+"\n";
            JOptionPane.showMessageDialog(frame,part1,"                                   ~~~~WINNER~~~~",JOptionPane.INFORMATION_MESSAGE);
        }else if (business.size()!=0){
            String Winner2=Business.freeTicket(business).toUpperCase();// Καλώ την μέθοδο free ticket του subclass business και επιστρέφει έναν τυχαίο χρήστη
            String part2="Winner of the Business Free Ticket is "+Winner2;
            JOptionPane.showMessageDialog(frame,part2,"                                   ~~~~WINNER~~~~",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame,"No users yet in the Text File to search from","ERROR 404 Users Not Found ",JOptionPane.ERROR_MESSAGE);
        }

        // και βρίσκει για την πόλη με τα περισσότερα Visit τον χρήστη με το μεγαλύτερο similarity
        tourist.removeAll(tourist);
        business.removeAll(business);
        AllTravellers.removeAll(AllTravellers);
    }
    public static void setFont(JButton b1){
        b1.setFont(new Font("Arial", Font.PLAIN, 30));
    }
    public static void setFont(JLabel label, double x, double y){
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds((int) (290+x), (int) (60*y),250,30);
    }
    public static void setFont(JTextField Text,int x,double y ){
        Text.setFont(new Font("Arial", Font.PLAIN, 30));
        Text.setBounds(560+x,(int) (60*y), 200,50);
    }
}
