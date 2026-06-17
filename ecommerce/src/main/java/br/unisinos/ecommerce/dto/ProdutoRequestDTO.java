package br.unisinos.ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO( 

    @NotBlank
    String nome,

    @Size(max = 500)
    String descricao,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal preco,

    @NotNull
    Integer estoque,

    @NotNull
    Long categoriaId

) {}
