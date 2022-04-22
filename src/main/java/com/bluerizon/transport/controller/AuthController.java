package com.bluerizon.transport.controller;

import com.bluerizon.transport.dao.PaysDao;
import com.bluerizon.transport.dao.RolesDao;
import com.bluerizon.transport.dao.UsersDao;
import com.bluerizon.transport.entity.RefreshTokens;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.exception.TokenRefreshException;
import com.bluerizon.transport.helper.Helpers;
import com.bluerizon.transport.requeste.CompteRequest;
import com.bluerizon.transport.requeste.LoginRequest;
import com.bluerizon.transport.requeste.SignupRequest;
import com.bluerizon.transport.requeste.TokenRefreshRequest;
import com.bluerizon.transport.response.JwtAuthenticationResponse;
import com.bluerizon.transport.response.TokenRefreshResponse;
import com.bluerizon.transport.security.JwtTokenProvider;
import com.bluerizon.transport.security.UserPrincipal;
import com.bluerizon.transport.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private RolesDao rolesDao;

    @Autowired
    private PaysDao paysDao;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );


        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users users = usersDao.findByIdUser(userPrincipal.getId());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        RefreshTokens refreshTokens = refreshTokenService.createRefreshToken(users.getIdUser());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshTokens.getToken(), users));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokens::getUsers)
                .map(user -> {
                    String token = jwtTokenProvider.generateTokenFromUsername(user.getIdUser());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @RequestMapping(value = { "/signup" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Users save(@Validated @RequestBody final SignupRequest user) {
        Users userInit = new Users();
        userInit.setEmail(user.getEmail());
        userInit.setUsername(user.getUsername());
        userInit.setPassword(user.getPassword());
        userInit.setRole(rolesDao.findByIdRole(6));
        userInit.setPays(paysDao.findByIdPays(user.getPays().getIdPays()));
        userInit.setExpirer(Helpers.defaultDate());
        userInit.setNbrCompagnie(0);
        userInit.setActive(false);
        return this.usersDao.save(userInit);
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
