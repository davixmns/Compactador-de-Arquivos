public class No {
    public String info;
    public No prox;
    public No previous;
    public int weight;

    public No(String info, int weight){
        this.info = info;
        this.prox = null;
        this.previous = null;
        this.weight = weight;
    }

}