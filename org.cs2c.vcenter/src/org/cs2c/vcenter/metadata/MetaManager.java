package org.cs2c.vcenter.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MetaManager {
	static DocumentBuilderFactory domFactory = null;
	static Document doc = null;

	private static class MetaManagerHoder {
		
		private static MetaManager instance = new MetaManager();
		
	}

	public static MetaManager getInstance() throws Exception {
		try {
			doc = domFactory.newDocumentBuilder().parse(FileLocator.toFileURL(Platform.getBundle("org.cs2c.vcenter").getEntry("")).getPath()+"conf/element.xml");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			throw e1;
		}
		return MetaManagerHoder.instance;
	}

	private MetaManager()  {
		// System.out.println("create metamanager");
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!

	}

	public BlockMeta getBlockMeta(String blockName) {

		BlockMeta blockMetaResult = new BlockMeta();
		blockMetaResult.setName(blockName);
		try {
			blockMetaResult.setBlockMeta(FetchBlock(blockName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			blockMetaResult.setDirectiveMeta(FetchDirectivesofBlock(blockName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return blockMetaResult;
	}

	public BlockMeta getBlockMeta(String blockName, String fatherBlockName) {

		if (fatherBlockName.isEmpty() || fatherBlockName == null) {

			BlockMeta blockMetaResult = getBlockMeta(blockName);
			return blockMetaResult;
		} else {
			BlockMeta blockMetaResult = new BlockMeta();
			blockMetaResult.setName(blockName);
			try {
				blockMetaResult.setBlockMeta(FetchBlock(blockName,
						fatherBlockName));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				blockMetaResult.setDirectiveMeta(FetchDirectivesofBlock(
						blockName, fatherBlockName));
			} catch (Exception e) {
				e.printStackTrace();
			}

			return blockMetaResult;
		}
	}

	private List<BlockMeta> FetchBlock(String blockName, String fatherBlockName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String comstr = null;
		List<BlockMeta> blockMetaList = new ArrayList<BlockMeta>(0);

		if ((!blockName.isEmpty()) && (!fatherBlockName.isEmpty())) {
			String strtmp = blockName.substring(1)
					+ "in"
					+ fatherBlockName
							.substring(0, fatherBlockName.length() - 1);

			// System.out.println("block"+strtmp);
			comstr = "//block[contains(@scope,\"" + blockName
					+ "\")]|//block[contains(@scope,\"" + strtmp + "\")]";

			XPathExpression expr = xpath.compile(comstr);

			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				BlockMeta blockMetaResult = new BlockMeta();
				Element element = (Element) nodes.item(i);
				blockMetaResult.setName(decreaseBlank(element
						.getAttribute("name")));
				blockMetaResult.setTips(element.getAttribute("tips"));
				blockMetaResult.setGroup(decreaseBlank(element
						.getAttribute("group")));
				if (decreaseBlank(element.getAttribute("reused")) == "true")
					blockMetaResult.setReused(true);
				else
					blockMetaResult.setReused(false);
				blockMetaList.add(blockMetaResult);

			}

			return blockMetaList;

		}

		else {
			blockMetaList = FetchBlock(blockName);
			return blockMetaList;
		}

	}

	private List<BlockMeta> FetchBlock(String blockName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String comstr = null;

		comstr = "//block[contains(@scope,\"" + blockName + "\")]";

		XPathExpression expr = xpath.compile(comstr);

		List<BlockMeta> blockMetaList = new ArrayList<BlockMeta>(0);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			BlockMeta blockMetaResult = new BlockMeta();
			Element element = (Element) nodes.item(i);
			blockMetaResult
					.setName(decreaseBlank(element.getAttribute("name")));
			blockMetaResult.setTips(element.getAttribute("tips"));
			blockMetaResult.setGroup(decreaseBlank(element
					.getAttribute("group")));
			if (decreaseBlank(element.getAttribute("reused")) == "true")
				blockMetaResult.setReused(true);
			else
				blockMetaResult.setReused(false);
			blockMetaList.add(blockMetaResult);

		}

		return blockMetaList;
	}

	private List<DirectiveMeta> FetchDirectivesofBlock(String blockName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String comstr = null;

		if (blockName.equalsIgnoreCase("main"))
			comstr = "//directive[contains(@scope,\"" + blockName + "\")]";
		else
			comstr = "//directive[contains(@scope,\"" + blockName
					+ "\")]|//directive[contains(@scope,\"exmai\")]";
		XPathExpression expr = xpath.compile(comstr);

		List<DirectiveMeta> directiveMetaList = new ArrayList<DirectiveMeta>(0);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			DirectiveMeta directiveMetaResult = new DirectiveMeta();
			Element element = (Element) nodes.item(i);
			directiveMetaResult.setName(decreaseBlank(element
					.getAttribute("name")));
			directiveMetaResult.setTips(element.getAttribute("tips"));
			directiveMetaResult.setGroup(decreaseBlank(element
					.getAttribute("group")));
			Set<String> tmpscope = new HashSet<String>();

			if (element.getAttribute("scope").indexOf(":") == -1) {
				String scopevalue[] = new String[1];
				scopevalue[0] = decreaseBlank(element.getAttribute("scope"));
				tmpscope.add(scopevalue[0]);
				directiveMetaResult.setScope(tmpscope);
				// directiveMetaResult.setScope(element.getAttribute("scope"));
			} else {
				String scopevalue[] = element.getAttribute("scope").split(":");

				for (int scopenum = 0; scopenum < scopevalue.length; scopenum++) {
					tmpscope.add(decreaseBlank(scopevalue[scopenum]));
				}
				directiveMetaResult.setScope(tmpscope);
			}

			if (decreaseBlank(element.getAttribute("reused")) == "true")
				directiveMetaResult.setReused(true);
			else
				directiveMetaResult.setReused(false);
			// ParameterMeta tmpParaMeta=new ParameterMeta();
			List<ParameterMeta> tmpParaList = new ArrayList<ParameterMeta>(0);
			tmpParaList = FetchParamofDirective(
					decreaseBlank(element.getAttribute("name")),
					decreaseBlank(element.getAttribute("group")), blockName);

			directiveMetaResult.setOptions(tmpParaList);

			directiveMetaList.add(directiveMetaResult);
		}

		return directiveMetaList;
	}

	private List<DirectiveMeta> FetchDirectivesofBlock(String blockName,
			String fatherBlockName) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String comstr = null;
		List<DirectiveMeta> directiveMetaList = new ArrayList<DirectiveMeta>(0);

		if ((!blockName.isEmpty()) && (!fatherBlockName.isEmpty())) {
			String strtmp = blockName.substring(1)
					+ "in"
					+ fatherBlockName
							.substring(0, fatherBlockName.length() - 1);

			comstr = "//directive[contains(@scope,\"" + blockName
					+ "\")]|//directive[contains(@scope,\"" + strtmp
					+ "\")]|//directive[contains(@scope,\"exmai\")]";

		}
		// else if(fatherBlockName.isEmpty()||fatherBlockName==null)
		else {
			directiveMetaList = FetchDirectivesofBlock(blockName);
			return directiveMetaList;
		}
		XPathExpression expr = xpath.compile(comstr);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			DirectiveMeta directiveMetaResult = new DirectiveMeta();
			Element element = (Element) nodes.item(i);
			directiveMetaResult.setName(decreaseBlank(element
					.getAttribute("name")));
			directiveMetaResult.setTips(element.getAttribute("tips"));
			directiveMetaResult.setGroup(decreaseBlank(element
					.getAttribute("group")));
			Set<String> tmpscope = new HashSet<String>();

			if (element.getAttribute("scope").indexOf(":") == -1) {
				String scopevalue[] = new String[1];
				scopevalue[0] = decreaseBlank(element.getAttribute("scope"));
				tmpscope.add(scopevalue[0]);
				directiveMetaResult.setScope(tmpscope);

			} else {
				String scopevalue[] = element.getAttribute("scope").split(":");

				for (int scopenum = 0; scopenum < scopevalue.length; scopenum++) {
					tmpscope.add(decreaseBlank(scopevalue[scopenum]));
				}
				directiveMetaResult.setScope(tmpscope);
			}

			if (decreaseBlank(element.getAttribute("reused")) == "true")
				directiveMetaResult.setReused(true);
			else
				directiveMetaResult.setReused(false);
			// ParameterMeta tmpParaMeta=new ParameterMeta();
			List<ParameterMeta> tmpParaList = new ArrayList<ParameterMeta>(0);
			tmpParaList = FetchParamofDirective(
					decreaseBlank(element.getAttribute("name")),
					decreaseBlank(element.getAttribute("group")), blockName);

			directiveMetaResult.setOptions(tmpParaList);

			directiveMetaList.add(directiveMetaResult);
		}

		return directiveMetaList;
	}

	private List<ParameterMeta> FetchParamofDirective(String directiveName,
			String groupName, String scope)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<ParameterMeta> tmpParaList = new ArrayList<ParameterMeta>(0);
		String tmpcom;

		tmpcom = "//directive[@name=\"" + directiveName + "\" and @group=\""
				+ groupName + "\" and contains(@scope,\"" + scope
				+ "\")]/param";

		XPathExpression recuroneexpr = xpath.compile(tmpcom);
		Object recuroneresult = recuroneexpr.evaluate(doc,
				XPathConstants.NODESET);
		NodeList recuronenodes = (NodeList) recuroneresult;

		for (int j = 0; j < recuronenodes.getLength(); j++) {
			ParameterMeta tmpParaMeta = new ParameterMeta();
			Element recuroneelement = (Element) recuronenodes.item(j);

			tmpParaMeta.setName(decreaseBlank(recuroneelement
					.getAttribute("name")));
			tmpParaMeta.setValue(decreaseBlank(recuroneelement
					.getAttribute("value")));
			tmpParaMeta.setClassName(decreaseBlank(recuroneelement
					.getAttribute("class")));
			if ((decreaseBlank(recuroneelement.getAttribute("min")) == "")
					|| (decreaseBlank(recuroneelement.getAttribute("min")) == null))
				tmpParaMeta.setMin(-2);
			else
				tmpParaMeta.setMin(Integer
						.parseInt(decreaseBlank(recuroneelement
								.getAttribute("min"))));
			if ((decreaseBlank(recuroneelement.getAttribute("max")) == "")
					|| (decreaseBlank(recuroneelement.getAttribute("max")) == null))
				tmpParaMeta.setMax(-2);
			else
				tmpParaMeta.setMax(Integer
						.parseInt(decreaseBlank(recuroneelement
								.getAttribute("max"))));

			tmpParaMeta.setTips(recuroneelement.getAttribute("tips"));

			if (decreaseBlank(recuroneelement.getAttribute("items")) != "") {
				if (recuroneelement.getAttribute("items").indexOf(":") == -1) {
					List<String> tmpitems = new ArrayList<String>();
					tmpitems.add(decreaseBlank(recuroneelement
							.getAttribute("items")));
					tmpParaMeta.setItems(tmpitems);
				} else {
					// recuroneelement.getAttribute("items").to
					String itemsvalue[] = recuroneelement.getAttribute("items")
							.split(":");// 7

					List<String> tmpitems = new ArrayList<String>();
					for (int m = 0; m < itemsvalue.length; m++) {
						tmpitems.add(decreaseBlank(itemsvalue[m]));
					}
					tmpParaMeta.setItems(tmpitems);
				}
			} else
				tmpParaMeta.setItems(null);
			if (decreaseBlank(recuroneelement.getAttribute("units")) != "") {
				if (recuroneelement.getAttribute("units").indexOf(":") == -1) {
					List<String> tmpunits = new ArrayList<String>();
					tmpunits.add(decreaseBlank(recuroneelement
							.getAttribute("units")));
					tmpParaMeta.setUnits(tmpunits);
				} else {
					String unitsvalue[] = recuroneelement.getAttribute("units")
							.split(":");
					List<String> tmpunits = new ArrayList<String>();
					for (int n = 0; n < unitsvalue.length; n++) {
						tmpunits.add(decreaseBlank(unitsvalue[n]));
					}
					tmpParaMeta.setUnits(tmpunits);
				}
			} else
				tmpParaMeta.setUnits(null);
			tmpParaList.add(tmpParaMeta);
		}

		return tmpParaList;
	}

	private String decreaseBlank(String str) {

		if (str == null)
			return null;
		else if (!str.isEmpty()) {
			return str.replaceAll(" ", "");
		} else
			return "";
	}
	/*
	 * private static void printblock(List<BlockMeta> blocklist) {
	 * System.out.println("blocknum="+blocklist.size()); } private static void
	 * printblock1(List<BlockMeta> blocklist) { for(int
	 * i=0;i<blocklist.size();i++) {
	 * System.out.println("name="+blocklist.get(i).getName());
	 * System.out.println("tips="+blocklist.get(i).getTips());
	 * System.out.println("reused="+blocklist.get(i).getReused()); }
	 * 
	 * } private static void printdirective(List<DirectiveMeta> directivelist) {
	 * 
	 * System.out.println("directivenum="+directivelist.size()); } private
	 * static void printdirective1(List<DirectiveMeta> directivelist) { for(int
	 * i=0;i<directivelist.size();i++) {
	 * System.out.println("name="+directivelist.get(i).getName());
	 * System.out.println("tips="+directivelist.get(i).getTips());
	 * System.out.println("scope="+directivelist.get(i).getScope());
	 * System.out.println("resued="+directivelist.get(i).getReused()); for(int
	 * j=0;j<directivelist.get(i).getOptions().size();j++) {
	 * //System.out.println
	 * //("class="+directivelist.get(i).getOptions().get(j).getClassName());
	 * //System //
	 * .out.println("name="+directivelist.get(i).getOptions().get(j).getName());
	 * 
	 * //System.out.println("min="+directivelist.get(i).getOptions().get(j).getMin
	 * //());
	 * //System.out.println("max="+directivelist.get(i).getOptions().get(j). //
	 * getMax());
	 * //System.out.println("units="+directivelist.get(i).getOptions() //
	 * .get(j).getUnits());
	 * //System.out.println("items="+directivelist.get(i).getOptions //
	 * ().get(j).getItems());
	 * 
	 * } }
	 * 
	 * 
	 * } public static void main(String[] args) { MetaManager manager =
	 * MetaManager.getInstance(); BlockMeta blockMetaResult = new BlockMeta();
	 * blockMetaResult = manager.getBlockMeta("if","http");
	 * System.out.println(blockMetaResult.getGroups()); for(int
	 * i=0;i<blockMetaResult.getDirectiveMeta("fastcgi").size();i++) {
	 * if(blockMetaResult
	 * .getDirectiveMeta("fastcgi").get(i).getName().equals("fastcgi_cache_bypass"
	 * )) {
	 * System.out.println(blockMetaResult.getDirectiveMeta("fastcgi").get(i)
	 * .getOptions().toString()); } } for(int
	 * i=0;i<blockMetaResult.getGroups().size();i++) {
	 * printdirective1(blockMetaResult
	 * .getDirectiveMeta(blockMetaResult.getGroups().get(i))); } }
	 */

}
