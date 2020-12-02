package io.github.aveuiller.experiment.delivery;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Long> {

}