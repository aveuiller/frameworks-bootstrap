package io.github.aveuiller.experiment.delivery;

import io.github.aveuiller.experiment.delivery.thirdparty.AvailableThirdParty;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryServiceTest {

    @MockBean
    private DeliveryRepository repository;

    @Autowired
    private DeliveryService service;

    @Test
    void testDeliveryCreate() {
        final int expectedId = 42;
        Delivery delivery = new Delivery(DeliveryState.RETURNED, "extid", "Paris", AvailableThirdParty.MONDIAL_RELAY);
        Mockito.when(repository.save(delivery)).then(call -> {
            Delivery del = call.getArgument(0);
            del.id = expectedId;
            return del;
        });

        delivery = service.create(delivery);

        assertEquals(expectedId, delivery.id);
    }
}