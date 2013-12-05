package org.cs2c.vcenter.views;

import java.util.*;

//import org.cs2c.vcenter.actions.HostManagerAction;
//import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
//import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.cs2c.vcenter.views.models.*;

import org.cs2c.nginlib.*;

public class MiddlewareView extends ViewPart {

	public static final String ID = "org.cs2c.vcenter.views.MiddlewareView"; //$NON-NLS-1$
	private TreeViewer treeViewer=null;
	private List<TreeElement> projectList=new LinkedList<TreeElement>();
	private List<String> projectnameList=new ArrayList<String>();

	public MiddlewareView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.BORDER);
		this.treeViewer=new TreeViewer(tree);
		this.treeViewer.setContentProvider(new NginxTreeContentProvider());
		this.treeViewer.setLabelProvider(new NginxLabelProvider());
		
		MenuManager menu=new MenuManager();
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		tree.setMenu(menu.createContextMenu(tree));
		this.getViewSite().registerContextMenu(menu, treeViewer);
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	public TreeViewer getTreeViewer(){
		return this.treeViewer;
	}
	
	public void addProject(String name, MiddlewareFactory middleware){
		ProjectElement project=new ProjectElement(null);
		project.init(name, "main","0","", middleware);
		if(this.projectnameList.contains(name)){
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openInformation(shell, "Information", "the middleware is already exist!");
			return;
		}
		this.projectnameList.add(name);
		this.projectList.add(project);
		this.treeViewer.setInput(projectList);
//		showMessage("load already.");
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
	
	public void showMessage(String message) {
		IActionBars bars = getViewSite().getActionBars();
		bars.getStatusLineManager().setMessage(message);
	}
}
