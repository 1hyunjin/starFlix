package com.ssafy.starflix.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.weather.model.WeatherDTO;

@Service
public class WeatherService {

	private static String serviceKey = "UuxOS17nvqpC2QYJPA%2BQlTx41Jln2ZiXptAG81ndjleA6wF1Zczi1JASBPKrr7JyKcDhMvF1MGZQRBFZt9UiXw%3D%3D";
	private static String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

	public List<WeatherDTO> getWeatherDataForRegion(String baseDate) throws Exception {
		List<WeatherDTO> allWeatherData = new ArrayList<>();

		String[][] regions = { { "서울", "60", "127" }, { "부산", "98", "76" }, { "대구", "89", "90" }, { "인천", "55", "124" },
				{ "광주", "58", "74" }, { "대전", "67", "100" }, { "울산", "102", "84" }, { "세종", "66", "103" },
				{ "경기도", "60", "120" }, { "충청북도", "69", "107" }, { "충청남도", "68", "100" }, { "전라북도", "63", "89" },
				{ "전라남도", "51", "67" }, { "경상북도", "87", "106" }, { "경상남도", "91", "77" }, { "제주", "52", "38" },
				{ "강원도", "73", "134" } };

		for (String[] region : regions) {
			String regionName = region[0];
			String nx = region[1];
			String ny = region[2];

			System.out.println(Arrays.toString(region));

			String jsonResponse = getInfoWeather(baseDate, nx, ny);
			List<WeatherDTO> weatherData = parseWeatherData(jsonResponse, regionName);
			allWeatherData.addAll(weatherData);
		}
		return allWeatherData;
	}

	public String getInfoWeather(String baseDate, String nx, String ny) throws Exception {
		StringBuilder sb = new StringBuilder();

		StringBuilder urlBuilder = new StringBuilder(apiUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("12", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
				+ URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON) Default: XML */
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("2000", "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /* 예보지점의 X 좌표값 */
		urlBuilder.append(
				"&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /* 예보지점의 Y 좌표값 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		String result = sb.toString();
		System.out.println("API Response: " + result);

		return result;
	}

	public List<WeatherDTO> parseWeatherData(String jsonResponse, String regionName) throws Exception {
		List<WeatherDTO> weatherDTOList = new ArrayList<>();

		try {
			JSONObject jsonObject = new JSONObject(jsonResponse);
			JSONObject response = jsonObject.getJSONObject("response");
			JSONObject body = response.getJSONObject("body");
			JSONObject items = body.getJSONObject("items");
			JSONArray itemArray = items.getJSONArray("item");

			for (int i = 0; i < itemArray.length(); i++) {
				JSONObject item = itemArray.getJSONObject(i);
				String fcstTime = item.getString("fcstTime");
				String category = item.getString("category");

				if ("2100".equals(fcstTime) && ("SKY".equals(category) || "PTY".equals(category))) {
					WeatherDTO weatherDTO = new WeatherDTO();
					weatherDTO.setRegion(regionName);
					weatherDTO.setFcsDate(item.getString("fcstDate"));
					if ("SKY".equals(category)) {
						weatherDTO.setWeatherState(item.getString("fcstValue"));
					} else if ("PTY".equals(category)) {
						weatherDTO.setRainyState(item.getString("fcstValue"));
					}
					weatherDTOList.add(weatherDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("weatherDTOList : " + weatherDTOList);
		return weatherDTOList;
	}

//	WeatherDTO weatherInfo = new WeatherDTO();
//	
//    //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//
//    // response 키를 가지고 데이터를 파싱
//    JSONObject jsonObj_1 = new JSONObject(result);
//    String response = jsonObj_1.getString("response");
//
//    // response 로 부터 body 찾기
//    JSONObject jsonObj_2 = new JSONObject(response);
//    String body = jsonObj_2.getString("body");
//
//    // body 로 부터 items 찾기
//    JSONObject jsonObj_3 = new JSONObject(body);
//    String items = jsonObj_3.getString("items");
//
//    // items로 부터 itemlist 를 받기 
//    JSONObject jsonObj_4 = new JSONObject(items);
//    JSONArray jsonArray = jsonObj_4.getJSONArray("item");
//
//    for(int i=0;i<jsonArray.length();i++){
//        jsonObj_4 = jsonArray.getJSONObject(i);
//        String fcstValue = jsonObj_4.getString("fcstValue");
//        String category = jsonObj_4.getString("category");
//
//        if(category.equals("SKY")){
//            weatherInfo.setWeatherState(fcstValue);
//        }
//        else if(category.equals("PTY")) {
//        	weatherInfo.setRainyState(fcstValue);
//        }
//    }
}
