package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.PassagerRequest;
import com.bluerizon.transport.response.ResponseBilletPassagerPage;
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
public class BilletPassagersController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_billet_passager_page}")
    private String url_billet_passager_page;

    @Value("${app.url_billet_passager_user_page}")
    private String url_billet_passager_user_page;

    @Value("${app.url_billet_passager_user_connecte_page}")
    private String url_billet_passager_user_connecte_page;

    @Value("${app.url_billet_passager_voyage_page}")
    private String url_billet_passager_voyage_page;

    @Value("${app.url_billet_passager_client_page}")
    private String url_billet_passager_client_page;

    @Value("${app.url_billet_passager_search_page}")
    private String url_billet_passager_search_page;

    @Value("${app.url_billet_passager_voyage_search_page}")
    private String url_billet_passager_voyage_search_page;

    @Value("${app.url_billet_passager_client_search_page}")
    private String url_billet_passager_client_search_page;

    @Autowired
    private BilletPassagersDao billetPassagersDao;

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

    @Autowired
    private CompagniesDao compagniesDao;

    @RequestMapping(value = { "/billet_passager/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public BilletPassagers selectOne(@PathVariable("id") final Long id) {
        return this.billetPassagersDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/billet_passager_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<BilletPassagers> selectNoDeleted() {
        return this.billetPassagersDao.findByDeletedFalseOrderByIdBilletDesc();
    }
    
    @RequestMapping(value = { "/billet_passager_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<BilletPassagers> selectDeleted() {
        return this.billetPassagersDao.findByDeletedTrueOrderByIdBilletDesc();
    }

    @RequestMapping(value = { "/billet_passager" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public BilletPassagers save(@Validated @RequestBody final PassagerRequest request, @CurrentUser UserPrincipal currentUser) {

        Clients clientInit = clientsDao.findByContact(request.getContact());
        Clients clientSave;
        if (clientInit==null){
            Clients client = new Clients();
            client.setCompagnie(compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie()));
            client.setNomComplet(request.getNomComplet());
            client.setContact(request.getContact());
            client.setCode("CL-"+clientsDao.count());
            clientSave = clientsDao.save(client);
        }else {
            clientSave = clientInit;
        }

        BilletPassagers billetPassager = new BilletPassagers();
        if (clientSave!=null){
            Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
            Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
            TarifPK tarifPK = new TarifPK(bus, ligne);
            Tarifs tarif = tarifsDao.findByTarifPK(tarifPK);
            Users user = usersDao.findByIdUser(currentUser.getId());

            billetPassager.setUser(user);
            billetPassager.setVoyage(voyagesDao.findByIdVoyage(request.getVoyage().getIdVoyage()));
            billetPassager.setPoidsAutorise(1);
            billetPassager.setPoidsApporte(request.getPoidsApporte());
            billetPassager.setTarif(tarif);
            billetPassager.setClient(clientSave);
        }

        return this.billetPassagersDao.save(billetPassager);
    }

    @RequestMapping(value = { "/billet_passager/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public BilletPassagers update(@PathVariable("id") final Long id, @Validated @RequestBody final PassagerRequest request) {
        BilletPassagers billetPassagersInit = this.billetPassagersDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));


        Clients clientInit = clientsDao.findByIdClient(billetPassagersInit.getClient().getIdClient());
        clientInit.setCompagnie(request.getCompagnie());
        clientInit.setNomComplet(request.getNomComplet());
        clientInit.setContact(request.getContact());
        Clients clientSave = clientsDao.save(clientInit);

        if (clientSave!=null){
            Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
            Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
            TarifPK tarifPK = new TarifPK(bus, ligne);
            Tarifs tarif = tarifsDao.findByTarifPK(tarifPK);

            billetPassagersInit.setVoyage(voyagesDao.findByIdVoyage(request.getVoyage().getIdVoyage()));
            billetPassagersInit.setPoidsAutorise(1);
            billetPassagersInit.setPoidsApporte(request.getPoidsApporte());
            billetPassagersInit.setTarif(tarif);
            billetPassagersInit.setClient(clientSave);
        }

        return this.billetPassagersDao.save(billetPassagersInit);
    }

    @RequestMapping(value ="/billet_passager_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPassagerPage selectBilleColisPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<BilletPassagers> billetPassagers = this.billetPassagersDao.findAllByDeletedFalse(pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();

        Long total = this.billetPassagersDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_page+1);
            billetPassagerPage.setLast_page_url(url_billet_passager_page+lastPage);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_page+(page+1));
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_page+(page-1));
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);
        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value ="/billet_passager_user_connecte_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPassagerPage selectBilleColisUserConnectPage(@PathVariable(value = "page") int page,
                                                                   @CurrentUser UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(currentUser.getId());
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.findByUser(user,pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();

        Long total = this.billetPassagersDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_user_connecte_page+1);
            billetPassagerPage.setLast_page_url(url_billet_passager_user_connecte_page+lastPage);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_user_connecte_page+(page+1));
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_user_connecte_page+(page-1));
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);
        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value ="/billet_passager_user_page/{idUser}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPassagerPage selectBilleColisUserPage(@PathVariable(value = "idUser") Long idUser,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(idUser);
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.findByUser(user, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();

        Long total = this.billetPassagersDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_user_page+idUser+"/"+1);
            billetPassagerPage.setLast_page_url(url_billet_passager_user_page+idUser+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_user_page+idUser+"/"+(page+1));
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_user_page+idUser+"/"+(page-1));
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);
        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value ="/billet_passager_voyage_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPassagerPage selectBilleColisVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.findByVoyage(voyage, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();

        Long total = this.billetPassagersDao.countByVoyage(voyage);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_voyage_page+id+"/"+1);
            billetPassagerPage.setLast_page_url(url_billet_passager_voyage_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_voyage_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_voyage_page+id+"/"+(page-1));
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);
        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value ="/billet_passager_client_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPassagerPage selectBilleColisClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Clients client = clientsDao.findByIdClient(id);
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.findByClient(client, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();

        Long total = this.billetPassagersDao.countByClient(client);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_client_page+id+"/"+1);
            billetPassagerPage.setLast_page_url(url_billet_passager_client_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_client_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_client_page+id+"/"+(page-1));
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);
        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value = "/billet_passager_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPassagerPage searchbilletPassagerPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.recherche(s, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();
        Long total = this.billetPassagersDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_search_page+1+"/"+s);
            billetPassagerPage.setLast_page_url(url_billet_passager_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_search_page+(page-1)+"/"+s);
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);

        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value = "/billet_passager_voyage_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPassagerPage searchBilletColisVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.rechercheVoyage(voyage, s, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();
        Long total = this.billetPassagersDao.countRechercheVoyage(voyage, s);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_voyage_search_page+id+"/"+1+"/"+s);
            billetPassagerPage.setLast_page_url(url_billet_passager_voyage_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_voyage_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_voyage_search_page+id+"/"+(page-1)+"/"+s);
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);

        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    @RequestMapping(value = "/billet_passager_client_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPassagerPage searchBilletColisClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Clients client = clientsDao.findByIdClient(id);
        List<BilletPassagers> billetPassagers = this.billetPassagersDao.rechercheClient(client, s, pageable);

        ResponseBilletPassagerPage billetPassagerPage = new ResponseBilletPassagerPage();
        Long total = this.billetPassagersDao.countRechercheClient(client, s);
        Long lastPage;

        if (total > 0){
            billetPassagerPage.setTotal(total);
            billetPassagerPage.setPer_page(page_size);
            billetPassagerPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPassagerPage.setLast_page(lastPage);
            billetPassagerPage.setFirst_page_url(url_billet_passager_client_search_page+id+"/"+1+"/"+s);
            billetPassagerPage.setLast_page_url(url_billet_passager_client_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPassagerPage.setNext_page_url(url_billet_passager_client_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPassagerPage.setPrev_page_url(null);
                billetPassagerPage.setFrom(1L);
                billetPassagerPage.setTo(Long.valueOf(page_size));
            } else {
                billetPassagerPage.setPrev_page_url(url_billet_passager_client_search_page+id+"/"+(page-1)+"/"+s);
                billetPassagerPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPassagerPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPassagerPage.setPath(path);
            billetPassagerPage.setData(billetPassagers);

        }else {
            billetPassagerPage.setTotal(0L);
        }

        return billetPassagerPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
