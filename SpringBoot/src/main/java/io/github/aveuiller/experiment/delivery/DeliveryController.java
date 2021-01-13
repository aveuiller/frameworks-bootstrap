package io.github.aveuiller.experiment.delivery;

import io.github.aveuiller.experiment.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/delivery",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<Delivery>> index(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 50);
        return new ResponseEntity<>(deliveryService.findAll(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Delivery> post(@Valid @RequestBody Delivery delivery) throws ApiException {
        try {
            delivery = deliveryService.create(delivery);
        } catch (Exception e) {
            // TODO: We may want a Map constructor to build a complete error json.
            throw new ApiException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return new ResponseEntity<>(delivery, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Delivery> get(@PathVariable long id) throws ApiException {
        Optional<Delivery> delivery = deliveryService.findOne(id);
        if (delivery.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, String.format("Delivery %d not found", id));
        }
        return new ResponseEntity<>(delivery.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Delivery> delete(@PathVariable long id) throws ApiException {
        Optional<Delivery> delivery = deliveryService.delete(id);
        if (delivery.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, String.format("Delivery %d not found", id));
        }
        return new ResponseEntity<>(delivery.get(), HttpStatus.OK);
    }
}
