package org.cs2c.vcenter.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MetaManager {
	DocumentBuilderFactory domFactory=null;
	Document doc=null;
	
	public MetaManager() {
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!
		try {
			 doc = domFactory.newDocumentBuilder().parse("conf/element.xml");
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	public MetaManager getInstance() {
		MetaManager metaManagerInstance = new MetaManager();
		return metaManagerInstance;
	}

	public BlockMeta getBlockMeta(String blockName) {
		
	
		BlockMeta blockMetaResult = new BlockMeta();
		blockMetaResult.setName(blockName);
		try {
			blockMetaResult.setBlockMeta(FetchBlock(blockName));
		} catch (ParserConfigurationException | SAXException
				| XPathExpressionException | IOException e) {
			e.printStackTrace();
		}

		try {
			blockMetaResult.setDirectiveMeta(FetchDirectivesofBlock( blockName));
		} catch (ParserConfigurationException | SAXException
				| XPathExpressionException | IOException e) {
			e.printStackTrace();
		}
		
		return blockMetaResult;
	}

	

	private List<BlockMeta> FetchBlock(String blockName) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {

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
			blockMetaResult.setName(decreaseBlank(element.getAttribute("name")));
			blockMetaResult.setTips(decreaseBlank(element.getAttribute("tips")));
			blockMetaResult.setGroup(decreaseBlank(element.getAttribute("group")));
			if(decreaseBlank(element.getAttribute("reused"))=="true")
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
		
		comstr = "//directive[contains(@scope,\"" + blockName + "\")]";
		
		XPathExpression expr = xpath.compile(comstr);

		List<DirectiveMeta> directiveMetaList = new ArrayList<DirectiveMeta>(0);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			DirectiveMeta directiveMetaResult = new DirectiveMeta();
			Element element = (Element) nodes.item(i);
			directiveMetaResult.setName(decreaseBlank(element.getAttribute("name")));
			directiveMetaResult.setTips(decreaseBlank(element.getAttribute("tips")));
			directiveMetaResult.setGroup(decreaseBlank(element.getAttribute("group")));
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
			
			 if(decreaseBlank(element.getAttribute("reused"))=="true")
				directiveMetaResult.setReused(true);
			 else
				 directiveMetaResult.setReused(false);
			// ParameterMeta tmpParaMeta=new ParameterMeta();
			List<ParameterMeta> tmpParaList = new ArrayList<ParameterMeta>(0);
			tmpParaList = FetchParamofDirective(
					decreaseBlank(element.getAttribute("name")), decreaseBlank(element.getAttribute("group")));

			directiveMetaResult.setOptions(tmpParaList);

			directiveMetaList.add(directiveMetaResult);
		}
		
		return directiveMetaList;
	}

	private List<ParameterMeta> FetchParamofDirective(String directiveName,String groupName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<ParameterMeta> tmpParaList = new ArrayList<ParameterMeta>(0);

		String tmpcom = "//directive[@name=\"" + directiveName
				+ "\" and @group=\"" + groupName + "\"]/param";
		XPathExpression recuroneexpr = xpath.compile(tmpcom);
		Object recuroneresult = recuroneexpr.evaluate(doc,
				XPathConstants.NODESET);
		NodeList recuronenodes = (NodeList) recuroneresult;

		for (int j = 0; j < recuronenodes.getLength(); j++) {
			ParameterMeta tmpParaMeta = new ParameterMeta();
			Element recuroneelement = (Element) recuronenodes.item(j);

			tmpParaMeta.setName(decreaseBlank(recuroneelement.getAttribute("name")));
			tmpParaMeta.setValue(decreaseBlank(recuroneelement.getAttribute("value")));
			tmpParaMeta.setClassName(decreaseBlank(recuroneelement.getAttribute("class")));
			if ((decreaseBlank(recuroneelement.getAttribute("min")) == "")||(decreaseBlank(recuroneelement.getAttribute("min")) == null))
				tmpParaMeta.setMin(-2);
			else
				tmpParaMeta.setMin(Long.parseLong(decreaseBlank(recuroneelement
						.getAttribute("min"))));
			if ((decreaseBlank(recuroneelement.getAttribute("max")) == "")||(decreaseBlank(recuroneelement.getAttribute("max")) == null))
				tmpParaMeta.setMax(-2);
			else
				tmpParaMeta.setMax(Long.parseLong(decreaseBlank(recuroneelement
						.getAttribute("max"))));
			
			tmpParaMeta.setTips(recuroneelement.getAttribute("tips"));

			if (decreaseBlank(recuroneelement.getAttribute("items")) != "") {
				if (recuroneelement.getAttribute("items").indexOf(":") == -1) {
					List<String> tmpitems = new ArrayList<String>();
					tmpitems.add(decreaseBlank(recuroneelement.getAttribute("items")));
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
			if (decreaseBlank(recuroneelement.getAttribute("unit") )!= "") {
				if (recuroneelement.getAttribute("unit").indexOf(":") == -1) {
					List<String> tmpunits = new ArrayList<String>();
					tmpunits.add(decreaseBlank(recuroneelement.getAttribute("unit")));
					tmpParaMeta.setUnits(tmpunits);
				} else {
					String unitsvalue[] = recuroneelement.getAttribute("unit")
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

	private  String decreaseBlank(String str)
	{
		
		 if(str==null)
			return null;
		 else if (!str.isEmpty())
			{
				return str.replaceAll(" ", "");
			}
		else return "";
	}

	

}
