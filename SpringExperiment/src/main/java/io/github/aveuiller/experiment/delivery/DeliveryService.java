package io.github.aveuiller.experiment.delivery;

import io.github.aveuiller.experiment.delivery.thirdparty.ThirdPartyService;
import io.github.aveuiller.experiment.delivery.thirdparty.ThirdPartyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Define all operations on deliveries.
 */
@Component
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ThirdPartyServices thirdPartyServices;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository,
                           ThirdPartyServices thirdPartyServices) {
        this.deliveryRepository = deliveryRepository;
        this.thirdPartyServices = thirdPartyServices;
    }

    public Page<Delivery> findAll(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }

    public Optional<Delivery> findOne(long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        delivery.ifPresent(x -> {
            ThirdPartyService thirdParty = thirdPartyServices.forName(x.thirdParty);
            x.setState(thirdParty.getState(x));
        });
        return delivery;
    }

    /**
     * Create the {@link Delivery} in database and returns the up to date version.
     *
     * @param delivery
     * @return
     */
    public Delivery create(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Optional<Delivery> delete(long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        delivery.ifPresent(deliveryRepository::delete);
        return delivery;
    }
}
