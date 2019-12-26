package com.pcz.spittr.db.domain;

import java.util.Date;

/**
 * @author picongzhi
 */
public class Spittle {
    private final Long id;
    private final Spitter spitter;
    private final String message;
    private final Date postedTime;

    public Spittle(Long id, Spitter spitter, String message, Date postedTime) {
        this.id = id;
        this.spitter = spitter;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Spitter getSpitter() {
        return spitter;
    }

    public Date getPostedTime() {
        return postedTime;
    }
}
