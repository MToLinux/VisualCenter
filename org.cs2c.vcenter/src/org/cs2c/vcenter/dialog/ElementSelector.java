package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.cs2c.vcenter.Activator;
import org.cs2c.vcenter.metadata.BlockMeta;
import org.cs2c.vcenter.metadata.DirectiveMeta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

public class ElementSelector extends FilteredItemsSelectionDialog {
	
	//类似于Eclipse中接口和类选择时的选择器，允许搜索。
	
	private List<String> directiveNames = new ArrayList<String>();
	private String strSelectedElement = null;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ElementSelector(Shell shell) {
		super(shell);
		
		setTitle("Element Selector");
		setMessage("Select an item to open(? = any character, *=any string), enter '?' to show all options:");
		//setSelectionHistory(new ResourceSelectionHistory());
		
		//Just for test!!! begin
		directiveNames.add("aaa");
		directiveNames.add("bbb");
		directiveNames.add("ccc");
		directiveNames.add("ddd");
		directiveNames.add("eee");
		directiveNames.add("fffd");
		//Just for test!!! end
		
		setInitialPattern("?", FULL_SELECTION);
	}
	
	public ElementSelector(Shell shell, BlockMeta meta) {
		super(shell);
		
		setTitle("Element Selector");
		setMessage("Select an item to open (? = any character, *=any string), enter '?' to show all options:");
		//setSelectionHistory(new ResourceSelectionHistory());
		
		List<DirectiveMeta> directives = new ArrayList<DirectiveMeta>();
		directives = meta.getDirectiveMeta(meta.getGroup());
		
		int count = directives.size();
		int i = 0;
		while(i<count)
		{
			directiveNames.add(directives.get(i).getName());
		}
		//Just for test!!! begin
		directiveNames.add("aaa");
		directiveNames.add("bbb");
		directiveNames.add("ccc");
		directiveNames.add("ddd");
		directiveNames.add("eee");
		directiveNames.add("fffe");
		//Just for test!!! end
		
		setInitialPattern("?", FULL_SELECTION);
	}
	public ElementSelector(Shell shell, List<String> directiveNames) {
		super(shell);
		
		setTitle("Element Selector");
		setMessage("Select an item to open(? = any character, *=any string), enter '?' to show all options:");
		//setSelectionHistory(new ResourceSelectionHistory());
		this.directiveNames = directiveNames;
		
		//Just for test!!! begin
		directiveNames.add("aaa");
		directiveNames.add("bbb");
		directiveNames.add("ccc");
		directiveNames.add("ddd");
		directiveNames.add("eee");
		directiveNames.add("ffff");
		//Just for test!!! end
		
		setInitialPattern("?", FULL_SELECTION);
	}
	
	private class ResourceSelectionHistory extends SelectionHistory {

		@Override
		protected Object restoreItemFromMemento(IMemento memento) {
			return null;
		}
		@Override
		protected void storeItemToMemento(Object item, IMemento memento) {
			
		}
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {

		return null;
	}

	private static final String DIALOG_SETTINGS = "ElementSelectorSettings";	

	@Override
	protected IDialogSettings getDialogSettings() 
	{

		IDialogSettings settings = Activator.getDefault().getDialogSettings()
				.getSection(DIALOG_SETTINGS);
		if (settings == null) 
		{
			settings = Activator.getDefault().getDialogSettings()
					.addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {

		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {

		return new ItemsFilter() 
		{
	         public boolean matchItem(Object item) 
	         {
	            return matches(item.toString());
	         }
	         public boolean isConsistentItem(Object item) 
	         {
	            return true;
	         }
		};
	}

	@Override
	protected Comparator<String> getItemsComparator() 
	{

		return new Comparator<String>() 
		{
	         public int compare(String arg0, String arg1)
	         {
        		 return arg0.toString().compareTo(arg1.toString());
	         }
	    };
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException 
	{
		progressMonitor.beginTask("Searching", directiveNames.size()); //$NON-NLS-1$
		for (Iterator<String> iter = directiveNames.iterator(); iter.hasNext();) 
		{
			contentProvider.add(iter.next(), itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();
	}

	@Override
	public String getElementName(Object item) {

		return item.toString();
	}
	
	@Override
	protected void okPressed()
	{
		super.okPressed();
	}
	
	@Override
	protected void handleSelected(StructuredSelection selection)
	{
		super.handleSelected(selection);
		strSelectedElement = selection.toString();
		strSelectedElement = strSelectedElement.substring(1, strSelectedElement.length()-1);
	}
	
	public String getSelectedElementName()
	{
		return strSelectedElement;
	}

}
