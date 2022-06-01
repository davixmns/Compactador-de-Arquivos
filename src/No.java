public class No {
    public char letra;
    public No proximo;
    public No anterior;
    public No direito;
    public No esquerdo;
    public int frequencia;

    public No(char letra, int frequencia){
        this.letra = letra;
        this.proximo = null;
        this.anterior = null;
        this.frequencia = frequencia;
    }

}