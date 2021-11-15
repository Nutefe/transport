package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilletColisRepository extends JpaRepository<BilletColis, Long> {

    BilletColis findByIdBillet(final Long id);

    List<BilletColis> findByDeletedFalseOrderByIdBilletDesc();

    List<BilletColis> findByDeletedTrueOrderByIdBilletDesc();

    List<BilletColis> findByDeletedFalseOrderByIdBilletDesc(Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.user=?1 ORDER BY b.idBillet DESC")
    List<BilletColis> findByUser(Users user, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.voyage=?1 ORDER BY b.idBillet DESC")
    List<BilletColis> findByVoyage(Voyages voyage, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.client=?1 ORDER BY b.idBillet DESC")
    List<BilletColis> findByClient(Clients client, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE (b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR "+
            "b.user.username LIKE CONCAT('%',:search,'%') OR "+
            "b.user.nom LIKE CONCAT('%',:search,'%') OR " +
            "b.user.prenom LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%')) AND b.deleted = false ORDER BY b.idBillet DESC")
    List<BilletColis> recherche(String search, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.user=:user AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%')) ORDER BY b.idBillet DESC")
    List<BilletColis> rechercheUser(Users user, String search, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.voyage=:voyage AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%')) ORDER BY b.idBillet DESC")
    List<BilletColis> rechercheVoyage(Voyages voyage, String search, Pageable pageable);

    @Query("SELECT b FROM BilletColis b WHERE b.deleted = false AND b.client=:client AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%')) ORDER BY b.idBillet DESC")
    List<BilletColis> rechercheClient(Clients client, String search, Pageable pageable);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.user=?1")
    Long countByUser(Users user);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.voyage=?1")
    Long countByVoyage(Voyages voyage);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.client=?1")
    Long countByClient(Clients client);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.user=:user AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%'))")
    Long countRecherche(String search);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.user=:user AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%'))")
    Long countRechercheUser(Users user, String search);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.voyage=:voyage AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.client.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.client.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%'))")
    Long countRechercheVoyage(Voyages voyage, String search);

    @Query("SELECT COUNT(b) FROM BilletColis b WHERE b.deleted = false AND b.client=:client AND " +
            "(b.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "b.contact LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.tarif.tarifPK.bus.typeBus.libelle LIKE CONCAT('%',:search,'%'))")
    Long countRechercheClient(Clients client, String search);

}
