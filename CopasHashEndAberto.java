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

class Hash {
    private JogoCopa[] tabela;
    private int M;
    public static int comparar;
    // public static int tentativa;

    Hash(int tamanho) {
        this.M = tamanho;

        tabela = new JogoCopa[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = null;
        }

    }

    // Trocar para retornar uma string e a chave tambem, dentro da funcao converter
    // para int
    private int funcaoHash(JogoCopa chave, int i) {

        // tentativa = 0;
        // criar outra variavel, i++ com selecao1.lenght, colocar o i como 1;
        // chave.getSelecao1();
        int soma;
        int soma1 = 0;
        int total;

        soma = chave.getDia() + chave.getMes() + chave.getAno();

        char selecoes[] = chave.getSelecao1().toCharArray();
        for (int j = 0; j < selecoes.length; j++) {
            soma1 = soma1 + (selecoes[j] * (j + 1));
        }
        total = soma + soma1;
        return (((total) % M) + ((i * total) % 311)) % M;

    }

    public void inserir(JogoCopa novo) throws Exception {

        int posicao, tentativa;
        boolean inseriu = false;
        tentativa = 0;

        while ((tentativa < M) && (!inseriu)) {
            // Sera get.item quando for na copa
            posicao = funcaoHash(novo, tentativa);
            if (tabela[posicao] == null) {
                tabela[posicao] = novo;
                inseriu = true;
            } else if (tabela[posicao] == novo) {
                throw new Exception("Chave repetida");
            } else {
                tentativa++;
            }
        }
        if (!inseriu) {
            throw new Exception("Nao foi possivel inserir o elemento na tabela;");
        }
    }

    public JogoCopa pesquisar(JogoCopa chave) /* throws Exception */ {
        int posicao, tentativa;
        tentativa = 0;

        while (tentativa < M) {
            posicao = funcaoHash(chave, tentativa);
            if (tabela[posicao] == null) {
                comparar++;
                System.out.println("NAO");
                break;
                // throw new Exception("Pesquisa sem sucesso");
            } else if (tabela[posicao] == chave) {
                comparar++;
                // System.out.println(posicao);
                // System.out.print("[" + tabela[posicao].saidaPradao() + "] - ");
                System.out.println(posicao + " SIM");
                break;
                // return tabela[posicao];
            } else {
                comparar++;
            }
            tentativa++;
        }
        // throw new Exception("Pesquisa sem sucesso");
        return new JogoCopa();
    }
}

public class CopasHashEndAberto {
    public static Scanner sc = new Scanner(System.in);
    public static JogoCopa games[] = new JogoCopa[1000];
    public static JogoCopa games2;
    public static Hash TabelaHash = new Hash(953);
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

                TabelaHash.inserir(games2);

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
                TabelaHash.pesquisar(games2);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

            entrada = MyIO.readLine();
        }
        tempoFinal = System.currentTimeMillis() - tempoInicio;

        arquivoEscrita = new ArquivoTextoEscrita("769233_hashRehashing.txt");
        String conteudo = String.format("769233\t\t%d\t%d", tempoFinal, Hash.comparar);
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

    /*
     * public boolean eMenor(JogoCopa jogo) {
     * if (this.getAno() < jogo.getAno()) {
     * return true;
     * } else if (this.getAno() == jogo.getAno()) {
     * if (this.getMes() < jogo.getMes()) {
     * return true;
     * } else if (this.getMes() == jogo.getMes()) {
     * if (this.getDia() < jogo.getDia()) {
     * return true;
     * } else if (this.getDia() == jogo.getDia()) {
     * if (this.getSelecao1().compareTo(jogo.getSelecao1()) < 0) {
     * return true;
     * }
     * }
     * }
     * }
     * 
     * return false;
     * }
     */

    /*
     * public String saidaPradao() {
     * return this.getDia() + "/" + this.getMes() + "/" + this.getAno() + ";" +
     * this.getSelecao1();
     * }
     */

}
