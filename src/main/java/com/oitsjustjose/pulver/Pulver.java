package com.oitsjustjose.pulver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oitsjustjose.pulver.items.DustRegistry;
import com.oitsjustjose.pulver.items.ItemDust;
import com.oitsjustjose.pulver.proxy.ClientProxy;
import com.oitsjustjose.pulver.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = Lib.MODID, name = Lib.NAME, version = Lib.VERSION, acceptedMinecraftVersions = "1.11", dependencies="after:*")
public class Pulver
{
	@Instance(Lib.MODID)
	public static Pulver INSTANCE;

	@SidedProxy(clientSide = Lib.CLIENT_PROXY, serverSide = Lib.COMMON_PROXY, modId = Lib.MODID)
	public static CommonProxy proxy;
	public static Config config;
	public static Logger LOGGER = LogManager.getLogger(Lib.NAME);
	public static ItemDust dusts;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config = new Config(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void postInit(FMLInitializationEvent event)
	{
		dusts = new ItemDust();
		dustPostProcessing();
		if (event.getSide().isClient())
		{
			ClientProxy.register(dusts);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(dusts, dusts);
		}
	}

	void dustPostProcessing()
	{
		DustRegistry reg = dusts.registry;
		int meta = 0;

		for (String s : reg.getVariants())
		{
			FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(dusts, 1, meta), OreDictionary.getOres("ingot" + s).get(0), 0.0F);
			OreDictionary.registerOre("dust" + s, new ItemStack(dusts, 1, meta));
			meta++;
		}
	}
}