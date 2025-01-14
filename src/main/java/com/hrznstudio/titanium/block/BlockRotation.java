/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>.
 *
 * This code is licensed under GNU Lesser General Public License v3.0, the full license text can be found in LICENSE.txt
 */

package com.hrznstudio.titanium.block;

import com.hrznstudio.titanium.block.tile.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockRotation<T extends TileBase> extends BlockTileBase<T> {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
    public static final DirectionProperty SUB_FACING = DirectionProperty.create("subfacing", Direction.Plane.HORIZONTAL);

    public BlockRotation(String name, Properties properties, Class<T> tileClass) {
        super(name, properties, tileClass);
    }

    @Nonnull
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return getRotationType().getHandler().getStateForPlacement(this, p_196258_1_);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        super.fillStateContainer(p_206840_1_);
        if (this.getRotationType().getProperties() != null) p_206840_1_.add(this.getRotationType().getProperties());
    }

    public enum RotationType {
        NONE((block, context) -> block.getDefaultState()),
        FOUR_WAY(((block, context) -> block.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())), FACING),
        SIX_WAY((block, context) -> block.getDefaultState().with(FACING, context.getNearestLookingDirection()), FACING),
        TWENTY_FOUR_WAY((block, context) -> {
            //TODO: Sub facing
            return block.getDefaultState().with(FACING, context.getNearestLookingDirection());
        }, FACING, SUB_FACING);

        private final RotationHandler handler;
        private final DirectionProperty[] properties;

        RotationType(RotationHandler handler, DirectionProperty... properties) {
            this.handler = handler;
            this.properties = properties;
        }

        public RotationHandler getHandler() {
            return handler;
        }

        public DirectionProperty[] getProperties() {
            return properties;
        }
    }
}
