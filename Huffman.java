
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {
	private NoHuffman raiz;

	public PriorityQueue<NoHuffman> countFrequencies(String sentence) {
		MapaOrdenado<Character, NoHuffman> count = new MapaOrdenado<>(Comparator.naturalOrder());
		
		for(char ch : sentence.toCharArray()) {
			if(!count.contem(ch)) {
				count.adicionar(ch, new NoHuffman(ch));
			}
			count.get(ch).add();
		}
		
		PriorityQueue<NoHuffman> priority = new PriorityQueue<>();
	
		count.emOrdem(par -> priority.add(par.getValor()));

		return priority;
	}
	
	public NoHuffman createTree(PriorityQueue<NoHuffman> nos) {
		
		while(true) {
			NoHuffman no1 = nos.poll();
			NoHuffman no2 = nos.poll();
			
			NoHuffman parent = new NoHuffman(no1,no2);
			
			if(nos.isEmpty()) {
				return parent;
			}
			
			nos.add(parent);
		}
	}

	private Mapa<Character, String> createCodeMap() {
		Mapa<Character, String> resultado = new MapaOrdenado<>(Comparator.naturalOrder());
		raiz.preencherMapa(resultado, "");
		return resultado;
	}

	public String encode(String text) {
		raiz = createTree(countFrequencies(text));
		Mapa<Character, String> map = createCodeMap();

		StringBuilder data = new StringBuilder();
		for (char ch : text.toCharArray()) {
			data.append(map.get(ch));
		}
		return data.toString();
	}


	public String decode(String data) {
		NoHuffman atual = raiz;

		StringBuilder resultado = new StringBuilder();
		for (char ch : data.toCharArray()) {
			if (ch == '0') {
				atual = atual.getLeft();
			} else {
				atual = atual.getRight();
			}

			if (atual.isFolha()) {
				resultado.append(atual.getLetter());
				atual = raiz;
			}
		}
		return resultado.toString();
	}


}
