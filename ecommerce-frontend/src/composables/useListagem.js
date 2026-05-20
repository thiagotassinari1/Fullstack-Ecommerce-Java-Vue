import { computed, ref } from "vue";

export function useListagem(carregadorPadrao) {
    
    const itens = ref([])
    const carregando = ref(false)
    const erro = ref('')

    const vazio = computed(() => {
        return !carregando.value && !erro.value && itens.value.length === 0
    })

    const total = computed(() => itens.value.length)

    async function carregar(carregador = carregadorPadrao) {
        if (typeof carregador !== 'function') {
            throw new Error('useListagem: informe uma função de carregamento.')
        }

        carregando.value = true;
        erro.value = ''

        try {
            const dados = await carregador()
            itens.value = Array.isArray(dados) ? dados : []
        } catch {
            erro.value = 'Não foi possível carregar os dados. Verifique se o backend está no ar.'
            itens.value = []
            console.error(e)
        } finally {
            carregando.value = false
        }
    }

    function limpar() {
        itens.value = []
        erro.value = ''
    }

    return {
        itens,
        carregando,
        erro,
        vazio,
        total,
        carregar,
        limpar
    }
}