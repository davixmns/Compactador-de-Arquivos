public class FilaPrioridade {
    private No primeiro;
    private No ultimo;
    private int tamanho;

    public FilaPrioridade() {
        this.primeiro = null;
        this.ultimo = null;
        tamanho = 0;
    }

    public void enqueue(char letra, int frequencia) {
        No novo = new No(letra, frequencia);

        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;

        } else if (novo.frequencia >= primeiro.frequencia) {
            novo.proximo = primeiro;
            primeiro = novo;

        } else if (novo.frequencia <= ultimo.frequencia) {
            ultimo.proximo = novo;
            ultimo = novo;

        } else {
            No atual = primeiro;
            while (novo.frequencia <= atual.proximo.frequencia) {
                atual = atual.proximo;
            }
            novo.proximo = atual.proximo;
            atual.proximo = novo;
        }
        tamanho++;
    }

    public char dequeue(){
        if (tamanho == 0)
            throw new NullPointerException("Fila vazia");
        No elemento = primeiro;
        primeiro = primeiro.proximo;
        tamanho--;
        return elemento.letra;
    }

    public boolean contains(char letra){
        No atual = this.primeiro;
        while (atual != null){
            if(atual.letra == letra){
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public char get(int position){
        No aux = primeiro;
        for (int i = 0; i < position; i++) {
            aux = aux.proximo;
        }
        return aux.letra;
    }

    public void clear(){
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    public char front(){
        return primeiro.letra;
    }

    public int frontF(){
        return primeiro.frequencia;
    }

    public int size(){
        return tamanho;
    }


    public void show(){
        No atual = primeiro;
        while (atual!=null){
            System.out.print(atual.letra + "(" + atual.frequencia + ") ");
            atual = atual.proximo;
        }
        System.out.println();
    }

}
