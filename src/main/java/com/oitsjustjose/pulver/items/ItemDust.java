package com.oitsjustjose.pulver.items;

import java.util.List;

import com.oitsjustjose.pulver.Lib;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class ItemDust extends Item
{
	public IDustRegistry registry;

	public ItemDust()
	{
		registry = new IDustRegistry();
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.setUnlocalizedName(Lib.MODID + ".dust");
		this.setRegistryName(new ResourceLocation(Lib.MODID, "dust"));
		GameRegistry.register(this);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		return this.getUnlocalizedNameInefficiently(itemstack);
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack itemstack)
	{
		return registry.getNameFromMeta(itemstack.getItemDamage()) + " " + I18n.translateToLocal("item.dust");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Lib.MODID + ".dust";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (Minecraft.getMinecraft().gameSettings.advancedItemTooltips)	
			tooltip.add(I18n.translateToLocal("tooltip.added") + getModID(registry.getPairedItemstack(itemstack.getItemDamage())));
	}

	String getModID(ItemStack stack)
	{
		// Grabs the lower-case ModID from the
		String m = stack.getItem().getRegistryName().getResourceDomain().toLowerCase();
		// Scans through every mod loaded, comparing if the modid matches and returns the NAME. Name != modid
		for (ModContainer c : Loader.instance().getModList())
			if (c.getModId().toLowerCase().equals(m))
				return c.getName();

		return TextFormatting.OBFUSCATED + "--------------";
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
}