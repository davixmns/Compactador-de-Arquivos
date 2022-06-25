package frequencia;

public class Frequencia {
    private final int[] frequenciaVetor;
    private final int utf8 = 255;

    public Frequencia() {
        this.frequenciaVetor = new int[utf8];
    }

    public void incrementar(int decimal) {
        if(decimal > this.utf8)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        this.frequenciaVetor[decimal] += 1;
    }

    public int getFrequencia(int decimal) {
        if(decimal > this.utf8)
            throw new ArrayIndexOutOfBoundsException("Índice inválido!!!");

        return this.frequenciaVetor[decimal];
    }

    public int lenght() {
        return this.frequenciaVetor.length;
    }
}
