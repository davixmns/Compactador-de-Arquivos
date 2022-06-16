package frequencia;

public class Frequencia {
    private final int[] frequenciaVetor;

    public Frequencia() {
        this.frequenciaVetor = new int[255];
    }

    public void incrementar(int decimal) {
        if(decimal > 255)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        this.frequenciaVetor[decimal] += 1;
    }

    public int getFrequencia(int decimal) {
        if(decimal > 255)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        return this.frequenciaVetor[decimal];
    }

    public int lenght() {
        return this.frequenciaVetor.length;
    }
}
