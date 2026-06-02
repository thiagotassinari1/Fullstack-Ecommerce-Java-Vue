package br.unisinos.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisinos.ecommerce.dto.AtualizarEstoqueDTO;
import br.unisinos.ecommerce.dto.ProdutoRequestDTO;
import br.unisinos.ecommerce.dto.ProdutoResponseDTO;
import br.unisinos.ecommerce.entity.Produto;
import br.unisinos.ecommerce.repository.ProdutoPorCategoriaProjection;
import br.unisinos.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor

public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        ProdutoResponseDTO produtoSalvo = produtoService.salvar(produtoDTO);
        return ResponseEntity.ok(produtoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);

        if (produto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        produtoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    /*
        Exemplo de busca:
        /produtos/nome=mouse&categoriaId=1&page=0&size=5&sort=nome,asc 
    */

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoriaId,
            Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscar(nome, categoriaId, pageable));
    }

    @GetMapping("/relatorio/por-categoria")
    public ResponseEntity<List<ProdutoPorCategoriaProjection>> relatorioProdutosPorCategoria() {
        return ResponseEntity.ok(produtoService.relatorioProdutosPorCategoria());
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponseDTO> atualizarEstoque(@PathVariable Long id, @Valid @RequestBody AtualizarEstoqueDTO request) {
        return ResponseEntity.ok(produtoService.atualizarEstoque(id, request.estoque()));
    }

}
