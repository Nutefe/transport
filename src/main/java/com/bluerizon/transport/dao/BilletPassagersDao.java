package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BilletPassagersDao {

    BilletPassagers findByIdBillet(final Long id);

    List<BilletPassagers> findByDeletedFalseOrderByIdBilletDesc();

    List<BilletPassagers> findByDeletedTrueOrderByIdBilletDesc();

    List<BilletPassagers> findAllByDeletedFalse(Pageable pageable);

    List<BilletPassagers> findByUser(Users user, Pageable pageable);

    List<BilletPassagers> findByVoyage(Voyages voyage, Pageable pageable);

    List<Billets> findByClient(Clients client, Pageable pageable);

    List<BilletPassagers> recherche(String search, Pageable pageable);

    List<BilletPassagers> rechercheUser(Users user, String search, Pageable pageable);

    List<BilletPassagers> rechercheVoyage(Voyages voyage, String search, Pageable pageable);

    List<BilletPassagers> rechercheClient(Clients client, String search, Pageable pageable);

    BilletPassagers save(BilletPassagers billetPassager);

    List<BilletPassagers> save(List<BilletPassagers> billetPassagers);

    Long countByDeletedFalse();

    Long countByUser(Users user);

    Long countByVoyage(Voyages voyage);

    Long countByClient(Clients client);

    Long countRecherche(String search);

    Long countRechercheUser(Users user, String search);

    Long countRechercheVoyage(Voyages voyage, String search);

    Long countRechercheClient(Clients client, String search);


}
