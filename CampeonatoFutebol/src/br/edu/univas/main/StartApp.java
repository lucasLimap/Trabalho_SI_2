package br.edu.univas.main;

import java.util.Scanner;
import br.edu.univas.bd.*;

public class StartApp {
	public static Scanner input = new Scanner(System.in);
	public static int tBase = 50;

	public static void main(String[] args) {
		
		int opcao = 0;
		
		Partida[] jogos = new Partida[tBase];
		Times[] times = new Times[tBase];
		
		do {
			imprimeMenu();
			opcao = input.nextInt();
			if (opcao == 1) {
				cadastrarTime(times);
			} else if (opcao == 2) {
				editarTime(times, jogos);
			} else if (opcao == 3) {
				excluirTime(times, jogos);
			} else if (opcao == 4) {
				cadastrarJogo(times, jogos);
			} else if (opcao == 5) {
				editarPartida(times, jogos);
			} else if (opcao == 6) {
				excluirJogo(times, jogos);
			} else if (opcao == 7) {
				tabela(times);
			}
		} while (opcao < 9);
		input.close();
		
		
	}public static void imprimeMenu() {

		System.out.printf("\n1 – Cadastrar Time\n" + 
						  "2 – Editar Time\n" + 
						  "3 – Excluir Time\n" + 
						  "4 – Cadastrar Jogo\n"+ 
						  "5 – Editar Jogo\n" + 
						  "6 – Excluir Jogo\n" + 
						  "7 – Listar Classificação do Campeonato\n"+
						  "9 - Sair\n");
	
	}public static void cadastrarTime(Times[] times) {
		System.out.printf("Quantidade de Times no Campeonato:");
		int qtdTimes = input.nextInt();
		for (int i = 0; i < qtdTimes; i++) {
			if (times[i] == null) {
				Times time = new Times();
				System.out.printf("Nome:\n");
				time.nomeTime = input.next();
				System.out.printf("Estado:\n");
				time.estadoOrigem = input.next();
				times[i] = time;
			}	
		}
		
	}public static void imprimeTimes(Times[] times) {
		for (int i = 0; i < tBase; i++) {
			if (i == tBase) {
				break;
			}
			if (times[i] != null) {
				Times time = times[i];
				System.out.println(time.nomeTime);
	}			
	} 
	}


	public static void editarTime(Times[] times, Partida[] jogos) {
		imprimeTimes(times);
		System.out.printf("Edição de times\n"
		+ "Selecione o time:");
		String nomeEditar = input.next();
		for (int i = 0; i < tBase; i++) {
		if (times[i] != null) {
		Times time = times[i];
		if (nomeEditar.equals(time.nomeTime)) {
		System.out.printf("Nome:\n");
		String editar = input.next();
		for (int j = 0; j < tBase; j++) {
		if (jogos[j] != null && jogos[j].TimeMandante.equals(time.nomeTime)) {
		jogos[j].TimeMandante = editar;
		} else if (jogos[j] != null && jogos[j].TimeVisitante.equals(time.nomeTime)) {
		jogos[j].TimeVisitante = editar;
		}
		}
		time.nomeTime = editar;
		System.out.printf("Estado:\n");
		String estadoTime = input.next();
		time.estadoOrigem = estadoTime;
		times[i] = time;			
		}
		}
		}
	
	} public static void excluirTime(Times[] times, Partida[] jogos) {
		imprimeTimes(times);
		int posicao = 0;
		System.out.printf("Excluir Time:");
		String nome = input.next();
		do {
		if (times[posicao] != null) {
		Times time = times[posicao];
		if (nome.equals(time.nomeTime)) {
		break;
			}
		}
		posicao++;
		} while (true);
		Times time = times[posicao];
		for (int i = 0; i < tBase; i++) {
			if (jogos[i] != null) {
			Partida jogo = jogos[i];
			if (jogo.TimeMandante.equals(time.nomeTime)) {
				removerPontuacao(jogos, times, i);
				jogos[i] = null;
			} else if (jogo.TimeVisitante.equals(time.nomeTime)) {
				removerPontuacao(jogos, times, i);
				jogos[i] = null;
			}
			}
		}
		times[posicao] = null;
	
	} public static void cadastrarJogo(Times[] times, Partida[] jogos) {
		System.out.println("Quantidades Partidas: ");
		int qtdPartidas = 0;
		for (int i = 1; i < qtdPartidas+1; i++) {
			dadosJogo(times, jogos);
		}

		
	}public static void dadosJogo (Times[] times, Partida[] jogos) {
		for (int i = 1; i < tBase; i++) {
		Partida jogo = new Partida();
		System.out.printf("\nTime mandante:");
		jogo.TimeMandante = input.next();
		System.out.printf("Gols mandante: ");
		jogo.golsMandante = input.nextInt();
		System.out.printf("\nTime visitante: ");
		jogo.TimeVisitante = input.next();
		System.out.printf("Gols visitante: ");
		jogo.golsVisitante = input.nextInt();
		jogos[i] = jogo;
		pontuacao(times, jogos, i);
		}
		System.out.printf("---Proxima Rodada---");
	}

	public static void editarPartida(Times[] times, Partida[] jogos) {
		imprimePartida(jogos);
		System.out.printf("Código da Partida\n");
		int codigo = input.nextInt() - 1;
		input.nextLine();
		Partida jogo = jogos[codigo];
		System.out.printf("Time mandante:\n");
		jogo.TimeMandante = input.next();
		System.out.printf("Gols mandante:\n");
		jogo.golsMandante = input.nextInt();
		System.out.printf("Time visitante:\n");
		jogo.TimeVisitante = input.next();
		System.out.printf("Gols visitante:\n");
		jogo.golsVisitante = input.nextInt();
		jogos[codigo] = jogo;
		pontuacao(times, jogos, codigo);
	
	}public static void pontuacao(Times[] times, Partida[] jogos, int qtdPartidas) {
		int mandante = 0;
		int visitante = 0;
		Partida jogo = jogos[qtdPartidas];

		int posicao = 0;
		int cout = 0;
		do {
			if (times[posicao] != null) {
				Times time = times[posicao];
				if (time.nomeTime.equals(jogo.TimeMandante)) {
				mandante = posicao;
				cout++;
				} else if (time.nomeTime.equals(jogo.TimeVisitante)) {
				visitante = posicao;
				cout++;
			}
			if (cout == 2) {
				break;
			}
		}

			posicao++;
		} while (true);
		
		if (jogo.golsMandante > jogo.golsVisitante) {
			times[mandante].pontos = times[mandante].pontos + 3;
			times[mandante].saldoGols = times[mandante].saldoGols + (jogo.golsMandante - jogo.golsVisitante);
			times[visitante].saldoGols = times[visitante].saldoGols + (jogo.golsVisitante - jogo.golsMandante);
		} else if (jogo.golsVisitante > jogo.golsMandante) {
			times[visitante].pontos = times[visitante].pontos + 3;
			times[mandante].saldoGols = times[mandante].saldoGols + (jogo.golsMandante - jogo.golsVisitante);
			times[visitante].saldoGols = times[visitante].saldoGols + (jogo.golsVisitante - jogo.golsMandante);
		} else if (jogo.golsVisitante == jogo.golsMandante) {
			times[mandante].pontos = times[mandante].pontos + 1;
			times[visitante].pontos = times[visitante].pontos + 1;
		}
	}

	public static void imprimePartida(Partida[] jogos) {
		System.out.printf("Tabela de Jogos::\n");
		for (int i = 0; i < tBase; i++){
		if (i == tBase) {
			}
			if (jogos[i] != null) {
			Partida jogo = jogos[i];
			System.out.printf("%s. %s %s X %s %s\n", (i), jogo.TimeMandante, jogo.golsMandante, jogo.golsVisitante, jogo.TimeVisitante);
		}
	}
	}

	public static void excluirJogo(Times[] times, Partida[] jogos) {
		imprimePartida(jogos);
		System.out.printf("Codigo da partida: ");
		int codigo = input.nextInt();
		codigo--;
		input.nextLine();
		removerPontuacao( jogos, times, codigo);
		jogos[codigo] = null;
	
	}public static void removerPontuacao(Partida[] jogos, Times[] times, int codigo ) {
		Partida jogo = jogos[codigo];
		int posicao = 0;
		int mandante = 0;
		int visitante = 0;
		int cout = 0;
		
		do {
			if (times[posicao] != null) {
				if (cout == 2) {
				break;
				}	
				Times time = times[posicao];	
			if (time.nomeTime.equals(jogo.TimeMandante)) {
				mandante = posicao;
				cout++;
			} else if (time.nomeTime.equals(jogo.TimeVisitante)) {
				visitante = posicao;
				cout++;
			}
			}
			posicao++;
		} while (true);
		
			if (jogo.golsMandante > jogo.golsVisitante) {
			times[mandante].pontos = times[mandante].pontos - 3;
			times[mandante].saldoGols = times[mandante].saldoGols - (jogo.golsMandante - jogo.golsVisitante);
			times[visitante].saldoGols = times[visitante].saldoGols - (jogo.golsVisitante - jogo.golsMandante);
			
			} else if (jogo.golsMandante < jogo.golsVisitante) {
			times[visitante].pontos = times[visitante].pontos - 3;
			times[mandante].saldoGols = times[mandante].saldoGols - (jogo.golsMandante - jogo.golsVisitante);
			times[visitante].saldoGols = times[visitante].saldoGols - (jogo.golsVisitante - jogo.golsMandante);
			
			} else {
			times[mandante].pontos = times[mandante].pontos - 1;
			times[visitante].pontos = times[visitante].pontos - 1;
		}
	}

	public static int cargaDados (Times[] times, Times[] tabela) {
		int posicao = 0;
		for (int i = 0; i < tBase; i++) {
			if (times[i] != null) {
			Times time = times[i];
			Times equipe = new Times();
			equipe.nomeTime = time.nomeTime;
			equipe.estadoOrigem = time.estadoOrigem;
			equipe.pontos = time.pontos;
			equipe.saldoGols = time.saldoGols;
			tabela[posicao] = equipe;
			posicao++;
			}
		}
		return posicao;
	}
	
	public static void classificacao(Times[] tabela, int x) {
		int posicao = 1;
		System.out.printf("Posição  Time  Pontos  SG\n");
		for(int i = x; i >= 0; i--) {
			if(tabela[i] != null) {
			Times time = tabela[i];
			System.out.printf("  %s        %s      %s    %s\n",posicao, time.nomeTime, time.pontos, time.saldoGols);
			posicao++;
			}
		}
	}

	public static void tabela(Times[] times) {
		Times[] tabela = new Times[tBase];
		int posicao = cargaDados(times, tabela);
		ordenarClassificacao(tabela, posicao);
		classificacao(tabela, posicao);
	
	}

	public static void ordenarClassificacao(Times[] tabela, int posicao) {
		for (int i = 0; i < posicao - 1; i++) {
			for (int j = 0; j < posicao - 1 - i; j++) {
			if (tabela[j] != null && tabela[j + 1] != null && tabela[j].pontos >= tabela[j + 1].pontos
			&& tabela[j].saldoGols > tabela[j + 1].saldoGols) {
			Times auxiliar = tabela[j];
			tabela[j] = tabela[j+1];
			tabela[j+1] = auxiliar;
				}
			}
		}
	}

}
