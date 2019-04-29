/*
 * This file is part of Titanium
 * Copyright (C) 2019, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * This means no, you cannot use this code. This is licensed for sole use by Horizon and it's partners, you MUST be granted specific written permission from Horizon to use this code.
 */

package com.hrznstudio.titanium.client.gui;

import com.hrznstudio.titanium.api.client.IGuiAddon;

import java.util.List;

public interface IGuiAddonConsumer {

    List<IGuiAddon> getAddons();
}
