package br.unicamp.ic.mc322.model;

final class Validacao {

    private Validacao() {
    }

    static void exigir(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    static void exigirIntervalo(double valor, double min, double max, String campo) {
        if (!Double.isFinite(valor) || valor < min || valor > max) {
            throw new IllegalArgumentException(
                    campo + " deve estar entre " + min + " e " + max + " (recebido: " + valor + ").");
        }
    }

    static void exigirNaoNegativo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException(
                    campo + " não pode ter valor negativo (recebido: " + valor + ").");
        }
    }

    static void exigirTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " é obrigatório e não pode ser vazio.");
        }
    }
}