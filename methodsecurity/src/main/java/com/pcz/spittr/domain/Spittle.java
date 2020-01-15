package com.pcz.spittr.domain;

import java.util.Date;

/**
 * @author picongzhi
 */
public class Spittle {
    private final Long id;
    private final String text;
    private final Date postedTime;
    private Spitter spitter;

    public Spittle(Long id, Spitter spitter, String text, Date postedTime) {
        this.id = id;
        this.spitter = spitter;
        this.text = text;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public Spitter getSpitter() {
        return spitter;
    }
}
