

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

public class MapaOrdenado<K, V> implements Mapa<K, V> {
    private static class No<K, V> {
        Par<K, V> par;
        No<K, V> esquerda;
        No<K, V> direita;

        public No(K k, V v) {
            this.par = new Par<>(k, v);
        }

        public boolean isFolha() {
            return esquerda == null && direita == null;
        }
    }

    private static class NosRetorno<K, V> {
        No<K, V> pai;
        No<K, V> no;
        int cmp;

        public boolean isRaiz() { return pai == null; }
        public boolean isEsquerda() {
            return pai != null && pai.esquerda == no;
        }
    }

    private final Comparator<? super K> comparator;
    private No<K, V> raiz;
    private int tamanho;

    public MapaOrdenado(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public boolean isVazio() {
        return raiz == null;
    }

    @Override
    public void limpar() {
        raiz = null;
        tamanho = 0;
    }

    private NosRetorno<K, V> acharNos(K chave) {
        var ret = new NosRetorno<K, V>();
        ret.no = raiz;
        while (ret.no != null) {
            ret.cmp = comparator.compare(chave, ret.no.par.getChave());
            if (ret.cmp == 0) {
                return ret;
            }

            ret.pai = ret.no;
            ret.no = ret.cmp < 0 ?
                ret.no.esquerda : ret.no.direita;
        }
        return ret;
    }
    
    @Override
    public V adicionar(K chave, V valor) {
        if (isVazio()) {
            raiz = new No<>(chave, valor);
            tamanho = tamanho + 1;
            return null;
        }

        var nos = acharNos(chave);
        if (nos.cmp == 0) return nos.no.par.setValor(valor);

        if (nos.cmp < 0) {
            nos.pai.esquerda = new No<>(chave, valor);
        } else {
            nos.pai.direita = new No<>(chave, valor);
        }
        tamanho = tamanho + 1;
        return null;
    }

    @Override
    public V get(K chave) {
        var nos = acharNos(chave);
        return nos.no == null ? null : nos.no.par.getValor();
    }

    @Override
    public boolean contem(K chave) {
        return acharNos(chave).no != null;
    }

    private NosRetorno<K, V> acharSucessor(No<K, V> no) {
        var ret = new NosRetorno<K, V>();
        ret.pai = no;
        ret.no = no.direita;
        while (ret.no.esquerda != null) {
            ret.pai = ret.no;
            ret.no = ret.no.esquerda;
        }
        return ret;
    }

    @Override
    public V remover(K chave) {
        var nos = acharNos(chave);
        if (nos.no == null) {
            return null;
        }

        var retorno = nos.no.par.getValor();

        //Caso 1: Exclusão de uma folha
        if (nos.no.isFolha()) {
            if (nos.isRaiz()) {
                raiz = null;
            } else if (nos.isEsquerda()) {
                nos.pai.esquerda = null;
            } else {
                nos.pai.direita = null;
            }

        //Caso 2: Exclusão com um só filho
        } else if (nos.no.direita == null || nos.no.esquerda == null) {
            //Obtém o no abaixo do nó excluído
            var no = nos.no.direita == null ?
                    nos.no.esquerda : nos.no.direita;
            if (nos.isRaiz()) {
                raiz = no;
            } else {
                if (nos.isEsquerda()) {
                    nos.pai.esquerda = no;
                } else {
                    nos.pai.direita = no;
                }
            }
        }
        //Caso 3: Exclusão com 2 filhos
        else {
            var sucessor = acharSucessor(nos.no);

            nos.no.par = sucessor.no.par;
            if (sucessor.no == nos.no.direita) {
                nos.no.direita = sucessor.no.direita;
            } else {
                sucessor.pai.esquerda = sucessor.no.direita;
            }
        }
        tamanho = tamanho - 1;
        return retorno;
    }
    
    @Override
    public void emOrdem(Consumer<Par<K,V>> consumer) {
        emOrdem(raiz, consumer);
    }

    private void emOrdem(No<K,V> no, Consumer<Par<K,V>> consumer) {
        if (no == null) return;
        emOrdem(no.esquerda, consumer);
        consumer.accept(no.par);
        emOrdem(no.direita, consumer);
    }
}
