package io.github.aveuiller.learning.kafka.raw;

import java.io.Serializable;

public class HelloMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private long userId;
    private String message;
    private float value;
    private int version;

    public HelloMessage(long userId, String message, float value, int version) {
        this.userId = userId;
        this.message = message;
        this.value = value;
        this.version = version;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
