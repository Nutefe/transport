package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.PassagerRequest;
import com.bluerizon.transport.response.ResponseBilletPage;
import com.bluerizon.transport.response.ResponseBilletPage;
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
public class BilletsController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_billet_page}")
    private String url_billet_page;

    @Value("${app.url_billet_user_page}")
    private String url_billet_user_page;

    @Value("${app.url_billet_user_connecte_page}")
    private String url_billet_user_connecte_page;

    @Value("${app.url_billet_voyage_page}")
    private String url_billet_voyage_page;

    @Value("${app.url_billet_client_page}")
    private String url_billet_client_page;

    @Value("${app.url_billet_search_page}")
    private String url_billet_search_page;

    @Value("${app.url_billet_voyage_search_page}")
    private String url_billet_voyage_search_page;

    @Value("${app.url_billet_client_search_page}")
    private String url_billet_client_search_page;

    @Autowired
    private BilletsDao billetsDao;

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

    @RequestMapping(value = { "/billet/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Billets selectOne(@PathVariable("id") final Long id) {
        return this.billetsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/billet_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Billets> selectNoDeleted() {
        return this.billetsDao.findByDeletedFalseOrderByIdBilletDesc();
    }
    
    @RequestMapping(value = { "/billet_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Billets> selectDeleted() {
        return this.billetsDao.findByDeletedTrueOrderByIdBilletDesc();
    }

    @RequestMapping(value ="/billet_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPage selectBilleColisPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Billets> billets = this.billetsDao.findAllByDeletedFalse(pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();

        Long total = this.billetsDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_page+1);
            billetPage.setLast_page_url(url_billet_page+lastPage);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_page+(page+1));
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_page+(page-1));
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPage.setPath(path);
            billetPage.setData(billets);
        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value ="/billet_user_connecte_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPage selectBilleUserConnectPage(@PathVariable(value = "page") int page,
                                                                   @CurrentUser UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(currentUser.getId());
        List<Billets> Billets = this.billetsDao.findByUser(user,pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();

        Long total = this.billetsDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_user_connecte_page+1);
            billetPage.setLast_page_url(url_billet_user_connecte_page+lastPage);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_user_connecte_page+(page+1));
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_user_connecte_page+(page-1));
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPage.setPath(path);
            billetPage.setData(Billets);
        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value ="/billet_user_page/{idUser}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPage selectBilleUserPage(@PathVariable(value = "idUser") Long idUser,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(idUser);
        List<Billets> Billets = this.billetsDao.findByUser(user, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();

        Long total = this.billetsDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_user_page+idUser+"/"+1);
            billetPage.setLast_page_url(url_billet_user_page+idUser+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_user_page+idUser+"/"+(page+1));
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_user_page+idUser+"/"+(page-1));
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPage.setPath(path);
            billetPage.setData(Billets);
        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value ="/billet_voyage_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPage selectBilleVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<Billets> Billets = this.billetsDao.findByVoyage(voyage, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();

        Long total = this.billetsDao.countByVoyage(voyage);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_voyage_page+id+"/"+1);
            billetPage.setLast_page_url(url_billet_voyage_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_voyage_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_voyage_page+id+"/"+(page-1));
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPage.setPath(path);
            billetPage.setData(Billets);
        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value ="/billet_client_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBilletPage selectBilleClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Clients client = clientsDao.findByIdClient(id);
        List<Billets> Billets = this.billetsDao.findByClient(client, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();

        Long total = this.billetsDao.countByClient(client);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_client_page+id+"/"+1);
            billetPage.setLast_page_url(url_billet_client_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_client_page+id+"/"+(page+1));
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_client_page+id+"/"+(page-1));
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }
            billetPage.setPath(path);
            billetPage.setData(Billets);
        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value = "/billet_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPage searchbilletPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Billets> Billets = this.billetsDao.recherche(s, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();
        Long total = this.billetsDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_search_page+1+"/"+s);
            billetPage.setLast_page_url(url_billet_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_search_page+(page-1)+"/"+s);
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPage.setPath(path);
            billetPage.setData(Billets);

        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value = "/billet_voyage_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPage searchBilletColisVoyagePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Voyages voyage = voyagesDao.findByIdVoyage(id);
        List<Billets> Billets = this.billetsDao.rechercheVoyage(voyage, s, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();
        Long total = this.billetsDao.countRechercheVoyage(voyage, s);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_voyage_search_page+id+"/"+1+"/"+s);
            billetPage.setLast_page_url(url_billet_voyage_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_voyage_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_voyage_search_page+id+"/"+(page-1)+"/"+s);
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPage.setPath(path);
            billetPage.setData(Billets);

        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    @RequestMapping(value = "/billet_client_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBilletPage searchBilletColisClientPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Clients client = clientsDao.findByIdClient(id);
        List<Billets> Billets = this.billetsDao.rechercheClient(client, s, pageable);

        ResponseBilletPage billetPage = new ResponseBilletPage();
        Long total = this.billetsDao.countRechercheClient(client, s);
        Long lastPage;

        if (total > 0){
            billetPage.setTotal(total);
            billetPage.setPer_page(page_size);
            billetPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            billetPage.setLast_page(lastPage);
            billetPage.setFirst_page_url(url_billet_client_search_page+id+"/"+1+"/"+s);
            billetPage.setLast_page_url(url_billet_client_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                billetPage.setNext_page_url(url_billet_client_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                billetPage.setPrev_page_url(null);
                billetPage.setFrom(1L);
                billetPage.setTo(Long.valueOf(page_size));
            } else {
                billetPage.setPrev_page_url(url_billet_client_search_page+id+"/"+(page-1)+"/"+s);
                billetPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                billetPage.setTo(Long.valueOf(page_size) * page);
            }

            billetPage.setPath(path);
            billetPage.setData(Billets);

        }else {
            billetPage.setTotal(0L);
        }

        return billetPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
