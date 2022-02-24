package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageLignesRepository extends JpaRepository<VoyageLignes, VoyagePK> {

    VoyageLignes findByVoyagePK(final VoyagePK voyagePK);

    List<VoyageLignes> findByDeletedFalse();

    List<VoyageLignes> findByDeletedTrue();

    List<VoyageLignes> findByDeletedFalse(Pageable pageable);

    @Query("SELECT v FROM VoyageLignes v WHERE v.deleted = false AND v.voyagePK.voyage=?1")
    List<VoyageLignes> findByVoyage(Voyages voyage);

    @Query("SELECT v FROM VoyageLignes v WHERE v.deleted = false AND v.voyagePK.voyage=?1")
    List<VoyageLignes> findByVoyage(Voyages voyage, Pageable pageable);

    @Query("SELECT v FROM VoyageLignes v WHERE v.deleted = false AND v.voyagePK.bus=?1")
    List<VoyageLignes> findByBus(Bus bus, Pageable pageable);

}
