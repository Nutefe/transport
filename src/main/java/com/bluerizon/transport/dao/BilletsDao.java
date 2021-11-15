package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BilletsDao {

    Billets findByIdBillet(final Long id);

    List<Billets> findByDeletedFalseOrderByIdBilletDesc();

    List<Billets> findByDeletedTrueOrderByIdBilletDesc();

    List<Billets> findAllByDeletedFalse(Pageable pageable);

    List<Billets> findByUser(Users user, Pageable pageable);

    List<Billets> findByVoyage(Voyages voyage, Pageable pageable);

    List<Billets> findByClient(Clients client, Pageable pageable);

    List<Billets> recherche(String search, Pageable pageable);

    List<Billets> rechercheUser(Users user, String search, Pageable pageable);

    List<Billets> rechercheVoyage(Voyages voyage, String search, Pageable pageable);

    List<Billets> rechercheClient(Clients client, String search, Pageable pageable);

    Billets save(Billets billet);

    List<Billets> save(List<Billets> billets);

    Long countByDeletedFalse();

    Long countByUser(Users user);

    Long countByVoyage(Voyages voyage);

    Long countByClient(Clients client);

    Long countRecherche(String search);

    Long countRechercheUser(Users user, String search);

    Long countRechercheVoyage(Voyages voyage, String search);

    Long countRechercheClient(Clients client, String search);


}
