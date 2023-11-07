package com.app.demoservice.controller;

import com.app.demoservice.entity.Accounts;
import com.app.demoservice.model.RestResponse;
import com.app.demoservice.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping(value = "/save")
    public ResponseEntity<RestResponse> save(@RequestBody Accounts accounts){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.isOk(accountsService.save(accounts)));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<RestResponse> update(@RequestBody Accounts accounts){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.isOk(accountsService.update(accounts)));
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<RestResponse> delete(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.isOk(accountsService.delete(id)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse> getAccounts(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.isOk(accountsService.getAccounts(id)));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<RestResponse> search(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestResponse.isOk(accountsService.search(pageable)));
    }
}
