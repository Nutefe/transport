package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseBusPage;
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
public class BusController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_bus_page}")
    private String url_bus_page;
    
    @Value("${app.url_bus_compagnie_page}")
    private String url_bus_compagnie_page;
    
    @Value("${app.url_bus_type_bus_page}")
    private String url_bus_type_bus_page;

    @Value("${app.url_bus_search_page}")
    private String url_bus_search_page;

    @Value("${app.url_bus_compagnie_search_page}")
    private String url_bus_compagnie_search_page;

    @Value("${app.url_bus_type_bus_search_page}")
    private String url_bus_type_bus_search_page;

    @Autowired
    private BusDao busDao;

    @Autowired
    private TypeBusDao typeBusDao;

    @Autowired
    private CompagniesDao compagniesDao;


    @RequestMapping(value = { "/bus/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Bus selectOne(@PathVariable("id") final Long id) {
        return this.busDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/bus_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Bus> selectNoDeleted() {
        return this.busDao.findByDeletedFalseOrderByIdBusDesc();
    }
    
    @RequestMapping(value = { "/bus_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Bus> selectDeleted() {
        return this.busDao.findByDeletedTrueOrderByIdBusDesc();
    }

    @RequestMapping(value = { "/bus" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Bus save(@Validated @RequestBody final Bus request) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        TypeBus typeBus = typeBusDao.findByIdTypeBus(request.getTypeBus().getIdTypeBus());
        request.setCompagnie(compagnie);
        request.setTypeBus(typeBus);
        return this.busDao.save(request);
    }

    @RequestMapping(value = { "/bus/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Bus update(@PathVariable("id") final Long id, @Validated @RequestBody final Bus request) {
        Bus busInit = this.busDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        TypeBus typeBus = typeBusDao.findByIdTypeBus(request.getTypeBus().getIdTypeBus());
        busInit.setCompagnie(compagnie);
        busInit.setTypeBus(typeBus);
        busInit.setNombrePlace(request.getNombrePlace());
        busInit.setNumeroMatricule(request.getNumeroMatricule());
        return this.busDao.save(busInit);
    }

    @RequestMapping(value ="/bus_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBusPage selectBusPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Bus> bus = this.busDao.findAllByDeletedFalse(pageable);

        ResponseBusPage busPage = new ResponseBusPage();

        Long total = this.busDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_page+1);
            busPage.setLast_page_url(url_bus_page+lastPage);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_page+(page+1));
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_page+(page-1));
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }
            busPage.setPath(path);
            busPage.setData(bus);
        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }
    
    @RequestMapping(value ="/bus_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBusPage selectBusCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Bus> bus = this.busDao.findByCompagnie(compagnie, pageable);

        ResponseBusPage busPage = new ResponseBusPage();

        Long total = this.busDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_compagnie_page+id+"/"+1);
            busPage.setLast_page_url(url_bus_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_compagnie_page+id+"/"+(page-1));
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }
            busPage.setPath(path);
            busPage.setData(bus);
        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }
    
    @RequestMapping(value ="/bus_type_bus_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBusPage selectBusTypeBusPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        TypeBus typeBus = typeBusDao.findByIdTypeBus(id);
        List<Bus> bus = this.busDao.findByTypeBus(typeBus, pageable);

        ResponseBusPage busPage = new ResponseBusPage();

        Long total = this.busDao.countByTypeBus(typeBus);
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_type_bus_page+id+"/"+1);
            busPage.setLast_page_url(url_bus_type_bus_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_type_bus_page+id+"/"+(page+1));
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_type_bus_page+id+"/"+(page-1));
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }
            busPage.setPath(path);
            busPage.setData(bus);
        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }


    @RequestMapping(value = "/bus_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBusPage searchBusPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Bus> bus = this.busDao.recherche(s, pageable);

        ResponseBusPage busPage = new ResponseBusPage();
        Long total = this.busDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_search_page+1+"/"+s);
            busPage.setLast_page_url(url_bus_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_search_page+(page-1)+"/"+s);
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }

            busPage.setPath(path);
            busPage.setData(bus);

        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }

    @RequestMapping(value = "/bus_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBusPage searchBusCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Bus> bus = this.busDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseBusPage busPage = new ResponseBusPage();
        Long total = this.busDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_compagnie_search_page+id+"/"+1+"/"+s);
            busPage.setLast_page_url(url_bus_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }

            busPage.setPath(path);
            busPage.setData(bus);

        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }

    @RequestMapping(value = "/bus_type_bus_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBusPage searchBusTypeBusPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        TypeBus typeBus = typeBusDao.findByIdTypeBus(id);
        List<Bus> bus = this.busDao.rechercheTypeBus(typeBus, s, pageable);

        ResponseBusPage busPage = new ResponseBusPage();
        Long total = this.busDao.countRechercheTypeBus(typeBus, s);
        Long lastPage;

        if (total > 0){
            busPage.setTotal(total);
            busPage.setPer_page(page_size);
            busPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            busPage.setLast_page(lastPage);
            busPage.setFirst_page_url(url_bus_type_bus_search_page+id+"/"+1+"/"+s);
            busPage.setLast_page_url(url_bus_type_bus_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                busPage.setNext_page_url(url_bus_type_bus_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                busPage.setPrev_page_url(null);
                busPage.setFrom(1L);
                busPage.setTo(Long.valueOf(page_size));
            } else {
                busPage.setPrev_page_url(url_bus_type_bus_search_page+id+"/"+(page-1)+"/"+s);
                busPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                busPage.setTo(Long.valueOf(page_size) * page);
            }

            busPage.setPath(path);
            busPage.setData(bus);

        }else {
            busPage.setTotal(0L);
        }

        return busPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
