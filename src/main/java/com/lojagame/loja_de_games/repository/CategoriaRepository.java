package com.lojagame.loja_de_games.repository;

import com.lojagame.loja_de_games.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public List<Categoria> findByDescricaoContainingIgnoreCase(@Param("descriçao") String descricao);

}





