/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.log.LogProfile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Administrator
 *
 */
public class LogInstanceElement extends TreeElement implements ILogsInstance, IEditorInput {
	private LogProfile log;
	/**
	 * @param size the size to set
	 */
	public void setLog(LogProfile log) {
		this.log = log;
	}
	public LogProfile getLog(){
		return this.log;
	}
	/**
	 * @param parent
	 */
	public LogInstanceElement(TreeElement parent) {
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
	public boolean equals(Object obj){
		if(obj instanceof LogInstanceElement){
			LogInstanceElement element=(LogInstanceElement)obj;
			return this.name==element.name;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}

}
