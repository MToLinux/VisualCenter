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
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.nginlib.config.RecConfigurator;
import org.cs2c.nginlib.config.RecStringParameter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Administrator
 *
 */
public class ServerElement extends TreeElement implements IServer, IEditorInput  {

	private Map<String, String> lisloName = null;
	private Map<String,String> maploNameIndexIndex = new HashMap<String,String>();//repeat block name use
//	private List<HashMap<String,String>> listReIndexName = new ArrayList<HashMap<String,String>>();

	/**
	 * @param parent
	 */
	public ServerElement(TreeElement parent) {
		super(parent);
	}

	@Override
	public List<TreeElement> getChildren(){
		List<TreeElement> children=new LinkedList<TreeElement>();

		Iterator<Entry<String, String>> it = lisloName.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> entry = (Entry<String, String>)it.next();
			String allname = entry.getKey().toString();
			String blIndex = entry.getValue().toString();
			String subname = allname.substring(9,allname.length());
			String outerBlNames = this.getOuterBlockNames()+"|"
					+this.getBlocktype()+":"+this.getBlockIndex();
//			System.out.println(outerBlNames+" : "+outerBlNames);

			LocationElement location=new LocationElement(this);
//			System.out.println(allname+" : "+blIndex);
			location.init(subname, allname, blIndex,outerBlNames, this.middleware);
			children.add(location);
		}
		
		//lop maploNameIndexIndex
		Iterator<Entry<String, String>> itIndexIndex = maploNameIndexIndex.entrySet().iterator();
		while(itIndexIndex.hasNext()){
			Entry<String, String> entry = (Entry<String, String>)itIndexIndex.next();
			String nameIndexStr[] = entry.getKey().toString().split("\\|");
		
			String allname = nameIndexStr[0];
//			System.out.println("nameIndexStr[0] : "+nameIndexStr[0]);	// 

			String blIndex = entry.getValue().toString();
			String subname = allname.substring(9,allname.length());
			String outerBlNames = this.getOuterBlockNames()+"|"
					+this.getBlocktype()+":"+this.getBlockIndex();

			LocationElement location=new LocationElement(this);
//			System.out.println(allname+" : "+blIndex);	// 
			location.init(subname, allname, blIndex,outerBlNames, this.middleware);
			children.add(location);
		}
		return children;
	}

	private Map<String, String> getLocationName() throws RemoteException {
		List<Block> list= null;
		String loIndex = "0";
		
		Map<String,String> maploNameIndex = new HashMap<String,String>();
//		List<String> reblList = new ArrayList<String>();
		Block blServer = getServerBlock();
		if(null == blServer){
			return null;
		}
		
		list= blServer.getBlocks();
		
		for(int i = 0;i<list.size();i++){

			if(list.get(i).getName().length() < 8){
				continue;
			}

			if ("location".equals(list.get(i).getName().substring(0, 8))){
				String loname = list.get(i).getName();

				if(!maploNameIndex.containsKey(loname)){
					maploNameIndex.put(loname,loIndex);
				}else{
					//1000 : support repeat block name 1000 times
					for(int j = 1;j<1000;j++){
						loname = loname+"|"+Integer.toString(j);
//						System.out.println("loname| : "+loname);	// TODO
						if(!maploNameIndexIndex.containsKey(loname)){
							maploNameIndexIndex.put(loname,Integer.toString(j));
							break;
						}
					}
				}
			}
		}
		
		return maploNameIndex;
	}
	
	private Block getServerBlock() throws RemoteException{

		String blockName = null;
		List<Block> list= null;
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.middleware.getConfigurator();
		String outerBlockNames = this.getOuterBlockNames();
		blockName = "server";
		list= orc.getBlocks(blockName, outerBlockNames);

		for(int i = 0;i<list.size();i++){
			if(i == Integer.parseInt(this.getBlockIndex())){
				return list.get(i);
			}
		}

		return null;
	}

	@Override
	public boolean hasChildren() throws RemoteException {
		boolean bHasChildren = false;
		
		lisloName =  getLocationName();
		if(null == lisloName){
			return false;
		}
		
		if(lisloName.size()>0){
			bHasChildren = true;
		}else{
			bHasChildren = false;
		}
		return bHasChildren ;
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
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
