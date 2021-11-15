package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompagniesDao {

    Compagnies findByIdCompagnie(final Long id);

    List<Compagnies> findByDeletedFalseOrderByIdCompagnieDesc();

    List<Compagnies> findByDeletedTrueOrderByIdCompagnieDesc();

    List<Compagnies> findAllByDeletedFalse(Pageable pageable);

    List<Compagnies> recherche(String search, Pageable pageable);

    Compagnies save(Compagnies compagnie);

    List<Compagnies> save(List<Compagnies> compagnies);

    Long countByDeletedFalse();

    Long countRecherche(String search);

    boolean existsByEmail(final String email, final Long id);

    boolean existsByEmail(final String email);

}
