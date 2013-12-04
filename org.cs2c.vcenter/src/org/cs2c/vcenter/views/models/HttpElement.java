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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Administrator
 *
 */
public class HttpElement extends TreeElement implements IHttp, IEditorInput {

	/**
	 * @param parent
	 */
	public HttpElement(TreeElement parent) {
		super(parent);
	}

	@Override
	public List<TreeElement> getChildren() throws RemoteException {
		List<TreeElement> children=new LinkedList<TreeElement>();
		String blIndex = null;
		
		List<String> Serlist =  getServerName();
		for(int i = 0;i<Serlist.size();i++){
			blIndex = Integer.toString(i);
			String sername = Serlist.get(i);
			ServerElement server=new ServerElement(this);
//			String sername = lisSerName.get(i).;
			String outerBlNames = "http:0";
			server.init(sername,"server",blIndex,outerBlNames, this.middleware);
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
//			String PathWithName = SetConfpath();
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);
		
		for(int i = 0;i<list.size();i++){
//			System.out.println("list:"+list.size());
			List<Directive> listdire = new ArrayList<Directive>();
			listdire = list.get(i).getDirectives();
			for(int j = 0;j<listdire.size(); j++){
				if(listdire.get(j).getName().equals("server_name")){
					RecStringParameter rsp = (RecStringParameter)listdire.get(j).getParameters().get(0);
					lstserverName.add(rsp.getValue());
//					System.out.println(Integer.toString(i)+"rsp:"+rsp.getValue());
				}
			}
		}
		
		return lstserverName;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		if(this.middleware==null){
			return false;
		}
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}


}
