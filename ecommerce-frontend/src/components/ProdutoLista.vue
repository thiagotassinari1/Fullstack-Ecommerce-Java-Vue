<script setup>
import { onMounted } from 'vue';
import ProdutoCard from './ProdutoCard.vue';
import PainelLista from './PainelLista.vue';
import { listarProdutos } from '../services/produtoService';
import { useListagem } from '../composables/useListagem';

const {
    itens: produtos,
    carregando,
    erro,
    vazio,
    total,
    carregar: carregarProdutos,
} = useListagem(listarProdutos)

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
        :total="total"
        rotulo-total="produto(s) cadastrado(s)"
        :carregando="carregando"
        :erro="erro"
        :vazio="vazio"
        mensagem-vazio="Nenhum produto cadastrado."
        @atualizar="carregarProdutos"
    >
        <section class="produto-grid">
            <ProdutoCard 
                v-for="produto in produtos"
                :key="produto.id"
                :produto="produto"
            />
        </section>
    </PainelLista>
</template>