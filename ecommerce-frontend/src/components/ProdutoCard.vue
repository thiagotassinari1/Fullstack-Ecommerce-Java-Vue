<script setup>

import { computed } from 'vue';

const props = defineProps({
    produto: {
        type: Object,
        required: true,
    }
})

const precoFormatado = computed(() => {
    const valor = Number(props.produto.preco)

    if (Number.isNaN(valor)) {
        return 'R$ 0,00'
    } 

    return valor.toLocaleString('pt-br', {
        style: 'currency',
        currency: 'BRL',
    })
})

const semEstoque = computed(() => Number(props.produto.estoque <= 0))

const estoqueBaixo = computed(() => {
    const qtd = Number(props.produto.estoque)
    return qtd > 0 && qtd < 5
})
</script>

<template>
    <article class="produto-card">
        <div>
            <header class="produto-card-header">
                <h2>{{ produto.nome }}</h2>
                <span v-if="semEstoque" class="badge badge-danger">Sem estoque</span>
                <span v-else-if="estoqueBaixo" class="badge badge-warning">Estoque baixo</span>
            </header>
            <p>{{ produto.descricao || 'Produto sem descrição cadastrada.'}}</p>

            <footer>
                <strong>{{ precoFormatado }}</strong>
                <span>Estoque: {{ produto.estoque }}</span>
            </footer>
        </div>

    </article>
</template>