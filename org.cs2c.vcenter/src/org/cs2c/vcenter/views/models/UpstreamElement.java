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
public class UpstreamElement extends TreeElement implements IUpstream {

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
		UpstreamInstanceElement upstreamInstance=new UpstreamInstanceElement(this);
		upstreamInstance.init("upstream tomcat", this.middleware);
		children.add(upstreamInstance);
		
		children.add(upstreamInstance);
		return children;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}


}
