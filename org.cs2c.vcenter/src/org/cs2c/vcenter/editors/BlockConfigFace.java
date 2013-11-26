package org.cs2c.vcenter.editors;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cs2c.nginlib.AuthInfo;
import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.vcenter.composites.BlockInput;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.HttpElement;
import org.cs2c.vcenter.views.models.LocationElement;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.SWT;

public class BlockConfigFace extends EditorPart {
	
	
	public static final String ID="org.cs2c.vcenter.editors.BlockConfigFace";
	
	private TabFolder tabFolder = null;

	private Hashtable<String, TabItem> htGroupTItems = new Hashtable<String, TabItem>();
	private Hashtable<String, BlockInput> htGroupBInputs = new Hashtable<String, BlockInput>();
	private BlockInput bInput = null;
	
	private TreeElement input;
	private String blockType = null;
	
	private BlockMeta bMeta = new BlockMeta();
	List<String> blockGroups = new ArrayList<String>();
	
	public BlockConfigFace() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if(bInput != null && bInput.isChanged())
		{
			//Save
			
			bInput.setChangedFlag(false);
		}
		else
		{
			//Save
			
			if(blockGroups!=null && !blockGroups.isEmpty())
			{
				int count = 0;
				int i = 0;
				while(i < count)
				{
					//get BlockInput
					BlockInput blkinput = htGroupBInputs.get(blockGroups.get(i));
					//Save
					
					//modify bInput falg to FALSE
					blkinput.setChangedFlag(false);
				}
			}

		}
		
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
		this.input=(TreeElement)input;
		
		//Just for test!!! begin by yanbin.jia
		AuthInfo authInfo=MiddlewareFactory.newAuthInfo();
		authInfo.setHost("10.1.50.4");
		authInfo.setUsername("root");
		authInfo.setPassword("cs2csolutions");
		MiddlewareFactory middleware;
		try {
			middleware = MiddlewareFactory.getInstance(authInfo, "/usr/local/nginx/");
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		}
		this.input = new HttpElement(null);
		this.input.init("","main","0","", middleware);
		//Just for test!!! end by yanbin.jia

	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

		this.blockType = input.getBlocktype();
		
		MetaManager mmanager = MetaManager.getInstance();
		bMeta = mmanager.getBlockMeta(this.blockType);
		blockGroups = bMeta.getGroups();
		
		int countGroups = 0;
		if(blockGroups == null && blockGroups.isEmpty())
		{
			return;
		}
		else
		{
			countGroups = blockGroups.size();
		}
		
		if(countGroups == 1)
		{
			String subGroupName = blockGroups.get(0);
			bInput = new BlockInput(parent, SWT.NONE, input, bMeta, subGroupName);//this.name, subGroupName, this.middleware);
		}
		else
		{
			this.tabFolder = new TabFolder(parent, SWT.NONE);
			
			int i = 0;
			while(i < countGroups)
			{
				String subGroupName = blockGroups.get(i);
				
				TabItem tbi = new TabItem(this.tabFolder, SWT.NONE);
				tbi.setText(subGroupName);
				
				BlockInput cpstBlockInput = new BlockInput(
						this.tabFolder, SWT.NONE, input, bMeta, subGroupName);//this.name, subGroupName, this.middleware);
				tbi.setControl(cpstBlockInput);
				
				htGroupTItems.put(subGroupName, tbi);
				htGroupBInputs.put(subGroupName, cpstBlockInput);
				
				i++;
			}
		}
	
	}

	@Override
	public void dispose(){
		super.dispose();
	}
	@Override
	public void setFocus() {

	}
	
}
