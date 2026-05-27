<script setup>
import { onMounted, ref } from 'vue';
import { listarCategorias } from '../services/categoriaService';

const props = defineProps({
    filtros: {
        type: Object,
        required: true,
    }
})

defineEmits(['limpar'])

const categorias = ref([])
const erroCategorias = ref('')

onMounted(async () => {
    try {
        const dados = await listarCategorias()
        categorias.value = Array.isArray(dados) ? dados : []
    } catch (e) {
        erroCategorias.value = 'Não foi possível carregar as categorias.'
        console.error(e)
    }
})

</script>

<template>
    <section class="filtros">
        <header class="filtros-header">
            <h3>Filtros</h3>
            <button type="button" class="btn-link" @click="$emit('limpar')">Limpar filtros</button>
        </header>

        <div class="filtros-grid">
            <label class="campo">
                <span>Buscar por nome</span>
                <input type="search" v-model="props.filtros.nome" placeholder="Ex.: notebook">
            </label>

            <label class="campo">
                <span>Categoria</span>
                <select v-model="props.filtros.categoriaId">
                    <option :value="null">Todas</option>
                    <option 
                        v-for="categoria in categorias"
                        :key="categoria.id"
                        :value="categoria.id">
                        {{ categoria.nome }}
                    </option>
                </select>
                <small v-if="erroCategorias" class="campo-erro">
                    {{ erroCategorias }}
                </small>
            </label>

            <label class="campo">
                <span>Preço mínimo</span>
                <input type="number" min="0" step="0.01" v-model.number="props.filtros.precoMin" placeholder="0,00">
            </label>

            <label class="campo">
                <span>Preço máximo</span>
                <input type="number" min="0" step="0.01" v-model.number="props.filtros.precoMax" placeholder="0,00">
            </label>

            <label class="campo campo-checkbox">
                <input type="checkbox" v-model="props.filtros.apenasDisponiveis">
                <span>Apenas com estoque</span>
            </label>
        </div>
    </section>
</template>