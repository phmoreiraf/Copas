import java.util.Scanner;

public class Copas2 {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Scanner sc = new Scanner(System.in);
        MyIO.setCharset("UTF-8");

        // Jogo jo,copia = null, jo2;
        // String s;

        String entrada;

        Jogo jogo = new Jogo();

        // Jogo vetor[] = new Jogo[1000];
        Jogo games[] = new Jogo[1000];

        int colocar = 0;

        // for(int i=0;i<1;i++) {
        // vetor[i]=sc.nextLine();
        // while(vetor[i]!="FIM") {
        // String[] separa = vetor[i].split("#");
        // Arrays.toString(separa);
        // jo = new Jogo(Integer.parseInt(separa[2]), Integer.parseInt(separa[3]),
        // Integer.parseInt(separa[0]), Integer.parseInt(separa[5]),
        // Integer.parseInt(separa[6]), separa[1], separa[4], separa[7],separa[8]);
        // copia = jo.clone();
        // //copia.imprimir();
        // }

        for (int j = 0; j < games.length; j++) {
            games[j] = new Jogo();
        }

        entrada = MyIO.readLine();
        while (!entrada.equals("FIM")) {
            games[colocar].BotarVetorString(entrada);
            colocar++;
            entrada = MyIO.readLine();
        }

        int entrar = MyIO.readInt();
        String vetorEntrada[] = new String[entrar];
        for (int i = 0; i < entrar; i++) {

            vetorEntrada[i] = MyIO.readLine();

        }

        for (int j = 0; j < vetorEntrada.length; j++) {
            fazer(vetorEntrada[j], games);
        }

    }

    public static void fazer(String entrada, Jogo games[]) {
        String vetor[] = new String[10];
        vetor = entrada.split("/");
        String vetor2[] = new String[10];
        vetor2 = vetor[2].split(";");
        for (int i = 0; i < games.length; i++) {
            if (games[i].getDia() == Integer.valueOf(vetor[0]) && games[i].getMes() == Integer.valueOf(vetor[1])
                    && games[i].getAno() == Integer.valueOf(vetor2[0])) {
                if (games[i].getSelecao1().equals(vetor2[1]) || games[i].getSelecao2().equals(vetor2[1])) {
                    games[i].imprimir();
                }
            }
        }

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

        // Jogo copia;

        // copia = new Jogo(this.dia, this.mes, this.ano, this.placselecao1,
        // this.placselecao2,this.etapa, this.selecao1, this.selecao2, this.local);
        // return copia;

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

    public void imprimir() {
        System.out.print("[COPA " + this.ano + "] ");
        System.out.print("[" + this.etapa + "] ");
        System.out.print("[" + this.dia + "/" + this.mes + "] ");
        System.out.print("[" + this.selecao1 + " (" + this.placSelecao1 + ") x (" + this.placSelecao2 + ") "
                + this.selecao2 + "] ");
        System.out.print("[" + this.local + "]\n");

        // System.out.println("[COPA "+this.ano+"] ["+this.etapa+"]
        // ["+this.dia+"/"+this.mes+"] ["+this.selecao1+" ("+this.placSelecao1+") x
        // ("+this.placSelecao2+") "+this.selecao2+"] ["+this.local+"]");

    }

}
