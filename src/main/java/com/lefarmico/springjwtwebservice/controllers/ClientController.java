package com.lefarmico.springjwtwebservice.controllers;

import com.lefarmico.springjwtwebservice.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/client", produces = APPLICATION_JSON_VALUE)
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientRepository clientRepository;

//    @GetMapping("/add")
//    @PostMapping(consumes = APPLICATION_JSON_VALUE)
//    public ResponseEntity<Client> addUser(@RequestBody Client client) {
////        clientRepository.save(client);
//    }
}
