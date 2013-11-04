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
	protected TreeElement parent;
	/**
	 * 
	 */
	public TreeElement(TreeElement parent) {
		this.parent=parent;
	}
	public void init(String name, MiddlewareFactory middleware){
		this.name=name;
		this.middleware=middleware;
	}
	
	public String getName(){
		return this.name;
	}
	
	public MiddlewareFactory getMiddlewareFactory(){
		return this.middleware;
	}
	
	abstract public List<TreeElement> getChildren();
	
	abstract public boolean hasChildren();
	
	public TreeElement getParent(){
		return this.parent;
	}
}
