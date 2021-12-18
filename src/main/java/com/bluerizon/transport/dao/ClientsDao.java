package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientsDao {

    Clients findByIdClient(final Long id);

    Optional<Clients> findById(final Long id);

    List<Clients> findByDeletedFalseOrderByIdClientDesc();

    List<Clients> findByDeletedTrueOrderByIdClientDesc();

    List<Clients> findAllByDeletedFalse(Pageable pageable);

    List<Clients> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<Clients> recherche(String search, Pageable pageable);

    List<Clients> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    Clients save(Clients client);

    List<Clients> save(List<Clients> clients);

    Long countByDeletedFalse();

    Long countRecherche(String search);

    Long countByCompagnie(Compagnies compagnie);

    Long countRechercheCompagnie(Compagnies compagnie, String search);

}
