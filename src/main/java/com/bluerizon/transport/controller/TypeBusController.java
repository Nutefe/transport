package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.TypeBusRequest;
import com.bluerizon.transport.response.ResponseTypeBusPage;
import com.bluerizon.transport.response.ResponseTypeBusPage;
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
public class TypeBusController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_type_bus_page}")
    private String url_type_bus_page;
    
    @Value("${app.url_type_bus_compagnie_page}")
    private String url_type_bus_compagnie_page;

    @Value("${app.url_type_bus_search_page}")
    private String url_type_bus_search_page;

    @Value("${app.url_type_bus_compagnie_search_page}")
    private String url_type_bus_compagnie_search_page;

    @Autowired
    private TypeBusDao typeBusDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private CompagniesDao compagniesDao;

    @Autowired
    private UserCompagnieDao userCompagnieDao;


    @RequestMapping(value = { "/type_bus/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public TypeBus selectOne(@PathVariable("id") final Long id) {
        return this.typeBusDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/type_bus_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<TypeBus> selectNoDeleted() {
        return this.typeBusDao.findByDeletedFalseOrderByIdTypeBusDesc();
    }
    
    @RequestMapping(value = { "/type_bus_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<TypeBus> selectDeleted() {
        return this.typeBusDao.findByDeletedTrueOrderByIdTypeBusDesc();
    }


    @RequestMapping(value = { "/type_bus_compagnie/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<TypeBus> SelectAllByCompagnie(@PathVariable("id") final Long id) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        return this.typeBusDao.findByCompagnie(compagnie);
    }

    @RequestMapping(value = { "/type_bus" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public TypeBus save(@Validated @RequestBody final TypeBusRequest request, @CurrentUser UserPrincipal currentUser) {
        Users user = usersDao.findByIdUser(currentUser.getId());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        TypeBus typeBus = new TypeBus();
        typeBus.setUserCompagnie(new UserCompagnies(new UserPK(user, compagnie)));
        typeBus.setLibelle(request.getLibelle());
        return this.typeBusDao.save(typeBus);
    }

    @RequestMapping(value = { "/type_bus/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public TypeBus update(@PathVariable("id") final Long id, @Validated @RequestBody final TypeBusRequest request) {
        TypeBus typeBusInit = this.typeBusDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        typeBusInit.setLibelle(request.getLibelle());
        return this.typeBusDao.save(typeBusInit);
    }

    @RequestMapping(value ="/type_bus_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTypeBusPage selectTypeBusPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<TypeBus> typeBus = this.typeBusDao.findAllByDeletedFalse(pageable);

        ResponseTypeBusPage typeBusPage = new ResponseTypeBusPage();

        Long total = this.typeBusDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            typeBusPage.setTotal(total);
            typeBusPage.setPer_page(page_size);
            typeBusPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            typeBusPage.setLast_page(lastPage);
            typeBusPage.setFirst_page_url(url_type_bus_page+1);
            typeBusPage.setLast_page_url(url_type_bus_page+lastPage);
            if (page >= lastPage){

            }else {
                typeBusPage.setNext_page_url(url_type_bus_page+(page+1));
            }

            if (page == 1){
                typeBusPage.setPrev_page_url(null);
                typeBusPage.setFrom(1L);
                typeBusPage.setTo(Long.valueOf(page_size));
            } else {
                typeBusPage.setPrev_page_url(url_type_bus_page+(page-1));
                typeBusPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBusPage.setTo(Long.valueOf(page_size) * page);
            }
            typeBusPage.setPath(path);
            typeBusPage.setData(typeBus);
        }else {
            typeBusPage.setTotal(0L);
        }

        return typeBusPage;
    }
    
    @RequestMapping(value ="/type_bus_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTypeBusPage selectTypeBusCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<TypeBus> typeBus = this.typeBusDao.findByCompagnie(pageable,compagnie);

        ResponseTypeBusPage typeBusPage = new ResponseTypeBusPage();

        Long total = this.typeBusDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            typeBusPage.setTotal(total);
            typeBusPage.setPer_page(page_size);
            typeBusPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            typeBusPage.setLast_page(lastPage);
            typeBusPage.setFirst_page_url(url_type_bus_compagnie_page+id+"/"+1);
            typeBusPage.setLast_page_url(url_type_bus_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                typeBusPage.setNext_page_url(url_type_bus_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                typeBusPage.setPrev_page_url(null);
                typeBusPage.setFrom(1L);
                typeBusPage.setTo(Long.valueOf(page_size));
            } else {
                typeBusPage.setPrev_page_url(url_type_bus_compagnie_page+id+"/"+(page-1));
                typeBusPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBusPage.setTo(Long.valueOf(page_size) * page);
            }
            typeBusPage.setPath(path);
            typeBusPage.setData(typeBus);
        }else {
            typeBusPage.setTotal(0L);
        }

        return typeBusPage;
    }


    @RequestMapping(value = "/type_bus_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTypeBusPage searchtypeBusPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<TypeBus> typeBus = this.typeBusDao.recherche(s, pageable);

        ResponseTypeBusPage typeBusPage = new ResponseTypeBusPage();
        Long total = this.typeBusDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            typeBusPage.setTotal(total);
            typeBusPage.setPer_page(page_size);
            typeBusPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            typeBusPage.setLast_page(lastPage);
            typeBusPage.setFirst_page_url(url_type_bus_search_page+1+"/"+s);
            typeBusPage.setLast_page_url(url_type_bus_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                typeBusPage.setNext_page_url(url_type_bus_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                typeBusPage.setPrev_page_url(null);
                typeBusPage.setFrom(1L);
                typeBusPage.setTo(Long.valueOf(page_size));
            } else {
                typeBusPage.setPrev_page_url(url_type_bus_search_page+(page-1)+"/"+s);
                typeBusPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBusPage.setTo(Long.valueOf(page_size) * page);
            }

            typeBusPage.setPath(path);
            typeBusPage.setData(typeBus);

        }else {
            typeBusPage.setTotal(0L);
        }

        return typeBusPage;
    }

    @RequestMapping(value = "/type_bus_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTypeBusPage searchClientCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<TypeBus> typeBus = this.typeBusDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseTypeBusPage typeBusPage = new ResponseTypeBusPage();
        Long total = this.typeBusDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            typeBusPage.setTotal(total);
            typeBusPage.setPer_page(page_size);
            typeBusPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            typeBusPage.setLast_page(lastPage);
            typeBusPage.setFirst_page_url(url_type_bus_compagnie_search_page+id+"/"+1+"/"+s);
            typeBusPage.setLast_page_url(url_type_bus_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                typeBusPage.setNext_page_url(url_type_bus_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                typeBusPage.setPrev_page_url(null);
                typeBusPage.setFrom(1L);
                typeBusPage.setTo(Long.valueOf(page_size));
            } else {
                typeBusPage.setPrev_page_url(url_type_bus_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                typeBusPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBusPage.setTo(Long.valueOf(page_size) * page);
            }

            typeBusPage.setPath(path);
            typeBusPage.setData(typeBus);

        }else {
            typeBusPage.setTotal(0L);
        }

        return typeBusPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
