package com.pcz.spittr.db;

import com.pcz.spittr.domain.Spittle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpittleRepository extends JpaRepository<Spittle, Long>, SpittleRepositoryCustom {
    List<Spittle> findBySpitterId(long spitterId);
}
