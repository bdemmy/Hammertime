package com.bdemmy.hammertime;

import com.bdemmy.hammertime.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class ModHammertime implements ModInitializer {
    public static final String MOD_ID = "hammertime";

    @Override
    public void onInitialize() {
        ModItems.init();
    }
}
