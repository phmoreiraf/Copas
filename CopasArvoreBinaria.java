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

class Nodo {

    private Jogo item;
    private Nodo esquerda;
    private Nodo direita;

    public Nodo(Jogo item) {
        this.item = item;
        this.esquerda = null;
        this.direita = null;
    }

    public Nodo() {
        this.item = new Jogo();
        this.esquerda = null;
        this.direita = null;
    }

    public Jogo getItem() {
        return item;
    }

    public void setItem(Jogo item) {
        this.item = item;
    }

    public Nodo getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Nodo esquerda) {
        this.esquerda = esquerda;
    }

    public Nodo getDireita() {
        return direita;
    }

    public void setDireita(Nodo direita) {
        this.direita = direita;
    }
}

class ABB {

    private Nodo raiz;

    public ABB() {
        raiz = null;
    }

    // valorPesquisado: 10
    public Jogo pesquisar(int valorPesquisado) throws Exception {
        return (pesquisar(valorPesquisado, raiz));
    }

    // 1.a execução:
    // raizSubarvore: 16
    // valorPesquisado: 10
    // retorno: pesquisa na subárvore esquerda --> null
    // 2.a execução:
    // raizSubarvore: 8
    // valorPesquisado: 10
    // retorno: pesquisa na subárvore direita -- > null
    // 3.a execução:
    // raisSubarvore: null
    // valorPesquisado: 10
    // retorno: null
    private Jogo pesquisar(int valorPesquisado, Nodo raizSubarvore) throws Exception {

        if (raizSubarvore == null) {
            // return null;
            throw new Exception("A chave de pesquisa nao encontrada!");
        } else if (raizSubarvore.getItem().getValor() == valorPesquisado) {
            return raizSubarvore.getItem();
        } else if (valorPesquisado > raizSubarvore.getItem().getValor()) {
            return pesquisar(valorPesquisado, raizSubarvore.getDireita());
        } else {
            return pesquisar(valorPesquisado, raizSubarvore.getEsquerda());
        }
    }

    public void inserir(Jogo novo) throws Exception {
        this.raiz = inserir(novo, this.raiz);
    }

    private Nodo inserir(Jogo novo, Nodo raizSubarvore) throws Exception {

        if (raizSubarvore == null) {
            raizSubarvore = new Nodo(novo);
        } else if (raizSubarvore.getItem().getValor() == novo.getValor()) {
            throw new Exception("Nao foi possivel inserir o item: chave repetida");
        } else if (raizSubarvore.getItem().getValor() > novo.getValor()) {
            raizSubarvore.setEsquerda(inserir(novo, raizSubarvore.getEsquerda()));
        } else {
            raizSubarvore.setDireita(inserir(novo, raizSubarvore.getDireita()));
        }

        return raizSubarvore;

    }

    public void retirar(int valorPesquisado) throws Exception {

        this.raiz = remover(valorPesquisado, this.raiz);

    }

    private Nodo remover(int valorPesquisado, Nodo raizSubarvore) throws Exception {
        if (raizSubarvore == null) {
            throw new Exception("Nao foi possivel remover a chave: chave nao encontrada");
        } else if (raizSubarvore.getItem().getValor() == valorPesquisado) {
            if (raizSubarvore.getEsquerda() == null) {
                raizSubarvore = raizSubarvore.getDireita();
            } else if (raizSubarvore.getDireita() == null) {
                raizSubarvore = raizSubarvore.getEsquerda();
            } else {
                raizSubarvore.setEsquerda(antecessor(raizSubarvore, raizSubarvore.getEsquerda()));
            }
        } else if (raizSubarvore.getItem().getValor() > valorPesquisado) {
            raizSubarvore.setEsquerda(remover(valorPesquisado, raizSubarvore.getEsquerda()));
        } else {
            raizSubarvore.setDireita(remover(valorPesquisado, raizSubarvore.getDireita()));
        }
        return raizSubarvore;
    }

    private Nodo antecessor(Nodo noRetirar, Nodo raizSubarvore) throws Exception {

        if (raizSubarvore.getDireita() == null) {
            noRetirar.setItem(raizSubarvore.getItem());
            raizSubarvore = raizSubarvore.getEsquerda();
        } else {
            raizSubarvore.setDireita(antecessor(noRetirar, raizSubarvore.getDireita()));
        }

        return raizSubarvore;
    }
}

public class CopasArvoreBinaria {
    public static Scanner sc = new Scanner(System.in);
    public static Jogo games[] = new Jogo[1000];
    public static Jogo games2;
    public static ABB Arvore = new ABB();

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
                Arvore.inserir(games2);
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

                String entrada3 = MyIO.readLine();
                // switch (comando) {
                // case:"";
                // break;
                // }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        try {
            // Arvore.mostrar();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    private int valor;

    public Jogo(int dia, int mes, int ano, String etapa, String selecao1, String selecao2, int placSelecao1,
            int placSelecao2, String local, int valor) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.etapa = etapa;
        this.selecao1 = selecao1;
        this.selecao2 = selecao2;
        this.placSelecao1 = placSelecao1;
        this.placSelecao2 = placSelecao2;
        this.local = local;
        this.valor = valor;
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
        this.valor = 0;
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
        this.valor = jogo.valor;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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
