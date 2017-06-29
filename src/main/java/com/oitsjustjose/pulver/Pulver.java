package com.oitsjustjose.pulver;

import org.apache.logging.log4j.LogManager;

import com.oitsjustjose.pulver.items.IDustRegistry;
import com.oitsjustjose.pulver.items.ItemDust;
import com.oitsjustjose.pulver.proxy.CommonProxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = Lib.MODID, name = Lib.NAME, version = Lib.VERSION, acceptedMinecraftVersions = "1.11")
public class Pulver
{
	@Instance(Lib.MODID)
	public static Pulver INSTANCE;

	@SidedProxy(clientSide = Lib.CLIENT_PROXY, serverSide = Lib.COMMON_PROXY, modId = Lib.MODID)
	public static CommonProxy proxy;
	public static ItemDust dusts;

	@EventHandler
	public void initItems(FMLPreInitializationEvent event)
	{
		dusts = new ItemDust();
	}

	@EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
		{
			LogManager.getLogger(Lib.MODID).info("Deobfuscated Environment Detected: Initializing Debug OreDict Entries");
			debug();
			LogManager.getLogger(Lib.MODID).info("Debug OreDict Entries Initialized");
		}

		dusts.registry.initEntries();
		dustPostProcessing();

		proxy.register(dusts);
		proxy.registerColorizers(dusts);
	}

	private void dustPostProcessing()
	{
		IDustRegistry reg = dusts.registry;
		int meta = 0;

		for (String s : reg.getVariants())
		{
			FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(dusts, 1, meta), OreDictionary.getOres("ingot" + s).get(0), 0.0F);
			OreDictionary.registerOre("dust" + s, new ItemStack(dusts, 1, meta));
			meta++;
		}
	}

	private void debug()
	{
		for (int i = 1; i < Item.REGISTRY.getKeys().size(); i++)
		{
			Item item = Item.getItemById(i);
			if (item != null)
			{
				String name = new ItemStack(item).getDisplayName();
				OreDictionary.registerOre("ore" + name, item);
				OreDictionary.registerOre("ingot" + name, item);
			}
		}
	}
}