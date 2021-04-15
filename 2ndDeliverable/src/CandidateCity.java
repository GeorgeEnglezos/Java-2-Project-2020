import java.util.ArrayList;

public class CandidateCity {
    String City , Country;
    String weather;
    double lat, lon;
    int  museums, cafes ,Restaurants ,Bars;
    public int getMuseums() { return museums; }
    public void setMuseums(int museums) { this.museums = museums; }
    public int getCafes() { return cafes; }
    public void setCafes(int cafes) { this.cafes = cafes; }
    public int getRestaurants() { return Restaurants; }
    public void setRestaurants(int restaurants) { Restaurants = restaurants; }
    public int getBars() { return Bars; }
    public void setBars(int bars) { Bars = bars; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) {this.weather = weather;}
    public double getLat() {return lat;}
    public void setLat(double lat) {this.lat = lat;}
    public double getLon() {return lon;}
    public void setLon(double lon) {this.lon = lon;}
    public String getCity() {return City;}
    public void setCity(String city) {City = city;}
    public String getCountry() {return Country;}
    public void setCountry(String country) {Country = country;}

    public CandidateCity(String city, String country, int museums, int cafes, int restaurants, int bars, String weather, double lat, double lon) {
        this.museums = museums;
        this.cafes = cafes;
        this.Restaurants = restaurants;
        this.Bars = bars;
        this.weather = weather;
        this.City = city;
        this.Country = country;
        this.lat = lat;
        this.lon = lon;
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
        @Override
        public boolean equals(Object o) {

            // If the object is compared with itself then return true
            if (o == this) {
                return true;
            }

		/* Check if o is an instance of City or not
		"null instanceof [type]" also returns false */
            if (!(o instanceof City)) {
                return false;
            }

            // typecast o to City so that we can compare data members
            City c = (City) o;

            // Compare the data members and return accordingly */
            return ((this.getCity().equalsIgnoreCase(c.getCity()))&& (this.getCountry().equalsIgnoreCase(c.getCountry())));

        }

    }




