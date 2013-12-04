package org.cs2c.vcenter.dialog;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cs2c.nginlib.AuthInfo;
import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.vcenter.metadata.HostInfo;
import org.cs2c.vcenter.metadata.HostManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.events.SelectionAdapter;

public class MiddlewareInstallerDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;

	private HostManager hostXml = null;

	// private List<String> hostInfo=null;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public MiddlewareInstallerDialog(Shell parentShell) {
		super(parentShell);
		hostXml = HostManager.getInstance();
		
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(3, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("*Host:");

		text = new Text(container, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 2,
				1);
		gd_text.widthHint = 241;
		text.setLayoutData(gd_text);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText("*Username:");

		text_1 = new Text(container, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1);
		gd_text_1.widthHint = 241;
		text_1.setLayoutData(gd_text_1);

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setText("*Password:");

		text_2 = new Text(container, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1);
		gd_text_2.widthHint = 241;
		text_2.setLayoutData(gd_text_2);

		Label lblNewLabel_3 = new Label(container, SWT.NONE);
		lblNewLabel_3.setText("*Middleware:");

		text_3 = new Text(container, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1);
		gd_text_3.widthHint = 318;
		text_3.setLayoutData(gd_text_3);

		Label lblNewLabel_4 = new Label(container, SWT.NONE);
		lblNewLabel_4.setText("*Home:");

		text_4 = new Text(container, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.FILL, false, false, 2,
				1);
		gd_text_4.widthHint = 311;
		text_4.setLayoutData(gd_text_4);

		Label lblNewLabel_5 = new Label(container, SWT.NONE);
		lblNewLabel_5.setText("*Install File:");

		text_5 = new Text(container, SWT.BORDER);
		GridData gd_text_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_text_5.widthHint = 241;
		text_5.setLayoutData(gd_text_5);

		Button btnNewButton = new Button(container, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_btnNewButton.widthHint = 58;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				FileDialog dialog = new FileDialog(shell);
				dialog.setText("Upload Files");
				dialog.setFilterExtensions(new String[] { "*.tar.gz" });
				dialog.open();

				text_5.setText(dialog.getFilterPath() + File.separator
						+ dialog.getFileName());

			}
		});
		btnNewButton.setText("...");

		/*
		 * text.setText("10.1.50.4"); text_1.setText("root");
		 * text_2.setText("cs2csolution"); text_3.setText("Nginxqlll");
		 * text_4.setText("/root/nginx/");
		 */
		Composite composite = new Composite(container, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 3, 1);
		gd_composite.widthHint = 396;
		composite.setLayoutData(gd_composite);

		Button btnInstall = new Button(composite, SWT.NONE);
		btnInstall.setBounds(211, 27, 68, 27);
		btnInstall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				if (contentIsValid()) {
					HostInfo hostInfo = new HostInfo();
					hostInfo.setHome(text_4.getText());
					hostInfo.setHostName(text.getText());
					hostInfo.setMiddleware(text_3.getText());
					hostInfo.setPassWord(text_2.getText());
					hostInfo.setUserName(text_1.getText());

					/*
					 * 
					 * hostInfo.setHome("/root/nginx/");
					 * hostInfo.setHostName("10.1.50.4");
					 * hostInfo.setMiddleware("Nginxlll");
					 * hostInfo.setPassWord("cs2csolution");
					 * hostInfo.setUserName("root");
					 */

					if (hostXml.hasHostInfo(hostInfo)) {
						MessageDialog
								.openError(
										getParentShell(),
										"Error",
										"The unique identification has been used, please reset the host name or the middleware name !");
						return;
					} else {
						AuthInfo authInfo = MiddlewareFactory.newAuthInfo();
						authInfo.setHost(text.getText());
						authInfo.setUsername(text_1.getText());
						authInfo.setPassword(text_2.getText());
						File gzFile = new File(text_5.getText());
						String targetPath = text_4.getText();
						try {
							MiddlewareFactory.install(authInfo, gzFile,
									targetPath, null);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							MessageDialog.openError(getParentShell(), "Error",
									e1.getMessage());
							return;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							MessageDialog.openError(getParentShell(), "Error",
									e1.getMessage());
							return;
						}

						if (hostXml.insertHostInfo("conf/host.xml", hostInfo)) {
							MessageDialog
									.openInformation(getParentShell(), "Note",
											"Install successfully and the host information has been written to host.xml!");
						} else {
							MessageDialog
									.openInformation(
											getParentShell(),
											"Note",
											"Install successfully but the host information has not been written to host.xml, please add the host information manually!");
						}
						setReturnCode(OK);
						close();

					}
				}
			}
		});
		btnInstall.setText("Install");

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setBounds(331, 27, 68, 27);
		btnCancel.addSelectionListener(new SelectionAdapter() {

		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
		btnCancel.setText("Cancel");
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
		return new Point(435, 298);
	}

	
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("Install Middleware");
		newShell.setImage(new Image(newShell.getDisplay(), "icons/project_add.png"));

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
		if (text_3.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error",
					"Middleware name is empty");
			return false;
		}
		if (text_4.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error", "Home path is empty");
			return false;
		}
		if (text_5.getText().isEmpty()) {
			MessageDialog.openError(getParentShell(), "Error",
					"The install file is empty");
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
		if (!isMiddlewareNameValid(text_3.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"Middleware name is invalid");
			return false;
		}
		if (!isPathValid(text_4.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"Home path is invalid");
			return false;
		}
		if (!isFileValid(text_4.getText())) {
			MessageDialog.openError(getParentShell(), "Error",
					"Install file is invalid");
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

	private boolean isFileValid(String str) {
		/*
		 * Pattern pattern =
		 * Pattern.compile("[A-Za-z][A-Za-z0-9_.]*[A-Za-z0-9_.$-]?" ); Matcher
		 * matcher = pattern.matcher(str); return matcher.matches();
		 */
		return true;
	}
}
