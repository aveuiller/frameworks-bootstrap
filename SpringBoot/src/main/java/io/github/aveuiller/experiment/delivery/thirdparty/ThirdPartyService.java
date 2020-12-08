package io.github.aveuiller.experiment.delivery.thirdparty;

import io.github.aveuiller.experiment.delivery.Delivery;
import io.github.aveuiller.experiment.delivery.DeliveryState;

public interface ThirdPartyService {
    /**
     * @param delivery
     * @return
     */
    DeliveryState getState(Delivery delivery);

    /**
     * @param delivery
     */
    void send(Delivery delivery);

    /**
     * @param delivery
     */
    void cancel(Delivery delivery);
}
