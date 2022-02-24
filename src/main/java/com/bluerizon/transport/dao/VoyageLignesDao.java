package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoyageLignesDao {

    VoyageLignes findByVoyagePK(final VoyagePK voyagePK);

    List<VoyageLignes> findByDeletedFalse();

    List<VoyageLignes> findByDeletedTrue();

    List<VoyageLignes> findByDeletedFalse(Pageable pageable);

    List<VoyageLignes> findByVoyage(Voyages voyage);

    List<VoyageLignes> findByVoyage(Voyages voyage, Pageable pageable);

    List<VoyageLignes> findByBus(Bus bus, Pageable pageable);

    VoyageLignes save(VoyageLignes voyageLigne);

    List<VoyageLignes> save(List<VoyageLignes> voyageLignes);

    void delete(final VoyagePK voyagePK);

    void delete(final List<VoyageLignes> voyageLignes);

}
