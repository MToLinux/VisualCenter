package org.cs2c.vcenter.metadata;

public class HostInfo {
	private String hostName;
	private String userName;
	private String passWord;
	private String middleware;
	private String home;
	private String statusPath;
	private String managerUserName;
	private String managerPassWord;
	
	public HostInfo()
	{
		hostName="";
		userName="";
		passWord="";
		middleware="";
		home="";
		statusPath="";
		managerUserName="";
		managerPassWord="";
	}
	public String getHostName()
	{
		return this.hostName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String getPassWord()
	{
		return this.passWord;
	}
	
	public String getMiddleware()
	{
		return this.middleware;
	}
	
	public String getHome()
	{
		return this.home;
	}
	
	public String getStatusPath()
	{
		return this.statusPath;
	}
	
	public String getManagerUserName()
	{
		
		return this.managerUserName;
	}
	
	public String getManagerPassWord()
	{
		return this.managerPassWord;
	}
	
	public void setHostName(String para)
	{
		this.hostName=para;
	}
	
	public void setUserName(String para)
	{
		this.userName=para;
	}
	
	public void setPassWord(String para)
	{
		this.passWord=para;
	}
	
	public void setMiddleware(String para)
	{
		this.middleware=para;
	}
	
	public void setHome(String para)
	{
		this.home=para;
	}
	
	public void setStatusPath(String para)
	{
		this.statusPath=para;
	}
	
	public void setManagerUserName(String para)
	{
		this.managerUserName=para;
	}
	public void setManagerPassWord(String para)
	{
		this.managerPassWord=para;
	}
	
}

