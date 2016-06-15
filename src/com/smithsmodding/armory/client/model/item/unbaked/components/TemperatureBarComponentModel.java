package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.smithsmodding.armory.client.model.item.baked.components.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class TemperatureBarComponentModel extends ItemLayerModel implements IModelPart {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked model.
     */
    public TemperatureBarComponentModel (ImmutableList<ResourceLocation> textures) {
        super(textures);
    }

    /**
     * Function get the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to get the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to get the grayscale texture location of this model faster.
     *
     * @return The location of the grayscale texture.
     */
    public ResourceLocation getTexture () {
        ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
        textures.addAll(getTextures());

        if (textures.size() == 0)
            return null;

        return textures.get(0);
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to get the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    public BakedTemperatureBarModel generateBackedComponentModel (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Get ourselfs a normal model to use.
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        // Use it as our base for the BakedComponentModel.
        BakedTemperatureBarModel bakedTemperatureBar = new BakedTemperatureBarModel(base);

        //Construct individual models for each of the sprites.
        for (ResourceLocation textureLocation : this.getTextures()) {
            TextureAtlasSprite sprite = bakedTextureGetter.apply(textureLocation);

            //We retexture this model with the newly colored textured from ther creator and get a Copy of this model
            IModel model2 = ItemLayerModel.instance.retexture(ImmutableMap.of("layer0", sprite.getIconName()));

            //We bake the new model to get a ready to use textured and ready to be colored baked model.
            IFlexibleBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

            //Set normals to ignore lighting.
            ModelHelper.setNormalsToIgnoreLightingOnItemModel(bakedModel2);

            //Add the model
            bakedTemperatureBar.addTexture(bakedModel2);
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedTemperatureBar;
    }

}
