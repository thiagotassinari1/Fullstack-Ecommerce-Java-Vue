package br.unisinos.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unisinos.ecommerce.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
                select p
                from Produto p
                where (:nome is null or lower(p.nome) like lower(concat('%', :nome, '%')))
                    and (:categoriaId is null or p.categoria.id = :categoriaId)
            """)

    Page<Produto> buscarComFiltros(String nome, Long categoriaId, Pageable pageable);

    @Query("""
                select c.nome as categoria, count(p) as quantidade
                from Produto p
                join p.categoria c
                group by c.nome
            """)
    List<ProdutoPorCategoriaProjection> relatorioProdutosPorCategoria();

}
