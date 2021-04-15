//https://examples.javacodegeeks.com/core-java/threads/return-a-value-from-a-thread/
import exception.WikipediaNoArcticleException;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


class Threads {

    private static String article;
    private static double[] arr;
    public static double[] getCords(String City, String Country, ExecutorService service) throws ExecutionException, InterruptedException {
        Future<double[]> RetCords = service.submit(new RetCords( City, Country));
        arr= RetCords.get();
        return arr;
    }
    public static String getArticle(String City, ExecutorService service ) throws ExecutionException, InterruptedException {
        Future<String> RetArticle = service.submit(new RetArticle(City));
        article=(RetArticle.get());
        System.out.println("Returning from ");
        return article;

    }

    static class RetCords implements Callable<double[]> {
        final  String appid =""; //Your openweathermap id.
        OpenDataRest ODR = new OpenDataRest();
        private String City;
        private String Country;

        RetCords(String city, String country) {
            this.City=city;
            this.Country=country;
        }

        @Override
        public double[] call() throws IOException {
            System.out.println("You are in retdouble");

            arr=ODR.RetrieveOpenWeatherMap(City,Country,appid);
            return arr;
        }
    }

    static class RetArticle implements Callable<String> {
        private String City;
        RetArticle(String city) {
            this.City=city;
        }

        @Override
        public String call() throws IOException, WikipediaNoArcticleException {
            System.out.println("You are in article");

            OpenDataRest ODR = new OpenDataRest();
            article =ODR.RetrieveWikipedia(City);
            return article;
        }
    }
}
/*
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import exception.WikipediaNoArcticleException;

import java.io.IOException;

class RunnableDemo implements Runnable {
    private final String appid = ""; //Your openweathermap id.

        private Thread t;
        private String threadName;
        private String threadCity;
        private String threadCountry;
        private int threadWhichMethod;//Ποια μέθοδος θα τρέξω από τις δύο

        Temp T;
        public double[] arr = new double[4];
        public String article="";
        OpenDataRest ODR= new OpenDataRest();

        public RunnableDemo(int whichMethod,String name,String City,String Country,Temp T) {
            threadWhichMethod=whichMethod;
            threadCity=City;
            threadCountry=Country;
            threadName = name;
            this.T=T;
            System.out.println("Creating " + threadName+" City:"+threadCity);
        }


    public void start() {
            System.out.println("Starting " + threadName);
            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }

        public void run() {
            if (threadWhichMethod==1){
                System.out.println("Running " + threadName+" for City :" +threadCity);
                try {
                    arr=ODR.RetrieveOpenWeatherMap(threadCity,threadCountry,appid);
                    T.setTemperature(arr[0]);
                    T.setLat(arr[1]);
                    T.setLon(arr[2]);
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(threadWhichMethod==2){
                try {
                    article=OpenDataRest.RetrieveWikipedia(threadCity);
                    T.setArticle(article);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WikipediaNoArcticleException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread " + threadName + " exiting.");
        }


    }

    class Temp{
        private double lat;
        private double lon;
        private double temperature;
        private String article;
        /*public Temp(double lat ,double lon,double temperature){
            this.lat=lat;
            this.lon=lon;
            this.temperature=temperature;
        }
         */
/*
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLat(){
        return lat;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }
    public void setTemperature(double temperature){
        this.temperature=temperature;
    }
    public double getTemperature(){
        return temperature;
    }
}


*/
