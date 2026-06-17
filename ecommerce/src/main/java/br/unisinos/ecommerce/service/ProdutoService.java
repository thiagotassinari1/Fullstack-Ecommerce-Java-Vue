package br.unisinos.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.unisinos.ecommerce.dto.ProdutoRequestDTO;
import br.unisinos.ecommerce.dto.ProdutoResponseDTO;
import br.unisinos.ecommerce.entity.Categoria;
import br.unisinos.ecommerce.entity.Produto;
import br.unisinos.ecommerce.exception.RecursoNaoEncontradoException;
import br.unisinos.ecommerce.exception.RegraNegocioException;
import br.unisinos.ecommerce.repository.CategoriaRepository;
import br.unisinos.ecommerce.repository.ProdutoPorCategoriaProjection;
import br.unisinos.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoDTO) {

        Categoria categoria = buscarCategoria(produtoDTO.categoriaId());

        Produto produto = Produto.builder()
                .nome(produtoDTO.nome())
                .descricao(produtoDTO.descricao())
                .preco(produtoDTO.preco())
                .estoque(produtoDTO.estoque())
                .categoria(categoria)
                .build();

        return toResponseDTO(produtoRepository.save(produto));
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria() != null ? produto.getCategoria().getId() : null,
                produto.getCategoria() != null ? produto.getCategoria().getNome() : null);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new RegraNegocioException("A categoria do produto é obrigatória.");
        }

        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada com o ID: " + categoriaId));
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());

        return produtoRepository.save(produto);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

    public Page<ProdutoResponseDTO> buscar(String nome, Long categoriaId, Pageable pageable) {
        return produtoRepository.buscarComFiltros(nome, categoriaId, pageable).map(this::toResponseDTO);
    }

    public List<ProdutoPorCategoriaProjection> relatorioProdutosPorCategoria() {
        return produtoRepository.relatorioProdutosPorCategoria();
    }

    public ProdutoResponseDTO atualizarEstoque(Long id, Integer estoque) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o id: " + id));

        produto.setEstoque(estoque);
        return toResponseDTO(produtoRepository.save(produto));
    }
}
