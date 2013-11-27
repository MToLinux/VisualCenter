package org.cs2c.vcenter.metadata;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ParameterMeta {
	private String name;
	private String value;
	private String className;
	private long min;
	private long max;
	private List<String> units;
	private String tips;
	private List<String> items;
	public ParameterMeta()
	{
		 name=null;
		 value=null;
		 className=null;
		 min=-2;
		 max=-2;
		 units=new ArrayList<String>(0);
		 tips=null;
		 items=new ArrayList<String>(0);
	}
	public void setName(String para)
	{
		this.name=para;
	}
	public void setValue(String para)
	{
		this.value=para;
	}
	public void setClassName(String para)
	{
		this.className=para;
	}
	public void setMin(long para)
	{
		this.min=para;
	}
	public void setMax(long para)
	{
		this.max=para;
	}
	
	public void setTips(String para)
	{
		this.tips=para;
	}
	
	public void setUnits(List<String>  strlist)
	{
		this.units=strlist;
		
	}
	public void setItems(List <String>  strlist)
	{
		this.items=strlist;
	}
	public  String getName()
	{
		return this.name;
	}
	public String getValue( )
	{
		return this.value;
	}
	public String getClassName()
	{
		return this.className;
	}
	public long getMin()
	{
		return this.min;
	}
	public long getMax()
	{
		return this.max;
	}
	
	public String getTips()
	{
		return this.tips;
	}
	
	
	public List <String> getUnit()
	{
		return this.units;
	}
	public List <String> getItems()
	{
		return this.items;
	}
	
}
