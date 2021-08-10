package com.bdemmy.hammertime.item;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class HammerItem extends PickaxeItem {
    public HammerItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
