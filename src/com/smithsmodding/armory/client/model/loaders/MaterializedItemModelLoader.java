package com.smithsmodding.armory.client.model.loaders;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.client.model.deserializers.MaterializedItemModelDeserializer;
import com.smithsmodding.armory.client.model.deserializers.definition.MaterializedItemModelDefinition;
import com.smithsmodding.armory.client.model.item.unbaked.MaterializedItemModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".MIM-armory";

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        modelLocation = ModelHelper.getModelLocation(modelLocation);

        try {
            MaterializedItemModelDefinition definition = MaterializedItemModelDeserializer.INSTANCE.deserialize(modelLocation);

            MaterializedTextureCreator.registerBaseTexture(definition.getCoreTexture());

            return new MaterializedItemModel(definition.getCoreTexture(), definition.getTransforms());
        } catch (Exception ex) {
            Armory.getLogger().error("Could not load {} as MaterializedModel", modelLocation.toString());
        }

        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
