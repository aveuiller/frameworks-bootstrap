package io.github.aveuiller.experiment.delivery.thirdparty;

import io.github.aveuiller.experiment.delivery.Delivery;
import io.github.aveuiller.experiment.delivery.DeliveryState;
import org.springframework.stereotype.Component;

@Component
public class RelaisColis implements ThirdPartyService {
    @Override
    public DeliveryState getState(Delivery delivery) {
        return DeliveryState.RETURNED;
    }

    @Override
    public void send(Delivery delivery) {
        // TODO
    }

    @Override
    public void cancel(Delivery delivery) {
        // TODO
    }
}
