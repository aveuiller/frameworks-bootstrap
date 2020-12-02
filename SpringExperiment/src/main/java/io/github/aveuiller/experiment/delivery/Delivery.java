package io.github.aveuiller.experiment.delivery;

import io.github.aveuiller.experiment.delivery.thirdparty.AvailableThirdParty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String state;
    String externalId;
    String location;
    String thirdParty;

    // TODO: Watch out, this date is not persisted as UTC.
    @CreationTimestamp
    Date createdAt;

    public Delivery() {

    }

    public long getId() {
        return id;
    }

    public void setState(DeliveryState state) {
        this.state = state.name();
    }

    public DeliveryState getState() {
        return DeliveryState.valueOf(this.state);
    }

    public AvailableThirdParty getThirdParty() {
        return AvailableThirdParty.valueOf(thirdParty);
    }

    public void setThirdParty(AvailableThirdParty thirdParty) {
        this.thirdParty = thirdParty.name();
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
