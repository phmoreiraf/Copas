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

class ListaLinear {

    private Jogo[] lista;
    private int primeiro, ultimo;
    private int qtdePreenchida;

    ListaLinear(int tamanho) {

        lista = new Jogo[tamanho];
        primeiro = ultimo = 0;
        qtdePreenchida = 0;
    }

    private boolean listaVazia() {
        if (primeiro == ultimo) {
            return true;
        } else {
            return false;
        }
    }

    private boolean listaCheia() {
        if (qtdePreenchida == lista.length) {
            return true;
        } else {
            return false;
        }
    }

    public void inserirInicio(Jogo novo) throws Exception {
        inserir(primeiro, novo);
    }

    public void inserir(int posicao, Jogo novo) throws Exception {

        // int posicao = 0;
        if (!listaCheia()) {

            if ((posicao >= 0) && (posicao <= qtdePreenchida)) {

                for (int i = ultimo; i > posicao; i--) {
                    lista[i] = lista[i - 1];
                }

                lista[posicao] = novo;

                ultimo++;
                qtdePreenchida++;

            } else {
                throw new Exception("Posicao invalida");
            }

        } else {
            throw new Exception("Lista cheia");
        }

    }

    public void inserirFim(Jogo novo) throws Exception {
        inserir(ultimo, novo);
    }

    public Jogo removerInicio() throws Exception {
        return retirar(0);
    }

    public Jogo retirar(int posicao) throws Exception {

        // int posicao = 0;
        Jogo removido;

        if (!listaVazia()) {

            if ((posicao >= 0) && (posicao < qtdePreenchida)) {

                removido = lista[posicao];
                ultimo--;
                qtdePreenchida--;
                // imprimir percorrer primeiro, e depois < ultimo - 1
                for (int i = posicao; i < ultimo - 1; i++) {
                    lista[i] = lista[i + 1];
                }

                return removido;

            } else {
                throw new Exception("Posicao invalida");
            }

        } else {
            throw new Exception("Lista vazia");
        }

    }

    public Jogo removerFim() throws Exception {
        return retirar(ultimo - 1);
    }

    public void mostrar() throws Exception {

        int count = 0;
        // int posicao;
        // Jogo obj;

        if (!listaVazia()) {
            for (int i = primeiro; i < ultimo; i++) {
                // posicao = i % qtdePreenchida;
                // obj = lista[posicao];
                System.out.print("[" + count + "]");
                count++;

                lista[i].imprimir();

            }
        } else {
            throw new Exception("Lista Vazia");
        }

    }

}

public class CopasListaLinear {
    public static Scanner sc = new Scanner(System.in);
    public static Jogo games[] = new Jogo[1000];
    public static Jogo games2;
    public static ListaLinear listarLista = new ListaLinear(1000);

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
                colocar++;
                texto = arquivoLeitura.ler();
            }
        } catch (Error error) {
            System.out.print("Erro ao abrir arquivo! " + error);
        }

        String entrada = MyIO.readLine();

        do {

            games2 = fazer(entrada);
            try {
                listarLista.inserirFim(games2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            entrada = MyIO.readLine();
        } while (!entrada.equals("FIM"));

        int entrada2 = MyIO.readInt();

        try {

            for (int i = 0; i < entrada2; i++) {

                String entrada3 = MyIO.readLine();
                // entrada3 = entrada3.substring(0, 2);
                String comando = entrada3.substring(0, 2);
                int posicao;

                switch (comando) {
                    case "II":
                        // entrada3 = MyIO.readString();
                        games2 = fazer(entrada3.substring(3));
                        try {
                            // listarLista.inserirInicio(games2);
                            listarLista.inserirInicio(games2);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "I*":

                        // inserir = MyIO.readInt();
                        // entrada = MyIO.readLine();
                        // inserir = Integer.parseInt(entrada3.split(" ")[1]);
                        String posicaoNum = entrada3.split(" ")[1];
                        // entrada3 = entrada3.replace(entrada3.split(" ")[1] + " ", "");
                        entrada3 = entrada3.replace(posicaoNum + " ", "");
                        posicao = Integer.parseInt(posicaoNum);
                        games2 = fazer(entrada3.substring(3));
                        try {
                            listarLista.inserir(posicao, games2);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "IF":
                        // entrada = MyIO.readLine();

                        games2 = fazer(entrada3.substring(3));
                        try {
                            // listarLista.inserirFim(games2);
                            listarLista.inserirFim(games2);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "RI":
                        // Jogo mostrar1;

                        System.out.print("(R) ");
                        // mostrar1 = listarLista.retirarInicio();

                        listarLista.removerInicio().imprimir();
                        break;
                    case "R*":
                        // inserir = Integer.parseInt(entrada3.split(" ")[1]);
                        posicao = Integer.parseInt(entrada3.substring(3));
                        // System.out.print(posicao);
                        // Jogo mostrar2;

                        System.out.print("(R) ");
                        // mostrar2 = listarLista.retirar(remover);

                        listarLista.retirar(posicao).imprimir();
                        break;
                    case "RF":
                        // Jogo mostrar3;

                        System.out.print("(R) ");
                        // mostrar3 = listarLista.retirarFim();

                        listarLista.removerFim().imprimir();
                    default:
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        try {
            listarLista.mostrar();
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
