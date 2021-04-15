import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import exception.WikipediaNoArcticleException;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

/**Description and weather information using OpenData with Jackson JSON processor and Jersey Client.
 * @since 29-2-2020
 * @version 1.0
 * @author John Violos  */
public class OpenDataRest {

	/**Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city using Jersey framework.
	 * @param city The Wikipedia article and OpenWeatherMap city.
	 * @param country The country initials (i.e. gr, it, de).
	 * @param appid Your API key of the OpenWeatherMap.*/
	public static double[] RetrieveOpenWeatherMap(String city, String country, String appid) throws JsonParseException, JsonMappingException, IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+"").build());
		ObjectMapper mapper = new ObjectMapper();
		String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
		OpenWeatherMap weather_obj = mapper.readValue(json,OpenWeatherMap.class);
		double temperature =weather_obj.getMain().getTemp();
		double lat=weather_obj.getCoord().getLat();
		double lon=weather_obj.getCoord().getLon();
		double[] arr = new double[3]; // Array με τρία πράματα για να πάρω το temp,lat ,lon
		arr[0] = temperature ;
		arr[1] =lat ;
		arr[2]=lon;
		return arr;
		// returning array of elements
	}


	/**Retrieves Wikipedia information, geotag (lan, lon) and a Wikipedia article for a given city using Jersey framework.
	 * @param city The Wikipedia article and OpenWeatherMap city.
	 * @throws WikipediaNoArcticleException */
	public static String RetrieveWikipedia(String city) throws  IOException, WikipediaNoArcticleException {
		String article="";
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city.toLowerCase()+"&format=json&formatversion=2").build());
		ObjectMapper mapper = new ObjectMapper();
		String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
		if (json.contains("pageid")) {
			MediaWiki mediaWiki_obj =  mapper.readValue(json, MediaWiki.class);
			article= mediaWiki_obj.getQuery().getPages().get(0).getExtract();
			article=article.toLowerCase();
			//System.out.println(city+" Wikipedia article: "+article);
		} else throw new exception.WikipediaNoArcticleException(city);
		return article;
	}


	public static int countTotalWords(String str) {
		String s[]=str.split(" ");
		return 	s.length;
	}

	/** Counts the number of times a criterion occurs in the city wikipedia article.
	 @param cityArticle  The String of the retrieved wikipedia article.
	 @param criterion The String of the criterion we are looking for.
	 @return An integer, the number of times the criterion-string occurs in the wikipedia article.
	 */
	public static int countCriterionCity(String cityArticle, String criterion) {
		cityArticle=cityArticle.toLowerCase();
		int index = cityArticle.indexOf(criterion);
		int count = 0;
		while (index != -1) {
			count++;
			cityArticle = cityArticle.substring(index + 1);
			index = cityArticle.indexOf(criterion);
		}
		return count;
	}

}