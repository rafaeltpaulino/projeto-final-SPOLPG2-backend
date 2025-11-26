package br.com.ifsp.backend.controller.catalog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class MasterController {

    @PostMapping("/insert")
    public ResponseEntity<?> insert() {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return null;
    }
}
