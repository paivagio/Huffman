
import java.util.Map;

public class NoHuffman implements Comparable<NoHuffman>{
	
	private char letter;
    private int count;

    private NoHuffman left;
    private NoHuffman right;

    public NoHuffman(char symbol) {
        this.letter = symbol;
    }

    public NoHuffman(NoHuffman left, NoHuffman right) {
        this.letter = '+';
        this.left = left;
        this.right = right;
    }

    public boolean isFolha() {
        return left == null && right == null;
    }

	public int getFrequency() {
        if (isFolha())
            return count;
        return left.getFrequency() + right.getFrequency();
	}

    public char getLetter() {
        return letter;
    }

    public NoHuffman getLeft() {
        return left;
    }

    public NoHuffman getRight() {
        return right;
    }

    public void add() {
        count++;
    }

    @Override
	public int compareTo(NoHuffman o) {
        if(getFrequency() > o.getFrequency()) return 1;
        else return -1;
	}

    public void preencherMapa(Mapa<Character, String> map, String num) {
        if (isFolha()) {
            map.adicionar(getLetter(), num);
            return;
        }

        left.preencherMapa(map, num + "0");
        right.preencherMapa(map, num + "1");
    }
}
