package com.ssafy.starflix.weather.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ssafy.starflix.weather.model.dto.WeatherDTO;

@Service
public class WeatherService {

	private static String serviceKey = "UuxOS17nvqpC2QYJPA%2BQlTx41Jln2ZiXptAG81ndjleA6wF1Zczi1JASBPKrr7JyKcDhMvF1MGZQRBFZt9UiXw%3D%3D";
	private static String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

	public List<List<WeatherDTO>> getWeatherDataForRegion(String baseDate) throws Exception {

		String[][] regions = { { "서울", "60", "127" }, { "부산", "98", "76" }, { "대구", "89", "90" }, { "인천", "55", "124" },
				{ "광주", "58", "74" }, { "대전", "67", "100" }, { "울산", "102", "84" }, { "세종", "66", "103" },
				{ "경기도", "60", "120" }, { "충청북도", "69", "107" }, { "충청남도", "68", "100" }, { "전라북도", "63", "89" },
				{ "전라남도", "51", "67" }, { "경상북도", "87", "106" }, { "경상남도", "91", "77" }, { "제주", "52", "38" },
				{ "강원도", "73", "134" } };

		List<CompletableFuture<List<WeatherDTO>>> futures = new ArrayList<>();

		for (String[] region : regions) {
			String regionName = region[0];
			String nx = region[1];
			String ny = region[2];

			CompletableFuture<List<WeatherDTO>> future = CompletableFuture.supplyAsync(() -> {
				try {
					String jsonResponse = getInfoWeather(baseDate, nx, ny);
					return parseWeatherData(jsonResponse, regionName);
				} catch (Exception e) {
					e.printStackTrace();
					return new ArrayList<>();
				}
			});
			futures.add(future);
		}

		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		CompletableFuture<List<WeatherDTO>> allWeatherDataFuture = allOf.thenApply(
				v -> futures.stream().flatMap(future -> future.join().stream()).collect(Collectors.toList()));

		List<WeatherDTO> allWeatherData = allWeatherDataFuture.get();

		return groupWeatherDataByDate(allWeatherData);
	}

	public String getInfoWeather(String baseDate, String nx, String ny) throws Exception {
		StringBuilder sb = new StringBuilder();

		StringBuilder urlBuilder = new StringBuilder(apiUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("1000", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
				+ URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON) Default: XML */
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /* 예보지점의 X 좌표값 */
		urlBuilder.append(
				"&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /* 예보지점의 Y 좌표값 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
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

		return result;
	}

	public List<WeatherDTO> parseWeatherData(String jsonResponse, String regionName) throws Exception {
		List<WeatherDTO> weatherDTOList = new ArrayList<>();
		Map<String, WeatherDTO> weatherMap = new HashMap<>();

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
					String fcstDate = item.getString("fcstDate");
					String key = regionName + "_" + fcstDate;

					WeatherDTO weatherDTO = weatherMap.getOrDefault(key, new WeatherDTO());
					weatherDTO.setRegion(regionName);
					weatherDTO.setFcsDate(fcstDate);
					weatherDTO.setFcsTime(fcstTime);
					weatherDTO.setFcsDateTime(fcstDate, fcstTime);

					if ("SKY".equals(category)) {
						weatherDTO.setWeatherState(item.getString("fcstValue"));
					} else if ("PTY".equals(category)) {
						weatherDTO.setRainyState(item.getString("fcstValue"));
					}

					weatherMap.put(key, weatherDTO);
				}
			}
			weatherDTOList.addAll(weatherMap.values());

		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("weatherDTOList : " + weatherDTOList);
		return weatherDTOList;
	}

	public List<List<WeatherDTO>> groupWeatherDataByDate(List<WeatherDTO> weatherData) {
		return weatherData.stream()
				.collect(Collectors.groupingBy(WeatherDTO::getFcsDate, LinkedHashMap::new,
						Collectors.mapping(weather -> weather, Collectors.toList())))
				.entrySet().stream()
				.map(entry -> entry.getValue().stream().sorted(Comparator.comparing(WeatherDTO::getFcsDateTime)) // Date
																													// 객체로
																													// 정렬
						.collect(Collectors.toList()))
				.sorted(Comparator.comparing(list -> list.get(0).getFcsDate())) // 날짜별로 오름차순 정렬
				.collect(Collectors.toList());
	}
}
