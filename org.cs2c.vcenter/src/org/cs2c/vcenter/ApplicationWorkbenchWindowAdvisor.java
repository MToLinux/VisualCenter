package org.cs2c.vcenter;

import org.cs2c.vcenter.editors.BlockConfigFace;
import org.cs2c.vcenter.views.models.ProjectElement;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(400, 300));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setShowPerspectiveBar(true);
        configurer.setTitle("VisualCenter"); //$NON-NLS-1$
        PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, IWorkbenchPreferenceConstants.TOP_RIGHT);
    }
    @Override
    public void postWindowCreate(){
    	super.postWindowCreate();
    	this.getWindowConfigurer().getWindow().getShell().setMaximized(true);
    	
    	//For develop test!!! begin. By yanbin.jia
    	IWorkbenchPage page = null;
    	page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    	ProjectElement projElement = new ProjectElement(null);
		try {
			page.openEditor(projElement, BlockConfigFace.ID);//HttpEditor,MonitorFace,BasicConfigFace,BlockConfigFace
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		//For develop test!!! end. By yanbin.jia
		
    }
}
