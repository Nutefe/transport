package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.TypeBillets;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer> {

    Pays findByIdPays(final Integer id);

    List<Pays> findByDeletedFalseOrderByIdPaysDesc();

    List<Pays> findByDeletedTrueOrderByIdPaysDesc();

    List<Pays> findByDeletedFalseOrderByIdPaysDesc(Pageable pageable);

    @Query("SELECT p FROM Pays p WHERE (p.nomPays LIKE CONCAT('%',:search,'%') OR p.codePays LIKE CONCAT('%',:search,'%') " +
            "OR p.indicatifPays LIKE CONCAT('%',:search,'%') ) AND p.deleted = false")
    List<Pays> recherche(String search, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Pays p WHERE (p.nomPays LIKE CONCAT('%',:search,'%') OR p.codePays LIKE CONCAT('%',:search,'%') " +
            "OR p.indicatifPays LIKE CONCAT('%',:search,'%') ) AND p.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(p) FROM Pays p WHERE p.deleted = false")
    Long countByDeletedFalse();

}
