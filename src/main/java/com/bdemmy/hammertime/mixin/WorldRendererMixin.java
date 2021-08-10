package com.bdemmy.hammertime.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.SortedSet;

import static com.bdemmy.hammertime.Utils.*;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j){}

    @Shadow
    private ClientWorld world;

    @Shadow
    private MinecraftClient client;

    @Inject(method = "drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("HEAD"))
    public void mixinDrawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        BlockOutlineHandler(matrixStack, vertexConsumer, entity, d, e, f, blockPos);
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILSOFT,
            method = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockRenderManager;renderDamage(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"))
    public void mixinRenderInvokeRenderDamage(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci, Profiler profiler, Vec3d vec3d, double d, double e, double f, Matrix4f matrix4f2, boolean bl, Frustum frustum2, boolean bl3, VertexConsumerProvider.Immediate immediate, ObjectIterator var39, Long2ObjectMap.Entry entry2, BlockPos blockPos3, SortedSet sortedSet2, int z, MatrixStack.Entry entry3, VertexConsumer vertexConsumer2) {
        BlockDamageHandler(client.player, blockPos3, matrices, vertexConsumer2);
    }

    public void BlockDamageHandler(ClientPlayerEntity player, BlockPos pos, MatrixStack matrices, VertexConsumer consumer) {
        if (playerNotValidForSpecialTool(player)) {
            return;
        }

        collectAdjacentToPlayerBlockTarget(player, world, pos).forEach(
                (BlockPos curPos) -> {
                    matrices.push();

                    Vec3i translate = new Vec3i(curPos.getX() - pos.getX(), curPos.getY() - pos.getY(), curPos.getZ() - pos.getZ());

                    matrices.translate(translate.getX(), translate.getY(), translate.getZ());

                    this.client.getBlockRenderManager().renderDamage(this.world.getBlockState(curPos), curPos, this.world, matrices, consumer);

                    matrices.pop();
                }
        );
    }

    public void BlockOutlineHandler(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity player, double d, double e, double f, BlockPos pos) {
        if (playerNotValidForSpecialTool(player)) {
            return;
        }

        collectAdjacentToPlayerBlockTarget((PlayerEntity) player, world, pos).forEach(
                (BlockPos curPos) -> {
                    BlockState curState = world.getBlockState(curPos);

                    drawShapeOutline(matrixStack, vertexConsumer, curState.getOutlineShape(world, curPos, ShapeContext.of(player)), (double)curPos.getX() - d, (double)curPos.getY() - e, (double)curPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.7F);
                }
        );
    }
}
