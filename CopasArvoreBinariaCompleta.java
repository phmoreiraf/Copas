import java.util.*;
import java.io.*;

class ArquivoTextoEscrita {

    private BufferedWriter saida;

    ArquivoTextoEscrita(String nomeArquivo) {

        try {
            saida = new BufferedWriter(new FileWriter(nomeArquivo));
        } catch (FileNotFoundException excecao) {
            System.out.println("Arquivo nao encontrado");
        } catch (IOException excecao) {
            System.out.println("Erro na abertura do arquivo de escrita: " + excecao);
        }
    }

    public void fecharArquivo() {

        try {
            saida.close();
        } catch (IOException excecao) {
            System.out.println("Erro no fechamento do arquivo de escrita: " + excecao);
        }
    }

    public void escrever(String games) {

        try {
            saida.write(games);
            saida.newLine();
        } catch (IOException excecao) {
            System.out.println("Erro de entrada/saída " + excecao);
        }
    }
}

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

class ABB {
    private Nodo raiz;
    public static int comparar;

    public ABB() {
        raiz = null;
    }

    public JogoCopa pesquisar(JogoCopa chave) {
        return pesquisar(this.raiz, chave);
    }

    private JogoCopa pesquisar(Nodo raizsubarvore, JogoCopa chave) {
        JogoCopa jogoRaiz;
        Nodo esquerda, direita;

        if (raizsubarvore == null) {
            comparar++;
            System.out.println("NAO");
            return null;
        } else {
            comparar++;
            jogoRaiz = raizsubarvore.getItem();
            esquerda = raizsubarvore.getEsquerda();
            direita = raizsubarvore.getDireita();

            System.out.print("[" + jogoRaiz.saidaPradao() + "] - ");

            if (chave.equals(jogoRaiz)) {
                comparar++;
                System.out.println("SIM");
                return raizsubarvore.getItem();

            } else if (!chave.eMenor(jogoRaiz)) {
                comparar++;
                return pesquisar(direita, chave);

            } else {
                comparar++;
                return pesquisar(esquerda, chave);
            }
        }
    }

    public void inserir(JogoCopa novo) throws Exception {
        this.raiz = inserir(this.raiz, novo);
    }

    private Nodo inserir(Nodo raizsubarvore, JogoCopa novo) throws Exception {
        JogoCopa jogoRaiz;
        Nodo esquerda, direita;

        if (raizsubarvore == null) {
            raizsubarvore = new Nodo(novo);
        } else {
            jogoRaiz = raizsubarvore.getItem();
            esquerda = raizsubarvore.getEsquerda();
            direita = raizsubarvore.getDireita();

            if (novo.equals(jogoRaiz)) {
                throw new Exception("Não foi possível inserir o item na árvore: chave já inseriada anteriormente!");

            } else if (novo.eMenor(jogoRaiz)) {
                raizsubarvore.setEsquerda(inserir(esquerda, novo));

            } else {
                raizsubarvore.setDireita(inserir(direita, novo));
            }
        }

        return raizsubarvore;
    }

    public void remover(JogoCopa chaveRemover) throws Exception {
        this.raiz = remover(this.raiz, chaveRemover);
    }

    private Nodo remover(Nodo raizsubarvore, JogoCopa chaveRemover) throws Exception {

        JogoCopa jogoRaiz;
        Nodo esquerda, direita;

        if (raizsubarvore == null) {
            throw new Exception("Não foi possível remover o item da árvore: chave não encontrada!");

        } else {
            jogoRaiz = raizsubarvore.getItem();
            esquerda = raizsubarvore.getEsquerda();
            direita = raizsubarvore.getDireita();

            if (chaveRemover.equals(jogoRaiz)) {
                if (esquerda == null)
                    raizsubarvore = direita;
                else if (direita == null)
                    raizsubarvore = esquerda;
                else
                    raizsubarvore.setEsquerda(antecessor(raizsubarvore, esquerda));
            } else if (!chaveRemover.eMenor(jogoRaiz))
                raizsubarvore.setDireita(remover(direita, chaveRemover));
            else
                raizsubarvore.setEsquerda(remover(esquerda, chaveRemover));
        }

        return raizsubarvore;
    }

    private Nodo antecessor(Nodo noRetirar, Nodo raizsubarvore) {

        if (raizsubarvore.getDireita() != null)
            raizsubarvore.setDireita(antecessor(noRetirar, raizsubarvore.getDireita()));
        else {
            noRetirar.setItem(raizsubarvore.getItem());
            raizsubarvore = raizsubarvore.getEsquerda();
        }

        return raizsubarvore;
    }

    public void caminhamentoEmOrdem() {
        caminhamentoEmOrdem(this.raiz);
    }

    private void caminhamentoEmOrdem(Nodo raizSubarvore) {

        if (raizSubarvore != null) {
            caminhamentoEmOrdem(raizSubarvore.getEsquerda());
            raizSubarvore.getItem().imprimir();
            caminhamentoEmOrdem(raizSubarvore.getDireita());
        }
    }

    public void caminhamentoPreOrdem() throws Exception {
        caminhamentoPreOrdem(this.raiz);
    }

    private void caminhamentoPreOrdem(Nodo raizSubarvore) throws Exception {

        if (raizSubarvore != null) {
            raizSubarvore.getItem().imprimir();
            caminhamentoPreOrdem(raizSubarvore.getEsquerda());
            caminhamentoPreOrdem(raizSubarvore.getDireita());
        }
    }

    public void caminhamentoPosOrdem() throws Exception {
        caminhamentoPosOrdem(this.raiz);
    }

    private void caminhamentoPosOrdem(Nodo raizSubarvore) throws Exception {

        if (raizSubarvore != null) {
            caminhamentoPosOrdem(raizSubarvore.getEsquerda());
            caminhamentoPosOrdem(raizSubarvore.getDireita());
            raizSubarvore.getItem().imprimir();
        }
    }
}

class Nodo {
    private JogoCopa item;
    private Nodo esquerda;
    private Nodo direita;

    public Nodo(JogoCopa registro) {
        item = registro;
        esquerda = null;
        direita = null;
    }

    public JogoCopa getItem() {
        return item;
    }

    public Nodo getEsquerda() {
        return esquerda;
    }

    public Nodo getDireita() {
        return direita;
    }

    public void setItem(JogoCopa item) {
        this.item = item;
    }

    public void setEsquerda(Nodo esquerda) {
        this.esquerda = esquerda;
    }

    public void setDireita(Nodo direita) {
        this.direita = direita;
    }
}

public class CopasArvoreBinariaCompleta {
    public static Scanner sc = new Scanner(System.in);
    public static JogoCopa games[] = new JogoCopa[1000];
    public static JogoCopa games2;
    public static ABB Arvore = new ABB();
    public static long tempoFinal;
    // public static Jogo jogo;

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");

        String nomeArquivo;
        String texto;
        ArquivoTextoEscrita arquivoEscrita;
        ArquivoTextoLeitura arquivoLeitura;
        String entrada;

        nomeArquivo = "/tmp/partidas.txt";
        arquivoLeitura = new ArquivoTextoLeitura(nomeArquivo);

        int colocar = 0;

        for (int j = 0; j < games.length; j++) {
            games[j] = new JogoCopa();
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

        // PRIMEIRA PARTE
        entrada = MyIO.readLine();

        while (!entrada.equals("FIM")) {
            games2 = fazer(entrada);
            try {
                Arvore.inserir(games2);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            entrada = MyIO.readLine();
        }

        // SEGUNDA PARTE
        entrada = MyIO.readLine();

        long tempoInicio = System.currentTimeMillis();
        while (!entrada.equals("FIM")) {
            games2 = fazer(entrada);
            try {
                Arvore.pesquisar(games2);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

            entrada = MyIO.readLine();
        }
        tempoFinal = System.currentTimeMillis() - tempoInicio;

        arquivoEscrita = new ArquivoTextoEscrita("769233_arvoreBinaria.txt");
        String conteudo = String.format("769233\t\t%d\t%d", tempoFinal, ABB.comparar);
        arquivoEscrita.escrever(conteudo);
        arquivoEscrita.fecharArquivo();

    }

    public static JogoCopa fazer(String entrada) {
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

class JogoCopa {

    private int dia;
    private int mes;
    private int ano;
    private String etapa;
    private String selecao1;
    private String selecao2;
    private int placSelecao1;
    private int placSelecao2;
    private String local;

    public JogoCopa(int dia, int mes, int ano, String etapa, String selecao1, String selecao2, int placSelecao1,
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

    public JogoCopa() {
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

    public void clone(JogoCopa jogo) {

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

    public boolean eMenor(JogoCopa jogo) {
        if (this.getAno() < jogo.getAno()) {
            return true;
        } else if (this.getAno() == jogo.getAno()) {
            if (this.getMes() < jogo.getMes()) {
                return true;
            } else if (this.getMes() == jogo.getMes()) {
                if (this.getDia() < jogo.getDia()) {
                    return true;
                } else if (this.getDia() == jogo.getDia()) {
                    if (this.getSelecao1().compareTo(jogo.getSelecao1()) < 0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String saidaPradao() {
        return this.getDia() + "/" + this.getMes() + "/" + this.getAno() + ";" + this.getSelecao1();
    }

}
