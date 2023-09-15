package live.nerotv.api.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import live.nerotv.api.utils.Strings;
import org.slf4j.Logger;

@Plugin(
        id = "neroapi",
        name = "NeroAPI",
        version = Strings.api_version
)
public class ProxiedNero {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }
}
