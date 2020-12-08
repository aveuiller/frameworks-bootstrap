package io.github.aveuiller.experiment.delivery.thirdparty;

import io.github.aveuiller.experiment.delivery.Delivery;
import io.github.aveuiller.experiment.delivery.DeliveryState;
import org.springframework.stereotype.Component;

@Component
public class MondialRelay implements ThirdPartyService {
    @Override
    public DeliveryState getState(Delivery delivery) {
        return DeliveryState.DELIVERING;
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
