/**
 * 
 */
package org.cs2c.vcenter.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Administrator
 *
 */
public class NginxTreeContentProvider implements ITreeContentProvider {

	/**
	 * 
	 */
	public NginxTreeContentProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		Document doc=(Document)inputElement;
		Element root=doc.getDocumentElement();
		NodeList els=root.getElementsByTagName("project");
		Object[] objs=new Object[els.getLength()];
		for(int i=0;i<els.getLength();i++){
			objs[i]=els.item(i);
		}
		return objs;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		Node pnode=(Node)parentElement;
		NodeList els=pnode.getChildNodes();
		ArrayList list=new ArrayList();
		for(int i=0;i<els.getLength();i++){
			if(els.item(i).getNodeType()==Node.ELEMENT_NODE){
				list.add(els.item(i));
			}
		}
		return list.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		Node node=(Node)element;
		return node.getParentNode();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		Node pnode=(Node)element;
		return pnode.hasChildNodes();
	}

}
