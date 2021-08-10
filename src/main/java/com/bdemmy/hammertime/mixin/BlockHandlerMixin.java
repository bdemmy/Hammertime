package com.bdemmy.hammertime.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.bdemmy.hammertime.Utils.*;

@Mixin(Block.class)
public abstract class BlockHandlerMixin {
    private static boolean mixinFlag = false;

    @Inject(at = @At(value = "HEAD"), method = "onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V")
    private void onBreakMixin(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (!world.isClient) {
            if (!mixinFlag) {
                mixinFlag = true;

                handleAugmentedTool(world, pos, player);

                mixinFlag = false;
            }
        }
    }

    private void handleAugmentedTool(World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient) {
            return;
        }

        if (playerNotValidForSpecialTool(player) || blockNotValidForSpecialTool(world, pos, player)) {
            return;
        }

        collectAdjacentToPlayerBlockTarget(player, world, pos).forEach(
                ((ServerPlayerEntity) player).interactionManager::tryBreakBlock
        );
    }
}
