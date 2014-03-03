package com.captainbern.common.protocol.compat;

import com.captainbern.common.utils.PluginDependencyProvider;
import org.bukkit.plugin.Plugin;

public class ProtocolLib extends PluginDependencyProvider {

    public ProtocolLib(Plugin myPluginInstance, String dependencyName) {
        super(myPluginInstance, dependencyName);
    }

    // TODO: add ProtocolLib to dependencies
}
