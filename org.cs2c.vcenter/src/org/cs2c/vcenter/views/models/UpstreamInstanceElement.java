/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;

/**
 * @author Administrator
 *
 */
public class UpstreamInstanceElement extends TreeElement implements
		IUpstreamInstance {

	/**
	 * @param parent
	 */
	public UpstreamInstanceElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}


}
