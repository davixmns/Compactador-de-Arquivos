package frequencia;

public class Frequencia {
    private final int[] frequenciaVetor;
    private final int utf8 = 255;

    public Frequencia() {
        this.frequenciaVetor = new int[utf8];
    }

    public void incrementarNoIndiceDoVetor(int decimal) {
        if(decimal > this.utf8)
            throw new ArrayIndexOutOfBoundsException("Índice inválido -> " + (char)decimal);

        this.frequenciaVetor[decimal] += 1;
    }

    public int getFrequencia(int decimal) {
        if(decimal > this.utf8)
            throw new ArrayIndexOutOfBoundsException("Índice inválido -> " + decimal);

        return this.frequenciaVetor[decimal];
    }

    public int lenght() {
        return this.frequenciaVetor.length;
    }
}
