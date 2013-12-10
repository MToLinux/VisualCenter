package org.cs2c.vcenter.dialog;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cs2c.vcenter.metadata.HostInfo;
import org.cs2c.vcenter.metadata.HostManager;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

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
	private HostManager hostXml = null;
	// private List<String> hostInfo=null;
	private HostInfo newHostInfo = null;
	private HostInfo oldHostInfo = null;
	Button btn_Ok = null;
	Button btnCancle = null;
	private int flag = 0;// add:1;edit:2;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public HostsEditDialog(Shell parentShell) {
		super(parentShell);
	}

	public HostsEditDialog(Shell parentShell, String para, HostManager domPara) {
		super(parentShell);
		selectItem = para;
		hostXml = domPara;
		oldHostInfo = new HostInfo();
		newHostInfo = new HostInfo();

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
		GridData gd_grpHostinfo = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_grpHostinfo.widthHint = 323;
		grpHostinfo.setLayoutData(gd_grpHostinfo);
		grpHostinfo.setText("HostInfo");
		grpHostinfo.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel.setText("*Host:");

		text = new Text(grpHostinfo, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1);
		gd_text.widthHint = 229;
		text.setLayoutData(gd_text);

		Label lblNewLabel_1 = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel_1.setText("*Username:");

		text_1 = new Text(grpHostinfo, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_1.widthHint = 229;
		text_1.setLayoutData(gd_text_1);

		Label lblNewLabel_2 = new Label(grpHostinfo, SWT.NONE);
		lblNewLabel_2.setText("*Password:");

		text_2 = new Text(grpHostinfo, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_2.widthHint = 237;
		text_2.setLayoutData(gd_text_2);

		Group grpMiddlewareinfo = new Group(container, SWT.NONE);
		GridData gd_grpMiddlewareinfo = new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1);
		gd_grpMiddlewareinfo.widthHint = 325;
		grpMiddlewareinfo.setLayoutData(gd_grpMiddlewareinfo);
		grpMiddlewareinfo.setText("Middleware Info");
		grpMiddlewareinfo.setLayout(new GridLayout(2, false));

		Label lblmiddleware = new Label(grpMiddlewareinfo, SWT.NONE);
		lblmiddleware.setText("*Middleware:");

		text_4 = new Text(grpMiddlewareinfo, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1);
		gd_text_4.widthHint = 228;
		text_4.setLayoutData(gd_text_4);

		Label lblhome = new Label(grpMiddlewareinfo, SWT.NONE);
		lblhome.setText("*Home:");

		text_5 = new Text(grpMiddlewareinfo, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));

		Group grpMiddlewareManagerInfo = new Group(container, SWT.NONE);
		GridData gd_grpMiddlewareManagerInfo = new GridData(SWT.LEFT,
				SWT.CENTER, false, false, 1, 1);
		gd_grpMiddlewareManagerInfo.widthHint = 325;
		grpMiddlewareManagerInfo.setLayoutData(gd_grpMiddlewareManagerInfo);
		grpMiddlewareManagerInfo.setText("Middleware Manager Info");
		grpMiddlewareManagerInfo.setLayout(new GridLayout(2, false));

		Label lblStatusPath = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblStatusPath.setText("Status Path:");

		text_6 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		GridData gd_text_6 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_6.widthHint = 228;
		text_6.setLayoutData(gd_text_6);

		Label lblUsername = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblUsername.setText("Username:");

		text_7 = new Text(grpMiddlewareManagerInfo, SWT.BORDER);
		GridData gd_text_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_text_7.widthHint = 241;
		text_7.setLayoutData(gd_text_7);

		Label lblPassword = new Label(grpMiddlewareManagerInfo, SWT.NONE);
		lblPassword.setText("Password:");

		text_8 = new Text(grpMiddlewareManagerInfo, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_text_8.widthHint = 240;
		text_8.setLayoutData(gd_text_8);

		Composite composite = new Composite(container, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 329;
		composite.setLayoutData(gd_composite);

		btn_Ok = new Button(composite, SWT.NONE);
		btn_Ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (flag == 1) {
					if (contentIsValid()) {
						if (hostXml.hasHostInfo(newHostInfo) == false) {
					
							try {
								hostXml.insertHostInfo(FileLocator.toFileURL(Platform.getBundle("org.cs2c.vcenter").getEntry("")).getPath()+"conf/host.xml", newHostInfo);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								MessageDialog.openInformation(getShell(), "IOException", e1.getMessage());
								return;
							}
							setReturnCode(OK);
							close();
						} else {
							MessageDialog
									.openError(
											getParentShell(),
											"Error",
											"The unique identification has been used, please reset the host name or the middleware name!");
							return;
							// setReturnCode(CANCEL);
						}

					}

				} else if (flag == 2) {
					if (contentIsValid()) {
						int result;
						try {
							result = hostXml.modifyHostInfo(FileLocator.toFileURL(Platform.getBundle("org.cs2c.vcenter").getEntry("")).getPath()+"conf/host.xml",
									oldHostInfo, newHostInfo);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							MessageDialog.openInformation(getShell(),
									"IOException", e1.getMessage());
							return;
						}
						if (result == 3) {
							MessageDialog
									.openError(
											getParentShell(),
											"Error",
											"The unique identification has been used, please reset the host name or the middleware name!");
							return;

						} else if (result == 1) {
							MessageDialog.openInformation(getParentShell(),
									"Note", "Modify successfully!");
							setReturnCode(OK);
							close();
						} else if (result == 2) {

							MessageDialog.openError(getParentShell(), "Error",
									"Modify failed!");
							setReturnCode(CANCEL);
							close();
						} else if (result == 0) {
							MessageDialog.openError(getParentShell(), "Error",
									"Find host element failed!");
							setReturnCode(CANCEL);
							close();

						}

					}
				}

			}
		});
		btn_Ok.setBounds(61, 10, 54, 27);
		btn_Ok.setText("   OK   ");

		btnCancle = new Button(composite, SWT.NONE);
		btnCancle.setBounds(214, 10, 59, 27);
		btnCancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
		btnCancle.setText("CANCLE");

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setHostName(text.getText());
				// contentIsValid();
			}
		});
		text_1.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setUserName(text_1.getText());
			}
		});

		text_2.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setPassWord(text_2.getText());
			}
		});

		text_4.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setMiddleware(text_4.getText());
			}
		});

		text_5.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setHome(text_5.getText());
			}
		});

		text_6.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setStatusPath(text_6.getText());
			}
		});

		text_7.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setManagerUserName(text_7.getText());
			}
		});
		text_8.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				newHostInfo.setManagerPassWord(text_8.getText());
			}
		});

		if (!selectItem.equals("")) {
			oldHostInfo = hostXml.getHostInfo(selectItem);
			newHostInfo.setHostName(oldHostInfo.getHostName());
			newHostInfo.setUserName(oldHostInfo.getUserName());
			newHostInfo.setPassWord(oldHostInfo.getPassWord());
			newHostInfo.setMiddleware(oldHostInfo.getMiddleware());
			newHostInfo.setHome(oldHostInfo.getHome());
			newHostInfo.setStatusPath(oldHostInfo.getStatusPath());
			newHostInfo.setManagerUserName(oldHostInfo.getManagerUserName());
			newHostInfo.setManagerPassWord(oldHostInfo.getManagerPassWord());
			// System.out.println(hostInfo.get(0));
			text.setText(oldHostInfo.getHostName());
			text_1.setText(oldHostInfo.getUserName());
			text_2.setText(oldHostInfo.getPassWord());
			text_4.setText(oldHostInfo.getMiddleware());
			text_5.setText(oldHostInfo.getHome());
			text_6.setText(oldHostInfo.getStatusPath());
			text_7.setText(oldHostInfo.getManagerUserName());
			text_8.setText(oldHostInfo.getManagerPassWord());
			flag = 2;

		} else {
			text.setText("");
			text_1.setText("");
			text_2.setText("");
			text_4.setText("");
			text_5.setText("");
			text_6.setText("");
			text_7.setText("");
			text_8.setText("");
			flag = 1;
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

		if (text.getText().isEmpty()) {

			MessageDialog.openError(getParentShell(), "Error",
					"Host name is empty");
			return false;
		}
		if (text_1.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error",
					"Username is empty");
			return false;
		}
		if (text_2.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error",
					"Password is empty");
			return false;
		}
		if (text_4.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error",
					"Middleware name is empty");
			return false;
		}
		if (text_5.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error", "Home path is empty");
			return false;
		}
		if (!(isIPValid(text.getText()) || isHostNameValid(text.getText()))) {
			MessageDialog.openError(getParentShell(), "Error",
					"Host name is invalid");
			return false;
		}
		if (!isUserNameValid(text_1.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"UserName is invalid");
			return false;
		}
		if (!isPassWordValid(text_2.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"PassWord is invalid");
			return false;
		}
		if (!isMiddlewareNameValid(text_4.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"Middleware name is invalid");
			return false;
		}
		if (!isPathValid(text_5.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"Home path is invalid");
			return false;
		}
		if ((!text_6.getText().isEmpty()) && (!isPathValid(text_6.getText()))) {
			MessageDialog.openError(getParentShell(), "Error",
					"Status path for middleware manager is invalid");
			return false;
		}
		if ((!text_7.getText().isEmpty())
				&& (!isUserNameValid(text_7.getText()))) {
			MessageDialog.openError(getParentShell(), "Error",
					"UserName for middleware manager is invalid");
			return false;
		}
		if ((!text_8.getText().isEmpty())
				&& (!isPassWordValid(text_8.getText()))) {
			MessageDialog.openError(getParentShell(), "Error",
					"PassWord for middleware manager is invalid");
			return false;
		}
		return true;

	}

	private boolean isIPValid(String str) {
		/*
		 * Pattern pattern =
		 * Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"); Matcher
		 * matcher = pattern.matcher(str); return matcher.matches();
		 */
		return true;
	}

	private boolean isHostNameValid(String str) {
		/*
		 * Pattern pattern = Pattern.compile(
		 * "\b(([a-zA-Z0-9]\\w{0,61}?[a-zA-Z0-9]|[a-zA-Z0-9]).){1,2}(com|edu|gov|int|mil|net|org|biz|info|name|museum|coop|aero|[a-z][a-z])(.[a-z][a-z]){0,1}\b"
		 * ); Matcher matcher = pattern.matcher(str); return matcher.matches();
		 */
		return true;
	}

	private boolean isUserNameValid(String str) {
		Pattern pattern = Pattern
				.compile("[A-Za-z0-9_.][A-Za-z0-9_.-]*[A-Za-z0-9_.$-]?");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private boolean isPathValid(String str) {
		// Pattern pattern = Pattern.compile(
		// "^/([0-9a-zA-Z_-]+/$)+");//(\/([0-9a-zA-Z]+))+
		Pattern pattern = Pattern
				.compile("(\\/([\\x21-\\x2e\\x30-\\x7e]{1,255}))+[/]?");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private boolean isPassWordValid(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{4,}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private boolean isMiddlewareNameValid(String str) {
		/*
		 * Pattern pattern =
		 * Pattern.compile("[A-Za-z][A-Za-z0-9_.]*[A-Za-z0-9_.$-]?" ); Matcher
		 * matcher = pattern.matcher(str); return matcher.matches();
		 */
		return true;
	}
}
