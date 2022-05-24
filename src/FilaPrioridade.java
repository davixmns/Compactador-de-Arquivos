public class FilaPrioridade {
    private No primeiro;
    private No ultimo;
    private int tamanho;

    public FilaPrioridade() {
        this.primeiro = null;
        this.ultimo = null;
        tamanho = 0;
    }

    public void enqueue(String value, int weight) {
        No novo = new No(value, weight);

        if (primeiro == null) {
            primeiro = novo;
            ultimo = novo;
        } else if (novo.weight >= primeiro.weight) {
            novo.prox = primeiro;
            primeiro = novo;
        } else if (novo.weight < ultimo.weight) {
            ultimo.prox = novo;
            ultimo = novo;
        } else {

            No atual = primeiro;

            while(novo.weight < atual.prox.weight){
                atual = atual.prox;
            }

            novo.prox = atual.prox;
            atual.prox = novo;

        }
        tamanho++;
    }

    public String dequeue(){
        String elemento = primeiro.info;
        primeiro = primeiro.prox;
        tamanho--;
        return elemento;
    }

    public String search(int position){
        return toSearch(position).info;
    }


    public No toSearch(int position){
        No aux = primeiro;

        for (int i = 0; i < position; i++) {
            aux = aux.prox;
        }
        return aux;
    }

    public void clean(){
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    public String front(){
        return primeiro.info;
    }

    public int frontW(){
        return primeiro.weight;
    }

    public int length(){
        return tamanho;
    }


    public void show(){
        No atual = primeiro;
        while (atual!=null){
            System.out.print(atual.info + "(" + atual.weight + ") ");
            atual = atual.prox;
        }
        System.out.println();
    }

}
