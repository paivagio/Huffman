

import java.util.function.Consumer;

public interface Mapa<K, V> {
	
    class Par<K, V> {
        private K chave;
        private V valor;

        public Par(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public K getChave() {
            return chave;
        }

        public V getValor() {
            return valor;
        }

        protected V setValor(V valor) {
            var antigo = this.valor;
            this.valor = valor;
            return antigo;
        }

        @Override
        public String toString() {
            return chave + ":" + valor;
        }
    }
    
    int getTamanho();
    boolean isVazio();

    void limpar();
    V adicionar(K chave, V valor);
    V get(K chave);

    boolean contem(K chave);

    V remover(K chave);

    void emOrdem(Consumer<Par<K,V>> consumer);
}
