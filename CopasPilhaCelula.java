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

class Celula {

    private Jogo item;
    private Celula proximo;
    private Celula anterior;

    Celula() {
        this.item = new Jogo();
        this.proximo = null;
    }

    Celula(Jogo item) {

        this.item = item;
        this.proximo = null;

    }

    public Jogo getItem() {

        return item;

    }

    public void setItem(Jogo item) {

        this.item = item;

    }

    public Celula getProximo() {

        return proximo;

    }

    public void setProximo(Celula proximo) {

        this.proximo = proximo;

    }

    public void setAnterior(Celula anterior) {

        this.anterior = anterior;

    }

    public Celula getAnterior() {

        return anterior;

    }

}

class PilhaCelula {

    private Celula topo;
    private Celula fundo;

    PilhaCelula() {

        Celula sentinela;

        sentinela = new Celula();
        fundo = sentinela;
        topo = sentinela;

    }

    private boolean pilhaVazia() {
        if (fundo == topo) {
            return true;
        } else {
            return false;
        }
    }

    public void empilhar(Jogo novo) {

        Celula novaCelula;

        novaCelula = new Celula(novo);
        topo.setAnterior(novaCelula);
        novaCelula.setProximo(topo);
        topo = novaCelula;

    }

    public Jogo desempilhar() throws Exception {

        Jogo desempilhado;

        if (!pilhaVazia()) {
            desempilhado = topo.getItem();
            topo = topo.getProximo();
            topo.setAnterior(null);
            return desempilhado;
        } else {
            throw new Exception("Nao foi possivel desempilhar: Fila Vazia");
        }

    }

    /*
     * public void mostrar() throws Exception {
     * 
     * int count = 0;
     * Celula aux = fundo.getAnterior();
     * 
     * if (!pilhaVazia()) {
     * 
     * // topo = topo.getProximo();
     * 
     * while (aux.getAnterior() != null) {
     * 
     * try {
     * // Jogo obj = topo.getItem();
     * System.out.print("[" + count + "]");
     * aux.getItem().imprimir();
     * // obj.imprimir();
     * count++;
     * } catch (Exception e) {
     * System.err.println(e.getMessage());
     * }
     * aux = aux.getAnterior();
     * // topo = topo.getProximo();
     * }
     * } else {
     * throw new Exception("Fila Vaiza");
     * }
     * // return null;
     * 
     * }
     */

    public Jogo mostrar() throws Exception {

        int count = 0;
        // int count2 = 0;
        Celula aux;
        // Jogo aux2[] = new Jogo[1000];

        if (!pilhaVazia()) {

            // aux = topo;
            aux = fundo.getAnterior();

            while (aux != null) {
                // Jogo obj = aux.getItem();8

                System.out.print("[" + count + "]");
                aux.getItem().imprimir();
                // aux2[count] = obj;
                count++;
                aux = aux.getAnterior();
            }

            // Caso no desempilhar nao tiver o anterior
            // System.out.print("[" + count + "]");
            // topo.getItem().imprimir();

            // Implementar um contador para trocar a posicao e fazer o contrario mas nao
            // funcionou
            /*
             * for (int i = count - 1; i > 0; i--) {
             * count2++;
             * System.out.print("[" + count2 + "]");
             * aux2[i].imprimir();
             * }
             */
        } else {
            throw new Exception("Fila Vaiza");
        }
        return null;

    }

}

public class CopasPilhaCelula {
    public static Scanner sc = new Scanner(System.in);
    public static Jogo games[] = new Jogo[1000];
    public static Jogo games2;
    public static PilhaCelula listarPilha = new PilhaCelula();
    // public static PilhaCelula listarPilha2 = new PilhaCelula();
    // public static PilhaCelula listarPilha3 = new PilhaCelula();

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

            games2 = fazer(entrada);
            try {
                listarPilha.empilhar(games2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // String novaLinha = MyIO.readLine();
            // fazer(novaLinha);

            entrada = MyIO.readLine();
        } while (!entrada.equals("FIM"));

        int entrada2 = MyIO.readInt();

        try {

            for (int i = 0; i < entrada2; i++) {
                String entrada3 = MyIO.readString();

                switch (entrada3) {
                    case "E":
                        entrada = MyIO.readLine();
                        games2 = fazer(entrada);
                        try {
                            listarPilha.empilhar(games2);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "D":
                        Jogo mostrar;
                        // Jogo mostrar2;
                        // Criar uma segunda variavel para armazenar tudo que esta sendo desempilhado.

                        System.out.print("(D) ");
                        mostrar = listarPilha.desempilhar();
                        // listarPilha2.empilhar(mostrar);
                        // mostrar2 = listarPilha2.desempilhar();
                        // mostrar2 = listarPilha2.empilhar(mostrar);
                        // mostrar2 = listarPilha2.mostrar();

                        mostrar.imprimir();
                    default:
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        listarPilha.mostrar();

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
