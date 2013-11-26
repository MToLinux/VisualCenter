package org.cs2c.vcenter.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockMeta {
	private String name;
	private String tips;
	private boolean reused=false;
	private List<DirectiveMeta> directiveMeta=null;
	private List<BlockMeta> blockMeta=null;
	private String group=null;
	
	public void BlockMeta()
	{
		name=null;
		group=null;
		directiveMeta=new ArrayList<DirectiveMeta>(0);
		blockMeta=new ArrayList<BlockMeta>(0);
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
	
	public void setDirectiveMeta(List <DirectiveMeta> para)
	{
		this.directiveMeta=para;
	}
	public void setBlockMeta(List <BlockMeta> para)
	{
		this.blockMeta=para;
	}
	public String getName( )
	{
		return this.name;
	}
	public String getGroup( )
	{
		return this.group;
	}
	public String getTips( )
	{
		return this.tips;
	}
	
	public List<String> getGroups( )
	{
		List<String> groups=new ArrayList<String>(0);
		for(int i=0;i<this.directiveMeta.size();i++)
		{
			if(groups.contains(this.directiveMeta.get(i).getGroup())==false)
			{
				groups.add(this.directiveMeta.get(i).getGroup());
			}
		}
		for(int i=0;i<this.blockMeta.size();i++)
		{
			if(groups.contains(this.blockMeta.get(i).getGroup())==false)
			{
				groups.add(this.blockMeta.get(i).getGroup());
			}
		}
		return groups;
	}
	public List <DirectiveMeta> getDirectiveMeta(String group)
	{
		List <DirectiveMeta> tmpDirectiveList=new  ArrayList<DirectiveMeta>(0);
		for(int i=0;i<this.directiveMeta.size();i++)
		{
			if(this.directiveMeta.get(i).getGroup().equals(group))
			{
				tmpDirectiveList.add(this.directiveMeta.get(i));
			}
		}
		
		return tmpDirectiveList;
	}
	public List <BlockMeta> getBlockMeta(String group)
	{
		List<BlockMeta> tmpBlockList=new ArrayList<BlockMeta>(0);
		for(int i=0;i<this.blockMeta.size();i++)
		{
			if(this.blockMeta.get(i).getGroup().equals(group))
			{
				tmpBlockList.add(this.blockMeta.get(i));
			}
		}
		return tmpBlockList;
	}
	public boolean getReused()
	{
		return this.reused;
	}
	public void setReused(boolean para)
	{
		reused=para;
	}
}
