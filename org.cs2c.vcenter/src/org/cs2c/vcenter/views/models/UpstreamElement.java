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
public class UpstreamElement extends TreeElement implements IUpstream {
	private Map<String, String> lisloName = null;
	
	/**
	 * @param parent
	 */
	public UpstreamElement(TreeElement parent) {
		super(parent);
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();

		Iterator<Entry<String, String>> it = lisloName.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> entry = (Entry<String, String>)it.next();
			String blIndex = entry.getKey().toString();
			String allname = entry.getValue().toString();
			String subname = allname.substring(9,allname.length());
				
			String outerBlNames = "http:0";
			UpstreamInstanceElement upstreamInstance=new UpstreamInstanceElement(this);
			upstreamInstance.init(subname, allname, blIndex, outerBlNames, this.middleware);
//			System.out.println(sername);
			children.add(upstreamInstance);
		}

		return children;
	}

	private Map<String, String> getUpstreamName() throws RemoteException {
		List<Block> list= null;
		Map<String,String> lstloName = new HashMap<String,String>();

		Block blServer = getUpstreamBlock();
//		System.out.println(blServer.toString());
		list= blServer.getBlocks();
//		System.out.println("list size"+list.size());
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getName().length() < 8){
				continue;
			}
			if ("upstream".equals(list.get(i).getName().substring(0, 8))){
				String loname = list.get(i).getName();
				lstloName.put(Integer.toString(i), loname);
			}
		}
		
		return lstloName;
	}
	
	private Block getUpstreamBlock() throws RemoteException{
		String blockName = null;
		String outerBlockNames = "";
		List<Block> list= null;
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.middleware.getConfigurator();
		
		blockName = "http";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		if(list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public boolean hasChildren() throws RemoteException {
		boolean bHasChildren = false;
		
		lisloName =  getUpstreamName();
		if(lisloName.size()>0){
			bHasChildren = true;
		}else{
			bHasChildren = false;
		}
		return bHasChildren ;
	}
}
