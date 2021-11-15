package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.Pays;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaysDao {

    Pays findByIdPays(final Integer id);

    List<Pays> findByDeletedFalseOrderByIdPaysDesc();

    List<Pays> findByDeletedTrueOrderByIdPaysDesc();

    List<Pays> findAllByDeletedFalse(Pageable pageable);

    List<Pays> recherche(String search, Pageable pageable);

    Pays save(Pays pays);

    List<Pays> save(List<Pays> pays);

    Long countRecherche(String search);

    Long countByDeletedFalse();

}
