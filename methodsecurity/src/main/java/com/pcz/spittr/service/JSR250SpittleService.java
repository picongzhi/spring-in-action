package com.pcz.spittr.service;

import com.pcz.spittr.domain.Spittle;

import javax.annotation.security.RolesAllowed;

/**
 * @author picongzhi
 */
public class JSR250SpittleService implements SpittleService {
    @Override
    @RolesAllowed("ROLE_SPITTER")
    public void addSpittle(Spittle spittle) {
        System.out.println("Method was called successfully");
    }
}
