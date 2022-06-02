package davi;

import java.io.PrintWriter;

public class FilaPrioridade {
    private No primeiro;
    private No ultimo;
    private int tamanho;

    public FilaPrioridade() {
        this.primeiro = null;
        this.ultimo = null;
        tamanho = 0;
    }

    public void enqueue(No a, No b){
        No novo = new No(a.frequencia, b.frequencia, a, b);

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
            No aux = this.primeiro;

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
        No novo = new No(letra, frequencia);

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
            No aux = this.primeiro;

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

    public No dequeue(){
        if (tamanho == 0)
            throw new NullPointerException("Fila vazia");
        No elemento = primeiro;
        primeiro = primeiro.proximo;
        tamanho--;
        return elemento;
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

    public No front(){
        return this.primeiro;
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
