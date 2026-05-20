package br.unisinos.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtualizarEstoqueDTO(
    
    @NotNull
    @Min(value = 0, message = "O estoque não pode ser negativo.")
    Integer estoque

) {}
