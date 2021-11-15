package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Tarifs;
import com.bluerizon.transport.entity.TypeBillets;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeBilletsDao {

    TypeBillets findByIdTypeBillet(final Integer id);

    List<TypeBillets> findByDeletedFalseOrderByIdTypeBilletDesc();

    List<TypeBillets> findByDeletedTrueOrderByIdTypeBilletDesc();

    List<TypeBillets> findAllByDeletedFalse(Pageable pageable);

    List<TypeBillets> recherche(String search, Pageable pageable);

    TypeBillets save(TypeBillets typeBillet);

    List<TypeBillets> save(List<TypeBillets> typeBillets);

    Long countRecherche(String search);

    Long countByDeletedFalse();

}
