package com.smithsmodding.armory.common.anvil;

import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import com.smithsmodding.smithscore.util.client.color.ColorSampler;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilMaterial implements IAnvilMaterial {

    String id;
    int durability;
    String translatedDisplayName;
    TextFormatting translatedDisplayNameColor;
    ITextureController info;

    public AnvilMaterial (String id, int durability, String translatedDisplayName) {
        this.id = id;
        this.durability = durability;
        this.translatedDisplayName = translatedDisplayName;
        translatedDisplayNameColor = null;
    }

    @Override
    public String getID () {
        return id;
    }

    @Override
    public int durability () {
        return durability;
    }

    @Override
    public String translatedDisplayName () {
        return translatedDisplayName;
    }

    @Override
    public TextFormatting translatedDisplayNameColor() {
        if (translatedDisplayNameColor == null)
            translatedDisplayNameColor = ColorSampler.getChatMinecraftColorSample(info.getVertexColor());

        return translatedDisplayNameColor;
    }

    @Override
    public ITextureController getRenderInfo() {
        return info;
    }

    @Override
    public void setRenderInfo(ITextureController info) {
        this.info = info;
    }

}
