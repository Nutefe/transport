package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.RolesDao;
import com.bluerizon.transport.dao.UsersDao;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.CompteRequest;
import com.bluerizon.transport.requeste.DateExpirationRequeste;
import com.bluerizon.transport.requeste.NbrCompagnieRequeste;
import com.bluerizon.transport.response.ResponseUserPage;
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
public class UsersController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_user_all_no_admin_page}")
    private String url_user_all_no_admin_page;

    @Value("${app.url_user_all_admin_page}")
    private String url_user_all_admin_page;

    @Value("${app.url_user_all_directeur_page}")
    private String url_user_all_directeur_page;

    @Value("${app.url_user_directeur_actif_page}")
    private String url_user_directeur_actif_page;

    @Value("${app.url_user_directeur_inactif_page}")
    private String url_user_directeur_inactif_page;

    @Value("${app.url_user_all_directeur_search_page}")
    private String url_user_all_directeur_search_page;

    @Value("${app.url_user_all_no_admin_search_page}")
    private String url_user_all_no_admin_search_page;

    @Value("${app.url_user_directeur_actif_search_page}")
    private String url_user_directeur_actif_search_page;

    @Value("${app.url_user_directeur_inactif_search_page}")
    private String url_user_directeur_inactif_search_page;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private RolesDao rolesDao;

    @GetMapping("/user/me")
    public Users getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        Users users = usersDao.findByIdUser(currentUser.getId());
        return users;
    }

    @RequestMapping(value = { "/user_optional/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Users selectOneOptional(@PathVariable("id") final Long id) {
        return this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }

    @RequestMapping(value = { "/user/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Users selectOne(@PathVariable("id") final Long id) {
        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Users save(@Validated @RequestBody final CompteRequest user) {
        Users userInit = new Users();
        userInit.setNom(user.getNom());
        userInit.setPrenom(user.getPrenom());
        userInit.setEmail(user.getEmail());
        userInit.setUsername(user.getUsername());
        userInit.setPassword(user.getPassword());
        userInit.setTelephone(user.getTelephone());
        userInit.setAdresse(user.getAdresse());
        userInit.setRole(rolesDao.findByIdRole(user.getRole().getIdRole()));
        userInit.setExpirer(user.getExpirer());
        userInit.setNbrCompagnie(user.getNbrCompagnie());
        userInit.setActive(user.isActive());
        return this.usersDao.save(userInit);
    }

    @RequestMapping(value = { "/user/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users update(@PathVariable("id") final Long id, @Validated @RequestBody final CompteRequest user) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));

        this.usersDao.updateUser(userInit.getIdUser(), user.getUsername(), user.getEmail(), user.getNom(),
                user.getPrenom(), user.getTelephone(), user.getAdresse(), user.getExpirer(), user.getNbrCompagnie(),
                rolesDao.findByIdRole(user.getRole().getIdRole()));

        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user_connect" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateConnect(@Validated @RequestBody final CompteRequest user, @CurrentUser UserPrincipal currentUser) {
        Users userInit = this.usersDao.getOneOptional(currentUser.getId()).orElseThrow(() -> new NotFoundRequestException("Objet n'existe pas!"));

        this.usersDao.updateUser(userInit.getIdUser(), user.getUsername(), user.getEmail(), user.getNom(),
                user.getPrenom(), user.getTelephone(), user.getAdresse(), user.getExpirer(),
                user.getNbrCompagnie(), rolesDao.findByIdRole(user.getRole().getIdRole()));

        return this.usersDao.findByIdUser(currentUser.getId());
    }

    @RequestMapping(value = { "/user_nbr_compagnie/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateNbrCompagnie(@PathVariable("id") final Long id, @Validated @RequestBody final NbrCompagnieRequeste requeste) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        this.usersDao.updateNbrCompagnie(userInit.getIdUser(), requeste.getNbrCompagnie());
        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user_expirer/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateDateExpiration(@PathVariable("id") final Long id, @Validated @RequestBody final DateExpirationRequeste requeste) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        this.usersDao.updateExpire(userInit.getIdUser(), requeste.getExpirer());
        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user_active/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateActive(@PathVariable("id") final Long id) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        this.usersDao.active(userInit.getIdUser());
        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user_desactive/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateDesactive(@PathVariable("id") final Long id) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        this.usersDao.desactive(userInit.getIdUser());
        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user/{id}" }, method = { RequestMethod.DELETE })
    @ResponseStatus(HttpStatus.OK)
    public Users delete(@PathVariable("id") final Long id) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        userInit.setDeleted(true);
        return this.usersDao.save(userInit);
    }


    @RequestMapping(value = { "/check_username/{s}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUsername(@PathVariable("s") final String s) {
        return this.usersDao.existsByUsername(s);
    }

    @RequestMapping(value = { "/check_username_up/{s}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUsername(@PathVariable("s") final String s, @CurrentUser final UserPrincipal currentUser) {
        final Users users = this.usersDao.getOneOptional(currentUser.getId()).orElseThrow(() -> new NotFoundRequestException("Objet n'existe pas!"));
        return this.usersDao.existsByUsername(s, users.getIdUser());
    }

    @RequestMapping(value = { "/check_email/{s}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkEmail(@PathVariable("s") final String s) {
        return this.usersDao.existsByEmail(s);
    }

    @RequestMapping(value = { "/check_email_up/{s}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkEmail(@PathVariable("s") final String s, @CurrentUser final UserPrincipal currentUser) {
        final Users users = this.usersDao.getOneOptional(currentUser.getId()).orElseThrow(() -> new NotFoundRequestException("Objet n'existe pas!"));
        return this.usersDao.existsByEmail(s, users.getIdUser());
    }

    @RequestMapping(value = { "/user_all_no_admin" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Users> selectAllUserNoAdmin(@CurrentUser UserPrincipal currentUser) {
        return this.usersDao.selectUser(currentUser.getId());
    }

    @RequestMapping(value ="/user_all_no_admin_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserPage selectUserNoAdminPage(@PathVariable(value = "page") int page,
                                                   @CurrentUser UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Users> users = this.usersDao.selectUser(currentUser.getId(), pageable);

        ResponseUserPage userPage = new ResponseUserPage();

        Long total = this.usersDao.countUser(currentUser.getId());
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_all_no_admin_page+1);
            userPage.setLast_page_url(url_user_all_no_admin_page+lastPage);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_all_no_admin_page+(page+1));
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_all_no_admin_page+(page-1));
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }
            userPage.setPath(path);
            userPage.setData(users);
        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = { "/user_all_admin" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Users> selectAllUserAdmin(@CurrentUser UserPrincipal currentUser) {
        return this.usersDao.selectUserSys(currentUser.getId());
    }

    @RequestMapping(value ="/user_all_admin_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserPage selectUserAdminPage(@PathVariable(value = "page") int page,
                                                   @CurrentUser UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Users> users = this.usersDao.selectUserSys(currentUser.getId(), pageable);

        ResponseUserPage userPage = new ResponseUserPage();

        Long total = this.usersDao.countUserSys(currentUser.getId());
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_all_admin_page+1);
            userPage.setLast_page_url(url_user_all_admin_page+lastPage);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_all_admin_page+(page+1));
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_all_admin_page+(page-1));
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }
            userPage.setPath(path);
            userPage.setData(users);
        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = { "/user_all_directeur" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Users> selectAllUserDirecteur() {
        return this.usersDao.selectUserDirecteur();
    }

    @RequestMapping(value ="/user_all_directeur_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserPage selectUserDirecteurPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Users> users = this.usersDao.selectUserDirecteur(pageable);

        ResponseUserPage userPage = new ResponseUserPage();

        Long total = this.usersDao.countUserDirecteur();
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_all_directeur_page+1);
            userPage.setLast_page_url(url_user_all_directeur_page+lastPage);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_all_directeur_page+(page+1));
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_all_directeur_page+(page-1));
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }
            userPage.setPath(path);
            userPage.setData(users);
        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = { "/user_directeur_actif" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Users> selectAllUserDirecteurActif() {
        return this.usersDao.selectUserDirecteurActif();
    }

    @RequestMapping(value ="/user_directeur_actif_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserPage selectUserDirecteurActifPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Users> users = this.usersDao.selectUserDirecteurActif(pageable);

        ResponseUserPage userPage = new ResponseUserPage();

        Long total = this.usersDao.countUserDirecteurActif();
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_directeur_actif_page+1);
            userPage.setLast_page_url(url_user_directeur_actif_page+lastPage);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_directeur_actif_page+(page+1));
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_directeur_actif_page+(page-1));
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }
            userPage.setPath(path);
            userPage.setData(users);
        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = { "/user_directeur_inactif" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Users> selectAllUserDirecteurInactif() {
        return this.usersDao.selectUserDirecteurDesactif();
    }

    @RequestMapping(value ="/user_directeur_inactif_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserPage selectUserDirecteurInactifPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Users> users = this.usersDao.selectUserDirecteurDesactif(pageable);

        ResponseUserPage userPage = new ResponseUserPage();

        Long total = this.usersDao.countUserDirecteurDesactif();
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_directeur_inactif_page+1);
            userPage.setLast_page_url(url_user_directeur_inactif_page+lastPage);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_directeur_inactif_page+(page+1));
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_directeur_inactif_page+(page-1));
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }
            userPage.setPath(path);
            userPage.setData(users);
        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = "/user_all_no_admin_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserPage searchUserAllNoAdminPage(@PathVariable(value = "page") int page,
                                                    @PathVariable(value = "s") String s,
                                                     @CurrentUser UserPrincipal currentUser){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Users> users = this.usersDao.rechercheUser(s, currentUser.getId(), pageable);

        ResponseUserPage userPage = new ResponseUserPage();
        Long total = this.usersDao.countRechercheUser(s, currentUser.getId());
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_all_no_admin_search_page+1+"/"+s);
            userPage.setLast_page_url(url_user_all_no_admin_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_all_no_admin_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_all_no_admin_search_page+(page-1)+"/"+s);
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }

            userPage.setPath(path);
            userPage.setData(users);

        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = "/user_all_directeur_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserPage searchUserAllDirecteurPage(@PathVariable(value = "page") int page,
                                                    @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Users> users = this.usersDao.rechercheDirecteur(s, pageable);

        ResponseUserPage userPage = new ResponseUserPage();
        Long total = this.usersDao.countRechercheDirecteur(s);
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_all_directeur_search_page+1+"/"+s);
            userPage.setLast_page_url(url_user_all_directeur_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_all_directeur_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_all_directeur_search_page+(page-1)+"/"+s);
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }

            userPage.setPath(path);
            userPage.setData(users);

        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = "/user_directeur_actif_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserPage searchUserDirecteurActifPage(@PathVariable(value = "page") int page,
                                                    @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Users> users = this.usersDao.rechercheDirecteurActif(s, pageable);

        ResponseUserPage userPage = new ResponseUserPage();
        Long total = this.usersDao.countRechercheDirecteurActif(s);
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_directeur_actif_search_page+1+"/"+s);
            userPage.setLast_page_url(url_user_directeur_actif_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_directeur_actif_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_directeur_actif_search_page+(page-1)+"/"+s);
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }

            userPage.setPath(path);
            userPage.setData(users);

        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    @RequestMapping(value = "/user_directeur_inactif_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserPage searchUserDirecteurInactifPage(@PathVariable(value = "page") int page,
                                                    @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Users> users = this.usersDao.rechercheDirecteurDesactif(s, pageable);

        ResponseUserPage userPage = new ResponseUserPage();
        Long total = this.usersDao.countRechercheDirecteurDesactif(s);
        Long lastPage;

        if (total > 0){
            userPage.setTotal(total);
            userPage.setPer_page(page_size);
            userPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userPage.setLast_page(lastPage);
            userPage.setFirst_page_url(url_user_directeur_inactif_search_page+1+"/"+s);
            userPage.setLast_page_url(url_user_directeur_inactif_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userPage.setNext_page_url(url_user_directeur_inactif_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                userPage.setPrev_page_url(null);
                userPage.setFrom(1L);
                userPage.setTo(Long.valueOf(page_size));
            } else {
                userPage.setPrev_page_url(url_user_directeur_inactif_search_page+(page-1)+"/"+s);
                userPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userPage.setTo(Long.valueOf(page_size) * page);
            }

            userPage.setPath(path);
            userPage.setData(users);

        }else {
            userPage.setTotal(0L);
        }

        return userPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
