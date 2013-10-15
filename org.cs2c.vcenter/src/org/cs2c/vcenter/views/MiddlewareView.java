package org.cs2c.vcenter.views;

import java.io.File;

import org.cs2c.vcenter.actions.HostManagerAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.w3c.dom.Document;

import javax.xml.parsers.*;

public class MiddlewareView extends ViewPart {

	public static final String ID = "org.cs2c.vcenter.views.MiddlewareView"; //$NON-NLS-1$

	public MiddlewareView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.BORDER);
		TreeViewer treeViewer=new TreeViewer(tree);
		try{
			DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=db.parse(new File("tree.xml"));
			treeViewer.setContentProvider(new NginxTreeContentProvider());
			treeViewer.setLabelProvider(new NginxLabelProvider());
			treeViewer.setInput(doc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		MenuManager menu=new MenuManager();
		menu.add(new HostManagerAction("sss"));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		tree.setMenu(menu.createContextMenu(tree));
		this.getViewSite().registerContextMenu(menu, treeViewer);
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
