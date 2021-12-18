package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarifsDao {

    Tarifs findByTarifPK(final TarifPK tarifPK);

    List<Tarifs> findByDeletedFalse();

    List<Tarifs> findByDeletedTrue();

    List<Tarifs> findAllByDeletedFalse(Pageable pageable);

    List<Tarifs> findByLigne(Lignes ligne, Pageable pageable);

    List<Tarifs> findByBus(Bus bus, Pageable pageable);

    List<Tarifs> recherche(String search, Pageable pageable);

    List<Tarifs> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    List<Tarifs> rechercheBus(Bus bus, String search, Pageable pageable);

    Tarifs save(Tarifs tarif);

    List<Tarifs> save(List<Tarifs> tarifs);

    Long countRecherche(String search);

    Long contRechercheLigne(Lignes ligne, String search);

    Long countRechercheBus(Bus bus, String search);

    Long countByLigne(Lignes ligne);

    Long countByBus(Bus bus);

    Long countByDeletedFalse();

    List<Tarifs> findByCompagnie(Compagnies compagnie);

    List<Tarifs> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<Tarifs> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    Long countRechercheCompagnie(Compagnies compagnie, String search);

    Long countByCompagnie(Compagnies compagnie);

}
