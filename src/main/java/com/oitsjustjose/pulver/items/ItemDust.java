package com.oitsjustjose.pulver.items;

import com.oitsjustjose.pulver.Lib;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDust extends Item implements IItemColor
{
	public DustRegistry registry;

	public ItemDust()
	{
		registry = new DustRegistry();
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.setUnlocalizedName(Lib.MODID + ".dust");
		this.setRegistryName(new ResourceLocation(Lib.MODID, "dust"));
		GameRegistry.register(this);
	}

	public int getMaxSubtypes()
	{
		return this.registry.getVariants().length;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		return this.getUnlocalizedNameInefficiently(itemstack);
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack itemstack)
	{
		return registry.getNameFromMeta(itemstack.getItemDamage()) + " Dust";
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Lib.MODID + ".dust";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (int i = 0; i < registry.getVariants().length; i++)
		{
			ItemStack stack = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int renderPass)
	{
		if (stack.getItemDamage() >= registry.getVariants().length)
			return 0;
		if (renderPass == 1)
		{
			return registry.getColorFromMeta(stack.getItemDamage());
		}
		else
			return 10724259;
	}
}