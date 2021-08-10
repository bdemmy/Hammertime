package com.bdemmy.hammertime.item;

import com.bdemmy.hammertime.ModHammertime;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static ToolMaterial AUGMENTED_DIAMOND_MATERIAL = new AugmentedMaterial(ToolMaterials.DIAMOND);
    public static HammerItem DIAMOND_HAMMER = new HammerItem(AUGMENTED_DIAMOND_MATERIAL, 2, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static SpadeItem DIAMOND_SPADE = new SpadeItem(AUGMENTED_DIAMOND_MATERIAL, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));

    public static ToolMaterial AUGMENTED_GOLDEN_MATERIAL = new AugmentedMaterial(ToolMaterials.GOLD);
    public static HammerItem GOLDEN_HAMMER = new HammerItem(AUGMENTED_GOLDEN_MATERIAL, 2, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static SpadeItem GOLDEN_SPADE = new SpadeItem(AUGMENTED_GOLDEN_MATERIAL, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));

    public static ToolMaterial AUGMENTED_IRON_MATERIAL = new AugmentedMaterial(ToolMaterials.IRON);
    public static HammerItem IRON_HAMMER = new HammerItem(AUGMENTED_IRON_MATERIAL, 2, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static SpadeItem IRON_SPADE = new SpadeItem(AUGMENTED_IRON_MATERIAL, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));

    public static ToolMaterial AUGMENTED_STONE_MATERIAL = new AugmentedMaterial(ToolMaterials.STONE);
    public static HammerItem STONE_HAMMER = new HammerItem(AUGMENTED_STONE_MATERIAL, 2, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static SpadeItem STONE_SPADE = new SpadeItem(AUGMENTED_STONE_MATERIAL, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));

    public static ToolMaterial AUGMENTED_WOODEN_MATERIAL = new AugmentedMaterial(ToolMaterials.WOOD);
    public static HammerItem WOODEN_HAMMER = new HammerItem(AUGMENTED_WOODEN_MATERIAL, 2, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static SpadeItem WOODEN_SPADE = new SpadeItem(AUGMENTED_WOODEN_MATERIAL, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "diamond_hammer"), DIAMOND_HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "diamond_spade"), DIAMOND_SPADE);

        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "golden_hammer"), GOLDEN_HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "golden_spade"), GOLDEN_SPADE);

        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "iron_hammer"), IRON_HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "iron_spade"), IRON_SPADE);

        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "stone_hammer"), STONE_HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "stone_spade"), STONE_SPADE);

        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "wooden_hammer"), WOODEN_HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ModHammertime.MOD_ID, "wooden_spade"), WOODEN_SPADE);
    }
}
