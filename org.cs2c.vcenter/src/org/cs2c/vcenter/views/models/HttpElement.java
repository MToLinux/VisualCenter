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
public class HttpElement extends TreeElement implements IHttp {

	/**
	 * @param parent
	 */
	public HttpElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();
		ServerElement server=new ServerElement(this);
		server.init("server", this.middleware);
		children.add(server);
		return children;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}


}
