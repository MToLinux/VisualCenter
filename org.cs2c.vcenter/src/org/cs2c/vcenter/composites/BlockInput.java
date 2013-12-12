package org.cs2c.vcenter.composites;

import java.util.Hashtable;
import java.util.ArrayList;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Configurator;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.dialog.BlockConfigDialog;
import org.cs2c.vcenter.dialog.BlockElementInfo;
import org.cs2c.vcenter.dialog.BlockNameSetDialog;
import org.cs2c.vcenter.dialog.DirectiveInput;
import org.cs2c.vcenter.dialog.ElementSelector;
import org.cs2c.vcenter.editors.BlockConfigFace;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.DirectiveMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class BlockInput extends Composite {
	
	private org.eclipse.swt.widgets.List ctlList;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	boolean flagChanged = false;
	
	private MiddlewareFactory middleware = null;
	private String blockSubGroup = null;
	private Configurator orc = null;
	
	private final String strBlockMark = " {...}";
	
	private BlockMeta bMeta = null;
	
	private Block block = null; 
	
	private java.util.List<Directive> directives = null;
	private java.util.List<Block> blocks = null;
	
	private BlockConfigFace bcfParent = null;
	
	
	public BlockInput(Composite parent, int style, BlockElementInfo bcInfo, String blkSubGroup, BlockConfigFace edParent) {
		super(parent, style);
		
		this.middleware = bcInfo.getMiddleware();
		this.bMeta = bcInfo.getBlockMeta();
		this.blockSubGroup = blkSubGroup;
		
		this.orc = this.middleware.getConfigurator();
		
		this.block = bcInfo.getBlock();
		
		this.bcfParent = edParent;
		
		this.layout(true);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3,false));
	    
		this.ctlList = new List(this, SWT.BORDER | SWT.V_SCROLL);
		this.ctlList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridData gridDataList=new GridData(GridData.FILL_BOTH);
	    gridDataList.verticalSpan=6;
	    this.ctlList.setLayoutData(gridDataList);
		
		new Label(this, SWT.NONE);
		this.btnAdd = new Button(this,SWT.NONE);
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				java.util.List<DirectiveMeta> directiveMetas = new ArrayList<DirectiveMeta>();
				java.util.List<BlockMeta> blockMetas = new ArrayList<BlockMeta>();
				directiveMetas = bMeta.getDirectiveMeta(blockSubGroup);
				blockMetas = bMeta.getBlockMeta(blockSubGroup);
				
				java.util.List<String> elementNames = new ArrayList<String>();
				Hashtable<String, String> htDMNames = new Hashtable<String, String>();
				Hashtable<String, String> htBMNames = new Hashtable<String, String>();
				
				int count = 0;
				int i = 0;
				
				if(directiveMetas != null && !directiveMetas.isEmpty())
				{
					count = directiveMetas.size();
					i = 0;
					while(i<count)
					{
						String dirctvname = directiveMetas.get(i).getName();
						elementNames.add(elementNames.size(), dirctvname);
						if(directiveMetas.get(i).getReused())
						{
							htDMNames.put(dirctvname, "true");
						}
						else
						{
							htDMNames.put(dirctvname, "false");
						}
						i++;
					}
				}
				if(blockMetas != null && !blockMetas.isEmpty())
				{
					count = blockMetas.size();
					i = 0;
					while(i<count)
					{
						String blkname = blockMetas.get(i).getName();
						elementNames.add(elementNames.size(), blkname+strBlockMark);
						if(blockMetas.get(i).getReused())
						{
							htBMNames.put(blkname, "true");
						}
						else
						{
							htBMNames.put(blkname, "false");
						}
						i++;
					}
				}
				
				if(directives != null && !directives.isEmpty())
				{
					count = directives.size();
					i = 0;
					while(i < count)
					{
						String name = directives.get(i).getName();
						String nameslice[] = name.split(" ");
						String typeInName = nameslice[0];
						if(htDMNames.containsKey(typeInName))
						{
							if((htDMNames.get(typeInName)).equals("true"))
							{
							}
							else
							{
								elementNames.remove(typeInName);
							}
						}
						i++;
					}
				}
				if(blocks != null && !blocks.isEmpty())
				{
					count = blocks.size();
					i = 0;
					while(i < count)
					{
						String name = blocks.get(i).getName();
						String nameslice[] = name.split(" ");
						String typeInName = nameslice[0];
						if(htBMNames.containsKey(typeInName))
						{
							if((htBMNames.get(typeInName)).equals("true"))
							{
							}
							else
							{
								elementNames.remove(typeInName);
							}
						}
						i++;
					}
				}
				
				
				ElementSelector eleSelector = new ElementSelector(getShell(), elementNames);
				
				if(Window.OK == eleSelector.open())
				{
					String selEleName = eleSelector.getSelectedElementName();
					
					int len = selEleName.length();
					if(len > 6 && selEleName.substring(selEleName.length()-6,selEleName.length()).equals(strBlockMark))
					{
						try {
							Block newblk = null;
							newblk = orc.newBlock();
							
							BlockNameSetDialog bnsDlg = new BlockNameSetDialog(getShell(), 
									selEleName.substring(0, selEleName.length()-6));
							if(Window.OK == bnsDlg.open())
							{
								String newBKName = bnsDlg.getBlockName();
								if(newBKName!=null && !newBKName.isEmpty())
								{
									newblk.setName((selEleName.substring(0, selEleName.length()-6)).trim() +
											" " + newBKName);
								}
								else
								{
									if(MessageDialog.openConfirm(getShell(), "Question", "Without a block name?"))
									{
										newblk.setName((selEleName.substring(0, selEleName.length()-6)).trim());
									}
									else
									{
										return;
									}
								}
								block.addBlock(newblk);
								
								UpdateListCtl();
								if(bcfParent != null)
								{
									bcfParent.SetDirty(true);
								}
								flagChanged = true;
							}
						} catch (RemoteException e1) {
							MessageDialog.openError(getShell(), "Error", e1.getMessage());
							e1.printStackTrace();
						}
						
					}
					else
					{
						DirectiveMeta dMeta = null;
						count = directiveMetas.size();
						i = 0;
						while(i < count)
						{
							dMeta = directiveMetas.get(i);
							String metaName = dMeta.getName();
							if(selEleName.equals(metaName))
							{
								break;
							}
							dMeta = null;
							i++;
						}
						if(dMeta == null)
						{
							return;
						}
						
						java.util.List<ParameterMeta> listParamMeta = dMeta.getOptions();
						if(listParamMeta == null || listParamMeta.isEmpty())
						{
							if(!MessageDialog.openConfirm(getShell(), "Question", "This directive has no parameter?"))
							{
								return;
							}
							
							Directive dirct = orc.newDirective();
							dirct.setName(selEleName);
							
							block.addDirective(dirct);
							UpdateListCtl();
							if(bcfParent != null)
							{
								bcfParent.SetDirty(true);
							}
							flagChanged = true;
							
							return;
						}
						
						DirectiveInput dInput = new DirectiveInput(getShell(), null, dMeta);
						
						if(Window.OK == dInput.open())
						{
							Directive dirct = orc.newDirective();
							dirct.setName(selEleName);
							
							java.util.List<Parameter> listParams = dInput.getParams();
							if(listParams!=null && !listParams.isEmpty())
							{
								Parameter param = null;
								count = listParams.size();
								i = 0;
								while(i < count)
								{
									param = listParams.get(i);
									dirct.addParameter(param);
									i++;
								}
							}
							else
							{
								return;
							}

							block.addDirective(dirct);
							UpdateListCtl();
							if(bcfParent != null)
							{
								bcfParent.SetDirty(true);
							}
							flagChanged = true;
						}
					}//else
				}
			}
		});
		
		this.btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnAdd.setText("Add...");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		this.btnEdit = new Button(this,SWT.NONE);
		
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				java.util.List<DirectiveMeta> directiveMetas = new ArrayList<DirectiveMeta>();
				java.util.List<BlockMeta> blockMetas = new ArrayList<BlockMeta>();
				directiveMetas = bMeta.getDirectiveMeta(blockSubGroup);
				blockMetas = bMeta.getBlockMeta(blockSubGroup);
				
				if(ctlList.getSelectionCount() == 0)
				{
					MessageDialog.openWarning(getShell(),"Question","Please select an option!");
					return;
				}
				String selEle = ctlList.getItem(ctlList.getSelectionIndex());
				
				int len = selEle.length();
				if(len > 6 && (selEle.trim()).substring(selEle.length()-6,selEle.length()).equals(strBlockMark))
				{
					String strSelEleType = (selEle.trim()).substring(0, selEle.length()-6);
					strSelEleType = strSelEleType.trim();
					String[] strSelEleTypeSlip = strSelEleType.split(" ");
					String strSelEleBaseType = strSelEleTypeSlip[0];
					
					BlockMeta curbMeta = null;
					int count = blockMetas.size();
					int i = 0;
					while(i < count)
					{
						curbMeta = blockMetas.get(i);
						if(strSelEleBaseType.equals(curbMeta.getName()))
						{
							break;
						}
						curbMeta = null;
						i++;
					}
					if(curbMeta == null)
					{
						return;
					}
					
					int indexSel = ctlList.getSelectionIndex();
					int indexSubBlock = 0;
					
					i = 0;
					while(i < indexSel)
					{
						String strItemText = ctlList.getItem(i).trim();
						if( strItemText.equals(selEle.trim()) )
						{
							indexSubBlock++;
						}
						i++;
					}
					
					Block curBlk = null;
					count = blocks.size();
					i = 0;
					int wIndex = -1;
					while(i<count && wIndex<indexSubBlock)
					{
						curBlk = blocks.get(i);
						if(strSelEleType.equals(curBlk.getName()))
						{
							wIndex++;
							if(wIndex == indexSubBlock)
							{
								break;
							}
						}
						i++;
					}
					if(wIndex != indexSubBlock)
					{
						MessageDialog.openError(getShell(), "Error", "Can not find the Block!");
						return;
					}
					
					BlockElementInfo bcInfo = new BlockElementInfo();
					bcInfo.setBlock(curBlk);
					bcInfo.setBlockType(strSelEleBaseType);
					
					MetaManager mmanager = null;
					BlockMeta subbMeta = null;
					mmanager = MetaManager.getInstance();
					String parentName = "";
					if(bMeta != null)
					{
						parentName = bMeta.getName();
					}
					subbMeta = mmanager.getBlockMeta(strSelEleBaseType, parentName);
					bcInfo.setBlockMeta(subbMeta);
					
					bcInfo.setMiddleware(middleware);
					
					BlockConfigDialog bcDlg = new BlockConfigDialog(getShell(), bcInfo);
					
					if(Window.OK == bcDlg.open())
					{
						UpdateListCtl();
						if(bcfParent != null)
						{
							bcfParent.SetDirty(true);
						}
						flagChanged = true;
					}
				}
				else
				{
					if(selEle.startsWith(";"))
					{
						return;
					}
					while(selEle.endsWith(";"))
					{
						selEle = selEle.substring(0, selEle.length()-1);
						if(selEle.startsWith(";"))
						{
							return;
						}
					}
					String selEleSlip[] = selEle.split(" ");
					String strSelEleName = selEleSlip[0];
					DirectiveMeta dMeta = null;
					int count = directiveMetas.size();
					int i = 0;
					while(i < count)
					{
						dMeta = directiveMetas.get(i);
						if(strSelEleName.equals(dMeta.getName()))
						{
							break;
						}
						dMeta = null;
						i++;
					}
					if(dMeta == null)
					{
						return;
					}
					
					Directive oldDirct = null;
					if(directives!=null && !directives.isEmpty())
					{
						count = directives.size();
						i = 0;
						while(i < count)
						{
							oldDirct = directives.get(i);
							if(strSelEleName.equals(oldDirct.getName()))
							{
								break;
							}
							oldDirct = null;
							i++;
						}
						if(oldDirct == null)
						{
							return;
						}
					}
					
					java.util.List<ParameterMeta> listParamMeta = dMeta.getOptions();
					if(listParamMeta == null || listParamMeta.isEmpty())
					{
						MessageDialog.openInformation(getShell(), "Information", "This directive has no parameter!");
						return;
					}
					
					DirectiveInput dInput = new DirectiveInput(getShell(), oldDirct, dMeta);//oldDirct
					
					if(Window.OK == dInput.open())
					{
						Directive newDirct = orc.newDirective();
						newDirct.setName(strSelEleName);
						
						java.util.List<Parameter> listParams = dInput.getParams();
						if(listParams!=null && !listParams.isEmpty())
						{
							Parameter param = null;
							count = listParams.size();
							i = 0;
							while(i < count)
							{
								param = listParams.get(i);
								newDirct.addParameter(param);
								i++;
							}
						}
						else
						{
							return;
						}
						
						block.deleteElement(oldDirct);
						block.addDirective(newDirct);

						UpdateListCtl();
						if(bcfParent != null)
						{
							bcfParent.SetDirty(true);
						}
						flagChanged = true;
					}
				}
			}
		});
		
		this.btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnEdit.setText("Edit...");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		this.btnDelete = new Button(this,SWT.NONE);
		
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {

				if(ctlList.getSelectionCount() == 0)
				{
					MessageDialog.openWarning(getShell(),"Warning","Please select an option!");
					return;
				}
				if(false == MessageDialog.openQuestion(getShell(), "Question", "Are you sure to delete the option?"))
				{
					return;
				}
				String selEle = ctlList.getItem(ctlList.getSelectionIndex());
				
				int len = selEle.length();
				int count = 0;
				int i = 0;
				if(len > 6 && selEle.substring(selEle.length()-6,selEle.length()).equals(strBlockMark))
				{
					String strSelEleType = (selEle.trim()).substring(0, selEle.length()-6);
					strSelEleType = strSelEleType.trim();
					
					int indexSel = ctlList.getSelectionIndex();
					int indexSubBlock = 0;
					
					i = 0;
					while(i < indexSel)
					{
						String strItemText = ctlList.getItem(i).trim();
						if( strItemText.equals(selEle.trim()) )
						{
							indexSubBlock++;
						}
						i++;
					}
					
					Block curBlk = null;
					count = blocks.size();
					i = 0;
					int wIndex = -1;
					while(i<count && wIndex<indexSubBlock)
					{
						curBlk = blocks.get(i);
						if(strSelEleType.equals(curBlk.getName()))
						{
							wIndex++;
							if(wIndex == indexSubBlock)
							{
								break;
							}
						}
						i++;
					}
					if(wIndex != indexSubBlock)
					{
						MessageDialog.openError(getShell(), "Error", "Can not find the Block!");
						return;
					}
					
					block.deleteElement(curBlk);
				}
				else
				{
					if(selEle.startsWith(";"))
					{
						return;
					}
					while(selEle.endsWith(";"))
					{
						selEle = selEle.substring(0, selEle.length()-1);
						if(selEle.startsWith(";"))
						{
							return;
						}
					}
					String selEleSlip[] = selEle.split(" ");
					String strSelEleType = selEleSlip[0];
					
					int indexSel = ctlList.getSelectionIndex();
					int indexSubDirct = 0;
					
					i = 0;
					while(i < indexSel)
					{
						String strItemText = ctlList.getItem(i).trim();
						if( strItemText.equals(selEle.trim()) )
						{
							indexSubDirct++;
						}
						i++;
					}
					
					Directive curDirct = null;
					count = directives.size();
					i = 0;
					int wIndex = -1;
					while(i<count && wIndex<indexSubDirct)
					{
						curDirct = directives.get(i);
						if(strSelEleType.equals(curDirct.getName()))
						{
							wIndex++;
							if(wIndex == indexSubDirct)
							{
								break;
							}
						}
						i++;
					}
					if(wIndex != indexSubDirct)
					{
						MessageDialog.openError(getShell(), "Error", "Can not find the Directive!");
						return;
					}
					
					block.deleteElement(curDirct);
				}
				
				UpdateListCtl();
				if(bcfParent != null)
				{
					bcfParent.SetDirty(true);
				}
				flagChanged = true;
			}
		});
		
		this.btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnDelete.setText("   Delete   ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		UpdateListCtl();
	}
	
	
	private void UpdateListCtl()
	{
		try {
			this.directives = this.block.getDirectives();
			this.blocks = this.block.getBlocks();
		} catch (RemoteException e1) {
			e1.printStackTrace();
			MessageDialog.openError(getShell(), "Error", e1.getMessage());
			return;
		}
		
		this.ctlList.removeAll();
		
		java.util.List<DirectiveMeta> subDirectiveMetas = this.bMeta.getDirectiveMeta(this.blockSubGroup);
		java.util.List<BlockMeta> subBlockMetas = this.bMeta.getBlockMeta(this.blockSubGroup);
		
		java.util.List<String> subDirectiveTypes = new ArrayList<String>();
		java.util.List<String> subBlockTypes = new ArrayList<String>();
		
		subBlockTypes.clear();
		subDirectiveTypes.clear();
		int count = 0;
		int i = 0;
		if(subDirectiveMetas != null && !subDirectiveMetas.isEmpty())
		{
			count = subDirectiveMetas.size();
			while(i<count)
			{
				String name = subDirectiveMetas.get(i).getName();
				int curDCount = subDirectiveTypes.size();
				subDirectiveTypes.add(curDCount,name);
				i++;
			}
		}
		i = 0;
		if(subBlockMetas != null && !subBlockMetas.isEmpty())
		{
			count = subBlockMetas.size();
			while(i<count)
			{
				String name = subBlockMetas.get(i).getName();
				int curBCount = subBlockTypes.size();
				subBlockTypes.add(curBCount,name);
				i++;
			}
		}

		i = 0;
		if(this.directives != null && !this.directives.isEmpty())
		{
			count = this.directives.size();
			while(i<count)
			{
				String name = this.directives.get(i).getName();
				String nameslice[] = name.split(" ");
				String typeInName = nameslice[0];
				if(subDirectiveTypes.contains(typeInName))
				{
					this.ctlList.add(this.directives.get(i).toString().trim());
				}
				i++;
			}
		}
		i = 0;
		if(this.blocks != null && !this.blocks.isEmpty())
		{
			count = this.blocks.size();
			while(i<count)
			{
				String name = this.blocks.get(i).getName();
				String nameslice[] = name.split(" ");
				String typeInName = nameslice[0];
				if(subBlockTypes.contains(typeInName))
				{
					this.ctlList.add(name + this.strBlockMark);
				}
				i++;
			}
		}
		
	}
	
	public boolean isChanged()
	{
		return this.flagChanged;
	}
	
	public void benchmark()
	{
		this.flagChanged = false;
	}
	
	public void setMeta(BlockMeta meta)
	{
		this.bMeta = meta;
	}
	
}
