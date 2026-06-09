import { api } from "./api"

export async function listarProdutos() {
    const response = await api.get('/produtos/todos')
    return response.data
}

export async function cadastrarProduto(produto) {
    const response = await api.post('/produtos', produto)
    return response.data
}

export async function buscarProdutosPaginados({ page, size, nome, categoriaId, sort } = {}) {
    const params = { page, size, nome }

    if (nome && nome.trim()) {
        params.nome = nome.trim()
    }

    if (categoriaId !== null && categoriaId !== undefined && categoriaId !== '') {
        params.categoriaId = categoriaId
    }

    if (sort) {
        params.sort = sort
    }

    const response = await api.get('/produtos', { params })
    return response.data
}