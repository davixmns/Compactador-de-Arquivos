public class No {
    public char letra;
    public No proximo;
    public No direito;
    public No esquerdo;
    public No anterior;
    public int numero;
    public int frequencia;

    public No(char letra, int frequencia){
        this.letra = letra;
        this.proximo = null;
        this.anterior = null;
        this.frequencia = frequencia;
    }

    public No(int filhoA, int filhoB){
        this.numero = filhoA + filhoB;
        this.direito = null;
        this.esquerdo = null;
    }

}