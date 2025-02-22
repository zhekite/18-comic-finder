package vip.comic18.finder.config;

import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

/**
 * Created by jiayao on 2021/3/24.
 */
@ApplicationScoped
public class ComicConfig {
    @ConfigProperty(name = "comic.proxy.host")
    Optional<String> proxyHost;

    @ConfigProperty(name = "comic.proxy.port")
    Optional<Integer> proxyPort;

    @Inject
    Vertx vertx;

    @Produces
    @Named("webClient")
    public WebClient getWebClient() {
        var webClientOptions = new WebClientOptions();
        if(proxyHost.isPresent() && proxyPort.isPresent()) {
            webClientOptions.setProxyOptions(new ProxyOptions().setHost(proxyHost.get()).setPort(proxyPort.get()).setType(ProxyType.HTTP));
        }
        webClientOptions.setVerifyHost(false);
        webClientOptions.setSsl(true);
        return WebClient.create(vertx, webClientOptions);
    }
}
