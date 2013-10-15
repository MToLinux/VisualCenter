package org.cs2c.vcenter;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.cs2c.vcenter.views.*;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.setFixed(false);
		layout.addView(MiddlewareView.ID, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		layout.getViewLayout(MiddlewareView.ID).setCloseable(false);
	}
}
