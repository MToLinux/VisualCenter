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
	   DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
	   String hostName=null;
	   String userName=null;
	   String passWord=null;
	  
	   //Load and parse XML file into DOM 
	   public Document parse(String filePath) { 
	      Document document = null; 
	      try { 
	         //DOM parser instance 
	         DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	         //parse an XML file into a DOM tree 
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
	   
	   public List<String> getMainHostInfo(String xmlFile)
	   {
		   List<String> mainHostInfo=null;
		   mainHostInfo=new ArrayList<String>(0);
		   Document document = this.parse(xmlFile); 
		   Element rootElement = document.getDocumentElement(); 
	       NodeList nodeList = rootElement.getElementsByTagName("host");
	       //System.out.println(rootElement.getElementsByTagName("host").getLength());
	       for (int i=0; i < nodeList.getLength(); i++) 
	       { 
	            Element element = (Element)nodeList.item(i); 
	            //System.out.println(element.getAttribute("name"));
	            mainHostInfo.add(element.getAttribute("middleware")+"-"+element.getAttribute("name"));
	       } 
		   return mainHostInfo;
	   }
	   public List<String> getHostInfo(String xmlFile,String mainHostInfo)
	   {
		    List<String> hostInfo= null;
		    hostInfo=new ArrayList<String>(0);
		   String host[]=mainHostInfo.split("-", 2);
		   System.out.println(host[0]);
		   System.out.println(host[1]);
		   Document document = this.parse(xmlFile); 
		   Element rootElement = document.getDocumentElement(); 
	       NodeList nodeList = rootElement.getElementsByTagName("host");
	       System.out.println(rootElement.getElementsByTagName("host").getLength());
	       for (int i=0; i < nodeList.getLength(); i++) 
	       { 
	    	   Element element = (Element)nodeList.item(i); 
	           // System.out.println(element.toString());
	            System.out.println(element.getAttribute("middleware"));
	            if((element.getAttribute("middleware").compareTo(host[0])==0)&&(element.getAttribute("name").compareTo(host[1])==0))
	            {
	            	hostInfo.add(element.getAttribute("name"));
	            	hostInfo.add(element.getAttribute("username"));
	            	hostInfo.add(element.getAttribute("password"));
	            	hostInfo.add(element.getAttribute("middleware"));
	            	hostInfo.add(element.getAttribute("home"));
	            	hostInfo.add(element.getAttribute("stapath"));
	            	hostInfo.add(element.getAttribute("musername"));
	            	hostInfo.add(element.getAttribute("mpassword"));
	            	return hostInfo;
	    
	            	 }
	       } 
		   return null;
	   }
		
	   public boolean deleteHostInfo(String xmlFile,String mainHostInfo)
	   {
		   String host[]=mainHostInfo.split("-", 2);
		   System.out.println(host[0]);
		   System.out.println(host[1]);
		   Document document = this.parse(xmlFile); 
		   Element rootElement = document.getDocumentElement(); 
	       NodeList nodeList = rootElement.getElementsByTagName("host");
	       System.out.println(rootElement.getElementsByTagName("host").getLength());
	       for (int i=0; i < nodeList.getLength(); i++) 
	       { 
	            Element element = (Element)nodeList.item(i); 
	            if((element.getAttribute("middleware").compareTo(host[0])==0)&&(element.getAttribute("name").compareTo(host[1])==0))
	            {
	            
	            	if(nodeList.item(i).getParentNode().removeChild(nodeList.item(i))!=null)
	            	{
	            		//delete the blank line
	            		System.out.println(rootElement.getElementsByTagName("host").getLength());
	            		saveXml(document,xmlFile);
	            			return true;
	            	}
	            }
	       } 
	       System.out.println("112");
	       return false;
	   }
	   public boolean saveHostInfo(String xmlFile,List<String> hostInfo)
	   {
		  
		   
		   Document document = this.parse(xmlFile); 
		   Element rootElement = document.getDocumentElement(); 
	       NodeList nodeList = rootElement.getElementsByTagName("host");
	       System.out.println(rootElement.getElementsByTagName("host").getLength());
	       
	       for (int i=0; i < nodeList.getLength(); i++) 
	       { 
	    	   
	            Element element = (Element)nodeList.item(i); 
	            if((element.getAttribute("middleware").compareTo(hostInfo.get(3))==0)&&(element.getAttribute("name").compareTo(hostInfo.get(0))==0))
	            {
	            	element.setAttribute("name", hostInfo.get(0));
	            	element.setAttribute("username", hostInfo.get(1));
	            	element.setAttribute("password", hostInfo.get(2));
	            	element.setAttribute("middleware", hostInfo.get(3));
	            	element.setAttribute("home", hostInfo.get(4));
	            	element.setAttribute("stapath", hostInfo.get(5));
	            	element.setAttribute("musername", hostInfo.get(6));
	            	element.setAttribute("upassword", hostInfo.get(7));
	            	saveXml(document,xmlFile);
	            	return true;
	            }
	            
	       } 
	       System.out.println("111");
	       Element theHost=null;
	       theHost=document.createElement("host");
	       theHost.setAttribute("name", hostInfo.get(0));
	       theHost.setAttribute("username", hostInfo.get(1));
	       theHost.setAttribute("password", hostInfo.get(2));
	       theHost.setAttribute("middleware", hostInfo.get(3));
	       theHost.setAttribute("home", hostInfo.get(4));
	       theHost.setAttribute("stapath", hostInfo.get(5));
	       theHost.setAttribute("musername", hostInfo.get(6));
	       theHost.setAttribute("upassword", hostInfo.get(7));
	       rootElement.appendChild(theHost);
       	   if(saveXml(document,xmlFile))
       		   return true;
       	   else
       		   return false;
	           
	       
	   }
	   
	   private boolean saveXml(Document document,String filename) {
		// TODO Auto-generated method stub
		   boolean flag = true; 
		      try 
		       { 
		            /** 将document中的内容写入文件中   */ 
		             TransformerFactory tFactory = TransformerFactory.newInstance();    
		             Transformer transformer = tFactory.newTransformer();  
		            /** 编码 */ 
		            //transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312"); 
		             DOMSource source = new DOMSource(document);  
		             StreamResult result = new StreamResult(new File(filename));    
		             transformer.transform(source, result);  
		         }catch(Exception ex) 
		         { 
		             flag = false; 
		             ex.printStackTrace(); 
		         } 
		        return flag;       
	}

	public static void main(String[] args) { 
	         DOMParser parser = new DOMParser(); 
	       /*  if(parser.deleteHostInfo("host.xml","Nginx-10.1.50.4"))
	        	 System.out.println("ok");
	      */
	        /*

			 List<String> hostInfo= null;
			 hostInfo=new ArrayList<String>(0);
			 hostInfo.add("10.1.50.4");
			 hostInfo.add("112");
			 hostInfo.add("111");
			 hostInfo.add("Nginx");
			 hostInfo.add("111");
			 hostInfo.add("111");
			 hostInfo.add("111");
			 hostInfo.add("111");
			 System.out.println(hostInfo);
	         if(parser.saveHostInfo("host.xml", hostInfo)==true)
	        	 System.out.println("ok");
	        	 */
	         System.out.println(parser.getMainHostInfo("d:/host.xml"));
	   } 
	 } 
	    