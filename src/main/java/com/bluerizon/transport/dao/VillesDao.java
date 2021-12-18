package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VillesDao {

    Villes findByIdVille(final Long id);

    Optional<Villes> findById(final Long id);

    List<Villes> findByDeletedFalseOrderByIdVilleDesc();

    List<Villes> findByDeletedTrueOrderByIdVilleDesc();

    List<Villes> findByCompagnie(Compagnies compagnie);

    List<Villes> findByPays(Pays pays);

    List<Villes> findAllByDeletedFalse(Pageable pageable);

    List<Villes> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<Villes> findByPays(Pays pays, Pageable pageable);

    List<Villes> recherche(String search, Pageable pageable);

    List<Villes> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    List<Villes> recherchePays(Pays pays, String search, Pageable pageable);

    Villes save(Villes ville);

    List<Villes> save(List<Villes> villes);

    Long countRecherche(String search);

    Long countByDeletedFalse();

    Long countRechercheCompagnie(Compagnies compagnie, String search);

    Long countRecherchePays(Pays pays, String search);

    Long countByPays(Pays pays);

    Long countByCompagnie(Compagnies compagnie);

}
