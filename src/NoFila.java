public class NoFila {
    public Character caractere;
    public NoFila proximo;
    public NoFila direito;
    public NoFila esquerdo;
    public NoFila anterior;
    public int frequencia;

    public NoFila(char letra, int frequencia){
        this.caractere = letra;
        this.proximo = null;
        this.anterior = null;
        this.direito = null;
        this.esquerdo = null;
        this.frequencia = frequencia;
    }

    public NoFila(NoFila esquerdo, NoFila direito){
        this.frequencia = esquerdo.frequencia + direito.frequencia;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

}