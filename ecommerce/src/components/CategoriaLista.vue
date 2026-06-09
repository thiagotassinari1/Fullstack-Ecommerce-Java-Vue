<script setup>
import { onMounted } from 'vue';
import PainelLista from './PainelLista.vue';
import { listarCategorias } from '../services/categoriaService';
import { useListagem } from '../composables/useListagem';

const {
    itens: categorias,
    carregando,
    erro,
    vazio,
    total,
    carregar: carregarCategorias,
} = useListagem(listarCategorias)

defineExpose({
    carregarCategorias,
})

onMounted(() => {
    carregarCategorias()
})

</script>

<template>

    <PainelLista 
        titulo="Categorias"
        :total="total"
        rotulo-total="categoria(s) cadastrada(s)"
        :carregando="carregando"
        :erro="erro"
        :vazio="vazio"
        mensagem-vazio="Nenhuma categoria cadastrada."
        @atualizar="carregarCategorias"
    >
        <ul class="categoria-lista">
            <li v-for="categoria in categorias" :key="categoria.id">
                <strong>{{ categoria.nome }}</strong>
                <span v-if="categoria.descricao"> - {{ categoria.descricao }}</span>
            </li>
        </ul>
    </PainelLista>


</template>