<script setup>
import { ref } from 'vue'
import AppHeader from './components/AppHeader.vue'
import ProdutoFormulario from './components/ProdutoFormulario.vue';
import ProdutoLista from './components/ProdutoLista.vue';
import CategoriaLista from './components/CategoriaLista.vue';

const produtoLista = ref(null)

function atualizarProdutos() {
  produtoLista.value?.carregarProdutos()
}

</script>

<template>
  <AppHeader />

  <main class="container">
    <section class="page-title">
      <p class="eyebrow">Frontend</p>
      <h1>Listagens de produtos e categorias</h1>
      <p>Listagens reutilizáveis com composables e slots.</p>
    </section>

    <ProdutoFormulario @produto-cadastrado="atualizarProdutos"/>
    <ProdutoLista ref="produtoLista"/>

    <CategoriaLista/>

  </main>

</template>
