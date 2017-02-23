package com.oitsjustjose.pulver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oitsjustjose.pulver.items.ItemDust;
import com.oitsjustjose.pulver.proxy.ClientProxy;
import com.oitsjustjose.pulver.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Lib.MODID, name = Lib.NAME, version = Lib.VERSION, acceptedMinecraftVersions = "1.11")
public class Pulver
{
	@Instance(Lib.MODID)
	public static Pulver INSTANCE;

	@SidedProxy(clientSide = Lib.CLIENT_PROXY, serverSide = Lib.COMMON_PROXY, modId = Lib.MODID)
	public static CommonProxy proxy;
	public static Logger LOGGER = LogManager.getLogger(Lib.NAME);

	public static ItemDust dusts;

	@EventHandler
	public void postInit(FMLInitializationEvent event)
	{
		dusts = new ItemDust();

		if (event.getSide().isClient())
		{
			ClientProxy.register(dusts);
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(dusts, dusts);
		}
	}
}