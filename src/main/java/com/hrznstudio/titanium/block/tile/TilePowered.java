/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * This means no, you cannot use this code. This is licensed for sole use by Horizon and it's partners, you MUST be granted specific written permission from Horizon to use this code.
 */

package com.hrznstudio.titanium.block.tile;

import com.google.common.collect.Sets;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.block.BlockTileBase;
import com.hrznstudio.titanium.energy.NBTEnergyHandler;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class TilePowered extends TileActive {

    @Save
    private NBTEnergyHandler energyHandler;
    private LazyOptional<IEnergyStorage> energyCap;

    public TilePowered(BlockTileBase blockTileBase) {
        super(blockTileBase);
        this.energyHandler = getEnergyHandlerFactory().create();
        this.energyCap = LazyOptional.of(() -> this.energyHandler);
    }

    protected IFactory<NBTEnergyHandler> getEnergyHandlerFactory() {
        return () -> new NBTEnergyHandler(this, 10000);
    }

    public IEnergyStorage getEnergyStorage() {
        return energyHandler;
    }

    public Set<EnumFacing> getValidEnergyFaces() {
        return Sets.newHashSet(EnumFacing.values());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
        if (cap == CapabilityEnergy.ENERGY)
            return energyCap.cast();
        return super.getCapability(cap, side);
    }
}
