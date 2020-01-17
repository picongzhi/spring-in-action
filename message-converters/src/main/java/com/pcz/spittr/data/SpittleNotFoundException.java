package com.pcz.spittr.data;

/**
 * @author picongzhi
 */
public class SpittleNotFoundException extends RuntimeException {
    private long spitterId;

    public SpittleNotFoundException(long spittleId) {
        this.spitterId = spittleId;
    }

    public long getSpitterId() {
        return spitterId;
    }
}
