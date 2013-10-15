package org.cs2c.vcenter;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.cs2c.vcenter.actions.*;
import org.eclipse.jface.action.Action;
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAllAction;
	private Action newProjectAction;
	private Action importProjectAction;
	private IWorkbenchAction aboutAction;
	private Action hostManagerAction;
	private MenuManager fileMenu;
	private MenuManager helpMenu;
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	this.exitAction=ActionFactory.QUIT.create(window);
    	this.saveAction=ActionFactory.SAVE.create(window);
    	this.saveAllAction=ActionFactory.SAVE_ALL.create(window);
    	this.aboutAction=ActionFactory.ABOUT.create(window);
    	this.newProjectAction=new NewProjectAction(window);
    	this.importProjectAction=new ImportProjectAction(window);
    	this.hostManagerAction=new HostManagerAction(window);
    	this.register(this.exitAction);
    	this.register(this.saveAction);
    	this.register(this.saveAllAction);
    	this.register(this.aboutAction);
    	this.register(this.newProjectAction);
    	this.register(this.importProjectAction);
    	this.register(this.hostManagerAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	this.fileMenu=new MenuManager("&File",IWorkbenchActionConstants.M_FILE);
    	this.helpMenu=new MenuManager("&Help",IWorkbenchActionConstants.M_HELP);
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	this.fileMenu.add(this.newProjectAction);
    	this.fileMenu.add(this.importProjectAction);
    	this.fileMenu.add(this.hostManagerAction);
    	this.fileMenu.add(new Separator());
    	this.fileMenu.add(this.saveAction);
    	this.fileMenu.add(this.saveAllAction);
    	this.fileMenu.add(new Separator());
    	this.fileMenu.add(this.exitAction);
    	this.helpMenu.add(this.aboutAction);
    }
    @Override
    protected void fillCoolBar(ICoolBarManager coolBar){
    	IToolBarManager toolBar=new ToolBarManager(SWT.FLAT|SWT.LEFT);
    	toolBar.add(this.newProjectAction);
    	toolBar.add(this.importProjectAction);
    	toolBar.add(this.hostManagerAction);
    	toolBar.add(this.saveAction);
    	toolBar.add(this.saveAllAction);
    	coolBar.add(new ToolBarContributionItem(toolBar,"coolbar.main"));
    }
    
}
