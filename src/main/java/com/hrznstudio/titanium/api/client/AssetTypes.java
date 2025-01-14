/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>.
 *
 * This code is licensed under GNU Lesser General Public License v3.0, the full license text can be found in LICENSE.txt
 */

package com.hrznstudio.titanium.api.client;

import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.api.client.assets.types.ITankAsset;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;

public class AssetTypes {
    public static final IAssetType<IAsset> BUTTON_SIDENESS_DISABLED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_ENABLED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_PULL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_PUSH = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<ITankAsset> TANK_NORMAL = new GenericAssetType<ITankAsset>(IAssetProvider.DEFAULT_PROVIDER::getAsset, ITankAsset.class);
    public static final IAssetType<ITankAsset> TANK_SMALL = new GenericAssetType<ITankAsset>(IAssetProvider.DEFAULT_PROVIDER::getAsset, ITankAsset.class);
    public static final IAssetType<IBackgroundAsset> BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IBackgroundAsset.class);
    public static final IAssetType<IAsset> SLOT = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> ENERGY_BAR = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> ENERGY_BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BORDER_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BACKGROUND_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_HORIZONTAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BACKGROUND_HORIZONTAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_MANAGER = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> AUGMENT_BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
}
