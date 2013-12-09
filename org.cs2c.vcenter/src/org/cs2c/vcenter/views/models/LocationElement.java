/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.RecConfigurator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Administrator
 *
 */
public class LocationElement extends TreeElement implements ILocation, IEditorInput {
	private Map<String, String> lisloName = null;
	private Map<String,String> maploNameIndexIndex = new HashMap<String,String>();

	/**
	 * @param parent
	 */
	public LocationElement(TreeElement parent) {
		super(parent);
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();

		Iterator<Entry<String, String>> it = lisloName.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> entry = (Entry<String, String>)it.next();
			String allname = entry.getKey().toString();
			String blIndex = entry.getValue().toString();
			String subname = allname.substring(9,allname.length());
			String outerBlNames = this.getOuterBlockNames()+"|"
					+this.getBlocktype()+":"+this.getBlockIndex();

			LocationElement location=new LocationElement(this);
//			System.out.println(" allname 【0】: "+blIndex);	// TODO
			location.init(subname, allname, blIndex,outerBlNames, this.middleware);
			children.add(location);
		}
		//lop maploNameIndexIndex
		Iterator<Entry<String, String>> itIndexIndex = maploNameIndexIndex.entrySet().iterator();
		while(itIndexIndex.hasNext()){
			Entry<String, String> entry = (Entry<String, String>)itIndexIndex.next();
			String nameIndexStr[] = entry.getKey().toString().split("\\|");

			String allname = nameIndexStr[0];
//			System.out.println("nameIndexStr[0] : "+nameIndexStr[0]);	// TODO

			String blIndex = entry.getValue().toString();
			String subname = allname.substring(9,allname.length());
			String outerBlNames = this.getOuterBlockNames()+"|"
					+this.getBlocktype()+":"+this.getBlockIndex();

			LocationElement location=new LocationElement(this);
//			System.out.println(outerBlNames+" : "+outerBlNames);	// TODO
			location.init(subname, allname, blIndex,outerBlNames, this.middleware);
			children.add(location);
		}
		
		return children;
	}
	
	private Map<String, String> getSubLocations() throws RemoteException {
		List<Block> list= null;
		String loIndex = "0";

		Map<String,String> maploNameIndex = new HashMap<String,String>();

		Block blFather = getFatherBlock();
		if(null == blFather){
			return null;
		}
//		System.out.println(blFather.getName());	//TODO
		list= blFather.getBlocks();
		if(list.isEmpty()){
			return null;
		}
		
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

	private Block getFatherBlock() throws RemoteException{

		String blockName = null;
		List<Block> list= null;
		RecConfigurator orc = null;

		orc = (RecConfigurator) this.middleware.getConfigurator();
		String outerBlockNames = this.getOuterBlockNames();
		blockName = this.getBlocktype();
		list= orc.getBlocks(blockName, outerBlockNames);
//		System.out.println("blockName:"+blockName);	//
//		System.out.println("outerBlockNames:"+outerBlockNames);	//
		
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
		lisloName =  getSubLocations();
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
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}
}
