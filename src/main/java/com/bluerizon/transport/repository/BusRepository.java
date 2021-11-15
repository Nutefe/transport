package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Bus findByIdBus(final Long id);

    List<Bus> findByDeletedFalseOrderByIdBusDesc();

    List<Bus> findByDeletedTrueOrderByIdBusDesc();

    List<Bus> findByDeletedFalseOrderByIdBusDesc(Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE b.deleted = false AND b.compagnie=?1 ORDER BY b.idBus DESC")
    List<Bus> findByCompagnie(Compagnies compagnie, Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE b.deleted = false AND b.typeBus=?1 ORDER BY b.idBus DESC")
    List<Bus> findByTypeBus(TypeBus typeBus, Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE (b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.adresse LIKE CONCAT('%',:search,'%') OR " +
            "b.typeBus.libelle LIKE CONCAT('%',:search,'%')) AND b.deleted = false ORDER BY b.idBus DESC")
    List<Bus> recherche(String search, Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE b.deleted = false AND b.compagnie=:compagnie AND " +
            "(b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.typeBus.libelle LIKE CONCAT('%',:search,'%')) ORDER BY b.idBus DESC")
    List<Bus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT b FROM Bus b WHERE b.deleted = false AND b.typeBus=:typeBus AND " +
            "(b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.adresse LIKE CONCAT('%',:search,'%')) ORDER BY b.idBus DESC")
    List<Bus> rechercheTypeBus(TypeBus typeBus, String search, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Bus b WHERE b.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(b) FROM Bus b WHERE b.deleted = false AND b.compagnie=?1")
    Long countByCompagnie(Compagnies compagnie);

    @Query("SELECT COUNT(b) FROM Bus b WHERE b.deleted = false AND b.typeBus=?1")
    Long countByTypeBus(TypeBus typeBus);

    @Query("SELECT COUNT(b) FROM Bus b WHERE (b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.adresse LIKE CONCAT('%',:search,'%') OR " +
            "b.typeBus.libelle LIKE CONCAT('%',:search,'%')) AND b.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(b) FROM Bus b WHERE b.deleted = false AND b.compagnie=:compagnie AND " +
            "(b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.typeBus.libelle LIKE CONCAT('%',:search,'%'))")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

    @Query("SELECT COUNT(b) FROM Bus b WHERE b.deleted = false AND b.typeBus=:typeBus AND " +
            "(b.numeroMatricule LIKE CONCAT('%',:search,'%') OR " +
            "b.nombrePlace LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "b.compagnie.adresse LIKE CONCAT('%',:search,'%'))")
    Long countRechercheTypeBus(TypeBus typeBus, String search);

    @Query("SELECT CASE WHEN COUNT(numeroMatricule) > 0 THEN true ELSE false END FROM Bus b WHERE b.numeroMatricule = :numero and b.idBus != :id")
    boolean existsByNumero(@Param("numero") final String numero, @Param("id") final Long id);

    @Query("SELECT CASE WHEN COUNT(numeroMatricule) > 0 THEN true ELSE false END FROM Bus b WHERE b.numeroMatricule = :numero")
    boolean existsByNumero(@Param("numero") final String numero);

}
