package Java;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Currencymethod {
	double result;
	public Currencymethod(String input, String out, String value) {
		
		try {
			String host = "https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency";
			
			com.mashape.unirest.http.HttpResponse<JsonNode> response =  Unirest.get(host)
				    .header("content-type", "application/json")
				    .header("X-RapidAPI-Key", "6b6359b301msh2c8d6ea91183092p17c9a5jsne34e11458142")
				    .header("X-RapidAPI-Host", "currency-converter-by-api-ninjas.p.rapidapi.com")
				    .queryString("have", input)
				    .queryString("want", out)
				    .queryString("amount", value)
				    .asJson();
			
			JsonNode jsonResponse = response.getBody();
			
			System.out.println(jsonResponse); // Print the JSON response as a JsonNode
			
			JsonParser parser = new JsonParser();
			JsonObject responseObj = parser.parse(response.getBody().toString()).getAsJsonObject();
			
			result = responseObj.get("new_amount").getAsDouble();		
			
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public double currencyreturn() {
		return result;
	}
}
