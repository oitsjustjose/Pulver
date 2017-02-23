package com.oitsjustjose.pulver.items;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import com.oitsjustjose.pulver.Pulver;

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

		parseConfig();
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
	 */
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

	/**
	 * @param name
	 *            The OreDict Name to to check for registration status
	 * @return True if there isn't a dust, and IS an ore and ingot for name
	 */
	private boolean shouldRegister(String name)
	{
		// Then checks to see if there's NO other dusts, and an ore AND ingot version exist!
		if (!OreDictionary.doesOreNameExist("dust" + name) && OreDictionary.getOres("ore" + name).size() > 0 && OreDictionary.getOres("ingot" + name).size() > 0)
			return true;

		return false;
	}

	/**
	 * Parses through the config
	 */
	private void parseConfig()
	{

		Logger log = Pulver.LOGGER;
		log.info("Parsing Config Entries");
		for (String s : Pulver.config.manualEntries)
		{
			String[] temp = s.split(":");
			// In a try catch to confirm formatting
			try
			{
				// Normalizes Names
				String cleaned = temp[0];
				if (cleaned.startsWith("ore"))
					cleaned = cleaned.replace("ore", "");
				else if (cleaned.startsWith("ingot"))
					cleaned = cleaned.replace("ingot", "");

				if (shouldRegister(cleaned))
					registerDust(cleaned, Integer.parseInt(temp[1]));
				else
					log.info(s + " could not be registered; it may be missing an Ore or Ingot, or already have a dust!");
			}
			catch (NumberFormatException | IndexOutOfBoundsException e)
			{
				log.info(s + " is not formatted properly. Skipping entry.");
			}
		}

		log.info("Finished Parsing!");
	}
}