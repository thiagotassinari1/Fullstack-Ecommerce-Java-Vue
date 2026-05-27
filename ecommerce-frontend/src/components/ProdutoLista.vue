<script setup>
import { onMounted } from 'vue';
import ProdutoCard from './ProdutoCard.vue';
import PainelLista from './PainelLista.vue';
import ProdutoFiltros from './ProdutoFiltros.vue';
import { listarProdutos } from '../services/produtoService';
import { useListagem } from '../composables/useListagem';
import { useFiltro } from '../composables/useFiltro';

const { filtros, limpar: limparFiltros } = useFiltro({
    nome: '',
    categoriaId: null,
    precoMin: null,
    precoMax: null,
    apenasDisponiveis: false,
})

function filtrarProdutos(lista) {
    return lista.filter((produto) => {
        if (filtros.nome.trim()) {
            const termo = filtros.nome.trim().toLowerCase()
            const nomeProduto = (produto.nome || '').toLowerCase()
            if (!nomeProduto.includes(termo)) {
                return false
            }
        }

        if (filtros.categoriaId !== null && filtros.categoriaId !== '') {
            const idCategoria = produto.categoria?.id ?? produto.categoriaId
            if (idCategoria !== filtros.categoriaId) {
                return false
            }
        }

        const preco = Number(produto.preco)
        if (filtros.precoMin !== null && !Number.isNaN(filtros.precoMin)) {
            if (preco < filtros.precoMin) {
                return false
            }
        }

        if (filtros.precoMax !== null && !Number.isNaN(filtros.precoMax)) {
            if (preco > filtros.precoMax) {
                return false
            }
        }

        if (filtros.apenasDisponiveis && Number(produto.estoque) <= 0) {
            return false
        }

        return true

    })
}

const {
    itensFiltrados: produtosFiltrados,
    carregando,
    erro,
    vazio,
    semResultado,
    total,
    totalFiltrado,
    carregar: carregarProdutos,
} = useListagem(listarProdutos, filtrarProdutos)

defineExpose({
    carregarProdutos,
})

onMounted(() => {
    carregarProdutos()
})

</script>

<template>
    <PainelLista 
        titulo="Catálogo de produtos"
        :total="totalFiltrado"
        rotulo-total="produto(s) exibido(s)"
        :carregando="carregando"
        :erro="erro"
        :vazio="vazio"
        mensagem-vazio="Nenhum produto cadastrado."
        @atualizar="carregarProdutos"
    >
        <template #filtros>
            <ProdutoFiltros :filtros="filtros" @limpar="limparFiltros"/>
        </template>

        <p v-if="semResultado" class="status">
            Nenhum produto corresponde aos filtros aplicados.
            <button type="button" class="btn-link" @click="limparFiltros">Limpar filtros</button>
        </p>

        <section v-else class="produto-grid">
            <ProdutoCard 
                v-for="produto in produtosFiltrados"
                :key="produto.id"
                :produto="produto"
            />
        </section>

        <p class="toolbar-info contagem-rodape">
            Mostrando {{ totalFiltrado }} de {{ total }} produtos(s).
        </p>
    </PainelLista>
</template>