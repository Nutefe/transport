package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Arrets;
import com.bluerizon.transport.entity.Villes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArretsDao {

    Arrets findByIdArret(final Long id);

    Optional<Arrets> findById(final Long id);

    List<Arrets> findByDeletedFalseOrderByIdArretDesc();

    List<Arrets> findByDeletedTrueOrderByIdArretDesc();

    List<Arrets> findAllByDeletedFalse(Pageable pageable);

    List<Arrets> findByVille(Villes ville, Pageable pageable);

    List<Arrets> recherche(String search, Pageable pageable);

    List<Arrets> rechercheVille(Villes ville, String search, Pageable pageable);

    Arrets save(Arrets arret);

    List<Arrets> save(List<Arrets> arrets);

    Long countByDeletedFalse();

    Long countByVille(Villes ville);

    Long countRecherche(String search);

    Long countRechercheVille(Villes ville, String search);

}
