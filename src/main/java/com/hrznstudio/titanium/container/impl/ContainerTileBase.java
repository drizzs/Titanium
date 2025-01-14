/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>.
 *
 * This code is licensed under GNU Lesser General Public License v3.0, the full license text can be found in LICENSE.txt
 */

package com.hrznstudio.titanium.container.impl;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.block.tile.TileActive;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.addon.interfaces.INetworkable;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.hrznstudio.titanium.network.NetworkHandler;
import com.hrznstudio.titanium.network.locator.ILocatable;
import com.hrznstudio.titanium.network.locator.LocatorInstance;
import com.hrznstudio.titanium.network.locator.instance.TileEntityLocatorInstance;
import com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;

public class ContainerTileBase extends ContainerInventoryBase implements ILocatable {

    @ObjectHolder("titanium:tile_container")
    public static ContainerType<ContainerTileBase> TYPE;

    private TileActive tile;
    private boolean hasPlayerInventory;

    public ContainerTileBase(int id, PlayerInventory player, PacketBuffer buffer) {
        this((TileActive) player.player.getEntityWorld().getTileEntity(buffer.readBlockPos()), player, id);
    }

    public ContainerTileBase(TileActive tile, PlayerInventory inventory, int id) {
        super(TYPE, inventory, id, tile.getAssetProvider());
        this.tile = tile;
        addTileSlots();
        addHotbarSlots(IAssetProvider.getAsset(tile.getAssetProvider(), AssetTypes.BACKGROUND).getHotbarPosition());
    }

    public void addHotbarSlots(Point hotbarPos) {
        for (int k = 0; k < 9; k++) {
            addSlot(new Slot(getPlayerInventory(), k, hotbarPos.x + k * 18, hotbarPos.y));
        }
    }

    public void addTileSlots() {
        if (tile.getMultiInventoryHandler() != null) {
            for (PosInvHandler handler : tile.getMultiInventoryHandler().getInventoryHandlers()) {
                int i = 0;
                for (int y = 0; y < handler.getYSize(); ++y) {
                    for (int x = 0; x < handler.getXSize(); ++x) {
                        addSlot(new SlotItemHandler(handler, i, handler.getXPos() + handler.getSlotPosition().apply(i).getLeft(), handler.getYPos() + handler.getSlotPosition().apply(i).getRight()));
                        ++i;
                    }
                }
            }
        }
    }

    public void updateSlotPosition() {
        if (tile.getMultiInventoryHandler() != null) {
            for (PosInvHandler handler : tile.getMultiInventoryHandler().getInventoryHandlers()) {
                int i = 0;
                for (int y = 0; y < handler.getYSize(); ++y) {
                    for (int x = 0; x < handler.getXSize(); ++x) {
                        for (Slot inventorySlot : this.inventorySlots) {
                            if (!(inventorySlot instanceof SlotItemHandler)) continue;
                            if (((SlotItemHandler) inventorySlot).getItemHandler().equals(handler) && i == inventorySlot.getSlotIndex()) {
                                inventorySlot.xPos = handler.getXPos() + handler.getSlotPosition().apply(i).getLeft();
                                inventorySlot.yPos = handler.getYPos() + handler.getSlotPosition().apply(i).getRight();
                                break;
                            }
                        }
                        ++i;
                    }
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public TileActive getTile() {
        return tile;
    }

    @Override
    public LocatorInstance getLocatorInstance() {
        return new TileEntityLocatorInstance(tile.getPos());
    }
}
