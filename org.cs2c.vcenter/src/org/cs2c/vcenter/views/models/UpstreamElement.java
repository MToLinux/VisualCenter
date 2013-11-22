/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;

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
	private List<String> lisloName = null;
	
	/**
	 * @param parent
	 */
	public UpstreamElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();

		for(int i = 0;i<lisloName.size();i++){
			UpstreamInstanceElement upstreamInstance=new UpstreamInstanceElement(this);
			String sername = new String(lisloName.get(i));
			//TODO
			String blIndex = null;
			String outerBlNames = null;
			upstreamInstance.init(sername, "server",blIndex,outerBlNames, this.middleware);
//			upstreamInstance.init(sername, this.middleware);
//			System.out.println(sername);
			children.add(upstreamInstance);
		}
		return children;
	}
	private List<String> getUpstreamName() throws RemoteException {
		List<Block> list= null;
		List<String> lstloName = new ArrayList<String>();
		Block blServer = getUpstreamBlock();
//		System.out.println(blServer.toString());
		list= blServer.getBlocks();
//		System.out.println("list size"+list.size());
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getName().length() < 8){
				continue;
			}
			if ("upstream".equals(list.get(i).getName().substring(0, 8))){
				String loname = list.get(i).getName().substring(9,list.get(i).getName().length());
				lstloName.add(loname);
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
		
//		String PathWithName = SetConfpath(); TODO dialog
//		orc.SetLocalConfpath(PathWithName);
		
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
