/*
 * This file is part of Titanium
 * Copyright (C) 2018, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 */
package com.hrznstudio.titanium.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.function.Supplier;

public final class SidedHandler {
    private SidedHandler() {
    }

    public static Side getSide() {
        return FMLCommonHandler.instance().getSide();
    }

    public static void runOn(Side side, Supplier<Runnable> toRun) {
        if (side == getSide()) {
            toRun.get().run();
        }
    }

    public static <T> T getSided(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        if (getSide().isClient()) {
            return clientTarget.get().get();
        } else {
            return serverTarget.get().get();
        }
    }
}