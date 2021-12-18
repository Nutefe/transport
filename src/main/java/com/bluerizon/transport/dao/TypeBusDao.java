package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBillets;
import com.bluerizon.transport.entity.TypeBus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeBusDao {

    TypeBus findByIdTypeBus(final Long id);

    Optional<TypeBus> findById(final Long id);

    List<TypeBus> findByDeletedFalseOrderByIdTypeBusDesc();

    List<TypeBus> findByDeletedTrueOrderByIdTypeBusDesc();

    List<TypeBus> findByCompagnie(Compagnies compagnie);

    List<TypeBus> findAllByDeletedFalse(Pageable pageable);

    List<TypeBus> findByCompagnie(Pageable pageable, Compagnies compagnie);

    List<TypeBus> recherche(String search, Pageable pageable);

    List<TypeBus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    TypeBus save(TypeBus typeBus);

    List<TypeBus> save(List<TypeBus> typeBus);

    Long countRecherche(String search);

    Long countByDeletedFalse();

    Long countRechercheCompagnie(Compagnies compagnie, String search);

    Long countByCompagnie(Compagnies compagnie);

}
