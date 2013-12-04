package org.cs2c.vcenter.composites;

import java.util.ArrayList;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Configurator;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.Parameter;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.nginlib.config.RecDirective;
import org.cs2c.nginlib.config.RecStringParameter;
import org.cs2c.vcenter.dialog.BlockConfigDialog;
import org.cs2c.vcenter.dialog.BlockElementInfo;
import org.cs2c.vcenter.dialog.DirectiveInput;
import org.cs2c.vcenter.dialog.ElementSelector;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.DirectiveMeta;
import org.cs2c.vcenter.metadata.MetaManager;
import org.cs2c.vcenter.views.models.HttpElement;
import org.cs2c.vcenter.views.models.TreeElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class BlockInput extends Composite {
	
	private org.eclipse.swt.widgets.List ctlList;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	boolean flagChanged;
	
	private TreeElement input;
	
	private MiddlewareFactory middleware = null;
	private String blockName = null;
	private String blockType = null;
	private String blockGroup = null;
	private String blockSubGroup = null;
	private String blockOutNames = null;
	private String blockIndex = null;
	private Configurator orc = null;
	
	private String blockMetaType = null;
	
	final String strBlockMark = " {...}";

	//Metadata
	private BlockMeta bMeta = new BlockMeta();
	
	//Server Configuration Information
	private Block block = new RecBlock();
	private java.util.List<Directive> directives = new ArrayList<Directive>();
	private java.util.List<Block> blocks = new ArrayList<Block>();
	
	public BlockInput(Composite parent, int style, BlockElementInfo bcInfo, String blkSubGroup) {
		super(parent, style);
		
		this.middleware = bcInfo.getMiddleware();
		this.blockName = bcInfo.getBlockName();
		this.blockType = bcInfo.getBlockType();
		this.blockOutNames = bcInfo.getBlockOutNames();
		this.blockIndex = bcInfo.getBlockIndex();
		this.orc = this.middleware.getConfigurator();
		this.bMeta = bcInfo.getBlockMeta();
		this.blockSubGroup = blkSubGroup;
		
		String sType[] = blockType.split(" ");
		this.blockMetaType = sType[0];
		
		this.block = bcInfo.getBlock();
		
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
				
				int count = 0;
				int i = 0;
				
				if(directives != null && !directives.isEmpty())
				{
					count = directives.size();
					i = 0;
					while(i < count)
					{
						String name = directives.get(i).getName();
						String nameslice[] = name.split(" ");
						String typeInName = nameslice[0];
						elementNames.add(elementNames.size(), typeInName);
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
						elementNames.add(elementNames.size(), typeInName+strBlockMark);
						i++;
					}
				}
				
				if(directiveMetas != null && !directiveMetas.isEmpty())
				{
					count = directiveMetas.size();
					i = 0;
					while(i<count)
					{
						String dirctvname = directiveMetas.get(i).getName();
						if(!elementNames.contains(dirctvname))
						{
							elementNames.add(elementNames.size(), dirctvname);
						}
						else if(!directiveMetas.get(i).getReused())
						{
							elementNames.remove(dirctvname);
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
						if(!elementNames.contains(blkname+strBlockMark))
						{
							elementNames.add(elementNames.size(), blkname+strBlockMark);
						}
						else if(!blockMetas.get(i).getReused())
						{
							elementNames.remove(blkname+strBlockMark);
						}
						i++;
					}
				}
				
				ElementSelector eleSelector = new ElementSelector(new Shell(), elementNames);
				
				if(Window.OK == eleSelector.open())
				{
					String selEleName = eleSelector.getSelectedElementName();
					
					int len = selEleName.length();
					if(len > 6 && selEleName.substring(selEleName.length()-6,selEleName.length()).equals(strBlockMark))
					{
						try {
							Block newblk;
							newblk = orc.newBlock();
							newblk.setName(selEleName.substring(0, selEleName.length()-6));
							block.addBlock(newblk);
						} catch (RemoteException e1) {
							MessageDialog.openError(getShell(), "Error", e1.getMessage());
							e1.printStackTrace();
						}
						
						UpdateListCtl();
						flagChanged = true;
					}
					else
					{
						DirectiveMeta dMeta = null;
						count = directiveMetas.size();
						i = 0;
						while(i < count)
						{
							dMeta = directiveMetas.get(i);
							if(selEleName.equals(dMeta.getName()))
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
						
						DirectiveInput dInput = new DirectiveInput(new Shell(), null, dMeta);
						
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
					
					BlockMeta bMeta = null;
					int count = blockMetas.size();
					int i = 0;
					while(i < count)
					{
						bMeta = blockMetas.get(i);
						if(strSelEleBaseType.equals(bMeta.getName()))
						{
							break;
						}
						bMeta = null;
						i++;
					}
					if(bMeta == null)
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
					
					Block curBlk = new RecBlock();
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
					}
					if(wIndex != indexSubBlock)
					{
						MessageDialog.openError(getShell(), "Error", "Can not find the Block!");
						return;
					}
					
					//HttpElement newInput = new HttpElement(null);
					//newInput.init(blockName, blockType , "0", blockOutNames, middleware);
					
					//String subOutNames = blockOutNames + "|" + strSelEleBaseType + ":" + Integer.toString(input.getBlockIndex());
					//http:0|server:2|location:1
					//location
					//http:0|server:2
					
					
					BlockElementInfo bcInfo = new BlockElementInfo();
					bcInfo.setBlock(curBlk);
					bcInfo.setBlockName(curBlk.getName());
					bcInfo.setBlockType(strSelEleBaseType);
					bcInfo.setBlockIndex(Integer.toString(indexSubBlock));
					bcInfo.setBlockMeta(bMeta);
					
					String subOutNames = blockOutNames + "|" + strSelEleBaseType + ":" + blockIndex;
					bcInfo.setBlockOutNames(subOutNames);
					
					bcInfo.setMiddleware(middleware);
					
					BlockConfigDialog bcDlg = new BlockConfigDialog(new Shell(), bcInfo);
					
					if(Window.OK == bcDlg.open())
					{
						//block.ModifySubBlock(oldBlock, bcDlg.getNewBlock);
						//block.deleteElement(oldDirct);
						//block.addDirective(newDirct);
						
						UpdateListCtl();
						
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
					
					DirectiveInput dInput = new DirectiveInput(new Shell(), oldDirct, dMeta);//oldDirct
					
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
					MessageDialog.openWarning(new Shell(),"Warning","Please select an option!");
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
					Block oldBlock = null;
					if(blocks!=null && !blocks.isEmpty())
					{
						count = blocks.size();
						i = 0;
						while(i < count)
						{
							oldBlock = blocks.get(i);
							if(oldBlock.getName() == selEle)
							{
								break;
							}
							oldBlock = null;
							i++;
						}
						if(oldBlock == null)
						{
							return;
						}
					}
					
					block.deleteElement(oldBlock);
				}
				else
				{
					Directive oldDirct = null;
					if(directives!=null && !directives.isEmpty())
					{
						count = directives.size();
						i = 0;
						while(i < count)
						{
							oldDirct = directives.get(i);
							if(selEle == oldDirct.getName())
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
						block.deleteElement(oldDirct);
					}
				}
				
				UpdateListCtl();
				
				flagChanged = true;
			}
		});
		
		this.btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnDelete.setText("   Delete   ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		UpdateListCtl();
	}
	
	public BlockInput(Composite parent, int style, TreeElement input, Block blk, BlockMeta blkMeta, String blkSubGroup) {
		super(parent, style);
		
		this.input = input;
		this.middleware = input.getMiddlewareFactory();
		this.blockName = input.getName();
		this.blockType = input.getBlocktype();
		this.blockOutNames = input.getOuterBlockNames();
		this.blockIndex = input.getBlockIndex();
		this.orc = this.middleware.getConfigurator();
		this.bMeta = blkMeta;
		this.blockSubGroup = blkSubGroup;
		
		String sType[] = blockType.split(" ");
		this.blockMetaType = sType[0];
		
		this.block = blk;
		
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
				
				int count = 0;
				int i = 0;
				
				if(directives != null && !directives.isEmpty())
				{
					count = directives.size();
					i = 0;
					while(i < count)
					{
						String name = directives.get(i).getName();
						String nameslice[] = name.split(" ");
						String typeInName = nameslice[0];
						elementNames.add(elementNames.size(), typeInName);
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
						elementNames.add(elementNames.size(), typeInName+strBlockMark);
						i++;
					}
				}
				
				if(directiveMetas != null && !directiveMetas.isEmpty())
				{
					count = directiveMetas.size();
					i = 0;
					while(i<count)
					{
						String dirctvname = directiveMetas.get(i).getName();
						if(!elementNames.contains(dirctvname))
						{
							elementNames.add(elementNames.size(), dirctvname);
						}
						else if(!directiveMetas.get(i).getReused())
						{
							elementNames.remove(dirctvname);
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
						if(!elementNames.contains(blkname+strBlockMark))
						{
							elementNames.add(elementNames.size(), blkname+strBlockMark);
						}
						else if(!blockMetas.get(i).getReused())
						{
							elementNames.remove(blkname+strBlockMark);
						}
						i++;
					}
				}
				
				ElementSelector eleSelector = new ElementSelector(new Shell(), elementNames);
				
				if(Window.OK == eleSelector.open())
				{
					String selEleName = eleSelector.getSelectedElementName();
					
					int len = selEleName.length();
					if(len > 6 && selEleName.substring(selEleName.length()-6,selEleName.length()).equals(strBlockMark))
					{
						try {
							Block newblk;
							newblk = orc.newBlock();
							newblk.setName(selEleName.substring(0, selEleName.length()-6));
							block.addBlock(newblk);
						} catch (RemoteException e1) {
							MessageDialog.openError(getShell(), "Error", e1.getMessage());
							e1.printStackTrace();
						}
						
						UpdateListCtl();
						flagChanged = true;
					}
					else
					{
						DirectiveMeta dMeta = null;
						count = directiveMetas.size();
						i = 0;
						while(i < count)
						{
							dMeta = directiveMetas.get(i);
							if(selEleName.equals(dMeta.getName()))
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
						
						DirectiveInput dInput = new DirectiveInput(new Shell(), null, dMeta);
						
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
					
					BlockMeta bMeta = null;
					int count = blockMetas.size();
					int i = 0;
					while(i < count)
					{
						bMeta = blockMetas.get(i);
						if(strSelEleBaseType.equals(bMeta.getName()))
						{
							break;
						}
						bMeta = null;
						i++;
					}
					if(bMeta == null)
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
					
					Block curBlk = new RecBlock();
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
					}
					if(wIndex != indexSubBlock)
					{
						MessageDialog.openError(getShell(), "Error", "Can not find the Block!");
						return;
					}
					
					//HttpElement newInput = new HttpElement(null);
					//newInput.init(blockName, blockType , "0", blockOutNames, middleware);
					
					//String subOutNames = blockOutNames + "|" + strSelEleBaseType + ":" + Integer.toString(input.getBlockIndex());
					//http:0|server:2|location:1
					//location
					//http:0|server:2
					
					
					BlockElementInfo bcInfo = new BlockElementInfo();
					bcInfo.setBlock(curBlk);
					bcInfo.setBlockName(curBlk.getName());
					bcInfo.setBlockType(strSelEleBaseType);
					bcInfo.setBlockIndex(Integer.toString(indexSubBlock));
					bcInfo.setBlockMeta(bMeta);
					
					String subOutNames = blockOutNames + "|" + strSelEleBaseType + ":" + blockIndex;
					bcInfo.setBlockOutNames(subOutNames);
					
					bcInfo.setMiddleware(middleware);
					
					BlockConfigDialog bcDlg = new BlockConfigDialog(new Shell(), bcInfo);
					
					if(Window.OK == bcDlg.open())
					{
						//block.ModifySubBlock(oldBlock, bcDlg.getNewBlock);
						//block.deleteElement(oldDirct);
						//block.addDirective(newDirct);
						
						UpdateListCtl();
						
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
					
					DirectiveInput dInput = new DirectiveInput(new Shell(), oldDirct, dMeta);//oldDirct
					
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
					MessageDialog.openWarning(new Shell(),"Warning","Please select an option!");
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
					Block oldBlock = null;
					if(blocks!=null && !blocks.isEmpty())
					{
						count = blocks.size();
						i = 0;
						while(i < count)
						{
							oldBlock = blocks.get(i);
							if(oldBlock.getName() == selEle)
							{
								break;
							}
							oldBlock = null;
							i++;
						}
						if(oldBlock == null)
						{
							return;
						}
					}
					
					block.deleteElement(oldBlock);
				}
				else
				{
					Directive oldDirct = null;
					if(directives!=null && !directives.isEmpty())
					{
						count = directives.size();
						i = 0;
						while(i < count)
						{
							oldDirct = directives.get(i);
							if(selEle == oldDirct.getName())
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
						block.deleteElement(oldDirct);
					}
				}
				
				UpdateListCtl();
				
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
		
		this.directives.clear();
		this.blocks.clear();
		try {
			this.directives = this.block.getDirectives();
			this.blocks = this.block.getBlocks();
		} catch (RemoteException e1) {
			e1.printStackTrace();
			MessageDialog.openError(new Shell(), "Error", e1.getMessage());
			return;
		}
		
		ctlList.removeAll();
		
		java.util.List<DirectiveMeta> subDirectiveMetas = bMeta.getDirectiveMeta(blockSubGroup);
		java.util.List<BlockMeta> subBlockMetas = bMeta.getBlockMeta(blockSubGroup);
		
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
				String name = directives.get(i).getName();
				String nameslice[] = name.split(" ");
				String typeInName = nameslice[0];
				if(subDirectiveTypes.contains(typeInName))
				{
					ctlList.add(directives.get(i).toString().trim());
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
				String name = blocks.get(i).getName();
				String nameslice[] = name.split(" ");
				String typeInName = nameslice[0];
				if(subBlockTypes.contains(typeInName))
				{
					ctlList.add(name+strBlockMark);
				}
				i++;
			}
		}
		
	}
	
	public List getList()
	{
		return ctlList;
	}
	public Button getAddButton()
	{
		return btnAdd;
	}
	public Button getEditButton()
	{
		return btnEdit;
	}
	public Button getDeleteButton()
	{
		return btnDelete;
	}
	
	public boolean isChanged()
	{
		return flagChanged;
	}
	public void setChangedFlag(boolean flag)
	{
		flagChanged = flag;
	}
	
	public Block getBlock()
	{
		return this.block;
	}
	
	public void benchmark()
	{
		
	}
	
	public void setMeta(BlockMeta meta)
	{
		bMeta = meta;
	}
	
}
