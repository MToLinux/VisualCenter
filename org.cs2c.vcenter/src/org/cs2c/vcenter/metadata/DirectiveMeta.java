package org.cs2c.vcenter.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DirectiveMeta {
	private String name=null;
	private String tips=null;
	private Set<String> scope=null;
	private List<ParameterMeta> options=null;
	private boolean reused=false;
	private String group=null;
	
	
	public void BlockMeta()
	{
		name=null;
		tips=null;
		scope=null;
		options=new ArrayList<ParameterMeta>(0);
		group=null;
		reused=false;
	}
	
	public void setName(String para)
	{
		this.name=para;
	}
	public void setGroup(String para)
	{
		this.group=para;
	}
	public void setTips(String para)
	{
		this.tips=para;
	}
	public void setReused(boolean para)
	{
		this.reused=para;
	}
	public void setScope(Set<String> para)
	{
		this.scope=para;
	}
	public void setOptions(List <ParameterMeta> para)
	{
		this.options=para;
	}
	public String getName( )
	{
		return this.name;
	}
	public String getGroup( )
	{
		return this.group;
	}
	public String getTips()
	{
		return this.tips;
	}
	
	public Set <String> getScope()
	{
		return this.scope;
	}
	public List <ParameterMeta> getOptions()
	{
		return this.options;
	}
	public boolean getReused()
	{
		return this.reused;
	}
}
