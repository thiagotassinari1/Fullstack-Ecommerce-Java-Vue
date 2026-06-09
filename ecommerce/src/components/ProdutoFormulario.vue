<script setup>
import { onMounted, ref, computed } from 'vue'
import { listarCategorias } from '../services/categoriaService'
import { cadastrarProduto } from '../services/produtoService'

const emit = defineEmits(['produto-cadastrado'])
const tentouEnviar = ref(false)

const produto = ref({
    nome: '',
    descricao: '',
    preco: '',
    estoque: '',
    categoriaId: '',
})

const categorias = ref([])
const carregandoCategorias = ref(false)
const erroCategorias = ref('')
const salvando = ref(false)
const erroFormulario = ref('')
const mensagemSucesso = ref('')

function limparFormulario() {
    produto.value = {
        nome: '',
        descricao: '',
        preco: '',
        estoque: '',
        categoriaId: '',
    }

    tentouEnviar.value = false
}

function validarFormulario() {
    tentouEnviar.value = true
    return formularioValido.value
}

const erros = computed(() => {
    const errosAtuais = {}

    if (!produto.value.nome.trim()) {
        errosAtuais.nome = 'Informe o nome do produto.'
    } else if (produto.value.nome.trim().length < 3) {
        errosAtuais.nome = 'O nome deve ter pelo menos 3 caracteres.'
    }

    if (produto.value.descricao.length > 500) {
        errosAtuais.descricao = 'A descrição deve ter no máximo 500 caracteres.'
    }

    if (!produto.value.preco) {
        errosAtuais.preco = 'Informe o preço do produto.'
    } else if (Number(produto.value.preco) <= 0) {
        errosAtuais.preco = 'O preço deve ser maior que zero.'
    }

    if (produto.value.estoque === '') {
        errosAtuais.estoque = 'Informe o estoque do produto.'
    } else if (Number(produto.value.estoque) < 0) {
        errosAtuais.estoque = 'O estoque deve ser maior ou igual a zero.'
    }

    if (!produto.value.categoriaId) {
        errosAtuais.categoriaId = 'Selecione uma categoria.'
    }

    return errosAtuais
})

const formularioValido = computed(() => {
    return Object.keys(erros.value).length === 0
})

async function salvarProduto() {
    erroFormulario.value = ''
    mensagemSucesso.value = ''

    if (!validarFormulario()) {
        erroFormulario.value = 'Revise os campos destacados antes de continuar.'
        return
    }

    salvando.value = true

    try {
        await cadastrarProduto({
            nome: produto.value.nome,
            descricao: produto.value.descricao,
            preco: Number(produto.value.preco),
            estoque: Number(produto.value.estoque),
            categoriaId: Number(produto.value.categoriaId)
        })

        mensagemSucesso.value = 'Produto cadastrado com sucesso.'
        limparFormulario()
        emit('produto-cadastrado')
    } catch (error) {
        erroFormulario.value = 'Não foi possível cadastrar o produto. Verifique os dados informados.'
        console.error(error)
    } finally {
        salvando.value = false
    }
}

async function carregarCategorias() {
    carregandoCategorias.value = true
    erroCategorias.value = ''

    try {
        categorias.value = await listarCategorias()
    } catch (error) {
        erroCategorias.value = 'Não foi possível carregar as categorias. Verifique se o backend está no ar.'
        console.error(error)
    } finally {
        carregandoCategorias.value = false
    }
}

onMounted(() => {
    carregarCategorias()
})

</script>

<template>
    <section class="form-panel">
        <div class="section-title">
            <h2>Novo produto</h2>
            <p>Cadastre um produto usando a API do backend.</p>
        </div>

        <form class="produto-form" @submit.prevent="salvarProduto">
            <label>
                Nome
                <input v-model="produto.nome" type="text" maxlength="120" placeholder="Ex.: Mouse sem fio" autofocus :class="{ 'input-error': tentouEnviar && erros.nome }">
                <span v-if="tentouEnviar && erros.nome" class="field-error">
                    {{ erros.nome }}
                </span>
            </label>

            <label>
                Descrição
                <textarea v-model="produto.descricao" rows="3" maxlength="500" placeholder="Descrição curta do produto" :class="{ 'input-error': tentouEnviar && erros.descricao }"/>
                <span class="field-help">
                    {{ produto.descricao.length }}/500 caracteres
                </span>
                <span v-if="tentouEnviar && erros.descricao" class="field-error">
                    {{ erros.descricao }}
                </span>
            </label>

            <div class="form-grid">
                <label>
                    Preço
                    <input v-model="produto.preco" type="number" min="0" step="0.01">
                </label>

                <label>
                    Estoque
                    <input v-model="produto.estoque" type="number" min="0" step="1">
                </label>
            </div>

            <p v-if="carregandoCateorias" class="form-help">Carregando categorias...</p>
            <p v-else-if="erroCateorias" class="form-error">{{ erroCateorias }}</p>
            <label v-else>
                Categoria
                <select v-model="produto.categoriaId" :class="{ 'input-error': tentouEnviar && erros.categoriaId }">
                    <option value="">Selecione uma categoria</option>
                    <option 
                        v-for="categoria in categorias"
                        :key="categoria.id"
                        :value="categoria.id">{{ categoria.nome }}</option>
                </select>
                <span v-if="tentouEnviar && erros.categoriaId" class="field-error">
                    {{ erros.categoriaId }}
                </span>
            </label>

            <p v-if="erroFormulario" class="form-error">{{ erroFormulario }}</p>
            <p v-if="mensagemSucesso" class="form-success">{{ mensagemSucesso }}</p>

            <button type="submit" :disabled="salvando || carregandoCategorias">
                {{salvando ? 'Cadastrando...' : 'Cadastrar produto'}}
            </button>
        </form>
    </section>
</template>