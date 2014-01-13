/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.*;

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
	}
	
	/**
	 * get StatusPath
	 * @return statusPath
	 */
	public String getStatusPath() {
		return statusPath;
	}

	/**
	 * set StatusPath
	 * @param statusPath statusPath
	 */
	public void setStatusPath(String statusPath) {
		this.statusPath = statusPath;
	}

	/**
	 * get Manager's user name
	 * @return Manager's user name
	 */
	public String getManagerUsername() {
		return managerUsername;
	}

	/**
	 * set Manager's user name
	 * @param managerUsername Manager's user name
	 */
	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}

	/**
	 * get user Manager's Password
	 * @return user Manager's password
	 */
	public String getManagerPassword() {
		return managerPassword;
	}
	
	/**
	 * set user Manager's Password
	 * @param managerPassword user Manager's password
	 */
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	
	@Override
	public List<TreeElement> getChildren() {
		List<TreeElement> elements=new LinkedList<TreeElement>();
		HttpElement http=new HttpElement(this);
		http.init("Http","http","0","", this.middleware);
		UpstreamElement upstream=new UpstreamElement(this);
		upstream.init("Upstream","allupstream","0","", this.middleware);
		ModuleElement module=new ModuleElement(this);
		module.init("Module","module","","", this.middleware);
		LogElement log=new LogElement(this);
		log.init("Log","log","","", this.middleware);
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
