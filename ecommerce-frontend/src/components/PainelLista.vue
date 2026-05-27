<script setup>
defineProps({
  titulo: {
    type: String,
    required: true,
  },
  total: {
    type: Number,
    default: 0,
  },
  rotuloTotal: {
    type: String,
    default: 'item(ns)',
  },
  carregando: {
    type: Boolean,
    default: false,
  },
  erro: {
    type: String,
    default: '',
  },
  vazio: {
    type: Boolean,
    default: false,
  },
  mensagemVazio: {
    type: String,
    default: 'Nenhum registro encontrado.',
  },
})

defineEmits(['atualizar'])
</script>

<template>
  <section class="lista-bloco">
    <header class="toolbar">
      <div>
        <h2>{{ titulo }}</h2>
        <p class="toolbar-info">{{ total }} {{ rotuloTotal }}</p>
      </div>
      <div class="toolbar-acoes">
        <slot name="acoes"></slot>
        <button type="button" @click="$emit('atualizar')">Atualizar</button>
      </div>
    </header>

    <slot name="filtros"></slot>

    <p v-if="carregando" class="status">Carregando...</p>
    <p v-else-if="erro" class="status status-error">{{ erro }}</p>
    <p v-else-if="vazio" class="status">{{ mensagemVazio }}</p>

    <div v-else>
      <slot></slot>
    </div>
  </section>
</template>