package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifsRepository extends JpaRepository<Tarifs, TarifPK> {

    Tarifs findByTarifPK(final TarifPK tarifPK);

    List<Tarifs> findByDeletedFalse();

    List<Tarifs> findByDeletedTrue();

    List<Tarifs> findByDeletedFalse(Pageable pageable);

    @Query("SELECT t FROM Tarifs t WHERE t.deleted = false AND t.tarifPK.ligne=?1")
    List<Tarifs> findByLigne(Lignes ligne, Pageable pageable);

    @Query("SELECT t FROM Tarifs t WHERE t.deleted = false AND t.tarifPK.bus=?1")
    List<Tarifs> findByBus(Bus bus, Pageable pageable);

    @Query("SELECT t FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND t.deleted = false")
    List<Tarifs> recherche(String search, Pageable pageable);

    @Query("SELECT t FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND (t.deleted = false AND t.tarifPK.ligne=:ligne)")
    List<Tarifs> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    @Query("SELECT t FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND (t.deleted = false AND t.tarifPK.bus=:bus)")
    List<Tarifs> rechercheBus(Bus bus, String search, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND t.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND (t.deleted = false AND t.tarifPK.ligne=:ligne)")
    Long contRechercheLigne(Lignes ligne, String search);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE (t.prixPassager LIKE CONCAT('%',:search,'%') OR " +
            "t.prixKilo LIKE CONCAT('%',:search,'%')) AND (t.deleted = false AND t.tarifPK.bus=:bus)")
    Long countRechercheBus(Bus bus, String search);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE t.deleted = false AND t.tarifPK.ligne=?1")
    Long countByLigne(Lignes ligne);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE t.deleted = false AND t.tarifPK.bus=?1")
    Long countByBus(Bus bus);

    @Query("SELECT COUNT(t) FROM Tarifs t WHERE t.deleted = false")
    Long countByDeletedFalse();

}
