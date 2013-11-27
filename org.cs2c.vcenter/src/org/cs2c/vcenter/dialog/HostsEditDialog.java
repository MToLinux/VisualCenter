package org.cs2c.vcenter.dialog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cs2c.vcenter.metadata.DOMParser;
import org.cs2c.vcenter.metadata.HostInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class HostsEditDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	// private int flag=0;
	private String selectItem = null;
	private DOMParser hostXml = null;
	// private List<String> hostInfo=null;
	private HostInfo hostInfo = null;
	Button btnOk = null;
	Button btnCancle = null;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public HostsEditDialog(Shell parentShell) {
		super(parentShell);
	}

	public HostsEditDialog(Shell parentShell, String para, DOMParser domPara) {
		super(parentShell);
		selectItem = para;
		hostXml = domPara;
		hostInfo = new HostInfo();

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		Group grpHostinfo = new Group(container, SWT.NONE);
		GridData gd_grpHostinfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpHostinfo.widthHint = 323;
		grpHostinfo.setLayoutData(gd_grpHostinfo);
		grpHostinfo.setText("HostInfo");
		grpHostinfo.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel.setText("*Host:");

		text = new Text(grpHostinfo, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 229;
		text.setLayoutData(gd_text);
		
				Label lblNewLabel_1 = new Label(grpHostinfo, SWT.NONE);
				lblNewLabel_1.setText("*Username:");

		text_1 = new Text(grpHostinfo, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 229;
		text_1.setLayoutData(gd_text_1);
		
				Label lblNewLabel_2 = new Label(grpHostinfo, SWT.NONE);
				lblNewLabel_2.setText("*Password:");

		text_2 = new Text(grpHostinfo, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_2.widthHint = 237;
		text_2.setLayoutData(gd_text_2);

		Group grpMiddlewareinfo = new Group(container, SWT.NONE);
		GridData gd_grpMiddlewareinfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpMiddlewareinfo.widthHint = 325;
		grpMiddlewareinfo.setLayoutData(gd_grpMiddlewareinfo);
		grpMiddlewareinfo.setText("Middleware Info");
		grpMiddlewareinfo.setLayout(new GridLayout(2, false));

		Label lblmiddleware = new Label(grpMiddlewareinfo, SWT.NONE);
		lblmiddleware.setText("*Middleware:");

		text_4 = new Text(grpMiddlewareinfo, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_text_4.widthHint = 228;
		text_4.setLayoutData(gd_text_4);
		
				Label lblhome = new Label(grpMiddlewareinfo, SWT.NONE);
				lblhome.setText("*Home:");

		text_5 = new Text(grpMiddlewareinfo, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Group grpMiddlewareManagerInfo = new Group(container, SWT.NONE);
		GridData gd_grpMiddlewareManagerInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpMiddlewareManagerInfo.widthHint = 325;
		grpMiddlewareManagerInfo.setLayoutData(gd_grpMiddlewareManagerInfo);
		grpMiddlewareManagerInfo.setText("Middleware Manager Info");
		grpMiddlewareManagerInfo.setLayout(new GridLayout(2, false));

		Label lblStatusPath = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblStatusPath.setText("Status Path:");

		text_6 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		GridData gd_text_6 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_6.widthHint = 228;
		text_6.setLayoutData(gd_text_6);
		
				Label lblUsername = new Label(grpMiddlewareManagerInfo, SWT.NONE);
				lblUsername.setText("Username:");

		text_7 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		GridData gd_text_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_7.widthHint = 241;
		text_7.setLayoutData(gd_text_7);
		
				Label lblPassword = new Label(grpMiddlewareManagerInfo, SWT.NONE);
				lblPassword.setText("Password:");

		text_8 = new Text(grpMiddlewareManagerInfo, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_8.widthHint = 240;
		text_8.setLayoutData(gd_text_8);
								
								Composite composite = new Composite(container, SWT.NONE);
								GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
								gd_composite.widthHint = 329;
								composite.setLayoutData(gd_composite);
								
								Button button = new Button(composite, SWT.NONE);
								button.setBounds(61, 10, 54, 27);
								button.setText("   OK   ");
								
										btnCancle = new Button(composite, SWT.NONE);
										btnCancle.setBounds(214, 10, 59, 27);
										btnCancle.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseDown(MouseEvent e) {
												setReturnCode(CANCEL);
												close();
											}
										});
										btnCancle.setText("CANCLE");

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setHostName(text.getText());
				//contentIsValid();
			}
		});
		text_1.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setUserName(text_1.getText());
			}
		});

		text_2.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setPassWord(text_2.getText());
			}
		});

		text_4.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setMiddleware(text_4.getText());
			}
		});

		text_5.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setHome(text_5.getText());
			}
		});

		text_6.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setStatusPath(text_6.getText());
			}
		});

		text_7.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setManagerUserName(text_7.getText());
			}
		});
		text_8.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				hostInfo.setManagerPassWord(text_8.getText());
			}
		});

		if (!selectItem.equals("")) {
			hostInfo = hostXml.getHostInfo(selectItem);
			// System.out.println(hostInfo.get(0));
			text.setText(hostInfo.getHostName());
			text_1.setText(hostInfo.getUserName());
			text_2.setText(hostInfo.getPassWord());
			text_4.setText(hostInfo.getMiddleware());
			text_5.setText(hostInfo.getHome());
			text_6.setText(hostInfo.getStatusPath());
			text_7.setText(hostInfo.getManagerUserName());
			text_8.setText(hostInfo.getManagerPassWord());
		} else {
			text.setText("");
			text_1.setText("");
			text_2.setText("");
			text_4.setText("");
			text_5.setText("");
			text_6.setText("");
			text_7.setText("");
			text_8.setText("");
		}
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(356, 422);
	}

	@Override  
    protected void configureShell(Shell newShell) {  
        // TODO Auto-generated method stub  
        super.configureShell(newShell);  
        newShell.setText("Host Information Edit");  
    }  
	
	private boolean contentIsValid() {
		
		if(text.getText().isEmpty())
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "HostName is empty");
			return false;
		}
		if(text_1.getText().isEmpty())
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Username is empty");
			return false;
		}
		if(text_2.getText().isEmpty())
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Password is empty");
			return false;
		}
		if(text_4.getText().isEmpty())
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Middleware is empty");
			return false;
		}
		if(text_5.getText().isEmpty())
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Home is empty");
			return false;
		}
		if(!(isIPValid(text.getText())||isHostNameValid(text.getText())))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "HostName is invalid");
			return false;
		}
		if(!isUserNameValid(text_1.getText()))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "UserName is invalid");
			return false;
		}
		if(!isPassWordValid(text_2.getText()))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "PassWord is invalid");
			return false;
		}
		if(!isMiddlewareNameValid(text_4.getText()))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "MiddlewareName is invalid");
			return false;
		}
		if(!isPathValid(text_5.getText()))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Path is invalid");
			return false;
		}
		if((!text_6.getText().isEmpty())&&(!isPathValid(text_6.getText())))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "Path for manager is invalid");
			return false;
		}
		if((!text_7.getText().isEmpty())&&(!isUserNameValid(text_7.getText())))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "UserName for manager is invalid");
			return false;
		}
		if((!text_8.getText().isEmpty())&&(!isPassWordValid(text_8.getText())))
		{
			MessageDialog.openWarning(getParentShell(), "Warning", "PassWord for manager is invalid");
			return false;
		}
		return true;
		
	}
	
	private boolean isIPValid(String str)
	{
		   /*
			Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
			*/
		return true;
	}
	
	private boolean isHostNameValid(String str)
	{
		/*
		Pattern pattern = Pattern.compile("\b(([a-zA-Z0-9]\\w{0,61}?[a-zA-Z0-9]|[a-zA-Z0-9]).){1,2}(com|edu|gov|int|mil|net|org|biz|info|name|museum|coop|aero|[a-z][a-z])(.[a-z][a-z]){0,1}\b");		
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
		*/
		return true;
	}
	
	private boolean isUserNameValid(String str)
	{
		Pattern pattern = Pattern.compile("[A-Za-z_][A-Za-z0-9_.-]*[A-Za-z0-9_.$-]?");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	private boolean isPathValid(String str)
	{
		//Pattern pattern = Pattern.compile( "^/([0-9a-zA-Z_-]+/$)+");//(\/([0-9a-zA-Z]+))+
		Pattern pattern = Pattern.compile( "(\\/([0-9a-zA-Z]+))+/");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	private boolean isPassWordValid(String str)
	{
		Pattern pattern = Pattern.compile("^[a-zA-Z]\\w*$" );				
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	private boolean isMiddlewareNameValid(String str)
	{
		/*
		Pattern pattern = Pattern.compile("[A-Za-z][A-Za-z0-9_.]*[A-Za-z0-9_.$-]?" );		
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
		*/
		return true;
	}
}
