package org.cs2c.vcenter.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * @author liuqin
 * 
 */
public class HostManager {
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory
			.newInstance();
	Document document = null;

	private static class HostManagerHoder {

		private static HostManager instance = new HostManager();
	}

	public static HostManager getInstance() {
		return HostManagerHoder.instance;
	}

	private HostManager() {
		String filePath = null;
		try {
			filePath = FileLocator.toFileURL(
					Platform.getBundle("org.cs2c.vcenter").getEntry(""))
					.getPath()
					+ "conf/host.xml";
			document = builderFactory.newDocumentBuilder().parse(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageDialog.openInformation(new Shell(), "Exception",
					e.getMessage());
			return;
		}
	}

	public Document getDocument() {

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
		// System.out.println(host[0]);
		// System.out.println(host[1]);

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		// System.out
		// .println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			// System.out.println(element.toString());
			// System.out.println(element.getAttribute("middleware"));
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
		// System.out.println(host[0]);
		// System.out.println(host[1]);

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		// System.out
		// .println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			if ((element.getAttribute("middleware").compareTo(host[0]) == 0)
					&& (element.getAttribute("name").compareTo(host[1]) == 0)) {

				if (nodeList.item(i).getParentNode()
						.removeChild(nodeList.item(i)) != null) {
					// delete the blank line
					// System.out.println(rootElement.getElementsByTagName("host")
					// .getLength());

					return true;
				}
			}
		}
		return false;
	}

	public boolean hasHostInfo(HostInfo hostInfo) {
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");

		for (int i = 0; i < nodeList.getLength(); i++) {

			Element element = (Element) nodeList.item(i);
			if ((element.getAttribute("middleware").toLowerCase()
					.compareTo(hostInfo.getMiddleware().toLowerCase()) == 0)
					&& (element.getAttribute("name").toLowerCase()
							.compareTo(hostInfo.getHostName().toLowerCase()) == 0)) {

				// System.out.println("have no the same service");
				return true;
			}
		}
		return false;
	}

	public boolean insertHostInfo(String xmlFile, HostInfo hostInfo) {

		Element rootElement = document.getDocumentElement();

		/*
		 * if(hasHostInfo(hostInfo)==true) { return false; }
		 */
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

	public int modifyHostInfo(String xmlFile, HostInfo oldHostInfo,
			HostInfo newHostInfo) {
		if ((oldHostInfo.getHostName().equals(newHostInfo.getHostName()))
				&& (oldHostInfo.getMiddleware().equals(newHostInfo
						.getMiddleware()))) {
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = rootElement.getElementsByTagName("host");
			for (int i = 0; i < nodeList.getLength(); i++) {

				Element element = (Element) nodeList.item(i);
				if ((element.getAttribute("middleware").compareTo(
						oldHostInfo.getMiddleware()) == 0)
						&& (element.getAttribute("name").compareTo(
								oldHostInfo.getHostName()) == 0)) {
					element.setAttribute("name", newHostInfo.getHostName());
					element.setAttribute("username", newHostInfo.getUserName());
					element.setAttribute("password", newHostInfo.getPassWord());
					element.setAttribute("middleware",
							newHostInfo.getMiddleware());
					element.setAttribute("home", newHostInfo.getHome());
					element.setAttribute("stapath", newHostInfo.getStatusPath());
					element.setAttribute("musername",
							newHostInfo.getManagerUserName());
					element.setAttribute("mpassword",
							newHostInfo.getManagerPassWord());
					if (saveXml(document, xmlFile))
						return 1;// save successfully
					else
						return 2;// save failed

				}
			}
		} else {
			if (hasHostInfo(newHostInfo) == true) {
				return 3;// already have the host information
			} else {
				Element rootElement = document.getDocumentElement();
				NodeList nodeList = rootElement.getElementsByTagName("host");
				for (int i = 0; i < nodeList.getLength(); i++) {

					Element element = (Element) nodeList.item(i);
					if ((element.getAttribute("middleware").compareTo(
							oldHostInfo.getMiddleware()) == 0)
							&& (element.getAttribute("name").compareTo(
									oldHostInfo.getHostName()) == 0)) {
						element.setAttribute("name", newHostInfo.getHostName());
						element.setAttribute("username",
								newHostInfo.getUserName());
						element.setAttribute("password",
								newHostInfo.getPassWord());
						element.setAttribute("middleware",
								newHostInfo.getMiddleware());
						element.setAttribute("home", newHostInfo.getHome());
						element.setAttribute("stapath",
								newHostInfo.getStatusPath());
						element.setAttribute("musername",
								newHostInfo.getManagerUserName());
						element.setAttribute("mpassword",
								newHostInfo.getManagerPassWord());

						if (saveXml(document, xmlFile))
							return 1;// save successfully
						else
							return 2;// save failed

					}
				}
			}
		}
		return 0;// find host element failed
	}

	public boolean saveXml(Document document, String filename) {

		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("host");
		// System.out
		// .println(rootElement.getElementsByTagName("host").getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			// Element element = (Element) nodeList.item(i);
			// System.out.println(element.getAttribute(i+"name"));
		}

		// TODO Auto-generated method stub
		boolean flag = true;
		try {

			/** the content of document are written to file */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

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

}
