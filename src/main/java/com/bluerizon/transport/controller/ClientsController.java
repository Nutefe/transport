package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.BusDao;
import com.bluerizon.transport.dao.ClientsDao;
import com.bluerizon.transport.dao.CompagniesDao;
import com.bluerizon.transport.dao.TypeBusDao;
import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBus;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseClientPage;
import com.bluerizon.transport.response.ResponseClientPage;
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
public class ClientsController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_client_page}")
    private String url_client_page;
    
    @Value("${app.url_client_compagnie_page}")
    private String url_client_compagnie_page;

    @Value("${app.url_clients_search_page}")
    private String url_clients_search_page;

    @Value("${app.url_client_compagnie_search_page}")
    private String url_client_compagnie_search_page;

    @Autowired
    private ClientsDao clientsDao;

    @Autowired
    private CompagniesDao compagniesDao;


    @RequestMapping(value = { "/clients/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Clients selectOne(@PathVariable("id") final Long id) {
        return this.clientsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/clients_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Clients> selectNoDeleted() {
        return this.clientsDao.findByDeletedFalseOrderByIdClientDesc();
    }
    
    @RequestMapping(value = { "/clients_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Clients> selectDeleted() {
        return this.clientsDao.findByDeletedTrueOrderByIdClientDesc();
    }

    @RequestMapping(value = { "/clients" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Clients save(@Validated @RequestBody final Clients request) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        request.setCompagnie(compagnie);
        request.setCode("CL-"+(clientsDao.count()+1));
        return this.clientsDao.save(request);
    }

    @RequestMapping(value = { "/clients/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Clients update(@PathVariable("id") final Long id, @Validated @RequestBody final Clients request) {
        Clients clientInit = this.clientsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        clientInit.setCompagnie(compagnie);
        clientInit.setContact(request.getContact());
        clientInit.setNomComplet(request.getNomComplet());
        return this.clientsDao.save(clientInit);
    }

    @RequestMapping(value ="/client_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseClientPage selectClientPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Clients> clients = this.clientsDao.findAllByDeletedFalse(pageable);

        ResponseClientPage clientPage = new ResponseClientPage();

        Long total = this.clientsDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            clientPage.setTotal(total);
            clientPage.setPer_page(page_size);
            clientPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            clientPage.setLast_page(lastPage);
            clientPage.setFirst_page_url(url_client_page+1);
            clientPage.setLast_page_url(url_client_page+lastPage);
            if (page >= lastPage){

            }else {
                clientPage.setNext_page_url(url_client_page+(page+1));
            }

            if (page == 1){
                clientPage.setPrev_page_url(null);
                clientPage.setFrom(1L);
                clientPage.setTo(Long.valueOf(page_size));
            } else {
                clientPage.setPrev_page_url(url_client_page+(page-1));
                clientPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                clientPage.setTo(Long.valueOf(page_size) * page);
            }
            clientPage.setPath(path);
            clientPage.setData(clients);
        }else {
            clientPage.setTotal(0L);
        }

        return clientPage;
    }
    
    @RequestMapping(value ="/client_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseClientPage selectClientCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Clients> clients = this.clientsDao.findByCompagnie(compagnie, pageable);

        ResponseClientPage clientPage = new ResponseClientPage();

        Long total = this.clientsDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            clientPage.setTotal(total);
            clientPage.setPer_page(page_size);
            clientPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            clientPage.setLast_page(lastPage);
            clientPage.setFirst_page_url(url_client_compagnie_page+id+"/"+1);
            clientPage.setLast_page_url(url_client_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                clientPage.setNext_page_url(url_client_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                clientPage.setPrev_page_url(null);
                clientPage.setFrom(1L);
                clientPage.setTo(Long.valueOf(page_size));
            } else {
                clientPage.setPrev_page_url(url_client_compagnie_page+id+"/"+(page-1));
                clientPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                clientPage.setTo(Long.valueOf(page_size) * page);
            }
            clientPage.setPath(path);
            clientPage.setData(clients);
        }else {
            clientPage.setTotal(0L);
        }

        return clientPage;
    }


    @RequestMapping(value = "/clients_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseClientPage searchClientPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Clients> clients = this.clientsDao.recherche(s, pageable);

        ResponseClientPage clientPage = new ResponseClientPage();
        Long total = this.clientsDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            clientPage.setTotal(total);
            clientPage.setPer_page(page_size);
            clientPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            clientPage.setLast_page(lastPage);
            clientPage.setFirst_page_url(url_clients_search_page+1+"/"+s);
            clientPage.setLast_page_url(url_clients_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                clientPage.setNext_page_url(url_clients_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                clientPage.setPrev_page_url(null);
                clientPage.setFrom(1L);
                clientPage.setTo(Long.valueOf(page_size));
            } else {
                clientPage.setPrev_page_url(url_clients_search_page+(page-1)+"/"+s);
                clientPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                clientPage.setTo(Long.valueOf(page_size) * page);
            }

            clientPage.setPath(path);
            clientPage.setData(clients);

        }else {
            clientPage.setTotal(0L);
        }

        return clientPage;
    }

    @RequestMapping(value = "/client_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseClientPage searchClientCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Clients> clients = this.clientsDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseClientPage clientPage = new ResponseClientPage();
        Long total = this.clientsDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            clientPage.setTotal(total);
            clientPage.setPer_page(page_size);
            clientPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            clientPage.setLast_page(lastPage);
            clientPage.setFirst_page_url(url_client_compagnie_search_page+id+"/"+1+"/"+s);
            clientPage.setLast_page_url(url_client_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                clientPage.setNext_page_url(url_client_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                clientPage.setPrev_page_url(null);
                clientPage.setFrom(1L);
                clientPage.setTo(Long.valueOf(page_size));
            } else {
                clientPage.setPrev_page_url(url_client_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                clientPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                clientPage.setTo(Long.valueOf(page_size) * page);
            }

            clientPage.setPath(path);
            clientPage.setData(clients);

        }else {
            clientPage.setTotal(0L);
        }

        return clientPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
