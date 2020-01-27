package org.n52.oidcspring.controller;

import java.util.HashMap;
import java.util.Map;

import org.n52.oidcspring.authentication.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private RequestUtils utils;

    @GetMapping(path = "/me")
    @PreAuthorize("hasAnyAuthority('ROLE_LICENSED_USER')")
    public ResponseEntity<Map<String, Object>> getUserName() {
        Map<String, Object> info = new HashMap<>();
        info.put("username", utils.getUserName());
        info.put("roles", utils.getUserRoles());
        return ResponseEntity.ok(info);
    }

}
