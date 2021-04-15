
import java.util.ArrayList;

public class CandidateCity {
    String City , Country;
    String weather;
    double lat, lon;
    int  Cafes, Museums ,Restaurants ,Bars,Parks,Zoos,Monuments,Viewpoints,Beaches;

    public CandidateCity(String City, String Country, String weather, double lat, double lon, int Cafes, int Museums, int Restaurants, int Bars, int Parks, int Zoos, int Monuments, int Viewpoints, int Beaches) {
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


    public static boolean CheckDBforCity(CandidateCity Candidate, ArrayList<City> Cities){
        boolean Exists=false;
        for(City C : Cities) {
            //System.out.println(Candidate.getCity()+"="+C.getCity());
            Exists=Candidate.equals(C);
            //System.out.println(Exists);
            if(Boolean.TRUE.equals(Exists)){
                Exists=true;
                break;
            }
        }
       // System.out.println("returning " + Exists);
        return Exists;

    }
    public static boolean CheckDBforCity(CandidateCity Candidate, City C){
        boolean Exists=false;
            Exists=Candidate.equals(C);
            //System.out.println(Exists);
            if(Boolean.TRUE.equals(Exists)){
                Exists=true;
            }
        // System.out.println("returning " + Exists);
        return Exists;

    }

        @Override
        public boolean equals(Object o) {

            // If the object is compared with itself then return true
            if (o == this) {
                return true;
            }

		/* Check if o is an instance of City or not
		"null instanceof [type]" also returns false*/
            if (!(o instanceof City)) {
                return false;
            }

            // typecast o to City so that we can compare data members
            City c = (City) o;

            // Compare the data members and return accordingly
            return ((this.getCity().equalsIgnoreCase(c.getCity()))&& (this.getCountry().equalsIgnoreCase(c.getCountry())));

        }

    }


// Driver class to test the City class



