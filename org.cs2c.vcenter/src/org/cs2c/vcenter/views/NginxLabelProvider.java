/**
 * 
 */
package org.cs2c.vcenter.views;

import org.cs2c.vcenter.views.models.HttpElement;
import org.cs2c.vcenter.views.models.LocationElement;
import org.cs2c.vcenter.views.models.LogElement;
import org.cs2c.vcenter.views.models.LogInstanceElement;
import org.cs2c.vcenter.views.models.ModuleElement;
import org.cs2c.vcenter.views.models.ProjectElement;
import org.cs2c.vcenter.views.models.ServerElement;
import org.cs2c.vcenter.views.models.TreeElement;
import org.cs2c.vcenter.views.models.UpstreamElement;
import org.cs2c.vcenter.views.models.UpstreamInstanceElement;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IViewerLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

/**
 * @author Administrator
 *
 */
public class NginxLabelProvider implements ILabelProvider {

	/**
	 * 
	 */
	public NginxLabelProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub  new Image(null, "icons/http.png");
		//TreeElement node=(TreeElement)element;
		if(element instanceof HttpElement)
			return new Image(null, "icons/http.png");
		else if(element instanceof LogElement)
			return new Image(null, "icons/logs.png");
		else if(element instanceof UpstreamElement)
			return new Image(null, "icons/upstreams.png");
		else if(element instanceof UpstreamInstanceElement)
			return new Image(null, "icons/upstream.png");
		/*else if(element instanceof ModuleElement)
			return new Image(null, "icons/module.png");
			*/
		else if(element instanceof ProjectElement)
				return new Image(null, "icons/project.png");
		else if(element instanceof LogInstanceElement)
			return new Image(null, "icons/log.png");
		else if(element instanceof LocationElement)
			return new Image(null, "icons/location.png");
		else if(element instanceof ServerElement)
			return new Image(null, "icons/server.png");
		
		else 
			return null;
		
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		TreeElement node=(TreeElement)element;
		return node.getName();
	}

}
