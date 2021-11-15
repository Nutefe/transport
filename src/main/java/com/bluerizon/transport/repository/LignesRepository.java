package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LignesRepository extends JpaRepository<Lignes, Long> {

    Lignes findByIdLigne(final Long id);

    List<Lignes> findByDeletedFalseOrderByIdLigneDesc();

    List<Lignes> findByDeletedTrueOrderByIdLigneDesc();

    List<Lignes> findByDeletedFalseOrderByIdLigneDesc(Pageable pageable);

    @Query("SELECT l FROM Lignes l WHERE l.deleted = false AND l.ligne=?1 ORDER BY l.idLigne DESC")
    List<Lignes> findByLigne(Lignes ligne, Pageable pageable);

    @Query("SELECT l FROM Lignes l WHERE " +
            "(l.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.ville.nomVille LIKE CONCAT('%',:search,'%')) AND l.deleted = false ORDER BY l.idLigne DESC")
    List<Lignes> recherche(String search, Pageable pageable);

    @Query("SELECT l FROM Lignes l WHERE l.deleted = false AND l.ligne=:ligne AND " +
            "(l.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.ville.nomVille LIKE CONCAT('%',:search,'%')) ORDER BY l.idLigne DESC")
    List<Lignes> rechercheLigne(Lignes ligne, String search, Pageable pageable);

    @Query("SELECT COUNT(l) FROM Lignes l WHERE l.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(l) FROM Lignes l WHERE l.deleted = false AND l.ligne=?1")
    Long countByLigne(Lignes ligne);

    @Query("SELECT COUNT(l) FROM Lignes l WHERE " +
            "(l.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.ligne.arriver.ville.nomVille LIKE CONCAT('%',:search,'%')) AND l.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(l) FROM Lignes l WHERE l.deleted = false AND l.ligne=:ligne AND " +
            "(l.depart.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.depart.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.libelle LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.adresse LIKE CONCAT('%',:search,'%') OR " +
            "l.arriver.ville.nomVille LIKE CONCAT('%',:search,'%'))")
    Long countRechercheLigne(Lignes ligne, String search);

}
