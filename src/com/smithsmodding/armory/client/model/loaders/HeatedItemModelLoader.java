package com.smithsmodding.armory.client.model.loaders;


import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.client.model.item.unbaked.HeatedItemItemModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.TemperatureBarComponentModel;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 */
public class HeatedItemModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".HI-armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // HeatedItem armory extension. Foo.HI-armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Load the default definition of the model as defined by the registrar first.
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);

            //Create the final list builder.
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * Temp (Component texture used when the armor is not broken)
            for (Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();

                ResourceLocation location = null;
                TemperatureBarComponentModel partModel = null;

                try {
                    if (name.startsWith("temp")) {
                        //Standard Layer
                        location = new ResourceLocation(entry.getValue());
                    } else {
                        //Unknown layer, warning and skipping.
                        Armory.getLogger().warn("HeatedItemModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                        continue;
                    }
                    //If the texture was added to any layer, add it to the list of used textures.
                    if (location != null) {
                        builder.add(location);
                    }
                } catch (NumberFormatException e) {
                    Armory.getLogger().error("HeatedItemModel{} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                }
            }

            if (builder.build().size() == 0) {
                Armory.getLogger().error("A given model definition for the HeatedItems did not contain any gauges.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new HeatedItemItemModel(builder.build());

            // Load all textures we need in to the creator.
            MaterializedTextureCreator.registerBaseTexture(builder.build());

            return output;
        } catch (IOException e) {
            Armory.getLogger().error("Could not load multimodel {}", modelLocation.toString());
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    private void addComponentToList (List<ArmorComponentModel> list, int index, ArmorComponentModel model) {
        while (list.size() <= index) {
            list.add(null);
        }
        list.set(index, model);
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }
}
