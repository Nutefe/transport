package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoyagesDao {

    Voyages findByIdVoyage(final Long id);

    List<Voyages> findByDeletedFalse();

    List<Voyages> findByDeletedTrue();

    List<Voyages> findAllByDeletedFalse(Pageable pageable);

    List<Voyages> findByLigne(Lignes ligne, Pageable pageable);

    List<Voyages> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<Voyages> recherche(String search, Pageable pageable);

    List<Voyages> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    List<Voyages> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    Voyages save(Voyages voyage);

    List<Voyages> save(List<Voyages> voyages);

    Long countByDeletedFalse();

    Long countByLigne(Lignes ligne);

    Long countByCompagnie(Compagnies compagnie);

    Long countRecherche(String search);

    Long countRechercheLigne(Lignes ligne, String search);

    Long countRechercheCompagnie(Compagnies compagnie, String search);
}
