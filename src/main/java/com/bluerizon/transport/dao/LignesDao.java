package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Lignes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LignesDao {

    Lignes findByIdLigne(final Long id);

    List<Lignes> findByDeletedFalseOrderByIdLigneDesc();

    List<Lignes> findByDeletedTrueOrderByIdLigneDesc();

    List<Lignes> findAllByDeletedFalse(Pageable pageable);

    List<Lignes> findByLigne(Lignes ligne, Pageable pageable);

    List<Lignes> recherche(String search, Pageable pageable);

    List<Lignes> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    Lignes save(Lignes ligne);

    List<Lignes> save(List<Lignes> lignes);

    Long countByDeletedFalse();

    Long countByLigne(Lignes ligne);

    Long countRecherche(String search);

    Long countRechercheLigne(Lignes ligne, String search);

}
