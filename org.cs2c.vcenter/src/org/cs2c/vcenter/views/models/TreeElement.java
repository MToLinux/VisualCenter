/**
 * 
 */
package org.cs2c.vcenter.views.models;
import org.cs2c.nginlib.*;
import java.util.*;
/**
 * @author Administrator
 *
 */
public abstract class TreeElement {
	protected MiddlewareFactory middleware;
	protected String name;
	protected String blocktype;
	protected String blockIndex;
	protected String outerBlockNames;
	protected TreeElement parent;
	/**
	 * 
	 */
	public TreeElement(TreeElement parent) {
		this.parent=parent;
	}
	
	public void init(String name,String blocktype,String blockIndex,String outerBlockNames, MiddlewareFactory middleware){
		this.name=name;
		this.blocktype=blocktype;
		this.blockIndex=blockIndex;
		this.outerBlockNames=outerBlockNames;
		this.middleware=middleware;
	}
	
	public String getName(){
		return this.name;
	}
	
	public MiddlewareFactory getMiddlewareFactory(){
		return this.middleware;
	}
	
	abstract public List<TreeElement> getChildren() throws RemoteException;
	
	abstract public boolean hasChildren() throws RemoteException;
	
	public TreeElement getParent(){
		return this.parent;
	}
}

