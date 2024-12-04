package com.lojagame.loja_de_games.controller;

import com.lojagame.loja_de_games.model.Categoria;
import com.lojagame.loja_de_games.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> readAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> readById(@PathVariable Long id) {
        return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categoria>> readByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(categoriaRepository.findByDescricaoContainingIgnoreCase(descricao));
    }


    @PutMapping
    public ResponseEntity<Categoria> update(@Valid @RequestBody Categoria categoria) {
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                        .body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        // Se o Optional estiver vazio (categoria não encontrada), retorna 404
        if (categoria.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }

        // Se a categoria for encontrada, deleta a categoria e retorna 200 OK
        categoriaRepository.delete(categoria.get());
        return ResponseEntity.ok("Deletado com sucesso!");

    }
}
