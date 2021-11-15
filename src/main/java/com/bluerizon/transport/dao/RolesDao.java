package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Roles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesDao {

    Roles findByIdRole(final Integer id);

    List<Roles> findByDeletedFalseOrderByIdRoleDesc();

    List<Roles> findByDeletedTrueOrderByIdRoleDesc();

    List<Roles> findAllByDeletedFalse(Pageable pageable);

    List<Roles> recherche(String search, Pageable pageable);

    Roles save(Roles role);

    List<Roles> save(List<Roles> roles);

    Long countRecherche(String search);

    Long countByDeletedFalse();

}
