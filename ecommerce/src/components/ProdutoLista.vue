<script setup>
import { computed, onMounted, watch } from 'vue'
import ProdutoCard from './ProdutoCard.vue'
import PainelLista from './PainelLista.vue'
import ProdutoFiltros from './ProdutoFiltros.vue'
import ProdutoPaginacao from './ProdutoPaginacao.vue'
import { buscarProdutosPaginados } from '../services/produtoService.js'
import { useListagemPaginada } from '../composables/useListagemPaginada.js'
import { useFiltro } from '../composables/useFiltro.js'

const { filtros, limpar: limparFiltros } = useFiltro({
    nome: '',
    categoriaId: null,
    precoMin: null,
    precoMax: null,
    apenasDisponiveis: false,
})

function buscador({ page, size }) {
    return buscarProdutosPaginados({
        page,
        size,
        nome: filtros.nome,
        categoriaId: filtros.categoriaId,
        sort: 'nome,asc',
    })
}

const {
    itens: produtos,
    carregando,
    erro,
    vazio,
    pagina,
    numeroHumano,
    carregar: carregarProdutos,
    reiniciar,
    proxima,
    anterior,
    mudarTamanho,
} = useListagemPaginada(buscador, { tamanhoInicial: 10 })

const produtosVisiveis = computed(() => {
    return produtos.value.filter((produto) => {
        const preco = Number(produto.preco)
        if (filtros.precoMin !== null && !Number.isNaN(filtros.precoMin)) {
            if (preco < filtros.precoMin) return false
        }
        if (filtros.precoMax !== null && !Number.isNaN(filtros.precoMax)) {
            if (preco > filtros.precoMax) return false
        }
        if (filtros.apenasDisponiveis && Number(produto.estoque) <= 0) {
            return false
        }
        return true
    })
})

const escondidosNaPagina = computed(() => {
    return produtos.value.length - produtosVisiveis.value.length
})

let temporizador = null
watch(
    () => [filtros.nome, filtros.categoriaId],
    () => {
        clearTimeout(temporizador)
        temporizador = setTimeout(() => {
            reiniciar()
        }, 350)
    }
)

defineExpose({ carregarProdutos: reiniciar })

onMounted(() => {
    carregarProdutos()
})
</script>

<template>
    <PainelLista
        titulo="Catálogo de produtos"
        :total="pagina.totalItens"
        rotulo-total="produto(s) no total"
        :carregando="carregando"
        :erro="erro"
        :vazio="vazio"
        mensagem-vazio="Nenhum produto cadastrado."
        @atualizar="carregarProdutos"
    >
        <template #filtros>
            <ProdutoFiltros :filtros="filtros" @limpar="limparFiltros" />
        </template>

        <p v-if="produtosVisiveis.length === 0" class="status">
            Nenhum produto desta página corresponde aos filtros de preço/estoque.
        </p>

        <section v-else class="produto-grid">
            <ProdutoCard
                v-for="produto in produtosVisiveis"
                :key="produto.id"
                :produto="produto"
            />
        </section>

        <p v-if="escondidosNaPagina > 0" class="toolbar-info">
            {{ escondidosNaPagina }} item(ns) desta página foram escondidos pelos filtros locais.
        </p>

        <ProdutoPaginacao
            :numero-humano="numeroHumano"
            :total-paginas="pagina.totalPaginas"
            :total-itens="pagina.totalItens"
            :tamanho="pagina.tamanho"
            :primeira="pagina.primeira"
            :ultima="pagina.ultima"
            :desabilitado="carregando"
            @anterior="anterior"
            @proxima="proxima"
            @mudar-tamanho="mudarTamanho"
        />
    </PainelLista>
</template>
