package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.UsersDao;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.CompteRequest;
import com.bluerizon.transport.requeste.DateExpirationRequeste;
import com.bluerizon.transport.requeste.NbrCompagnieRequeste;
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

    @Value("${app.url_user_agent_page}")
    private String url_user_agent_page;
    
    @Value("${app.url_user_admin_page}")
    private String url_user_admin_page;
    
    @Value("${app.url_user_directeur_page}")
    private String url_user_directeur_page;
    
    @Value("${app.url_user_directeur_actif_page}")
    private String url_user_directeur_actif_page;
    
    @Value("${app.url_user_directeur_desactif_page}")
    private String url_user_directeur_desactif_page;
    
    @Value("${app.url_user_agent_search_page}")
    private String url_user_agent_search_page;

    @Value("${app.url_user_directeur_search_page}")
    private String url_user_directeur_search_page;

    @Value("${app.url_user_directeur_actif_search_page}")
    private String url_user_directeur_actif_search_page;

    @Value("${app.url_user_directeur_desactif_search_page}")
    private String url_user_directeur_desactif_search_page;

    @Autowired
    private UsersDao usersDao;

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
        userInit.setRole(user.getRole());
        userInit.setExpirer(user.getExpirer());
        userInit.setNbrCompagnie(user.getNbrCompagnie());
        userInit.setActive(user.isActive());
        return this.usersDao.save(userInit);
    }

    @RequestMapping(value = { "/user/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users update(@PathVariable("id") final Long id, @Validated @RequestBody final CompteRequest user) {
        Users userInit = this.usersDao.getOneOptional(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        userInit.setNom(user.getNom());
        userInit.setPrenom(user.getPrenom());
        userInit.setEmail(user.getEmail());
        userInit.setUsername(user.getUsername());
        userInit.setPassword(user.getPassword());
        userInit.setTelephone(user.getTelephone());
        userInit.setAdresse(user.getAdresse());
        userInit.setRole(user.getRole());
        userInit.setExpirer(user.getExpirer());
        userInit.setNbrCompagnie(user.getNbrCompagnie());
        userInit.setActive(user.isActive());

        this.usersDao.updateUser(userInit.getIdUser(), userInit.getUsername(), userInit.getEmail(), userInit.getNom(),
                userInit.getPrenom(), userInit.getTelephone(), userInit.getAdresse(), userInit.getExpirer(),
                userInit.getNbrCompagnie(), userInit.getRole());

        return this.usersDao.findByIdUser(id);
    }

    @RequestMapping(value = { "/user_connect" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Users updateConnect(@Validated @RequestBody final CompteRequest user, @CurrentUser UserPrincipal currentUser) {
        Users userInit = this.usersDao.getOneOptional(currentUser.getId()).orElseThrow(() -> new NotFoundRequestException("Objet n'existe pas!"));
        userInit.setNom(user.getNom());
        userInit.setPrenom(user.getPrenom());
        userInit.setEmail(user.getEmail());
        userInit.setUsername(user.getUsername());
        userInit.setPassword(user.getPassword());
        userInit.setTelephone(user.getTelephone());
        userInit.setAdresse(user.getAdresse());

        this.usersDao.updateUser(userInit.getIdUser(), userInit.getUsername(), userInit.getEmail(), userInit.getNom(),
                userInit.getPrenom(), userInit.getTelephone(), userInit.getAdresse(), userInit.getExpirer(),
                userInit.getNbrCompagnie(), userInit.getRole());

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

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
