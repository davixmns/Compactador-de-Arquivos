package fila;

public class Fila { // Fila de prioridade
    private NoFila primeiro;
    private NoFila ultimo;
    private int tamanho;

    public Fila() {
        this.primeiro = null;
        this.ultimo = null;
        tamanho = 0;
    }

    public void enqueue(NoFila novo){
        if (this.tamanho == 0) {
            primeiro = novo;
            ultimo = novo;

        } else if(novo.frequencia <= this.primeiro.frequencia){
            novo.proximo = this.primeiro;
            this.primeiro.anterior = novo;
            this.primeiro = novo;

        } else if(novo.frequencia >= this.ultimo.frequencia){
            this.ultimo.proximo = novo;
            novo.anterior = this.ultimo;
            this.ultimo = novo;

        } else {
            NoFila aux = this.primeiro;

            while(aux.proximo != null && novo.frequencia >= aux.proximo.frequencia){
                aux = aux.proximo;
            }

            novo.proximo = aux.proximo;
            novo.anterior = aux;
            aux.proximo.anterior = novo;
            aux.proximo = novo;
        }
        this.tamanho++;
    }

    public void enqueue(char letra, int frequencia) {
        NoFila novo = new NoFila(letra, frequencia);

        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;

        } else if(novo.frequencia <= this.primeiro.frequencia){
            novo.proximo = this.primeiro;
            this.primeiro.anterior = novo;
            this.primeiro = novo;

        } else if(novo.frequencia >= this.ultimo.frequencia){
            this.ultimo.proximo = novo;
            novo.anterior = this.ultimo;
            this.ultimo = novo;

        } else {
            NoFila aux = this.primeiro;

            while(aux.proximo != null && novo.frequencia >= aux.proximo.frequencia){
                aux = aux.proximo;
            }

            novo.proximo = aux.proximo;
            novo.anterior = aux;
            aux.proximo.anterior = novo;
            aux.proximo = novo;
        }
        this.tamanho++;
    }

    public NoFila dequeue(){
        if (tamanho == 0)
            throw new NullPointerException("fila.Fila vazia");
        NoFila elemento = primeiro;
        primeiro = primeiro.proximo;
        tamanho--;
        return elemento;
    }

    public boolean contains(char letra){
        NoFila atual = this.primeiro;
        while (atual != null){
            if(atual.caractere == letra){
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public char get(int position){
        NoFila aux = primeiro;
        for (int i = 0; i < position; i++) {
            aux = aux.proximo;
        }
        return aux.caractere;
    }

    public void clear(){
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    public NoFila front(){
        return this.primeiro;
    }

    public int size(){
        return tamanho;
    }

    public void show(){
        NoFila atual = primeiro;
        while (atual!=null){
            System.out.print(atual.caractere + " " + atual.frequencia + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }

}
