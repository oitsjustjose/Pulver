package com.oitsjustjose.pulver;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Credits to mezz. I had an idea where to start but..
 * 
 * http://bit.ly/2lVsa2a
 */
@SideOnly(Side.CLIENT)
public class ColorUtils
{
	
	public static int getColor(ItemStack itemstack) throws IOException
	{
		BufferedImage image = getBufferedImage(itemstack);

		return image == null ? 0 : image.getRGB(image.getHeight() / 2, image.getWidth() / 2);
	}

	@Nullable
	private static BufferedImage getBufferedImage(ItemStack itemstack)
	{

		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ItemModelMesher itemModelMesher = renderItem.getItemModelMesher();
		IBakedModel itemModel = itemModelMesher.getItemModel(itemstack);
		TextureAtlasSprite textureAtlasSprite = itemModel.getParticleTexture();

		final int iconWidth = textureAtlasSprite.getIconWidth();
		final int iconHeight = textureAtlasSprite.getIconHeight();
		final int frameCount = textureAtlasSprite.getFrameCount();
		
		if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0)
		{
			return null;
		}

		BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight * frameCount, BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < frameCount; i++)
		{
			int[][] frameTextureData = textureAtlasSprite.getFrameTextureData(i);
			int[] largestMipMapTextureData = frameTextureData[0];
			bufferedImage.setRGB(0, i * iconHeight, iconWidth, iconHeight, largestMipMapTextureData, 0, iconWidth);
		}

		return bufferedImage;
	}
}
