package com.oitsjustjose.pulver.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDustColor
{
	@SideOnly(Side.CLIENT)
	IItemColor getOreColor(int meta, int renderPass);
}
