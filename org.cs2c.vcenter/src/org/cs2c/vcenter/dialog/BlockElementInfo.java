package org.cs2c.vcenter.dialog;

import org.cs2c.nginlib.MiddlewareFactory;
import org.cs2c.nginlib.config.Block;
import org.cs2c.vcenter.metadata.BlockMeta;

public class BlockElementInfo {
	
	private MiddlewareFactory middleware = null;
	private String blockType = null;
	
	private BlockMeta bMeta = null;
	
	private Block block = null;
	
	public BlockElementInfo() {
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
	
	public void setBlockType(String blockType)
	{
		this.blockType = blockType;
	}
	public String getBlockType()
	{
		return this.blockType;
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
