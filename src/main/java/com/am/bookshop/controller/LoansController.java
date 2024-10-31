package com.am.bookshop.controller;

import com.am.bookshop.service.LoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoansController {
    private final LoansService loansService;

    public LoansController(LoansService loansService) { this.loansService = loansService; }

    @PostMapping("/prestar/{id}")
    public ResponseEntity<Void> prestar(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String userId = auth.getName();

        if (role.equals("ROLE_CLIENT") || role.equals("ROLE_ADMIN")) {
            boolean loan = loansService.loanBook(id, userId);
            return loan ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/devolver/{id}")
    public ResponseEntity<Void> devolver(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String userId = auth.getName();

        if (role.equals("ROLE_CLIENT") || role.equals("ROLE_ADMIN")) {
            boolean devuelto = loansService.returnDate(id, userId);
            return devuelto ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}