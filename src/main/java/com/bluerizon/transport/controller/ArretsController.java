package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.ArretsDao;
import com.bluerizon.transport.dao.ArretsDao;
import com.bluerizon.transport.dao.VillesDao;
import com.bluerizon.transport.entity.Arrets;
import com.bluerizon.transport.entity.Villes;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.CompteRequest;
import com.bluerizon.transport.requeste.DateExpirationRequeste;
import com.bluerizon.transport.requeste.NbrCompagnieRequeste;
import com.bluerizon.transport.response.ResponseArretPage;
import com.bluerizon.transport.response.ResponseArretPage;
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
public class ArretsController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_arret_page}")
    private String url_arret_page;
    
    @Value("${app.url_arret_ville_page}")
    private String url_arret_ville_page;

    @Value("${app.url_arret_search_page}")
    private String url_arret_search_page;


    @Value("${app.url_arret_ville_search_page}")
    private String url_arret_ville_search_page;

    @Autowired
    private ArretsDao arretsDao;

    @Autowired
    private VillesDao villesDao;

    @RequestMapping(value = { "/arret/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Arrets selectOne(@PathVariable("id") final Long id) {
        return this.arretsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/arret_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Arrets> selectNoDeleted() {
        return this.arretsDao.findByDeletedFalseOrderByIdArretDesc();
    }
    
    @RequestMapping(value = { "/arret_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Arrets> selectDeleted() {
        return this.arretsDao.findByDeletedTrueOrderByIdArretDesc();
    }

    @RequestMapping(value = { "/arret" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Arrets save(@Validated @RequestBody final Arrets arrets) {
        arrets.setVille(villesDao.findByIdVille(arrets.getVille().getIdVille()));
        return this.arretsDao.save(arrets);
    }

    @RequestMapping(value = { "/arret/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Arrets update(@PathVariable("id") final Long id, @Validated @RequestBody final Arrets arrets) {
        Arrets arretInit = this.arretsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        arretInit.setLibelle(arrets.getLibelle());
        arretInit.setAdresse(arrets.getAdresse());
        arretInit.setVille(villesDao.findByIdVille(arrets.getVille().getIdVille()));
        arretInit.setLatitude(arrets.getLatitude());
        arretInit.setLongitude(arrets.getLongitude());
        arretInit.setGare(arrets.isGare());
        return this.arretsDao.save(arretInit);
    }

    @RequestMapping(value ="/arret_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseArretPage selectArretPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Arrets> arrets = this.arretsDao.findAllByDeletedFalse(pageable);

        ResponseArretPage arretPage = new ResponseArretPage();

        Long total = this.arretsDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            arretPage.setTotal(total);
            arretPage.setPer_page(page_size);
            arretPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            arretPage.setLast_page(lastPage);
            arretPage.setFirst_page_url(url_arret_page+1);
            arretPage.setLast_page_url(url_arret_page+lastPage);
            if (page >= lastPage){

            }else {
                arretPage.setNext_page_url(url_arret_page+(page+1));
            }

            if (page == 1){
                arretPage.setPrev_page_url(null);
                arretPage.setFrom(1L);
                arretPage.setTo(Long.valueOf(page_size));
            } else {
                arretPage.setPrev_page_url(url_arret_page+(page-1));
                arretPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                arretPage.setTo(Long.valueOf(page_size) * page);
            }
            arretPage.setPath(path);
            arretPage.setData(arrets);
        }else {
            arretPage.setTotal(0L);
        }

        return arretPage;
    }
    
    @RequestMapping(value ="/arret_ville_page/{idVille}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseArretPage selectArretVillePage(@PathVariable(value = "idVille") Long idVille,
                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Villes villes = villesDao.findByIdVille(idVille);

        List<Arrets> arrets = this.arretsDao.findByVille(villes, pageable);

        ResponseArretPage arretPage = new ResponseArretPage();

        Long total = this.arretsDao.countByVille(villes);
        Long lastPage;

        if (total > 0){
            arretPage.setTotal(total);
            arretPage.setPer_page(page_size);
            arretPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            arretPage.setLast_page(lastPage);
            arretPage.setFirst_page_url(url_arret_ville_page+idVille+"/"+1);
            arretPage.setLast_page_url(url_arret_ville_page+idVille+"/"+lastPage);
            if (page >= lastPage){

            }else {
                arretPage.setNext_page_url(url_arret_ville_page+idVille+"/"+(page+1));
            }

            if (page == 1){
                arretPage.setPrev_page_url(null);
                arretPage.setFrom(1L);
                arretPage.setTo(Long.valueOf(page_size));
            } else {
                arretPage.setPrev_page_url(url_arret_ville_page+idVille+"/"+(page-1));
                arretPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                arretPage.setTo(Long.valueOf(page_size) * page);
            }
            arretPage.setPath(path);
            arretPage.setData(arrets);
        }else {
            arretPage.setTotal(0L);
        }

        return arretPage;
    }

    
    @RequestMapping(value = "/arret_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseArretPage searchUserDirecteurInactifPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Arrets> arrets = this.arretsDao.recherche(s, pageable);

        ResponseArretPage arretPage = new ResponseArretPage();
        Long total = this.arretsDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            arretPage.setTotal(total);
            arretPage.setPer_page(page_size);
            arretPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            arretPage.setLast_page(lastPage);
            arretPage.setFirst_page_url(url_arret_search_page+1+"/"+s);
            arretPage.setLast_page_url(url_arret_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                arretPage.setNext_page_url(url_arret_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                arretPage.setPrev_page_url(null);
                arretPage.setFrom(1L);
                arretPage.setTo(Long.valueOf(page_size));
            } else {
                arretPage.setPrev_page_url(url_arret_search_page+(page-1)+"/"+s);
                arretPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                arretPage.setTo(Long.valueOf(page_size) * page);
            }

            arretPage.setPath(path);
            arretPage.setData(arrets);

        }else {
            arretPage.setTotal(0L);
        }

        return arretPage;
    }

    @RequestMapping(value = "/arret_ville_search_page/{idVille}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseArretPage searchUserDirecteurInactifPage(@PathVariable(value = "idVille") Long idVille,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Villes ville = villesDao.findByIdVille(idVille);
        List<Arrets> arrets = this.arretsDao.rechercheVille(ville, s, pageable);

        ResponseArretPage arretPage = new ResponseArretPage();
        Long total = this.arretsDao.countRechercheVille(ville, s);
        Long lastPage;

        if (total > 0){
            arretPage.setTotal(total);
            arretPage.setPer_page(page_size);
            arretPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            arretPage.setLast_page(lastPage);
            arretPage.setFirst_page_url(url_arret_ville_search_page+idVille+"/"+1+"/"+s);
            arretPage.setLast_page_url(url_arret_ville_search_page+idVille+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                arretPage.setNext_page_url(url_arret_ville_search_page+idVille+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                arretPage.setPrev_page_url(null);
                arretPage.setFrom(1L);
                arretPage.setTo(Long.valueOf(page_size));
            } else {
                arretPage.setPrev_page_url(url_arret_ville_search_page+idVille+"/"+(page-1)+"/"+s);
                arretPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                arretPage.setTo(Long.valueOf(page_size) * page);
            }

            arretPage.setPath(path);
            arretPage.setData(arrets);

        }else {
            arretPage.setTotal(0L);
        }

        return arretPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
