package com.pcz.spittr.data;

import com.pcz.spittr.Spitter;

/**
 * @author picongzhi
 */
public interface SpitterRepository {
    Spitter save(Spitter spitter);

    Spitter findByUsername(String username);
}
