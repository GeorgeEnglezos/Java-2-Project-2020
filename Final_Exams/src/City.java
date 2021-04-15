import exception.WikipediaNoArcticleException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class City {

    String City , Country;
    String weather;
    double lat, lon,Temperature;
    int  Cafes, Museums ,Restaurants ,Bars,Parks,Zoos,Monuments,Viewpoints,Beaches;

    public City(String City, String Country, String weather, double lat, double lon, int Cafes, int Museums, int Restaurants, int Bars, int Parks, int Zoos, int Monuments, int Viewpoints, int Beaches,double Temperature) {
        this.City = City;
        this.Country = Country;
        this.weather = weather;
        this.lat = lat;
        this.lon = lon;
        this.Cafes = Cafes;
        this.Museums = Museums;
        this.Restaurants = Restaurants;
        this.Bars = Bars;
        this.Parks = Parks;
        this.Zoos = Zoos;
        this.Monuments = Monuments;
        this.Viewpoints = Viewpoints;
        this.Beaches = Beaches;
        this.Temperature=Temperature;
    }
    public double getTemperature(){return Temperature;}
    public void setTemperature(double Temperature){this.Temperature=Temperature;}

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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
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

    public int getCafes() {
        return Cafes;
    }

    public void setCafes(int Cafes) {
        this.Cafes = Cafes;
    }

    public int getMuseums() {
        return Museums;
    }

    public void setMuseums(int Museums) {
        this.Museums = Museums;
    }

    public int getRestaurants() {
        return Restaurants;
    }

    public void setRestaurants(int Restaurants) {
        this.Restaurants = Restaurants;
    }

    public int getBars() {
        return Bars;
    }

    public void setBars(int Bars) {
        this.Bars = Bars;
    }

    public int getParks() {
        return Parks;
    }

    public void setParks(int Parks) {
        this.Parks = Parks;
    }

    public int getZoos() {
        return Zoos;
    }

    public void setZoos(int Zoos) {
        this.Zoos = Zoos;
    }

    public int getMonuments() {
        return Monuments;
    }

    public void setMonuments(int Monuments) {
        this.Monuments = Monuments;
    }

    public int getViewpoints() {
        return Viewpoints;
    }

    public void setViewpoints(int Viewpoints) {
        this.Viewpoints = Viewpoints;
    }

    public int getBeaches() {
        return Beaches;
    }

    public void setBeaches(int Beaches) {
        this.Beaches = Beaches;
    }
//Για να πάρω το Destination Panel
        public static void Destination(String[] Needs, JCheckBox[] Options, JFrame f, JPanel criterionPanel, JPanel menuPanel, Traveller traveller) throws IOException, WikipediaNoArcticleException { //Aν θες να τα γυρνάς κάνεις το void String και γυρνάς κάποιο array
        String appid = ""; //Your openweathermap id.
        System.out.println(Text.ANSI_BLUE+"You are in Destination"+Text.ANSI_RESET);
        ArrayList<City> cities=new ArrayList<>();
        ArrayList<JLabel> CityLabels = new ArrayList<>();
        ArrayList<JLabel> CountryLabels = new ArrayList<>();
        ArrayList<JTextField> CountryText = new ArrayList<>();
        ArrayList<JTextField> CityText = new ArrayList<>();
        ArrayList<CandidateCity> CC = new ArrayList<CandidateCity>();
        ArrayList<JLabel> wrong = new ArrayList<>();

        //Exams
            ArrayList<Exams> Citieshistory=new ArrayList<>();

        int[] criterionFound=new int[9];
        JPanel DestinationPanel = new JPanel();

        DestinationPanel.setVisible(true);
        DestinationPanel.setLayout(null);
        DestinationPanel.setBackground(Color.LIGHT_GRAY);
        DestinationPanel.setBounds(200,300,500,500);
        criterionPanel.setVisible(false);
        DestinationPanel.setVisible(true);
        f.setContentPane(DestinationPanel);
        DestinationPanel.setLayout(null);
        //Spinner για το πόσες πόλεις να εμφανίσει
        SpinnerModel value =
                new SpinnerNumberModel(2, //initial value
                        2, //minimum value
                        12, //maximum value
                        1); //step
        JSpinner citiesSpinner = new JSpinner(value);
        citiesSpinner.setBackground(Color.lightGray);
        ((JSpinner.DefaultEditor) citiesSpinner.getEditor()).getTextField().setEditable(false);
        citiesSpinner.setBounds(710,60,50,40);
        citiesSpinner.setFont(new Font("Arial",Font.BOLD,25));

        JLabel CitiesCount=new JLabel("How many Cities do you want to compare? ");
        CitiesCount.setFont(new Font("Arial", Font.PLAIN, 30));
        CitiesCount.setBounds(140,50,600,60);

        CityLabels.add(new JLabel("City 1:"));
        setFont(CityLabels.get(0),0,2);
        CityText.add(new JTextField());
        setFont(CityText.get(0),-40,2);
        CountryLabels.add(new JLabel("Country 1:"));
        setFont(CountryLabels.get(0),0,3);
        CountryText.add(new JTextField());
        setFont(CountryText.get(0),-40,3);

        wrong.add(new JLabel("*"));
        setFont(wrong.get(0),+440,0+2);
        wrong.get(0).setForeground(Color.red);
        wrong.get(0).setVisible(false);
        wrong.add(new JLabel("*"));
        setFont(wrong.get(1),+440,1+2);
        wrong.get(1).setForeground(Color.red);
        wrong.get(1).setVisible(false);

        CityLabels.add(new JLabel("City 2:"));
        setFont(CityLabels.get(1),0,4);
        CityText.add(new JTextField());
        setFont(CityText.get(1),-40,4);
        CountryLabels.add(new JLabel("Country 2:"));
        setFont(CountryLabels.get(1),0,5);
        CountryText.add(new JTextField());
        setFont(CountryText.get(1),-40,5);

        wrong.add(new JLabel("*"));
        setFont(wrong.get(2),+440,2+2);
        wrong.get(2).setForeground(Color.red);
        wrong.get(2).setVisible(false);
        wrong.add(new JLabel("*"));
        setFont(wrong.get(3),+440,3+2);
        wrong.get(3).setForeground(Color.red);
        wrong.get(3).setVisible(false);

        JLabel WeatherQuestion =new JLabel("Do you want to include Cities with bad weather?");
        WeatherQuestion.setBounds(100,10,670,50);
        WeatherQuestion.setFont(new Font("Arial", Font.PLAIN, 30));
        JRadioButton Yes=new JRadioButton("Yes");
        Yes.setBounds(730,10,80,50);
        Yes.setBackground(Color.lightGray);
        Yes.setFont(new Font("Arial", Font.PLAIN, 30));
        JRadioButton No=new JRadioButton("No");
        No.setBounds(810,10,80,50);
        No.setBackground(Color.lightGray);
        No.setFont(new Font("Arial", Font.PLAIN, 30));
        ButtonGroup bg=new ButtonGroup();
        bg.add(Yes);bg.add(No);
        Yes.setSelected(true);//Για να είναι κάποιο επιλεγμένο

        DestinationPanel.add(WeatherQuestion);
        DestinationPanel.add(Yes);DestinationPanel.add(No);

        DestinationPanel.add(CountryLabels.get(0));DestinationPanel.add(CountryText.get(0));
        DestinationPanel.add(CityLabels.get(0));DestinationPanel.add(CityText.get(0));
        DestinationPanel.add(CountryLabels.get(1));DestinationPanel.add(CountryText.get(1));
        DestinationPanel.add(CityLabels.get(1));DestinationPanel.add(CityText.get(1));
        DestinationPanel.add(wrong.get(0));DestinationPanel.add(wrong.get(1));
        DestinationPanel.add(wrong.get(2));DestinationPanel.add(wrong.get(3));

        JButton Confirm =new JButton("Confirm");
        JButton Menu =new JButton("Menu");
        DestinationPanel.add(Menu);
        DestinationPanel.add(citiesSpinner);
        DestinationPanel.add(Confirm);
        DestinationPanel.add(CitiesCount);
        setFont(Confirm,515,15);
        setFont(Menu,15,15);

        //For the Exams
            JButton ShowCities =new JButton("View your Cities");
            DestinationPanel.add(ShowCities);
            setFont(ShowCities,265,15);


            citiesSpinner.addChangeListener(new ChangeListener() {
            //@Override
            public void stateChanged(ChangeEvent e) { // Για το spinner
                int y=CityLabels.size()+4;
                int NumberInt= (int) ((JSpinner)e.getSource()).getValue();
                    if (NumberInt < CityLabels.size()) { // Για να μειώσω τα input
                        if (CityLabels.size()==7){
                            int j=2;
                             int z=1;
                             for (JLabel J :wrong){
                                 z++;
                                 setFont(J,440,z);
                             }

                            for (int i=0;i<6;i++){
                                j+=2;
                                setFont(CityLabels.get(i),0,j-2);
                                setFont(CityText.get(i),-40,j-2);
                                setFont(wrong.get(wrong.size()-1),500,j-2);
                                setFont(CountryLabels.get(i),0,j-1);
                                setFont(CountryText.get(i),-40,j-1);
                                setFont(wrong.get(wrong.size()-2),500,j-1);
                                DestinationPanel.revalidate();
                                DestinationPanel.repaint();
                                DestinationPanel.updateUI();
                            }
                        }
                        for (int i=CityLabels.size()-1;i>=NumberInt;i--){
                            DestinationPanel.remove(CityLabels.get(i)); // Όλα το ίδιο size έχουνε
                            DestinationPanel.remove(CountryLabels.get(i));
                            DestinationPanel.remove(CityText.get(i));
                            DestinationPanel.remove(CountryText.get(i));
                            wrong.get(i*2).setVisible(false);
                            wrong.get(i*2+1).setVisible(false);
                            DestinationPanel.remove(wrong.get(i*2));
                            DestinationPanel.remove(wrong.get(i*2+1));
                            DestinationPanel.revalidate();
                            DestinationPanel.repaint();
                            DestinationPanel.updateUI();

                            y=(CityLabels.size()-1);
                            CityLabels.remove(i);
                            CityText.remove(i);
                            CountryLabels.remove(i);
                            CountryText.remove(i);
                            wrong.remove(i*2+1);
                            wrong.remove(i*2);
                        }


                    }else if(CityLabels.size() < NumberInt){ // Για να αυξήσω τα input
                         if (CityLabels.size()<6){
                            for (int i = CityLabels.size(); i < NumberInt; i++) {
                                int  j=CityLabels.size()+1;
                                y+=CityLabels.size();
                                CityLabels.add(new JLabel("City " + j + ":"));
                                setFont(CityLabels.get(CityLabels.size()-1),0,y-2);
                                CityText.add(new JTextField());
                                setFont(CityText.get(CityText.size()-1),-40,y-2);
                                wrong.add(new JLabel("*"));
                                setFont(wrong.get(wrong.size()-1),+440,y-2);
                                wrong.get(wrong.size()-1).setForeground(Color.red);
                                wrong.get(wrong.size()-1).setVisible(false);
                                CountryLabels.add(new JLabel("Country" + j + ":"));
                                setFont(CountryLabels.get(CountryLabels.size()-1),0,y-1);
                                CountryText.add(new JTextField());
                                setFont(CountryText.get(CountryText.size()-1),-40,y-1);
                                wrong.add(new JLabel("*"));
                                setFont(wrong.get(wrong.size()-1),440,y-1);
                                wrong.get(wrong.size()-1).setForeground(Color.red);
                                wrong.get(wrong.size()-1).setVisible(false);

                                //setFont(Confirm,100,y+1);
                                DestinationPanel.add(CityLabels.get(CityLabels.size()-1)); // Όλα το ίδιο size έχουνε
                                DestinationPanel.add(CountryLabels.get(CityLabels.size()-1));
                                DestinationPanel.add(CityText.get(CityLabels.size()-1));
                                DestinationPanel.add(CountryText.get(CityLabels.size()-1));
                                DestinationPanel.add(wrong.get(wrong.size()-1));
                                DestinationPanel.add(wrong.get(wrong.size()-2));
                                DestinationPanel.revalidate();
                                DestinationPanel.repaint();
                                DestinationPanel.updateUI();
                            }
                            y++;
                        }else if (CityLabels.size()==6){
                             for (int i=0;i<12;i++){
                                 setFont(wrong.get(i),270,i+2);
                             }
                             int j=2;
                             for (int i=0;i<6;i++){
                                 //j+=i;
                                 j+=2;

                                 setFont(CityLabels.get(i),-200,j-2);
                                 setFont(CityText.get(i),-200,j-2);
                                 setFont(CountryLabels.get(i),-200,j-1);
                                 setFont(CountryText.get(i),-200,j-1);
                                 DestinationPanel.revalidate();
                                 DestinationPanel.repaint();
                                 DestinationPanel.updateUI();
                             }
                             j=4;
                             CityLabels.add(new JLabel("City 7:"));
                             setFont(CityLabels.get(CityLabels.size()-1),300,j-2);
                             CityText.add(new JTextField());
                             setFont(CityText.get(CityText.size()-1),260,j-2);
                             wrong.add(new JLabel("*"));
                             setFont(wrong.get(wrong.size()-1),740,j-2);
                             wrong.get(wrong.size()-1).setForeground(Color.red);
                             wrong.get(wrong.size()-1).setVisible(false);
                             CountryLabels.add(new JLabel("Country 7:"));
                             setFont(CountryLabels.get(CountryLabels.size()-1), 300,j-1);
                             CountryText.add(new JTextField());
                             setFont(CountryText.get(CountryText.size()-1),260,j-1);
                             wrong.add(new JLabel("*"));
                             setFont(wrong.get(wrong.size()-1),740,j-1);
                             wrong.get(wrong.size()-1).setForeground(Color.red);
                             wrong.get(wrong.size()-1).setVisible(false);

                             //setFont(Confirm,100,y+1);
                             DestinationPanel.add(CityLabels.get(CityLabels.size()-1)); // Όλα το ίδιο size έχουνε
                             DestinationPanel.add(CountryLabels.get(CityLabels.size()-1));
                             DestinationPanel.add(CityText.get(CityLabels.size()-1));
                             DestinationPanel.add(CountryText.get(CityLabels.size()-1));
                             DestinationPanel.add(wrong.get(wrong.size()-1));
                             DestinationPanel.add(wrong.get(wrong.size()-2));
                             DestinationPanel.revalidate();
                             DestinationPanel.repaint();
                             DestinationPanel.updateUI();
                        }else if (CityLabels.size()>6){


                             //JOptionPane.showMessageDialog(DestinationPanel,">7","Alert",JOptionPane.WARNING_MESSAGE);
                             y=CityLabels.size()-2;
                             for (int i = CityLabels.size(); i < NumberInt; i++) {
                                int  j=CityLabels.size()+1;
                                y+=CityLabels.size()-6;
                                CityLabels.add(new JLabel("City " + j + ":"));
                                setFont(CityLabels.get(CityLabels.size()-1),300,y-2);
                                CityText.add(new JTextField());
                                setFont(CityText.get(CityText.size()-1),260,y-2);
                                wrong.add(new JLabel("*"));
                                setFont(wrong.get(wrong.size()-1),+740,y-2);
                                wrong.get(wrong.size()-1).setForeground(Color.red);
                                wrong.get(wrong.size()-1).setVisible(false);
                                CountryLabels.add(new JLabel("Country" + j + ":"));
                                setFont(CountryLabels.get(CountryLabels.size()-1),300,y-1);
                                CountryText.add(new JTextField());
                                setFont(CountryText.get(CountryText.size()-1),260,y-1);
                                wrong.add(new JLabel("*"));
                                setFont(wrong.get(wrong.size()-1),740,y-1);
                                wrong.get(wrong.size()-1).setForeground(Color.red);
                                wrong.get(wrong.size()-1).setVisible(false);

                                //setFont(Confirm,100,y+1);
                                DestinationPanel.add(CityLabels.get(CityLabels.size()-1)); // Όλα το ίδιο size έχουνε
                                DestinationPanel.add(CountryLabels.get(CityLabels.size()-1));
                                DestinationPanel.add(CityText.get(CityLabels.size()-1));
                                DestinationPanel.add(CountryText.get(CityLabels.size()-1));
                                DestinationPanel.add(wrong.get(wrong.size()-1));
                                DestinationPanel.add(wrong.get(wrong.size()-2));
                                DestinationPanel.revalidate();
                                DestinationPanel.repaint();
                                DestinationPanel.updateUI();
                            }
                            y++;
                        }

                    }
            }
        });
        Menu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                CC.removeAll(CC);

                DestinationPanel.setVisible(false);
                menuPanel.setVisible(true);
                f.setContentPane(menuPanel);
            }
        });
        Confirm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                CC.removeAll(CC);
                cities.removeAll(cities);
                try {
                    CitiesDB.LoadCitiesFromDB(cities);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                int i=0;
                
                for (JTextField L : CityText) { //Ζυγοί Cities

                    if (Boolean.FALSE.equals(Text.checkVariable(L.getText()))) {
                       // JOptionPane.showMessageDialog(panel2,"bhke prwto","Alert",JOptionPane.WARNING_MESSAGE);
                        wrong.get(i).setVisible(true);
                    } else {
                        wrong.get(i).setVisible(false);
                    }
                    i+=2;
                }

                i=1;
                for (JTextField L : CountryText) {//Μονοί Countries
                    
                    if (Boolean.FALSE.equals(Text.checkVariable(L.getText()))) {
                        wrong.get(i).setVisible(true);
                    } else {
                        wrong.get(i).setVisible(false);
                    }
                    i+=2;
                }
                boolean ReadyToGo=true;
                for (JLabel W : wrong) {//Μονοί Countries
                    if (Boolean.TRUE.equals(W.isVisible())){ // Ελέγχω όλο το ArrayList wrong για να δω αν είναι όλα εντάξει
                        ReadyToGo=false;
                        break;
                    }
                }
                if (Boolean.TRUE.equals(ReadyToGo)){ // Αν δεν έχω Errors προχωράω
                    i=0;
                    for (JLabel J : CityLabels) { //Φτιάχνω πρόχειρα τις πόλεις Candidate Cities
                        String City = CityText.get(i).getText().toUpperCase();
                        String Country = CountryText.get(i).getText().toUpperCase();
                        CC.add(new CandidateCity(City, Country, "sunny", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0));
                        i++;
                    }

                    for (CandidateCity C:CC){ // Για κάθε CC
                        boolean DoesCityExist= CandidateCity.CheckDBforCity(C,cities);
                        System.out.println("Does City Exist?"+DoesCityExist);
                        try {
                            CitiesDB.LoadCitiesFromDB(cities);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        if (Boolean.FALSE.equals(DoesCityExist)){ // Αν η CC δεν υπάρχει στο ArrayList με τα Cities
                            try {

                                String Weather="Sunny";
                                double[] arr = new double[0]; //Για να πάρω temperature ,lat,lon
                                String article="";
                                try {
                                    ExecutorService service = Executors.newFixedThreadPool(3);
                                    arr = Threads.getCords(C.getCity(),C.getCountry(),service);
                                    article =Threads.getArticle(C.getCity(),service);
                                    service.shutdown();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                } catch (ExecutionException ex) {
                                    ex.printStackTrace();
                                }
                                for (int j=0;j<9;j++) {
                                    System.out.println(Options[j].getText()+" in "+"City: "+C.getCity());
                                    criterionFound[j] = countCriterion(article, Options[j].getText());
                                }
                                double temperature= arr[0];
                                System.out.println("_____________________________________________");
                                System.out.println("Temperature= "+temperature);
                                System.out.println("lat= "+arr[1]);
                                System.out.println("lon= "+arr[2]);
                                System.out.println("_____________________________________________");
                                double lat= arr[1];
                                double lon= arr[2];
                                C.setWeather(Weather);
                                C.setLat(lat);
                                C.setLon(lon);
                                C.setCafes(criterionFound[0]);
                                C.setMuseums(criterionFound[1]);
                                C.setRestaurants(criterionFound[2]);
                                C.setBars(criterionFound[3]);
                                C.setParks(criterionFound[4]);
                                C.setZoos(criterionFound[5]);
                                C.setMonuments(criterionFound[6]);
                                C.setViewpoints(criterionFound[7]);
                                C.setBeaches(criterionFound[8]);
                                C.setTemperature(temperature);
                                System.out.println("City Will Be Saved now");

                                CitiesDB.addDataToDB(C,OpenDataRest.countTotalWords(OpenDataRest.RetrieveWikipedia(C.getCity())));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (WikipediaNoArcticleException ex) {
                                ex.printStackTrace();
                            }
                            cities.add(new City(C.getCity(),C.getCountry(),C.getWeather(),C.getLat(),C.getLon(),C.getCafes(),C.getMuseums(),C.getRestaurants(),C.getBars(),C.getParks(),C.getZoos(),C.getMonuments(),C.getViewpoints(),C.getBeaches(),C.getTemperature()));
                        }else{
                            System.out.println(Text.ANSI_BLUE+"City "+C.getCity()+" was already in the ArrayList"+Text.ANSI_RESET);
                            for(City City :cities){
                                if (Boolean.TRUE.equals(CandidateCity.CheckDBforCity(C,City))){
                                    C.setWeather(City.getWeather());
                                    C.setLat(City.getLat());
                                    C.setLon(City.getLon());
                                    C.setCafes(City.getCafes());
                                    C.setMuseums(City.getMuseums());
                                    C.setRestaurants(City.getRestaurants());
                                    C.setBars(City.getBars());
                                    C.setParks(City.getParks());
                                    C.setZoos(City.getZoos());
                                    C.setMonuments(City.getMonuments());
                                    C.setViewpoints(City.getViewpoints());
                                    C.setBeaches(City.getBeaches());
                                    C.setTemperature(City.getTemperature());
                                    break;
                                }
                            }
                        }
                        //Exams
                        //Check for duplicates
                        int counter = 0;
                        String CityToSearch = C.getCity();
                        String CountryToSearch=C.getCountry();
                        for (Exams H:Citieshistory) {
                            if (CityToSearch.equals(H.getCity())&&CountryToSearch.equals(H.getCountry())) {
                                counter++;
                                break;
                            }
                        }
                        if (counter==0){//if not save
                            Citieshistory.add(new Exams(C.getCity(),C.getCountry(),C.getTemperature()));
                        }
                    }

                    try {
                        String[] maxSimCity=Traveller.CompareCities(CC,Needs,traveller,Yes,No);
                        String RecommendedCity=Text.GetRecomendation(Needs);
                        System.out.println(traveller.getKindOftrip());
                        if (maxSimCity[0].equals(RecommendedCity) || traveller.getKindOftrip()==2){
                            JOptionPane.showMessageDialog(DestinationPanel,"Best option for you is : "+maxSimCity[0]
                                    ,"RESULT:",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(DestinationPanel,"Best option for you is : "+maxSimCity[0]+
                                    "\nAlso check: "+RecommendedCity,"RESULT:",JOptionPane.INFORMATION_MESSAGE);
                        }
                        traveller.setSim(Double.valueOf(maxSimCity[1]));
                        traveller.setVisit(maxSimCity[0]);
                        // Ελέγχω αν ο χρήστης υπάρχει στo ArrayList και αν δεν υπάρχει τον αποθηκεύω
                        boolean DoesHeExist =Text.CheckIfHeExists(traveller.getName(),Needs);
                        Text.save_user_if_doesnt_exist(traveller,DoesHeExist,Needs);


                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (WikipediaNoArcticleException ex) {
                        ex.printStackTrace();
                    }
                    CC.removeAll(CC);
                }
                //Exams
                File file = new File("Cities.txt");//Φτιάχνω το αρχείο
                try {
                    Exams.SaveCities(Citieshistory);
                } catch (IOException ex) {
                    System.out.println("Exams Java Error");
                }
                // System.out.println("Something is wrong with the file");

            }});
            ShowCities.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("Sending "+Citieshistory.size());
                    Exams.printCities(Citieshistory);
                   // JOptionPane.showMessageDialog(DestinationPanel,"Works","OK:",JOptionPane.INFORMATION_MESSAGE);
                }
            });

    }
    public static void setFont(JButton b1, int x, int y){
        b1.setFont(new Font("Arial", Font.PLAIN, 30));
        b1.setBounds(180+x,20+50*y,250,50);
    }
    public static void setFont(JLabel label,int x,int y){
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(270+x,20+50*y,250,50);
    }
    public static void setFont(JTextField Text,int x,int y ){
        Text.setFont(new Font("Arial", Font.PLAIN, 30));
        Text.setBounds(540+x,20+50*y, 200,50);
    }
    public static void setFont(JCheckBox Check,int y){
        Check.setFont(new Font("Arial", Font.PLAIN, 30));
        Check.setBounds(450,100+50*y, 200,50);
    }
    public static void ChooseCriterions(Traveller traveller, JFrame f, JPanel UserInfoPanel, JPanel menuPanel) throws IOException, WikipediaNoArcticleException {//throws IOException, WikipediaNoArcticleException
        System.out.println(Text.ANSI_BLUE+"You are in ChooseCriterion"+Text.ANSI_RESET);
        JPanel CriterionPanel = new JPanel();
        String[] Needs=new String[9]; //Για να κρατάω αυτά που θέλω (Ίσως αχρείαστο)
        JCheckBox[] Options = new JCheckBox[9];//Για τις επιλογες του χρήστη
        CriterionPanel.setBackground(Color.lightGray);
        //JOptionPane.showMessageDialog(CriterionPanel,"Choose Criterions","Alert",JOptionPane.PLAIN_MESSAGE);
        Options[0]=new JCheckBox("Cafes");
        setFont(Options[0],0);
        Options[1]=new JCheckBox("Museums");
        setFont(Options[1],1);
        Options[2]=new JCheckBox("Restaurants");
        setFont(Options[2],2);
        Options[3]=new JCheckBox("Bars");
        setFont(Options[3],3);
        Options[4]=new JCheckBox("Parks");
        setFont(Options[4],4);
        Options[5]=new JCheckBox("Zoos");
        setFont(Options[5],5);
        Options[6]=new JCheckBox("Monuments");
        setFont(Options[6],6);
        Options[7]=new JCheckBox("Viewpoints");
        setFont(Options[7],7);
        Options[8]=new JCheckBox("Beaches");
        setFont(Options[8],8);


        if (traveller.getKindOftrip()==1){ //Αν είναι tourist θα του εμφανίσω το panel

            UserInfoPanel.setVisible(false);
            CriterionPanel.setVisible(true);
            f.setContentPane(CriterionPanel);
            CriterionPanel.setLayout(null);

            JLabel Header=new JLabel("What is important for your Vacations ? ");
            Header.setBounds(270,25,600,50);
            Header.setFont(new Font("Arial",Font.BOLD,30));

            JButton SelectAll = new JButton("Select all");
            setFont(SelectAll,280,12);
            JButton UnselectALL = new JButton("Unselect all");
            setFont(UnselectALL,280,13);
            JButton Confirm2 =new JButton("Confirm");
            setFont(Confirm2,280,14);

            for (int i=0;i<9;i++){
                Options[i].setBackground(Color.lightGray);
                CriterionPanel.add(Options[i]);
            }
            CriterionPanel.add(Header);
            CriterionPanel.add(Confirm2);
            CriterionPanel.add(SelectAll);
            CriterionPanel.add(UnselectALL);

            SelectAll.addActionListener(new ActionListener(){ //Τα διαλέγει όλα
                public void actionPerformed(ActionEvent e) {
                    for (int i=0;i<9;i++){
                        Options[i].setSelected(true);
                    }
                }});
            UnselectALL.addActionListener(new ActionListener(){ // Τα βγάζει όλα από τις επιλογές
                public void actionPerformed(ActionEvent e) {
                    for (int i=0;i<9;i++){
                        Options[i].setSelected(false);
                    }
                }});

            Confirm2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    boolean atLeastOne=false;
                    for (int i=0;i<9;i++){
                        if (Options[i].isSelected()){
                            //JOptionPane.showMessageDialog(f,"Yes ","Alert",JOptionPane.WARNING_MESSAGE);
                            Needs[i]="y";
                            atLeastOne=true;
                        }else{
                            //JOptionPane.showMessageDialog(f,"No","Alert",JOptionPane.WARNING_MESSAGE);
                            Needs[i]="n";
                        }
                    }
                    if (Boolean.FALSE.equals(atLeastOne)){//Αν δεν επιλέξει κριτήρια τον αλλάζω σε Business για να έχω κάτι να συγκρίνω
                        traveller.setKindOftrip(2);
                    }
                    try {
                        System.out.println(Text.ANSI_BLUE+"ChooseCrtierion->Destination"+Text.ANSI_RESET);
                        Destination(Needs,Options,f,CriterionPanel,menuPanel,traveller);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (WikipediaNoArcticleException ex) {
                        ex.printStackTrace();
                    }

                }});

        }else{//αλλιώς αν είναι business δεν δείχνω το panel και ορίζω όλο το array = n (ότι δεν θέλω να τα ψάξω )
            for (int i=0;i<9;i++){Needs[i]="n";}
            Destination(Needs,Options,f,CriterionPanel,menuPanel, traveller);
        }

    }
    public static int countCriterion (String article, String Criterion ) throws IOException, WikipediaNoArcticleException {
        System.out.println(Text.ANSI_YELLOW+"You are in countCriterion"+Text.ANSI_RESET);
        if (Criterion.equals("Beaches")){
            Criterion= Criterion.substring(0, Criterion.length() - 2);
        }else{
            Criterion= Criterion.substring(0, Criterion.length() - 1);
            // Επιδή έχω βάλει σε όλα ένα "s" στο τέλος και
            // δεν τα βρίσκει έτσι στο Wikipedia
        }
        int count = OpenDataRest.countCriterionCity(article,Criterion.toLowerCase()); // παίρνω το κριτήριο
        return count;
    }


}
