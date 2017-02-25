package com.oitsjustjose.pulver.items;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.oitsjustjose.pulver.ColorUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DustRegistry
{
	private Map<String, ItemStack> ENTRIES;
	private Map<String, Integer> COLORS;

	public DustRegistry()
	{
		ENTRIES = new LinkedHashMap<String, ItemStack>();
		COLORS = new LinkedHashMap<String, Integer>();
		initEntries();
	}

	public void initEntries()
	{
		for (String ore : OreDictionary.getOreNames())
			if (ore.startsWith("ore"))
				if (shouldRegister(ore.substring(3)))
					registerDust(ore.substring(3), OreDictionary.getOres("ingot" + (ore.substring(3))).get(0));
	}

	/**
	 * @return An array of all the names added to the list
	 */
	public String[] getVariants()
	{
		String[] ret = new String[ENTRIES.size()];

		int i = 0;
		for (String s : ENTRIES.keySet())
		{
			ret[i] = s;
			i++;
		}

		return ret;
	}

	/**
	 * @param meta
	 * @return The int color value affiliated with the metadata of the item
	 * @throws IOException
	 */
	public int getColorFromMeta(int meta) throws IOException
	{
		// Tallies through to keep up with meta
		int marker = 0;
		// Goes through every string
		for (String s : ENTRIES.keySet())
		{
			// Once it's found the entry matching meta:
			if (marker == meta)
			{
				// It checks to see if the color has been evaluated yet
				if (COLORS.get(s) == -1)
					COLORS.put(s, ColorUtils.getColor(ENTRIES.get(s)));
				// Returns the calculated color from ColorUtils
				return COLORS.get(s);
			}
			// Increments until it matches meta
			marker++;
		}
		// Worse case: no bueno
		return 0;
	}

	/**
	 * @param meta
	 *            Metadata of the item
	 * @return The Name associated with that metadata. "INVALID" is returned if for some reason the marker isn't
	 */
	public String getNameFromMeta(int meta)
	{
		int marker = 0;
		for (String s : ENTRIES.keySet())
		{
			if (marker == meta)
				return s;
			marker++;
		}
		return "INVALID";
	}

	/**
	 * @param name
	 *            The name which you want to find the color value for
	 * @return The Integer Color value of the String value
	 */
	public int getColorFromName(String name)
	{
		try
		{
			return ColorUtils.getColor(ENTRIES.get(name));
		}
		catch (IOException e)
		{
			return 0xFFFFFF;
		}
	}

	/**
	 * @param name
	 *            The ore name - such as "Iron", "Gold", "Copper", etc.
	 * @param colorVal
	 *            The HEX value of the color it should display as
	 * 
	 *            Creates a dust variant, adds this dust to the ore dictionary, and auto-adds smelting recipes
	 */
	public void registerDust(String name, ItemStack linkedIngot)
	{
		ENTRIES.put(name, linkedIngot);
		COLORS.put(name, -1);
	}

	/**
	 * @param name
	 *            The OreDict Name to to check for registration status
	 * @return True if there isn't a dust, and IS an ore and ingot for name
	 */
	 private boolean shouldRegister(String name)
 	{
 		// Then checks to see if there's NO other dusts, and an ore AND ingot version exist!
 		if (OreDictionary.getOres("dust" + name).size() <= 0)
 			if (OreDictionary.getOres("ore" + name).size() > 0)
 				if (OreDictionary.getOres("ingot" + name).size() > 0)
 					return true;
 		return false;
 	}
}