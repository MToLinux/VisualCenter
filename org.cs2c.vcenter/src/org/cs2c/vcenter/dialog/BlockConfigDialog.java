package org.cs2c.vcenter.dialog;

import java.util.Hashtable;
import java.util.List;

import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class BlockConfigDialog extends Dialog {
	
	private TabFolder tabFolder = null;
	
	private Hashtable<String, TabItem> htGroupTItems = new Hashtable<String, TabItem>();
	private Hashtable<String, BlockInput> htGroupBInputs = new Hashtable<String, BlockInput>();
	private BlockInput bInput = null;
	
	private BlockMeta bMeta = null;
	private List<String> blockGroups = null;
	
	private BlockElementInfo bcInfo = null;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public BlockConfigDialog(Shell parentShell, BlockElementInfo bcInfo) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM);
		
		this.bcInfo = bcInfo;
		
		this.bMeta = bcInfo.getBlockMeta();
		
	}

	public BlockConfigDialog(IShellProvider parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {
		
		getShell().setText(this.bMeta.getName());
		
		Composite cmpsite = (Composite)super.createDialogArea(parent);
		
		cmpsite.layout(true);
		cmpsite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cmpsite.setLayout(new GridLayout(1,false));
		
		this.blockGroups = this.bMeta.getGroups();
		
		int countGroups = 0;
		if(this.blockGroups == null && this.blockGroups.isEmpty())
		{
			return null;
		}
		else
		{
			countGroups = this.blockGroups.size();
		}
		
		if(countGroups == 1)
		{
			String subGroupName = this.blockGroups.get(0);
			this.bInput = new BlockInput(cmpsite, SWT.NONE, this.bcInfo, subGroupName, null);
		    
			this.bInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			GridData gridDataList=new GridData(GridData.FILL_BOTH);
			this.bInput.setLayoutData(gridDataList);
		}
		else
		{
			this.tabFolder = new TabFolder(cmpsite, SWT.NONE);
		    
			this.tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			GridData gridDataList=new GridData(GridData.FILL_BOTH);
			this.tabFolder.setLayoutData(gridDataList);
			
			int i = 0;
			BlockInput[] bInputs = new BlockInput[countGroups];
			while(i < countGroups)
			{
				String subGroupName = this.blockGroups.get(i);
				
				TabItem tbi = new TabItem(this.tabFolder, SWT.NONE);
				tbi.setText(subGroupName);
				
				bInputs[i] = new BlockInput(this.tabFolder, SWT.NONE, this.bcInfo, subGroupName, null);
				tbi.setControl(bInputs[i]);
				
				this.htGroupTItems.put(subGroupName, tbi);
				this.htGroupBInputs.put(subGroupName, bInputs[i]);
				
				i++;
			}
		}
		
		return cmpsite;
	}
	
}
