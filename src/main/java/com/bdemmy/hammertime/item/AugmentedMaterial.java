package com.bdemmy.hammertime.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class AugmentedMaterial implements ToolMaterial {
    private final ToolMaterial referenceMaterial;

    public AugmentedMaterial(ToolMaterial referenceMaterial) {
        this.referenceMaterial = referenceMaterial;
    }

    @Override
    public int getDurability() {
        return (int)(referenceMaterial.getDurability() * 3.1132f);
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.referenceMaterial.getMiningSpeedMultiplier() * 0.6f;
    }

    @Override
    public float getAttackDamage() {
        return this.referenceMaterial.getAttackDamage();
    }

    @Override
    public int getMiningLevel() {
        return this.referenceMaterial.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return this.referenceMaterial.getEnchantability();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.referenceMaterial.getRepairIngredient();
    }
}
