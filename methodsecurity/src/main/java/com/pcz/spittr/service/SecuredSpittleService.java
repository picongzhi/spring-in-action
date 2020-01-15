package com.pcz.spittr.service;

import com.pcz.spittr.domain.Spittle;
import org.springframework.security.access.annotation.Secured;

/**
 * @author picongzhi
 */
public class SecuredSpittleService implements SpittleService {
    @Override
    @Secured({"ROLE_SPITTER", "ROLE_ADMIN"})
    public void addSpittle(Spittle spittle) {
        System.out.println("Method was called successfully");
    }
}
