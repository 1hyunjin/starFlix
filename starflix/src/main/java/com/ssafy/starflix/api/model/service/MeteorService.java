package com.ssafy.starflix.api.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ssafy.starflix.api.model.dao.MeteorDAO;
import com.ssafy.starflix.api.model.dto.MeteorDTO;

@Service
public class MeteorService {

	@Value("${meteor.serviceKey}")
	private String serviceKey;
	private static String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/AstroEventInfoService/getAstroEventInfo";

	@Autowired
	private MeteorDAO mdao;
	
	public List<MeteorDTO> getList(int year) throws Exception{
		return mdao.selectList(year);
	}
	
	 @Scheduled(cron = "0 0 0 1 1 *") // 매년 1월 1일 자정에 실행
	public void fetchMeteorShowers() {
		LocalDate now = LocalDate.now();
		int currentYear = now.getYear();

		for (int month = 1; month <= 12; month++) {
			try {
				String xmlResponse = getXmlData(String.valueOf(currentYear), String.format("%02d", month));
				MeteorDTO meteor = parseXmlData(xmlResponse);
				if(meteor != null) {
					meteor.setYear(currentYear);
//					System.out.println("MeteorDTO = " + meteor);
					mdao.insert(meteor);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getXmlData(String year, String month) throws IOException {

		StringBuilder urlBuilder = new StringBuilder(apiUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /* 연 */
		urlBuilder
				.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /* 월 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		return sb.toString();
	}

	public MeteorDTO parseXmlData(String xmlData) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xmlData)));
		document.getDocumentElement().normalize();
		
		NodeList itemList = document.getElementsByTagName("item");

        if (itemList.getLength() > 0) {
            Node itemNode = itemList.item(0);

            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) itemNode;

                String astroEvent = getTagValue("astroEvent", itemElement);

                if (astroEvent != null && astroEvent.contains("유성우")) {
                    String locdate = getTagValue("locdate", itemElement);
                    String astroTitle = getTagValue("astroTitle", itemElement);

                    MeteorDTO meteorDTO = new MeteorDTO();
                    meteorDTO.setAstroEvent(astroEvent);
                    meteorDTO.setLocdate(locdate);
                    meteorDTO.setAstroTitle(astroTitle);

                    return meteorDTO;
                }
            }
        }
        return null;
	}
	
	public String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node != null ? node.getNodeValue() : null;
    }
}
