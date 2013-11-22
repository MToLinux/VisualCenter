/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.vcenter.views.models.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author Administrator
 *
 */
public class ProjectElement extends TreeElement implements IProject, IEditorInput{
	private String statusPath;
	private String managerUsername;
	private String managerPassword;
	/**
	 * @param tree
	 */
	public ProjectElement(TreeElement parent) {
		super(null);
		// TODO Auto-generated constructor stub
	}
	public String getStatusPath() {
		return statusPath;
	}

	public void setStatusPath(String statusPath) {
		this.statusPath = statusPath;
	}

	public String getManagerUsername() {
		return managerUsername;
	}

	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> elements=new LinkedList<TreeElement>();
		HttpElement http=new HttpElement(this);
		http.init("Http", this.middleware);
		UpstreamElement upstream=new UpstreamElement(this);
		upstream.init("Upstream", this.middleware);
		ModuleElement module=new ModuleElement(this);
		module.init("Module", this.middleware);
		LogElement log=new LogElement(this);
		log.init("Log", this.middleware);
		elements.add(http);
		elements.add(upstream);
		elements.add(module);
		elements.add(log);
		return elements;
	}

	@Override
	public boolean hasChildren() {
		return true;
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
		if(obj instanceof ProjectElement){
			ProjectElement element=(ProjectElement)obj;
			return this.name==element.name;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}
}
