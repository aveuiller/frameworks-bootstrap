package io.github.aveuiller.experiment.delivery;

import io.github.aveuiller.experiment.delivery.thirdparty.AvailableThirdParty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    DeliveryState state;

    @Column(nullable = false)
    @NotNull
    String externalId;

    @Column(nullable = false)
    @NotNull
    String location;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    AvailableThirdParty thirdParty;

    // TODO: Watch out, this date is not persisted as UTC.
    @CreationTimestamp
    Date createdAt;

    public Delivery() {

    }

    public Delivery(@NotNull DeliveryState state, @NotNull String externalId, @NotNull String location, @NotNull AvailableThirdParty thirdParty) {
        this.state = state;
        this.externalId = externalId;
        this.location = location;
        this.thirdParty = thirdParty;
    }

    public long getId() {
        return id;
    }

    public DeliveryState getState() {
        return state;
    }

    public void setState(DeliveryState state) {
        this.state = state;
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

    public AvailableThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(AvailableThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
