package com.oitsjustjose.pulver.items;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.oredict.OreDictionary;

public class DustRegistry
{
	private Map<String, Integer> ENTRIES;

	public DustRegistry()
	{
		ENTRIES = new LinkedHashMap<String, Integer>();
		initEntries();
	}

	public void initEntries()
	{
		if (shouldRegister("Iron"))
			registerDust("Iron", 12428902);

		if (shouldRegister("Gold"))
			registerDust("Gold", 16772608);

		if (shouldRegister("Copper"))
			registerDust("Copper", 16034370);

		if (shouldRegister("Tin"))
			registerDust("Tin", 14408667);

		if (shouldRegister("Silver"))
			registerDust("Silver", 13687771);

		if (shouldRegister("Lead"))
			registerDust("Lead", 3617594);

	}

	public boolean hasEntry(String str)
	{
		for (String s : ENTRIES.keySet())
			if (str.toLowerCase().equals(s.toLowerCase()))
				return true;
		return false;
	}

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

	public int getColorFromMeta(int meta)
	{
		int marker = 0;
		for (int i : ENTRIES.values())
		{
			if (marker == meta)
				return i;
			marker++;
		}
		return 0;
	}

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

	public int getColorFromName(String name)
	{
		return ENTRIES.get(name);
	}

	/**
	 * @param name
	 *            The ore name - such as "Iron", "Gold", "Copper", etc.
	 * @param colorVal
	 *            The HEX value of the color it should display as
	 * 
	 *            Creates a dust variant, adds this dust to the ore dictionary, and auto-adds smelting recipes
	 */
	public void registerDust(String name, int colorVal)
	{
		ENTRIES.put(name, colorVal);
	}

	private boolean shouldRegister(String name)
	{
		String s = name;

		// Normalizes Names
		if (s.startsWith("ore"))
			s.replace("ore", "");
		else if (s.startsWith("ingot"))
			s.replace("ingot", "");

		// Then checks to see if there's NO other dusts, and an ore AND ingot version exist!
		if (!OreDictionary.doesOreNameExist("dust" + name) && OreDictionary.getOres("ore" + s).size() > 0 && OreDictionary.getOres("ingot" + s).size() > 0)
			return true;

		return false;
	}
}