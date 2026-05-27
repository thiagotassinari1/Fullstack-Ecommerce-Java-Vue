import { api } from "./api"

export async function listarProdutos() {
    const response = await api.get('/produtos/todos')
    return response.data
}

export async function cadastrarProduto(produto) {
    const response = await api.post('/produtos', produto)
    return response.data
}