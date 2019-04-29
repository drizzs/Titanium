/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * This means no, you cannot use this code. This is licensed for sole use by Horizon and it's partners, you MUST be granted specific written permission from Horizon to use this code.
 */

package com.hrznstudio.titanium.block.tile.inventory;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.api.client.IGuiAddonProvider;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class PosInvHandler extends ItemStackHandler implements IGuiAddonProvider {

    private final String name;
    private int xPos;
    private int yPos;
    private int xSize;
    private int ySize;
    private TileEntity tileEntity;
    private BiPredicate<ItemStack, Integer> insertPredicate;
    private BiPredicate<ItemStack, Integer> extractPredicate;
    private BiConsumer<ItemStack, Integer> onSlotChanged;

    public PosInvHandler(String name, int xPos, int yPos, int size) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setSize(size);
        this.setRange(size, 1);
        this.insertPredicate = (stack, integer) -> true;
        this.extractPredicate = (stack, integer) -> true;
        this.onSlotChanged = (stack, integer) -> {
        };
    }

    /**
     * Defines how many slots/row and slots/column
     *
     * @param x How many slots there are horizontally
     * @param y How many slots there are vertically
     * @return itself
     */
    public PosInvHandler setRange(int x, int y) {
        this.xSize = x;
        this.ySize = y;
        return this;
    }

    /**
     * Sets the tile where the inventory is to allow markForUpdate automatically
     *
     * @param tile The tile to mark
     * @return itself
     */
    public PosInvHandler setTile(TileEntity tile) {
        this.tileEntity = tile;
        return this;
    }

    /**
     * Sets the predicate input filter to filter what items go into which slot.
     *
     * @param predicate A bi predicate where the itemstack is the item trying to be inserted and the slot where is trying to be inserted to
     * @return itself
     */
    public PosInvHandler setInputFilter(BiPredicate<ItemStack, Integer> predicate) {
        this.insertPredicate = predicate;
        return this;
    }

    /**
     * Sets the predicate output filter to filter what can be extracted from which slot.
     *
     * @param predicate A bi predicate where the itemstack is the item trying to be extracted and the slot where is trying to be extracted
     * @return itself
     */
    public PosInvHandler setOutputFilter(BiPredicate<ItemStack, Integer> predicate) {
        this.extractPredicate = predicate;
        return this;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!insertPredicate.test(stack, slot)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (this.tileEntity != null) tileEntity.markDirty();
        onSlotChanged.accept(getStackInSlot(slot), slot);
    }

    public String getName() {
        return name;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public TileEntity getTileEntity() {
        return tileEntity;
    }

    public BiPredicate<ItemStack, Integer> getInsertPredicate() {
        return insertPredicate;
    }

    public BiPredicate<ItemStack, Integer> getExtractPredicate() {
        return extractPredicate;
    }

    public BiConsumer<ItemStack, Integer> getOnSlotChanged() {
        return onSlotChanged;
    }

    /**
     * Sets the predicate slot changed that gets triggered when a slot is changed.
     *
     * @param onSlotChanged A bi predicate where the itemstack and slot changed
     * @return itself
     */
    public PosInvHandler setOnSlotChanged(BiConsumer<ItemStack, Integer> onSlotChanged) {
        this.onSlotChanged = onSlotChanged;
        return this;
    }

    @Override
    public List<IFactory<? extends IGuiAddon>> getGuiAddons() {
        return Collections.singletonList(() -> new SlotsGuiAddon(this));
    }
}