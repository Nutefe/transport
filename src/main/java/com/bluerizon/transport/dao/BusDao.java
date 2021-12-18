package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Billets;
import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusDao {

    Bus findByIdBus(final Long id);

    Optional<Bus> findById(final Long id);

    List<Bus> findByDeletedFalseOrderByIdBusDesc();

    List<Bus> findByDeletedTrueOrderByIdBusDesc();

    List<Bus> findAllByDeletedFalse(Pageable pageable);

    List<Bus> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<Bus> findByTypeBus(TypeBus typeBus, Pageable pageable);

    List<Bus> recherche(String search, Pageable pageable);

    List<Bus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    List<Bus> rechercheTypeBus(TypeBus typeBus, String search, Pageable pageable);

    Bus save(Bus bus);

    List<Bus> save(List<Bus> bus);

    Long countByDeletedFalse();

    Long countByCompagnie(Compagnies compagnie);

    Long countByTypeBus(TypeBus typeBus);

    Long countRecherche(String search);

    Long countRechercheCompagnie(Compagnies compagnie, String search);

    Long countRechercheTypeBus(TypeBus typeBus, String search);

    boolean existsByNumero(final String numero, final Long id);

    boolean existsByNumero(final String numero);

}
