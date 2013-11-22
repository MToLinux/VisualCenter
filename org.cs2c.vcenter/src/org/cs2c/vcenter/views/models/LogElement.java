/**
 * 
 */
package org.cs2c.vcenter.views.models;

import java.util.LinkedList;
import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.RemoteException;
import org.cs2c.nginlib.log.LogProfile;
import org.cs2c.nginlib.log.Logger;

/**
 * @author Administrator
 *
 */
public class LogElement extends TreeElement implements ILogs {

	/**
	 * @param parent
	 */
	public LogElement(TreeElement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TreeElement> getChildren() throws RemoteException {
		List<TreeElement> children=new LinkedList<TreeElement>();
		Logger logger=this.middleware.getLogger();
		List<LogProfile> logs=logger.getLogFileNames();
		for(LogProfile log: logs){
			LogInstanceElement logInstance=new LogInstanceElement(this);
			logInstance.init(log.getName()+" -- "+log.getSize()+"Bytes", this.middleware);
			logInstance.setLog(log);
			children.add(logInstance);
		}
		return children;
	}

	@Override
	public boolean hasChildren() throws RemoteException {
		Logger logger=this.middleware.getLogger();
		if(logger.getLogFileNames().size()==0){
			return false;
		}
		return true;
	}


}
