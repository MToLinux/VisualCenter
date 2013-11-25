package org.cs2c.vcenter.metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory
			.newInstance();
	Document document = null;
	String hostName = null;
	String userName = null;
	String passWord = null;

	public DOMParser(String filePath) {
		document = parseXml(filePath);
	}

	public  Document getDocument()
	{
		return document;
	}
	// Load and parse XML file into DOM
	public Document parseXml(String filePath) {
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			document = builder.parse(new File(filePath));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	public List<String> getMainHostInfo() {
		List<String> mainHostInfo = null;
		mainHostInfo = new ArrayList<String>(0);

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		// System.out.println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			// System.out.println(element.getAttribute("name"));
			mainHostInfo.add(element.getAttribute("middleware") + "-"
					+ element.getAttribute("name"));
		}
		return mainHostInfo;
	}

	public HostInfo getHostInfo(String mainHostInfo) {
		HostInfo hostInfo = new HostInfo();

		String host[] = mainHostInfo.split("-", 2);
		System.out.println(host[0]);
		System.out.println(host[1]);

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		System.out
				.println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			// System.out.println(element.toString());
			System.out.println(element.getAttribute("middleware"));
			if ((element.getAttribute("middleware").compareTo(host[0]) == 0)
					&& (element.getAttribute("name").compareTo(host[1]) == 0)) {
				hostInfo.setHostName(element.getAttribute("name"));
				hostInfo.setUserName(element.getAttribute("username"));
				hostInfo.setPassWord(element.getAttribute("password"));
				hostInfo.setMiddleware(element.getAttribute("middleware"));
				hostInfo.setHome(element.getAttribute("home"));
				hostInfo.setStatusPath(element.getAttribute("stapath"));
				hostInfo.setManagerUserName(element.getAttribute("musername"));
				hostInfo.setManagerPassWord(element.getAttribute("mpassword"));
				return hostInfo;

			}
		}
		return null;
	}

	public boolean deleteHostInfo(String mainHostInfo) {
		String host[] = mainHostInfo.split("-", 2);
		System.out.println(host[0]);
		System.out.println(host[1]);

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		System.out
				.println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			if ((element.getAttribute("middleware").compareTo(host[0]) == 0)
					&& (element.getAttribute("name").compareTo(host[1]) == 0)) {

				if (nodeList.item(i).getParentNode()
						.removeChild(nodeList.item(i)) != null) {
					// delete the blank line
					//System.out.println(rootElement.getElementsByTagName("host")
					//		.getLength());
					
					return true;
				}
			}
		}
		System.out.println("112");
		return false;
	}

	public boolean saveHostInfo(String xmlFile, HostInfo hostInfo) {

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");

		for (int i = 0; i < nodeList.getLength(); i++) {

			Element element = (Element) nodeList.item(i);
			if ((element.getAttribute("middleware").compareTo(
					hostInfo.getMiddleware()) == 0)
					&& (element.getAttribute("name").compareTo(
							hostInfo.getHostName()) == 0)) {
				element.setAttribute("name", hostInfo.getHostName());
				element.setAttribute("username", hostInfo.getUserName());
				element.setAttribute("password", hostInfo.getPassWord());
				element.setAttribute("middleware", hostInfo.getMiddleware());
				element.setAttribute("home", hostInfo.getHome());
				element.setAttribute("stapath", hostInfo.getStatusPath());
				element.setAttribute("musername", hostInfo.getManagerUserName());
				element.setAttribute("mpassword", hostInfo.getManagerPassWord());
				saveXml(document, xmlFile);
				return true;
			}

		}

		Element theHost = null;
		theHost = document.createElement("host");
		theHost.setAttribute("name", hostInfo.getHostName());
		theHost.setAttribute("username", hostInfo.getUserName());
		theHost.setAttribute("password", hostInfo.getPassWord());
		theHost.setAttribute("middleware", hostInfo.getMiddleware());
		theHost.setAttribute("home", hostInfo.getHome());
		theHost.setAttribute("stapath", hostInfo.getStatusPath());
		theHost.setAttribute("musername", hostInfo.getManagerUserName());
		theHost.setAttribute("mpassword", hostInfo.getManagerPassWord());

		rootElement.appendChild(theHost);
		if (saveXml(document, xmlFile)) {
			// System.out.println(hostXml.getMainHostInfo());
			return true;
		} else
			return false;

	}

	public boolean saveXml(Document document, String filename) {
		
		
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		System.out
				.println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			System.out.println(element.getAttribute(i+"name"));
		}
		
		
		// TODO Auto-generated method stub
		boolean flag = true;
		try {
			
			/** 将document中的内容写入文件中 */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			//transformer.setoutpu
			/** 编码 */
			// transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);

		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static void main(String[] args) {
		DOMParser parser = new DOMParser("d:/host.xml");
		/*
		 * if(parser.deleteHostInfo("host.xml","Nginx-10.1.50.4"))
		 * System.out.println("ok");
		 */

		HostInfo hostInfo = null;
		hostInfo = new HostInfo();
		hostInfo.setManagerPassWord("10.1.50.5");
		hostInfo.setHome("112");
		hostInfo.setManagerPassWord("111");
		hostInfo.setManagerUserName("Nginx");
		hostInfo.setPassWord("111");
		hostInfo.setUserName("111");
		hostInfo.setMiddleware("111");
		hostInfo.setHostName("23");

		if (parser.saveHostInfo("d:/host.xml", hostInfo) == true)
			System.out.println("ok");

		System.out.println(parser.getMainHostInfo());
	}
}
