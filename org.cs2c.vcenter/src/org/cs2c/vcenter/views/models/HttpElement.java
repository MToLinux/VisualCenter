/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;
import java.util.Map.Entry;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.RecStringParameter;

/**
 * @author Administrator
 *
 */
public class HttpElement extends TreeElement implements IHttp {

	/**
	 * @param parent
	 */
	public HttpElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() throws RemoteException {
		List<TreeElement> children=new LinkedList<TreeElement>();
		String blIndex = null;
		
		Map<String, String> SerMap =  getServerName();
//		Set set = SerMap.entrySet();
		Iterator<Entry<String, String>> it = SerMap.entrySet().iterator();
		while(it.hasNext()){
			Entry entry = (Entry)it.next();
			blIndex = entry.getKey().toString();
			String sername = entry.getValue().toString();
			ServerElement server=new ServerElement(this);
//			String sername = lisSerName.get(i).;	//TODO
			String outerBlNames = "http:0";
			server.init(sername,"server",blIndex,outerBlNames, this.middleware);
			children.add(server);
		}
		return children;
	}
	
	private Map<String, String> getServerName() throws RemoteException{
		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		RecConfigurator orc = null;
		Map<String,String> lstserverName = new HashMap<String,String>();

		orc = (RecConfigurator) this.middleware.getConfigurator();
//			String PathWithName = SetConfpath(); TODO dialog
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		for(int i = 0;i<list.size();i++){
//			System.out.println("list:"+list.size());
			List<Directive> listdire = new ArrayList<Directive>();
			listdire = list.get(i).getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					lstserverName.put(Integer.toString(i), rsp.getValue());
//					System.out.println(rsp.getValue());
				}
			}
		}
		
		return lstserverName;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}


}
