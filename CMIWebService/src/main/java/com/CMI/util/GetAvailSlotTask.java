package com.CMI.util;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.CMI.entity.CarPark;
import com.CMI.service.CarParkService;

@Component
public class GetAvailSlotTask {
	
	@Autowired
	private CarParkService  carParkService;
	
	@Scheduled(cron = "0/20 * * * * ?")
	public void getAvailSlot() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders  headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    Date now = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
	    
	    String url = "https://api.data.gov.sg/v1/transport/carpark-availability?date_time="+sdf.format(now)+"T"+sdf2.format(now);
	    System.out.println(url);
	    String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	    JSONObject o = new JSONObject(response);
	    JSONArray arr = o.getJSONArray("items");
	    JSONObject jsonObject = arr.optJSONObject(0);
	    JSONArray list = jsonObject.getJSONArray("carpark_data");
	    for(int i = 0 ; i < list.length();i++) {
	    	JSONObject object = list.getJSONObject(i);
	    	JSONArray carpark_info = object.getJSONArray("carpark_info");
	    	CarPark carPark = carParkService.getCarParkByCarParkName(object.getString("carpark_number"));
	    	if(carPark !=null) {
	    	carPark.setTotal_lot(Integer.parseInt(carpark_info.optJSONObject(0).getString("total_lots")));
	    	carPark.setLot_available(Integer.parseInt(carpark_info.optJSONObject(0).getString("lots_available")));
	    	carParkService.saveCarPark(carPark);
	    	}
		   
	    }
	    //System.out.println(list.get(0));
	}
}
