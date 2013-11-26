package org.cs2c.vcenter.dialog;

import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class BlockInputDialog extends Dialog {
	
	/**
	 * @wbp.parser.constructor
	 */
	public BlockInputDialog(Shell parentShell, TreeElement input, BlockMeta blkMeta, String blkSubGroup) {
		super(parentShell);

		//Composite
		Composite cmpsite = (Composite)super.getDialogArea();
		BlockInput bInput = new BlockInput(cmpsite, SWT.NONE, input, blkMeta, blkSubGroup);
		
		cmpsite.layout(true);
		cmpsite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cmpsite.setLayout(new GridLayout(3,false));
	    
		bInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		GridData gridDataList=new GridData(GridData.FILL_BOTH);
		bInput.setLayoutData(gridDataList);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public BlockInputDialog(IShellProvider parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
}
