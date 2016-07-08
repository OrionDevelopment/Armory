package com.smithsmodding.armory.client.logic;

/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.references.ModBlocks;
import com.smithsmodding.armory.api.references.ModItems;
import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.materials.MaterialRenderControllers;
import com.smithsmodding.armory.client.ArmoryClientProxy;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.client.model.loader.MultiComponentModelLoader;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ArmoryClientInitializer extends ArmoryInitializer {
    public static void InitializeClient() {
        ArmoryInitializer.SystemInit.RegisterCreativeTabs();
        ArmoryInitializer.SystemInit.RegisterFluids();
        MedievalInitialization.Initialize();
        GlobalInitialization.RegisterAnvilMaterials();
        ArmoryInitializer.SystemInit.RegisterBlocks();
        ArmoryInitializer.SystemInit.RegisterItems();
        ArmoryInitializer.SystemInit.RegisterTileEntities();
        SystemInit.registerIIR();
        SystemInit.registerTESR();
        ArmoryInitializer.SystemInit.loadMaterialConfig();
        MedievalClientInitialization.registerMaterialResources();
        MedievalClientInitialization.registerUpgradeResources();
        MedievalClientInitialization.registerRingResources();
        MedievalClientInitialization.registerChainResources();
        MedievalClientInitialization.registerNuggetResources();
        MedievalClientInitialization.registerPlateResources();
        MedievalInitialization.prepareGame();
        GlobalClientInitialization.RegisterAnvilMaterialRenderInfo();
        ArmoryInitializer.SystemInit.initializeOreDict();
    }

    public static class MedievalClientInitialization {
        public static void registerMaterialResources() {
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setRenderInfo(new MaterialRenderControllers.Metal(0xcacaca, 0f, 0.3f, 0f) {
                @Override
                public MinecraftColor getLiquidColor () {
                    return new MinecraftColor(MinecraftColor.RED);
                }
            });
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setTranslationKey(TranslationKeys.Materials.VisibleNames.Iron);

            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setRenderInfo(new MaterialRenderControllers.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setTranslationKey(TranslationKeys.Materials.VisibleNames.Obsidian);
        }

        public static void registerUpgradeResources() {

        }

        public static void registerRingResources() {

        }

        public static void registerChainResources() {

        }

        public static void registerNuggetResources() {

        }

        public static void registerPlateResources() {

        }

    }

    public static class GlobalClientInitialization
    {
        public static void RegisterAnvilMaterialRenderInfo()
        {
            IArmorMaterial iron = MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON);
            IArmorMaterial obsidian = MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN);

            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.STONE).setRenderInfo(new MaterialRenderControllers.BlockTexture("minecraft:blocks/stone"));
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.IRON).setRenderInfo(iron.getRenderInfo());
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN).setRenderInfo(obsidian.getRenderInfo());
        }
    }

    public static class SystemInit {
        public static void registerIIR() {
            ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

            MaterialRegistry.getInstance().getAllRegisteredArmors().values().forEach(ArmoryClientProxy::registerArmorItemModel);

            ArmoryClientProxy.registerHeatedItemItemModel((ItemHeatedItem) ModItems.heatedItem);
            ArmoryClientProxy.registerComponentItemModel((ItemArmorComponent) ModItems.armorComponent);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalChain);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalPlate);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalRing);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalNugget);

            OBJLoader.INSTANCE.addDomain(References.General.MOD_ID.toLowerCase());
            ModelLoader.setCustomModelResourceLocation(ModItems.guide, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));

            MultiComponentModelLoader.instance.registerDomain(References.General.MOD_ID);
            CoreClientProxy.registerMultiComponentItemModel(ModItems.tongs, new ResourceLocation("armory", "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
            CoreClientProxy.registerMultiComponentItemModel(ModItems.hammer, new ResourceLocation("armory", "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
        }


        public static void registerTESR() {
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockForge);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockBlackSmithsAnvil);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockFirePlace);
        }
    }


}