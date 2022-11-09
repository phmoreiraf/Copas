import java.util.*;
import java.io.*;

class ArquivoTextoLeitura {

    private BufferedReader entrada;

    ArquivoTextoLeitura(String nomeArquivo) {

        try {
            entrada = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException excecao) {
            System.out.println("Arquivo nao encontrado");
        }
    }

    public void fecharArquivo() {

        try {
            entrada.close();
        } catch (IOException excecao) {
            System.out.println("Erro no fechamento do arquivo de leitura: " + excecao);
        }
    }

    @SuppressWarnings("finally")
    public String ler() {

        String textoEntrada = null;

        try {
            textoEntrada = entrada.readLine();
        } catch (EOFException excecao) { // Excecao de final de arquivo.
            textoEntrada = null;
        } catch (IOException excecao) {
            System.out.println("Erro de leitura: " + excecao);
            textoEntrada = null;
        } finally {
            return textoEntrada;
        }
    }
}

class Inteiro {
    private int valor;

    Inteiro(int valor) {
        this.valor = valor;
    }

    Inteiro() {
        this.valor = 0;
    }

    public int getValor() {

        return valor;

    }

    public void setValor(int valor) {

        this.valor = valor;

    }

    public void imprimir() {
        System.out.println("Valor sera: " + this.valor);
    }
}

class Fila {
    private int frente;
    private int tras;
    private Jogo[] fila;
    private int tamanho;
    public int length;

    Fila(int tamanho) {
        fila = new Jogo[tamanho + 1];
        this.tamanho = tamanho;
        frente = tras = 0;
    }

    private boolean filaVazia() {
        if ((frente % tamanho) == (tras % tamanho)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean filaCheia() {
        if ((frente % tamanho) == ((tras + 1) % tamanho)) {
            return true;
        } else {
            return false;
        }
    }

    public void enfileirar(Jogo novo) throws Exception {

        int posicao;
        if (!filaCheia()) {
            posicao = tras % tamanho;
            fila[posicao] = novo;
            tras++;
        } else {
            throw new Exception("Fila cheia!");
        }
    }

    public Jogo desenfileirar() throws Exception {

        int posicao;
        Jogo desenfileirado;

        if (!filaVazia()) {
            posicao = frente % tamanho;
            desenfileirado = fila[posicao];
            frente++;
        } else {
            throw new Exception("Fila Vazia");
        }

        return desenfileirado;
    }

    public double obterMediaGols() throws Exception {

        Jogo obj;
        int posicao;
        int quant = 0;
        double soma = 0;
        int i;

        if (!filaVazia()) {

            for (i = frente; i < tras; i++) {
                posicao = i % tamanho;
                obj = fila[posicao];
                soma += obj.getPlacSelecao1() + obj.getPlacSelecao2();
                quant++;
            }
        } else {
            throw new Exception("Fila vazia");
        }

        return soma / quant;

    }

    public void mostrar() throws Exception {

        int count = 0;
        int posicao;
        Jogo obj;

        if (!filaVazia()) {
            for (int i = frente; i < tras; i++) {
                posicao = i % tamanho;
                obj = fila[posicao];
                System.out.print("[" + count + "]");
                count++;

                obj.imprimir();

            }
        } else {
            throw new Exception("Fila Vaiza");
        }

    }

}

public class CopasFilaCircular {
    public static Scanner sc = new Scanner(System.in);
    public static Jogo games[] = new Jogo[1000];
    public static Jogo games2;
    public static Fila listarFila = new Fila(101);
    // public static Jogo jogo;

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");

        String nomeArquivo;
        String texto;
        ArquivoTextoLeitura arquivoLeitura;

        nomeArquivo = "/tmp/partidas.txt";
        arquivoLeitura = new ArquivoTextoLeitura(nomeArquivo);

        int colocar = 0;

        for (int j = 0; j < games.length; j++) {
            games[j] = new Jogo();
        }

        try {

            texto = arquivoLeitura.ler();
            while (texto != null) {
                games[colocar].BotarVetorString(texto);
                // games[colocar].imprimir();
                colocar++;
                texto = arquivoLeitura.ler();
            }
            // arquivoLeitura.fecharArquivo();
            // sc.close();
        } catch (Error error) {
            System.out.print("Erro ao abrir arquivo! " + error);
        }

        String entrada = MyIO.readLine();
        do {
            // for (int i = 0; ((i < games.length) && (games[i] != null)); i++) {
            games2 = fazer(entrada);
            try {
                if (!listarFila.filaCheia()) {
                    listarFila.enfileirar(games2);
                    System.out.println(Math.round(listarFila.obterMediaGols()));
                } else {
                    listarFila.desenfileirar();
                    listarFila.enfileirar(games2);
                    System.out.println(Math.round(listarFila.obterMediaGols()));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            // }
            // String novaLinha = MyIO.readLine();
            // fazer(novaLinha);

            entrada = MyIO.readLine();
        } while (!entrada.equals("FIM"));

        int entrada2 = MyIO.readInt();

        try {

            for (int i = 0; i < entrada2; i++) {
                String entrada3 = MyIO.readString();

                if (entrada3.equals("E")) {
                    entrada = MyIO.readLine();
                    games2 = fazer(entrada);
                    try {

                        if (!listarFila.filaCheia()) {
                            listarFila.enfileirar(games2);
                            System.out.println(Math.round(listarFila.obterMediaGols()));
                        } else {
                            listarFila.desenfileirar();
                            listarFila.enfileirar(games2);
                            // listarFila.obterMediaGols();
                            System.out.println(Math.round(listarFila.obterMediaGols()));
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else if (entrada3.equals("D")) {
                    // listarFila.desenfileirar();
                    Jogo mostrar;

                    System.out.print("(D) ");
                    mostrar = listarFila.desenfileirar();
                    mostrar.imprimir();
                }
            }
        } catch (

        Exception e) {
            System.out.println(e.getMessage());

        }

        listarFila.mostrar();

    }

    public static Jogo fazer(String entrada) {
        String vetor[] = new String[10];
        vetor = entrada.split("/");
        String vetor2[] = new String[10];
        vetor2 = vetor[2].split(";");
        for (int i = 0; i < games.length; i++) {
            if (games[i].getDia() == Integer.valueOf(vetor[0]) && games[i].getMes() == Integer.valueOf(vetor[1])
                    && games[i].getAno() == Integer.valueOf(vetor2[0])) {
                if (games[i].getSelecao1().equals(vetor2[1])) {
                    // games[i].imprimir();
                    return games[i];
                }

            }
        }
        return null;
    }

}

class Jogo {

    private int dia;
    private int mes;
    private int ano;
    private String etapa;
    private String selecao1;
    private String selecao2;
    private int placSelecao1;
    private int placSelecao2;
    private String local;

    public Jogo(int dia, int mes, int ano, String etapa, String selecao1, String selecao2, int placSelecao1,
            int placSelecao2, String local) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.etapa = etapa;
        this.selecao1 = selecao1;
        this.selecao2 = selecao2;
        this.placSelecao1 = placSelecao1;
        this.placSelecao2 = placSelecao2;
        this.local = local;
    }

    public Jogo() {
        this.dia = 0;
        this.mes = 0;
        this.ano = 0;
        this.etapa = "";
        this.selecao1 = "";
        this.selecao2 = "";
        this.placSelecao1 = 0;
        this.placSelecao2 = 0;
        this.local = "";
    }

    public void BotarVetorString(String entrada) {
        String vetor[] = new String[9];
        vetor = entrada.split("#");
        this.ano = Integer.valueOf(vetor[0]);
        this.etapa = vetor[1];
        this.dia = Integer.valueOf(vetor[2]);
        this.mes = Integer.valueOf(vetor[3]);
        this.selecao1 = vetor[4];
        this.placSelecao1 = Integer.valueOf(vetor[5]);
        this.placSelecao2 = Integer.valueOf(vetor[6]);
        this.selecao2 = vetor[7];
        this.local = vetor[8];
    }

    public void clone(Jogo jogo) {

        this.dia = jogo.dia;
        this.mes = jogo.mes;
        this.ano = jogo.ano;
        this.etapa = jogo.etapa;
        this.selecao1 = jogo.selecao1;
        this.selecao2 = jogo.selecao2;
        this.placSelecao1 = jogo.placSelecao1;
        this.placSelecao2 = jogo.placSelecao2;
        this.local = jogo.local;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getSelecao1() {
        return selecao1;
    }

    public void setSelecao1(String selecao1) {
        this.selecao1 = selecao1;
    }

    public String getSelecao2() {
        return selecao2;
    }

    public void setSelecao2(String selecao2) {
        this.selecao2 = selecao2;
    }

    public int getPlacSelecao1() {
        return placSelecao1;
    }

    public void setPlacSelecao1(int placSelecao1) {
        this.placSelecao1 = placSelecao1;
    }

    public int getPlacSelecao2() {
        return placSelecao2;
    }

    public void setPlacSelecao2(int placSelecao2) {
        this.placSelecao2 = placSelecao2;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    // quando desiss
    public void imprimir() {
        System.out.print("[COPA " + this.ano + "] ");
        System.out.print("[" + this.etapa + "] ");
        System.out.print("[" + this.dia + "/" + this.mes + "] ");
        System.out.print("[" + this.selecao1 + " (" + this.placSelecao1 + ") x (" +
                this.placSelecao2 + ") "
                + this.selecao2 + "] ");
        System.out.print("[" + this.local + "]\n");

    }

}
