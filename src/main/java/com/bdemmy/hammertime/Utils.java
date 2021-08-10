package com.bdemmy.hammertime;

import com.bdemmy.hammertime.item.HammerItem;
import com.bdemmy.hammertime.item.SpadeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.bdemmy.hammertime.Settings.MAX_HAMMER_DELTA_HARDNESS;

public class Utils {
    public static boolean isValidEdgeBlock(World world, PlayerEntity player, BlockState sourceBlock, BlockPos targetBlock) {
        BlockState targetState = world.getBlockState(targetBlock);

        if (blockNotValidForSpecialTool(world, targetBlock, player)) {
            return false;
        }

        if (targetState.getHardness(null, targetBlock) - sourceBlock.getHardness(null, targetBlock) > MAX_HAMMER_DELTA_HARDNESS) {
            return false;
        }

        return !targetState.hasBlockEntity() && targetState.getHardness(null, targetBlock) != 0.f;
    }

    public static List<BlockPos> collectAdjacentToPlayerBlockTarget(PlayerEntity player, World world, BlockPos sourcePos) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult res = getPlayerBlockTarget(player, world);
        if (res.getType() != HitResult.Type.BLOCK) {
            return positions;
        }

        Direction hitSide = res.getSide();

        BlockPos curPos;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (hitSide == Direction.EAST || hitSide == Direction.WEST) {
                    curPos = sourcePos.up(i).north(j);
                } else if (hitSide == Direction.SOUTH || hitSide == Direction.NORTH) {
                    curPos = sourcePos.up(i).east(j);
                } else {
                    curPos = sourcePos.north(i).east(j);
                }

                if (isValidEdgeBlock(world, player, world.getBlockState(sourcePos), curPos)) {
                    positions.add(curPos);
                }
            }
        }

        return positions;
    }

    public static boolean playerNotValidForSpecialTool(Entity entity) {
        if (!(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isCreative()) {
            return true;
        }

        PlayerEntity player = (PlayerEntity) entity;
        ItemStack handStack = player.getMainHandStack();

        return !(handStack.getItem() instanceof HammerItem) && !(handStack.getItem() instanceof SpadeItem);
    }

    public static boolean blockNotValidForSpecialTool(World world, BlockPos pos, PlayerEntity player) {
        BlockState state = world.getBlockState(pos);
        ItemStack mainHand = player.getMainHandStack();

        // Make sure the tool is effective on the block
        if (mainHand.getItem().getMiningSpeedMultiplier(mainHand, state) <= 1.01f) {
            return true;
        }

        // And then make sure that the block has a real hardness value.
        return state.getHardness(null, pos) == 0.f;
    }

    public static BlockHitResult getPlayerBlockTarget(PlayerEntity player, World world) {
        Vec3d eyeVector       = new Vec3d(player.getX(), player.getEyeY(), player.getZ());
        Vec3d rotationVector  = player.getRotationVector();
        Vec3d directionVector = eyeVector.add(rotationVector.x * 15.f, rotationVector.y * 15.f, rotationVector.z * 15.f);
        return world.raycast(new RaycastContext(eyeVector, directionVector, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }
}
