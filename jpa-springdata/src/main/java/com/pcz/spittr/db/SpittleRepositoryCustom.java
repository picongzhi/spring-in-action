package com.pcz.spittr.db;

import com.pcz.spittr.domain.Spittle;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpittleRepositoryCustom {
    List<Spittle> findRecent();

    List<Spittle> findRecent(int count);
}
