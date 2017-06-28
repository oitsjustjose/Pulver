package com.oitsjustjose.pulver.items;

import java.util.List;

import com.oitsjustjose.pulver.Lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
		this.setCreativeTab(CreativeTabs.MISC);
		this.setUnlocalizedName(Lib.MODID + ".dust");
		this.setRegistryName(new ResourceLocation(Lib.MODID, "dust"));
		ForgeRegistries.ITEMS.register(this);
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
	public void addInformation(ItemStack itemstack, World world, List<String> tooltip, ITooltipFlag advanced)
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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (this.isInCreativeTab(tab))
		{
			for (int i = 0; i < registry.getVariants().length; ++i)
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
	}
}