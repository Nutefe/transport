package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.ColisRequest;
import com.bluerizon.transport.response.ResponseBilletColisPage;
import com.bluerizon.transport.security.CurrentUser;
import com.bluerizon.transport.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BilletColisController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_billet_colis_page}")
    private String url_billet_colis_page;

    @Value("${app.url_billet_colis_user_page}")
    private String url_billet_colis_user_page;

    @Value("${app.url_billet_colis_user_connecte_page}")
    private String url_billet_colis_user_connecte_page;

    @Value("${app.url_billet_colis_voyage_page}")
    private String url_billet_colis_voyage_page;

    @Value("${app.url_billet_colis_client_page}")
    private String url_billet_colis_client_page;

    @Value("${app.url_billet_colis_search_page}")
    private String url_billet_colis_search_page;

    @Value("${app.url_billet_colis_voyage_search_page}")
    private String url_billet_colis_voyage_search_page;

    @Value("${app.url_billet_colis_client_search_page}")
    private String url_billet_colis_client_search_page;

    @Autowired
    private BilletColisDao billetColisDao;

    @Autowired
    private VoyagesDao voyagesDao;

    @Autowired
    private ClientsDao clientsDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private BusDao busDao;

    @Autowired
    private LignesDao lignesDao;

    @Autowired
    private TarifsDao tarifsDao;

    @RequestMapping(value = { "/billet_colis/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public BilletColis selectOne(@PathVariable("id") final Long id) {
        return this.billetColisDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/billet_colis_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<BilletColis> selectNoDeleted() {
        return this.billetColisDao.findByDeletedFalseOrderByIdBilletDesc();
    }
    
    @RequestMapping(value = { "/billet_colis_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<BilletColis> selectDeleted() {
        return this.billetColisDao.findByDeletedTrueOrderByIdBilletDesc();
    }

    @RequestMapping(value = { "/billet_colis" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public BilletColis save(@Validated @RequestBody final ColisRequest request, @CurrentUser UserPrincipal currentUser) {

        Clients client = new Clients();
        client.setCompagnie(request.getCompagnie());
        client.setNomComplet(request.getNomCompletExpediteur());
        client.setContact(request.getContactDestinataire());
        Clients clientSave = clientsDao.save(client);

        BilletColis billetColis = new BilletColis();
        if (clientSave!=null){
            Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
            Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
            TarifPK tarifPK = new TarifPK(bus, ligne);
            Tarifs tarif = tarifsDao.findByTarifPK(tarifPK);
            Users user = usersDao.findByIdUser(currentUser.getId());

            billetColis.setUser(user);
            billetColis.setVoyage(request.getVoyage());
            billetColis.setNomComplet(request.getNomCompletDestinataire());
            billetColis.setContact(request.getContactDestinataire());
            billetColis.setPoidsColis(request.getPoidsColis());
            billetColis.setTarif(tarif);
            billetColis.setClient(clientSave);
        }

        return this.billetColisDao.save(billetColis);
    }

    @RequestMapping(value = { "/billet_colis/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public BilletColis update(@PathVariable("id") final Long id, @Validated @RequestBody final ColisRequest request) {
        BilletColis billetColisInit = this.billetColisDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));


        Clients clientInit = clientsDao.findByIdClient(billetColisInit.getClient().getIdClient());
        clientInit.setCompagnie(request.getCompagnie());
        clientInit.setNomComplet(request.getNomCompletExpediteur());
        clientInit.setContact(request.getContactDestinataire());
        Clients clientSave = clientsDao.save(clientInit);

        if (clientSave!=null){
            Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
            Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
            TarifPK tarifPK = new TarifPK(bus, ligne);
            Tarifs tarif = tarifsDao.findByTarifPK(tarifPK);

            billetColisInit.setVoyage(request.getVoyage());
            billetColisInit.setNomComplet(request.getNomCompletDestinataire());
            billetColisInit.setContact(request.getContactDestinataire());
            billetColisInit.setPoidsColis(request.getPoidsColis());
            billetColisInit.setTarif(tarif);
            billetColisInit.setClient(clientSave);
        }

        return this.billetColisDao.save(billetColisInit);
    }

    @RequestMapping(value ="/billet_colis_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletColisPage selectBilleColisPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<BilletColis> billetColis = this.billetColisDao.findAllByDeletedFalse(pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();

        Long total = this.billetColisDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_page+1);
            billetColisPage.setLast_page_url(url_billet_colis_page+lastPage);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_page+(page+1));
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_page+(page-1));
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }
            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);
        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value ="/billet_colis_user_connecte_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletColisPage selectBilleColisUserConnectPage(@PathVariable(value = "page") int page,
                                                                   @CurrentUser UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(currentUser.getId());
        List<BilletColis> billetColis = this.billetColisDao.findByUser(user,pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();

        Long total = this.billetColisDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_user_connecte_page+1);
            billetColisPage.setLast_page_url(url_billet_colis_user_connecte_page+lastPage);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_user_connecte_page+(page+1));
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_user_connecte_page+(page-1));
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }
            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);
        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value ="/billet_colis_user_page/{idUser}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletColisPage selectBilleColisUserPage(@PathVariable(value = "idUser") Long idUser,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(idUser);
        List<BilletColis> billetColis = this.billetColisDao.findByUser(user, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();

        Long total = this.billetColisDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_user_page+idUser+"/"+1);
            billetColisPage.setLast_page_url(url_billet_colis_user_page+idUser+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_user_page+idUser+"/"+(page+1));
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_user_page+idUser+"/"+(page-1));
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }
            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);
        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value ="/billet_colis_voyage_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletColisPage selectBilleColisVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<BilletColis> billetColis = this.billetColisDao.findByVoyage(voyage, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();

        Long total = this.billetColisDao.countByVoyage(voyage);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_voyage_page+id+"/"+1);
            billetColisPage.setLast_page_url(url_billet_colis_voyage_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_voyage_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_voyage_page+id+"/"+(page-1));
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }
            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);
        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value ="/billet_colis_client_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletColisPage selectBilleColisClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Clients client = clientsDao.findByIdClient(id);
        List<BilletColis> billetColis = this.billetColisDao.findByClient(client, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();

        Long total = this.billetColisDao.countByClient(client);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_client_page+id+"/"+1);
            billetColisPage.setLast_page_url(url_billet_colis_client_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_client_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_client_page+id+"/"+(page-1));
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }
            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);
        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value = "/billet_colis_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletColisPage searchBilletColisPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<BilletColis> billetColis = this.billetColisDao.recherche(s, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();
        Long total = this.billetColisDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_search_page+1+"/"+s);
            billetColisPage.setLast_page_url(url_billet_colis_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_search_page+(page-1)+"/"+s);
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }

            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);

        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value = "/billet_colis_voyage_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletColisPage searchBilletColisVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<BilletColis> billetColis = this.billetColisDao.rechercheVoyage(voyage, s, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();
        Long total = this.billetColisDao.countRechercheVoyage(voyage, s);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_voyage_search_page+id+"/"+1+"/"+s);
            billetColisPage.setLast_page_url(url_billet_colis_voyage_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_voyage_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_voyage_search_page+id+"/"+(page-1)+"/"+s);
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }

            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);

        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    @RequestMapping(value = "/billet_colis_client_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletColisPage searchBilletColisClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Clients client = clientsDao.findByIdClient(id);
        List<BilletColis> billetColis = this.billetColisDao.rechercheClient(client, s, pageable);

        ResponseBilletColisPage billetColisPage = new ResponseBilletColisPage();
        Long total = this.billetColisDao.countRechercheClient(client, s);
        Long lastPage;

        if (total > 0){
            billetColisPage.setTotal(total);
            billetColisPage.setPer_page(page_size);
            billetColisPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetColisPage.setLast_page(lastPage);
            billetColisPage.setFirst_page_url(url_billet_colis_client_search_page+id+"/"+1+"/"+s);
            billetColisPage.setLast_page_url(url_billet_colis_client_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetColisPage.setNext_page_url(url_billet_colis_client_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetColisPage.setPrev_page_url(null);
                billetColisPage.setFrom(1L);
                billetColisPage.setTo(Long.valueOf(page_size));
            } else {
                billetColisPage.setPrev_page_url(url_billet_colis_client_search_page+id+"/"+(page-1)+"/"+s);
                billetColisPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetColisPage.setTo(Long.valueOf(page_size) * page);
            }

            billetColisPage.setPath(path);
            billetColisPage.setData(billetColis);

        }else {
            billetColisPage.setTotal(0L);
        }

        return billetColisPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
