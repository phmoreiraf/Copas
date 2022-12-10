import java.io.*;

public class CopasABB {

	public static final int MAX_LENGTH = 1000;

	public static JogoCopa find(JogoCopa[] vetor, String string) {
		String data = string.split(";")[0];
		String selecao1 = string.split(";")[1];

		int dia = Integer.parseInt(data.split("/")[0]);
		int mes = Integer.parseInt(data.split("/")[1]);
		int ano = Integer.parseInt(data.split("/")[2]);

		for (JogoCopa jogo : vetor) {
			if ((jogo.getDia() == dia) && (jogo.getMes() == mes) && (jogo.getAno() == ano)
					&& jogo.getSelecao1().equals(selecao1)) {
				return jogo;
			}
		}

		return null;
	}

	// Main
	// ===================================================================================

	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");

		JogoCopa[] copa;
		ABB abb;
		String entrada, filename = "/tmp/partidas.txt";
		ArquivoLeitura arquivo = new ArquivoLeitura(filename);

		copa = arquivo.carregar(MAX_LENGTH);
		abb = new ABB();

		// First part
		entrada = MyIO.readLine();

		while (!entrada.equals("FIM")) {
			try {
				abb.inserir(find(copa, entrada));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			entrada = MyIO.readLine();
		}

		// Second part
		entrada = MyIO.readLine();

		while (!entrada.equals("FIM")) {
			try {
				abb.pesquisar(find(copa, entrada));
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

			entrada = MyIO.readLine();
		}
	}
}

class ABB {
	private No raiz;

	public ABB() {
		raiz = null;
	}

	public JogoCopa pesquisar(JogoCopa chave) {
		return pesquisar(this.raiz, chave);
	}

	private JogoCopa pesquisar(No raiz, JogoCopa chave) {
		JogoCopa jogoRaiz;
		No esquerda, direita;

		if (raiz == null) {
			System.out.println("NAO");
			return null;
		} else {
			jogoRaiz = raiz.getItem();
			esquerda = raiz.getEsquerda();
			direita = raiz.getDireita();

			System.out.print("[" + jogoRaiz.stringPadrao() + "] - ");

			if (chave.equals(jogoRaiz)) {
				System.out.println("SIM");
				return raiz.getItem();

			} else if (!chave.eMenor(jogoRaiz)) {
				return pesquisar(direita, chave);

			} else {
				return pesquisar(esquerda, chave);
			}
		}
	}

	public void inserir(JogoCopa novo) throws Exception {
		this.raiz = inserir(this.raiz, novo);
	}

	private No inserir(No raiz, JogoCopa novo) throws Exception {
		JogoCopa jogoRaiz;
		No esquerda, direita;

		if (raiz == null) {
			raiz = new No(novo);
		} else {
			jogoRaiz = raiz.getItem();
			esquerda = raiz.getEsquerda();
			direita = raiz.getDireita();

			if (novo.equals(jogoRaiz)) {
				throw new Exception("Não foi possível inserir o item na árvore: chave já inseriada anteriormente!");

			} else if (novo.eMenor(jogoRaiz)) {
				raiz.setEsquerda(inserir(esquerda, novo));

			} else {
				raiz.setDireita(inserir(direita, novo));
			}
		}

		return raiz;
	}

	public void remover(JogoCopa chaveRemover) throws Exception {
		this.raiz = remover(this.raiz, chaveRemover);
	}

	private No remover(No raiz, JogoCopa chaveRemover) throws Exception {

		JogoCopa jogoRaiz;
		No esquerda, direita;

		if (raiz == null) {
			throw new Exception("Não foi possível remover o item da árvore: chave não encontrada!");

		} else {
			jogoRaiz = raiz.getItem();
			esquerda = raiz.getEsquerda();
			direita = raiz.getDireita();

			if (chaveRemover.equals(jogoRaiz)) {
				if (esquerda == null)
					raiz = direita;
				else if (direita == null)
					raiz = esquerda;
				else
					raiz.setEsquerda(antecessor(raiz, esquerda));
			} else if (!chaveRemover.eMenor(jogoRaiz))
				raiz.setDireita(remover(direita, chaveRemover));
			else
				raiz.setEsquerda(remover(esquerda, chaveRemover));
		}

		return raiz;
	}

	private No antecessor(No noRetirar, No raiz) {

		if (raiz.getDireita() != null)
			raiz.setDireita(antecessor(noRetirar, raiz.getDireita()));
		else {
			noRetirar.setItem(raiz.getItem());
			raiz = raiz.getEsquerda();
		}

		return raiz;
	}

	public void caminhamentoEmOrdem() {
		caminhamentoEmOrdem(this.raiz);
	}

	private void caminhamentoEmOrdem(No raizSubarvore) {

		if (raizSubarvore != null) {
			caminhamentoEmOrdem(raizSubarvore.getEsquerda());
			raizSubarvore.getItem().imprimir();
			caminhamentoEmOrdem(raizSubarvore.getDireita());
		}
	}
}

class No {
	private JogoCopa item;
	private No esquerda;
	private No direita;

	public No(JogoCopa registro) {
		item = registro;
		esquerda = null;
		direita = null;
	}

	public JogoCopa getItem() {
		return item;
	}

	public No getEsquerda() {
		return esquerda;
	}

	public No getDireita() {
		return direita;
	}

	public void setItem(JogoCopa item) {
		this.item = item;
	}

	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}

	public void setDireita(No direita) {
		this.direita = direita;
	}
}

class JogoCopa implements Cloneable {
	private int ano, dia, mes, placarSelecao1, placarSelecao2;
	private String etapa, selecao1, selecao2, local;

	public JogoCopa() {
	}

	public JogoCopa(int ano, String etapa, int dia, int mes, String selecao1, int placarSelecao1, int placarSelecao2,
			String selecao2, String local) {
		this.setAno(ano);
		this.setEtapa(etapa);
		this.setDia(dia);
		this.setMes(mes);
		this.setSelecao1(selecao1);
		this.setPlacarSelecao1(placarSelecao1);
		this.setPlacarSelecao2(placarSelecao2);
		this.setSelecao2(selecao2);
		this.setLocal(local);
	}

	// #region Getters

	public int getAno() {
		return this.ano;
	}

	public String getEtapa() {
		return this.etapa;
	}

	public int getDia() {
		return this.dia;
	}

	public int getMes() {
		return this.mes;
	}

	public String getSelecao1() {
		return this.selecao1;
	}

	public int getPlacarSelecao1() {
		return placarSelecao1;
	}

	public int getPlacarSelecao2() {
		return placarSelecao2;
	}

	public String getSelecao2() {
		return selecao2;
	}

	public String getLocal() {
		return local;
	}
	// #endregion

	// #region Setters

	public void setAno(int ano) {
		this.ano = ano;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public void setSelecao1(String selecao1) {
		this.selecao1 = selecao1;
	}

	public void setSelecao2(String selecao2) {
		this.selecao2 = selecao2;
	}

	public void setPlacarSelecao1(int placarSelecao1) {
		this.placarSelecao1 = placarSelecao1;
	}

	public void setPlacarSelecao2(int placarSelecao2) {
		if (placarSelecao2 > 0) {
			this.placarSelecao2 = placarSelecao2;
		}
	}

	public void setLocal(String local) {
		this.local = local;
	}
	// #endregion

	// clonar classe
	public JogoCopa clonar() {
		try {
			return (JogoCopa) this.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Couldn't clone JogoCopa");
			return this;
		}
	}

	public void imprimir() {
		System.out.println("[COPA " + this.getAno() + "] " + "[" + this.getEtapa() + "] " + "[" + this.getDia() + "/"
				+ this.getMes() + "] " + "["
				+ this.getSelecao1() + " (" + this.getPlacarSelecao1() + ")" + " x " + "(" + this.getPlacarSelecao2()
				+ ") "
				+ this.getSelecao2() + "]" + " [" + this.getLocal() + "]");

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

	public String stringPadrao() {
		return this.getDia() + "/" + this.getMes() + "/" + this.getAno() + ";" + this.getSelecao1();
	}
}

class ArquivoLeitura {
	private BufferedReader entrada;
	private int quantidadeLinhas;

	public ArquivoLeitura(String nomeArquivo) {
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
		} catch (EOFException excecao) {
			textoEntrada = null;
		} catch (IOException excecao) {
			System.out.println("Erro de leitura: " + excecao);
			textoEntrada = null;
		} finally {
			return textoEntrada;
		}
	}

	public JogoCopa[] carregar(int length) {
		JogoCopa[] copa = new JogoCopa[length];
		String string = null;
		int i = 0;

		string = this.ler();

		while (string != null) {

			int ano = Integer.parseInt(string.split("#")[0]);
			String etapa = string.split("#")[1];
			int dia = Integer.parseInt(string.split("#")[2]);
			int mes = Integer.parseInt(string.split("#")[3]);
			String selecao1 = string.split("#")[4];
			int placarSelecao1 = Integer.parseInt(string.split("#")[5]);
			int placarSelecao2 = Integer.parseInt(string.split("#")[6]);
			String selecao2 = string.split("#")[7];
			String local = string.split("#")[8];

			copa[i++] = new JogoCopa(ano, etapa, dia, mes, selecao1, placarSelecao1, placarSelecao2, selecao2, local);
			string = this.ler();
		}

		this.setQuantidadeLinhas(i);

		this.fecharArquivo();

		return this.copiarVetor(copa);
	}

	public JogoCopa[] copiarVetor(JogoCopa[] vetorOriginal) {
		JogoCopa[] vetorCopia = new JogoCopa[this.getQuantidadeLinhas()];
		int i = 0;

		while (i != this.getQuantidadeLinhas()) {
			vetorCopia[i] = vetorOriginal[i++];
		}

		return vetorCopia;
	}

	public void setQuantidadeLinhas(int quantidade) {
		this.quantidadeLinhas = quantidade;
	}

	public int getQuantidadeLinhas() {
		return this.quantidadeLinhas;
	}
}
