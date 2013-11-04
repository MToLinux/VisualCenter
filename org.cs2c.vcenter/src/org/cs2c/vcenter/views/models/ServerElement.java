/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;

import org.cs2c.nginlib.MiddlewareFactory;

/**
 * @author Administrator
 *
 */
public class ServerElement extends TreeElement implements IServer {

	/**
	 * @param parent
	 */
	public ServerElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();
		LocationElement location=new LocationElement(this);
		location.init("location /", this.middleware);
		children.add(location);
		return children;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}


}
