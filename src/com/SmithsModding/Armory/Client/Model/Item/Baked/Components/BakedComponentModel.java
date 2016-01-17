package com.smithsmodding.Armory.Client.Model.Item.Baked.Components;

import com.smithsmodding.Armory.API.Armor.*;
import com.smithsmodding.Armory.API.Item.*;
import com.smithsmodding.Armory.API.Materials.*;
import com.smithsmodding.Armory.Common.Material.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * A baked model for a component made up out of a single Material.
 */
public class BakedComponentModel extends IFlexibleBakedModel.Wrapper implements ISmartItemModel {
    //Map that contains a premapped combination of materials to models.
    protected Map<String, IFlexibleBakedModel> materializedComponents;

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedComponentModel (IFlexibleBakedModel base) {
        super(base, base.getFormat());

        materializedComponents = new HashMap<String, IFlexibleBakedModel>(MaterialRegistry.getInstance().getArmorMaterials().size());
    }

    /**
     * Function to register a new PreBakeable model to this Model
     *
     * @param material The material to register a new model for.
     * @param model    The model to register.
     */
    public void addMaterialModel (IArmorMaterial material, IFlexibleBakedModel model) {
        materializedComponents.put(material.getUniqueID(), model);
    }

    /**
     * Function used to get a Baked model from an ItemStack.
     *
     * @param stack The ItemStack to get the Model for.
     * @return The baked model.
     */
    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        if (stack.getItem() instanceof MultiLayeredArmor) {
            String id = ((ISingleMaterialItem) stack.getItem()).getMaterialInternalName(stack);
            return getModelByIdentifier(id);
        }
        return this;
    }

    /**
     * Function to get a model from a MaterialID.
     *
     * @param identifier The MaterialId to get the model for.
     * @return If registered it will return the prebaked model that is registered to that material id, if not it will return this instance of a BakedComponent model.
     */
    public IFlexibleBakedModel getModelByIdentifier (String identifier) {
        IFlexibleBakedModel materialModel = materializedComponents.get(identifier);
        if (materialModel == null) {
            return this;
        }

        return materialModel;
    }
}
