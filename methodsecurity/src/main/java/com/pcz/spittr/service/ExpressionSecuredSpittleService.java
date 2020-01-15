package com.pcz.spittr.service;

import com.pcz.spittr.domain.Spittle;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author picongzhi
 */
public class ExpressionSecuredSpittleService implements SpittleService {
    @Override
    @PreAuthorize("(hasRole('ROLE_SPITTER') and #spittle.text.length() le 140) or hasRole('ROLE_PREMIUM')")
    public void addSpittle(Spittle spittle) {
        System.out.println("Method was called successfully");
    }
}
