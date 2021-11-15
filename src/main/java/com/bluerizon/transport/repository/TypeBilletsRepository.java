package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBillets;
import com.bluerizon.transport.entity.TypeBus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeBilletsRepository  extends JpaRepository<TypeBillets, Integer> {

    TypeBillets findByIdTypeBillet(final Integer id);

    List<TypeBillets> findByDeletedFalseOrderByIdTypeBilletDesc();

    List<TypeBillets> findByDeletedTrueOrderByIdTypeBilletDesc();

    List<TypeBillets> findByDeletedFalseOrderByIdTypeBilletDesc(Pageable pageable);

    @Query("SELECT t FROM TypeBillets t WHERE t.libelle LIKE CONCAT('%',:search,'%') AND t.deleted = false ORDER BY t.idTypeBillet DESC")
    List<TypeBillets> recherche(String search, Pageable pageable);

    @Query("SELECT COUNT(t) FROM TypeBillets t WHERE t.libelle LIKE CONCAT('%',:search,'%') AND t.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(t) FROM TypeBillets t WHERE t.deleted = false")
    Long countByDeletedFalse();

}
