package net.plang.HoWooAccount.system.base.controller;

import net.plang.HoWooAccount.system.base.to.OpenApiBean2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

@RestController
@RequestMapping("/base")
public class OpenApiController {

	// tag값의 정보를 가져오는 메소드

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;

		return nValue.getNodeValue();
	}

	@GetMapping("/getAPI")
	public ArrayList<OpenApiBean2> getAPI(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<OpenApiBean2> array= new ArrayList<>(); 
		OpenApiBean2 openApiBean2 = null;
		
		try {
			openApiBean2 = new OpenApiBean2();
			String year = request.getParameter("year");
			String month = request.getParameter("month");

			
			/*
			 <?xml ~>
			 <response>
			 	<header></header>
			 	<body>
			 		<items>
			 			<item>
			 				<dateKind><dateName><isHoliday><locdate><seq>
			 			</item>
			 			<item>
			 			</item>
			 		</items>
			 		<dumOfRows>
			 		<pageNo>
			 		<totalCount>
			 	</body>
			  </response>
			 */			
			String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear="+year+"&solMonth="+month+"&ServiceKey="
					+"Pdni0WEWWEuBXB7jeyiQ4SR13hAIYmU6XUrWJuu7VejgoJErAhJB241HQ2kHYlDCs2%2F1iKUDKGVZx64QYMmvdw%3D%3D&_type=xml";
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
	         for(int i=0; i<nList.getLength();i++) {
	            Element eElement = (Element) nList.item(i);
	         
			openApiBean2.setDateName(getTagValue("dateName", eElement));
			openApiBean2.setLocdate(getTagValue("locdate", eElement));
	
			array.add(openApiBean2);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("array = " + array);
		return array;
	}
}

