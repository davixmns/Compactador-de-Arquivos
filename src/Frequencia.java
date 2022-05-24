public class Frequencia {
    private final int[] frequenciaVetor; //Cada índice representa um decimal de um char

    public Frequencia() {
        this.frequenciaVetor = new int[256]; //UTF-8 (8 bits de representação = 256 caracteres)
    }

    public void incrementar(int decimal) { //Contagem para o histograma
        if(decimal > 255)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        this.frequenciaVetor[decimal] += 1;
    }

    public int getFrequencia(int decimal) { //coleta a frequência de um caractere
        if(decimal > 255)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        return this.frequenciaVetor[decimal];
    }

    public int lenght() {
        return this.frequenciaVetor.length;
    }
}
