package com.hrznstudio.titanium.util;

import com.hrznstudio.titanium.block.BlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RayTraceUtils {

    public static RayTraceResult rayTraceSimple(World world, LivingEntity living, double blockReachDistance, float partialTicks) {
        Vec3d vec3d = living.getEyePosition(partialTicks);
        Vec3d vec3d1 = living.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, living));
    }

    @Nullable
    public static VoxelShape rayTraceVoxelShape(BlockRayTraceResult original, World world, LivingEntity living, double blockReachDistance, float partialTicks) {
        BlockState og = Minecraft.getInstance().world.getBlockState(original.getPos());
        if (og.getBlock() instanceof BlockBase && ((BlockBase) og.getBlock()).hasIndividualRenderVoxelShape()) {
            Vec3d vec3d = living.getEyePosition(partialTicks);
            Vec3d vec3d1 = living.getLook(partialTicks);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
            List<VoxelShape> voxelShapes = ((BlockBase) og.getBlock()).getBoundingBoxes(og, Minecraft.getInstance().world, ((BlockRayTraceResult) original).getPos());
            VoxelShape closest = voxelShapes.get(0);
            double distance = Double.MAX_VALUE;
            for (VoxelShape voxelShape : voxelShapes) {
                BlockRayTraceResult result = voxelShape.rayTrace(vec3d, vec3d2, ((BlockRayTraceResult) original).getPos());
                if (result != null && vec3d.distanceTo(result.getHitVec()) < distance) {
                    closest = voxelShape;
                    distance = vec3d.distanceTo(result.getHitVec());
                }
            }
            return closest;
        }
        return null;
    }
}