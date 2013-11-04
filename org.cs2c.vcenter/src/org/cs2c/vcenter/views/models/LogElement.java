/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.LinkedList;
import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;

/**
 * @author Administrator
 *
 */
public class LogElement extends TreeElement implements ILogs {

	/**
	 * @param parent
	 */
	public LogElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> children=new LinkedList<TreeElement>();
		LogInstanceElement logInstance=new LogInstanceElement(this);
		logInstance.init("error.log", this.middleware);
		children.add(logInstance);
		return children;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}


}
