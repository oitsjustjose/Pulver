package com.oitsjustjose.pulver.items;

import java.util.LinkedHashMap;
import java.util.Map;

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
		ENTRIES.put("Iron", 12428902);
		ENTRIES.put("Gold", 16772608);
		ENTRIES.put("YADDA", 123456);
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
	 */
	public void registerDust(String name, int colorVal)
	{
		ENTRIES.put(name, colorVal);
	}
}