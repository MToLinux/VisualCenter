/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.RecStringParameter;

/**
 * @author Administrator
 *
 */
public class ServerElement extends TreeElement implements IServer {

	private List<String> lisloName = null;
	
	/**
	 * @param parent
	 */
	public ServerElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren(){
		List<TreeElement> children=new LinkedList<TreeElement>();

		for(int i = 0;i<lisloName.size();i++){
			LocationElement location=new LocationElement(this);
			String sername = new String(lisloName.get(i));
			location.init(sername, this.middleware);
//			System.out.println(sername);
			children.add(location);
		}
		return children;
	}

	private List<String> getLocationName() throws RemoteException {
		List<Block> list= null;
		List<String> lstloName = new ArrayList<String>();
		String serverBlockName = this.getName();
		Block blServer = getServerBlock(serverBlockName);
		
		list= blServer.getBlocks();
		
		for(int i = 0;i<list.size();i++){

			if(list.get(i).getName().length() < 8){
				continue;
			}

			if ("location".equals(list.get(i).getName().substring(0, 8))){
				String loname = list.get(i).getName().substring(9,list.get(i).getName().length());
				lstloName.add(loname);
//				System.out.println("lstloName: "+loname);
			}
		}
		
		return lstloName;
	}
	
	private Block getServerBlock(String serverBlockName) throws RemoteException{

		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.middleware.getConfigurator();
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		for(int i = 0;i<list.size();i++){
			List<Directive> listdire = new ArrayList<Directive>();
			Block tembl = list.get(i);
			listdire = tembl.getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					if(serverBlockName.equals(rsp.getValue())){
						return tembl;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean hasChildren() throws RemoteException {
		boolean bHasChildren = false;
		
		lisloName =  getLocationName();
		if(lisloName.size()>0){
			bHasChildren = true;
		}else{
			bHasChildren = false;
		}
		return bHasChildren ;
	}
}
