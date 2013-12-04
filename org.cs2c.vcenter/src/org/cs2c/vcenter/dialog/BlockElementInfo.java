package org.cs2c.vcenter.dialog;

import java.util.ArrayList;
import java.util.List;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.config.Block;
import org.cs2c.nginlib.config.Directive;
import org.cs2c.nginlib.config.RecBlock;
import org.cs2c.vcenter.metadata.BlockMeta;

public class BlockElementInfo {
	
	private MiddlewareFactory middleware = null;
	private String blockName = null;
	private String blockType = null;
	private String blockGroup = null;
	private String blockSubGroup = null;
	private String blockOutNames = null;
	private String blockIndex = null;
	
	//Metadata
	private BlockMeta bMeta = new BlockMeta();
	List<String> blockGroups = new ArrayList<String>();
	
	//Server Configuration Information
	private Block block = new RecBlock();
	private java.util.List<Directive> directives = new ArrayList<Directive>();
	private java.util.List<Block> blocks = new ArrayList<Block>();
	
	public BlockElementInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public void setBlock(Block blk)
	{
		this.block = blk;
	}
	public Block getBlock()
	{
		return this.block;
	}
	
	public void setMiddleware(MiddlewareFactory middleware)
	{
		this.middleware = middleware;
	}
	public MiddlewareFactory getMiddleware()
	{
		return this.middleware;
	}
	
	public void setBlockName(String blockName)
	{
		this.blockName = blockName;
	}
	public String getBlockName()
	{
		return this.blockName;
	}
	
	public void setBlockType(String blockType)
	{
		this.blockType = blockType;
	}
	public String getBlockType()
	{
		return this.blockType;
	}
	
	public void setBlockGroup(String blockGroup)
	{
		this.blockGroup = blockGroup;
	}
	public String getBlockGroup()
	{
		return this.blockGroup;
	}
	
	public void setBlockSubGroup(String blockSubGroup)
	{
		this.blockSubGroup = blockSubGroup;
	}
	public String getBlockSubGroup()
	{
		return this.blockSubGroup;
	}
	
	public void setBlockOutNames(String blockOutNames)
	{
		this.blockOutNames = blockOutNames;
	}
	public String getBlockOutNames()
	{
		return this.blockOutNames;
	}
	
	public void setBlockIndex(String blockIndex)
	{
		this.blockIndex = blockIndex;
	}
	public String getBlockIndex()
	{
		return this.blockIndex;
	}
	
	public void setBlockMeta(BlockMeta bMeta)
	{
		this.bMeta = bMeta;
	}
	public BlockMeta getBlockMeta()
	{
		return this.bMeta;
	}
	
}
