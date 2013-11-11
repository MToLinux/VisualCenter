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

		List<String> lisSerName =  getServerName();
		for(int i = 0;i<lisSerName.size();i++){
			ServerElement server=new ServerElement(this);
			String sername = lisSerName.get(i);
			server.init(sername, this.middleware);
			children.add(server);
		}
		return children;
	}
	
	private List<String> getServerName() throws RemoteException{
		String blockName = null;
		String outerBlockNames = "http:0";
		List<Block> list= null;
		RecConfigurator orc = null;
		List<String> lstserverName = new ArrayList<String>();

		orc = (RecConfigurator) this.middleware.getConfigurator();
//			String PathWithName = SetConfpath(); TODO dialog
		String PathWithName = "D:\\eclipseWorkspace\\confpath\\";
		orc.SetLocalConfpath(PathWithName);
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		for(int i = 0;i<list.size();i++){
			List<Directive> listdire = new ArrayList<Directive>();
			listdire = list.get(i).getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					lstserverName.add(rsp.getValue());
					System.out.println(rsp.getValue());
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
