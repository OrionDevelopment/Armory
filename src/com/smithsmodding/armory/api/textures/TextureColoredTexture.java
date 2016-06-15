package com.smithsmodding.armory.api.textures;

/*
  A BIG NOTE UPFRONT. Due to the similarities between TiC ToolSystem and Armories armor system this is a near repackage.
  Most of this code falls under their license, although some changes are made to fit the system in with Armories used
  of Wrapper classes instead of direct access.
 */

import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class TextureColoredTexture extends AbstractColoredTexture {

    protected final TextureAtlasSprite addTexture;
    protected final String addTextureLocation;
    public boolean stencil = false;
    protected int[][] textureData;

    public TextureColoredTexture (String addTextureLocation, TextureAtlasSprite baseTexture,
                                  String spriteName) {
        super(baseTexture, spriteName);
        this.addTextureLocation = addTextureLocation;
        this.addTexture = null;
    }

    public TextureColoredTexture (TextureAtlasSprite addTexture, TextureAtlasSprite baseTexture,
                                  String spriteName) {
        super(baseTexture, spriteName);
        this.addTextureLocation = addTexture.getIconName();
        this.addTexture = addTexture;
    }

    @Override
    protected int colorPixel (int pixel, int mipmap, int pxCoord) {
        int a = alpha(pixel);
        if (a == 0) {
            return pixel;
        }

        if (textureData == null) {
            loadData();
        }

        int c = textureData[mipmap][pxCoord];

        // multiply in the color
        int r = red(c);
        int b = blue(c);
        int g = green(c);

        if (!stencil) {
            r = mult(mult(r, red(pixel)), red(pixel));
            g = mult(mult(g, green(pixel)), green(pixel));
            b = mult(mult(b, blue(pixel)), blue(pixel));
        }
        return compose(r, g, b, a);
    }

    protected void loadData () {
        if (addTexture != null && addTexture.getFrameCount() > 0) {
            textureData = addTexture.getFrameTextureData(0);
        } else {
            textureData = backupLoadTexture(new ResourceLocation(addTextureLocation),
                    Minecraft.getMinecraft().getResourceManager());
        }
    }
}
