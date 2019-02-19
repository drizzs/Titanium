package com.hrznstudio.titanium.fluid;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.api.client.IGuiAddonProvider;
import com.hrznstudio.titanium.client.gui.addon.TankGuiAddon;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MultiTankHandler implements IGuiAddonProvider {

    private List<PosFluidTank> tanks;

    public MultiTankHandler() {
        tanks = new ArrayList<>();
    }

    public void addTank(PosFluidTank tank) {
        this.tanks.add(tank);
    }

    @Override
    public List<IFactory<? extends IGuiAddon>> getGuiAddons() {
        List<IFactory<? extends IGuiAddon>> addons = new ArrayList<>();
        for (PosFluidTank tank : tanks) {
            addons.add(() -> new TankGuiAddon(tank));
        }
        return addons;
    }

    public MultiTankCapabilityHandler getCapabilityForSide(EnumFacing facing) {
        if (facing == null)
            return new MultiTankCapabilityHandler(tanks);
        List<PosFluidTank> tanks = new ArrayList<>();
        for (PosFluidTank tank : this.tanks) {
            if (tank.getFacingModes().containsKey(facing) && tank.getFacingModes().get(facing).allowsConnection()) {
                tanks.add(tank);
            }
        }
        return new MultiTankCapabilityHandler(tanks);
    }

    public static class MultiTankCapabilityHandler implements IFluidHandler {

        private final List<PosFluidTank> tanks;

        public MultiTankCapabilityHandler(List<PosFluidTank> tanks) {
            this.tanks = tanks;
        }

        @Override
        public IFluidTankProperties[] getTankProperties() {
            IFluidTankProperties[] properties = new IFluidTankProperties[tanks.size()];
            for (int i = 0; i < tanks.size(); i++) {
                properties[i] = tanks.get(i).getTankProperties()[0];
            }
            return properties;
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (resource == null) return 0;
            for (PosFluidTank tank : tanks) {
                if (tank.canFill() && tank.canFillFluidType(resource) && tank.fill(resource, false) != 0)
                    return tank.fill(resource, doFill);
            }
            return 0;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null) return null;
            for (PosFluidTank tank : tanks) {
                if (tank.canDrain() && tank.canDrainFluidType(resource) && tank.drain(resource, false) != null)
                    return tank.drain(resource, doDrain);
            }
            return null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            for (PosFluidTank tank : tanks) {
                if (tank.canDrain() && tank.drain(maxDrain, false) != null) return tank.drain(maxDrain, doDrain);
            }
            return null;
        }

        public boolean isEmpty() {
            return tanks.isEmpty();
        }
    }
}