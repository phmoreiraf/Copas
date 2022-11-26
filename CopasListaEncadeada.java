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

class Celula {

    private Jogo item;
    private Celula proximo;

    public Celula(Jogo novo) {
        item = novo;
        proximo = null;
    }

    public Celula() {

        item = new Jogo();
        proximo = null;
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
}

class ListaEncadeada {
    private Celula primeiro;
    private Celula ultimo;
    private int tamanho;

    public ListaEncadeada() {

        Celula sentinela = new Celula();

        primeiro = sentinela;
        ultimo = sentinela;
        tamanho = 0;
    }

    public boolean listaVazia() {

        boolean resp;

        if (primeiro == ultimo)
            resp = true;
        else
            resp = false;

        return resp;
    }

    public void inserirInicio(Jogo novo) throws Exception {
        inserir(novo, 0);
    }

    public void inserir(Jogo novo, int posicao) throws Exception {
        Celula anterior, novaCelula, proximaCelula;

        if ((posicao >= 0) && (posicao <= tamanho)) {
            anterior = primeiro;
            for (int i = 0; i < posicao; i++)
                anterior = anterior.getProximo();

            novaCelula = new Celula(novo);

            proximaCelula = anterior.getProximo();

            anterior.setProximo(novaCelula);
            novaCelula.setProximo(proximaCelula);

            if (posicao == tamanho) // a inserção ocorreu na última posição da lista
                ultimo = novaCelula;

            tamanho++;

        } else
            throw new Exception("Não foi possível inserir o item na lista: a posição informada é inválida!");
    }

    public void inserirFim(Jogo novo) throws Exception {
        inserir(novo, tamanho);
    }

    public Jogo removerInicio() throws Exception {
        return remover(0);
    }

    public Jogo remover(int posicao) throws Exception {

        Celula anterior, celulaRemovida, proximaCelula;

        if (!listaVazia()) {
            if ((posicao >= 0) && (posicao < tamanho)) {
                anterior = primeiro;
                for (int i = 0; i < posicao; i++)
                    anterior = anterior.getProximo();

                celulaRemovida = anterior.getProximo();

                proximaCelula = celulaRemovida.getProximo();

                anterior.setProximo(proximaCelula);
                celulaRemovida.setProximo(null);

                if (celulaRemovida == ultimo)
                    ultimo = anterior;

                tamanho--;

                return (celulaRemovida.getItem());
            } else
                throw new Exception("Não foi possível remover o item da lista: a posição informada é inválida!");
        } else
            throw new Exception("Não foi possível remover o item da lista: a lista está vazia!");
    }

    public Jogo removerFim() throws Exception {
        return remover(tamanho - 1);
    }

    public void mostrar() throws Exception {
        int i = 0;
        Celula aux;

        if (!listaVazia()) {
            aux = primeiro.getProximo();
            while (aux != null) {
                System.out.print("[" + i++ + "]");
                aux.getItem().imprimir();
                aux = aux.getProximo();
            }
        } else
            throw new Exception("Não foi possível imprimir o conteúdo da lista: a lista está vazia!");
    }
}

public class CopasListaEncadeada {
    public static Scanner sc = new Scanner(System.in);
    public static Jogo games[] = new Jogo[1000];
    public static Jogo games2;
    public static ListaEncadeada listarLista = new ListaEncadeada();

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
                listarLista.inserirFim(games2);
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
                            listarLista.inserir(games2, posicao);

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

                        listarLista.remover(posicao).imprimir();
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
