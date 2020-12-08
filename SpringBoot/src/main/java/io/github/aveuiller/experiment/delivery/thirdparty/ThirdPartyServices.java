package io.github.aveuiller.experiment.delivery.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThirdPartyServices {
    private final Map<AvailableThirdParty, ThirdPartyService> services;

    @Autowired
    public ThirdPartyServices(MondialRelay mondialRelay,
                              RelaisColis relaisColis) {
        services = new HashMap<>();
        services.put(AvailableThirdParty.MONDIAL_RELAY, mondialRelay);
        services.put(AvailableThirdParty.RELAIS_COLIS, relaisColis);
    }

    public ThirdPartyService forName(AvailableThirdParty thirdParty) {
        return services.get(thirdParty);
    }

    public ThirdPartyService forName(String thirdParty) {
        return this.forName(AvailableThirdParty.valueOf(thirdParty));
    }
}
