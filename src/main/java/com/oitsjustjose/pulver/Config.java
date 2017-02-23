package com.oitsjustjose.pulver;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config
{
	public Configuration config;
	public String[] manualEntries;

	public Config(File configFile)
	{
		this.init(configFile);
	}

	void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	void loadConfiguration()
	{
		Property property = config.get(Configuration.CATEGORY_GENERAL, "Custom Ore Names & Color Values:", new String[]{}, "Formatted as <OreDictName>:<DecimalColorYouWant>." + "\n" + "Use http://www.colorpicker.com/ to find a color and http://bit.ly/1RhPhcX to convert it from Hex to Decimal.");
		manualEntries = property.getStringList();

		if (config.hasChanged())
			config.save();
	}
}