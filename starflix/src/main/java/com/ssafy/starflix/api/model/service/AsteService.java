package com.ssafy.starflix.api.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ssafy.starflix.api.model.dto.AsteDTO;


@Service
public class AsteService {
	
//	@Value("${api.astronomy.serviceKey}")
	private static String serviceKey = "muDWw8bNdOZVdOBkwbCtTiXv9keYEUrIznfKjytg5H2foRwBg39CS4nqA2bqCzDN+ieM8LD3IhcX1nq/EXGsBQ==";
	private static String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
	
	
	public List<AsteDTO> getAsteInfo(String locdate) throws Exception{
		
		List<AsteDTO> asteList = new ArrayList<>();
		
		String[] regions = {"서울", "인천", "전주", "대전", "홍성", "강릉", "대구", "울산", "부산", "광주", "목포", "제주"};
		
		for (String region : regions) {
			String xmlResponse = getXmlData(locdate, region);
			asteList.addAll(parseXml(xmlResponse));
		}
		System.out.println("asteList : " + asteList);
		 return asteList;
	}
	
	public String getXmlData(String locdate, String location) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("locdate","UTF-8") + "=" + URLEncoder.encode(locdate, "UTF-8")); /*날짜*/
        urlBuilder.append("&" + URLEncoder.encode("location","UTF-8") + "=" + URLEncoder.encode(location, "UTF-8")); /*지역*/
        URL url = new URL(urlBuilder.toString());
        System.out.println("url : " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
                
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();
        System.out.println(result);
        return result;
	}
	
	public List<AsteDTO> parseXml(String response) throws Exception{
		
		List<AsteDTO> asteDTOList = new ArrayList<>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response)));

        NodeList items = document.getElementsByTagName("item");
        if (items.getLength() > 0) {
            Element item = (Element) items.item(0);

            String aste = getElementTextContent(item, "aste");
            String locdate = getElementTextContent(item, "locdate");
            String location = getElementTextContent(item, "location");
            
            

            AsteDTO asteDTO = new AsteDTO();
            asteDTO.setAste(aste.trim());
            asteDTO.setLocdate(locdate.trim());
            asteDTO.setLocation(location.trim());
            
            asteDTOList.add(asteDTO);
        }
        return asteDTOList;
	}

	public String getElementTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }
}
