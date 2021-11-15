package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyagesRepository extends JpaRepository<Voyages, Long> {

    Voyages findByIdVoyage(final Long id);

    List<Voyages> findByDeletedFalse();

    List<Voyages> findByDeletedTrue();

    List<Voyages> findByDeletedFalse(Pageable pageable);

    @Query("SELECT v FROM Voyages v WHERE v.deleted = false AND v.ligne=?1 ORDER BY v.idVoyage DESC")
    List<Voyages> findByLigne(Lignes ligne, Pageable pageable);

    @Query("SELECT v FROM Voyages v WHERE v.deleted = false AND v.userCompagnie.userPK.compagnie=?1 ORDER BY v.idVoyage DESC")
    List<Voyages> findByCompagnie(Compagnies compagnie, Pageable pageable);

    @Query("SELECT v FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND v.deleted = false ORDER BY v.idVoyage DESC")
    List<Voyages> recherche(String search, Pageable pageable);

    @Query("SELECT v FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND (v.deleted = false AND v.ligne=:ligne) ORDER BY v.idVoyage DESC")
    List<Voyages> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    @Query("SELECT v FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%')) AND " +
            "(v.deleted = false AND v.userCompagnie.userPK.compagnie=:compagnie) ORDER BY v.idVoyage DESC")
    List<Voyages> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT COUNT(v) FROM Voyages v WHERE v.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(v) FROM Voyages v WHERE v.deleted = false AND v.ligne=?1")
    Long countByLigne(Lignes ligne);

    @Query("SELECT COUNT(v) FROM Voyages v WHERE v.deleted = false AND v.userCompagnie.userPK.compagnie=?1")
    Long countByCompagnie(Compagnies compagnie);

    @Query("SELECT COUNT(v) FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND v.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(v) FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "v.userCompagnie.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND (v.deleted = false AND v.ligne=:ligne)")
    Long countRechercheLigne(Lignes ligne, String search);

    @Query("SELECT COUNT(v) FROM Voyages v WHERE " +
            "(v.dateDepart LIKE CONCAT('%',:search,'%') OR " +
            "v.dateArriver LIKE CONCAT('%',:search,'%')) AND " +
            "(v.deleted = false AND v.userCompagnie.userPK.compagnie=:compagnie)")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

}
