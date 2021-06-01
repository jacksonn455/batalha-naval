package batalhanaval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class BatalhaNaval implements Serializable {

    static String nomeJogador1, nomeJogador2;
    static int altura, largura, quantidadedenavios, limitemaximodenavios;
    static int tabuleirojogador1[][], tabuleirojogador2[][];
    static int naviosjogador1, naviosjogador2;
    static int opcao, inicio;
    static Scanner entrada = new Scanner(System.in);

    public static void ObterTamanhoDosTabuleiros() {

        for (;;) {
            boolean verifica = false;
            try {
                System.out.print("Digite a altura do tabuleiro: ");
                altura = entrada.nextInt();
                System.out.print("Digite a largura do tabuleiro: ");
                largura = entrada.nextInt();
                verifica = true;
            } catch (InputMismatchException erro) {
                System.out.println("Digite dois valores numéricos");
            }
            if (verifica) {

                break;
            }
        }

    }

    public static void ObterNomesDosJogadores() {
        System.out.println("1- " + "Contra maquina");
        System.out.println("2- " + "Contra outro jogador");
        System.out.print("Digite sua Opção: ");
        opcao = entrada.nextInt();
        while (opcao < 1 || opcao > 2) {
            System.out.print("Digite a opção correta (1 / 2): ");
            opcao = entrada.nextInt();
        }
        if (opcao == 1) {
            System.out.print("Digite o nome do Jogador: ");
            nomeJogador1 = entrada.next();
            nomeJogador2 = "Maquina";
        } else {
            System.out.print("Digite o nome do Jogador 1: ");
            nomeJogador1 = entrada.next();
            System.out.print("Digite o nome do Jogador 2: ");
            nomeJogador2 = entrada.next();
        }
    }

    public static void CalcularQuantidadeMaximaDeNavios() {
        limitemaximodenavios = (altura * largura / 3);
    }

    public static int[][] RetornarNovoTabuleiroVazio() {
        return new int[altura][largura];
    }

    private static void IniciandoOsTabuleiros() {
        tabuleirojogador1 = RetornarNovoTabuleiroVazio();
        tabuleirojogador2 = RetornarNovoTabuleiroVazio();
    }

    public static void ObiterQuantidadeDeNaviosNoJogo() {
        System.out.println("Maximo " + limitemaximodenavios + " Navios");
        System.out.print("Digite a quantidade de navios do jogo: ");
        quantidadedenavios = entrada.nextInt();
        while (quantidadedenavios < 0 || quantidadedenavios > limitemaximodenavios) {
            if (limitemaximodenavios == 1 || limitemaximodenavios == 0) {
                System.out.print("Porfavor Digite " + limitemaximodenavios + ": ");
                quantidadedenavios = entrada.nextInt();
            } else {
                System.out.print("Porfavor Digite um número de 1 a " + limitemaximodenavios + ": ");
                quantidadedenavios = entrada.nextInt();
            }
        }
    }

    public static void IniciarContadordeNavios() {
        naviosjogador1 = quantidadedenavios;
        naviosjogador2 = quantidadedenavios;
    }

    public static int[][] RetornarNovoTabuleiroComOsNavios() {

        int novoTabuleiro[][] = RetornarNovoTabuleiroVazio();
        int quantidaderestantedenavios = quantidadedenavios;
        int x = 0, y = 0;
        Random rand = new Random();
        do {
            x = 0;
            y = 0;
            for (int[] linha : novoTabuleiro) {
                for (int coluna : linha) {
                    if (rand.nextInt(100) <= 10) {
                        if (coluna == 0) {
                            novoTabuleiro[x][y] = 1;
                            quantidaderestantedenavios--;
                            break;

                        }
                        if (quantidaderestantedenavios <= 0) {
                            break;
                        }
                    }
                    y++;
                }
                y = 0;
                x++;
                if (quantidaderestantedenavios <= 0) {
                    break;
                }
            }
        } while (quantidaderestantedenavios > 0);
        return novoTabuleiro;
    }

    public static void InserirOsNaviosNosTabuleirosDosJogadores() {
        tabuleirojogador1 = RetornarNovoTabuleiroComOsNavios();
        tabuleirojogador2 = RetornarNovoTabuleiroComOsNavios();
    }

    public static void ExibirNumerosDasColunasDoTabuleiro() {
        String numerosdotabuleiro;
        int numerosdacoluna = 1;
        numerosdotabuleiro = "   ";
        for (int i = 0; i < largura; i++) {
            numerosdotabuleiro += (numerosdacoluna++) + " ";
        }
        System.out.println(numerosdotabuleiro);
    }

    public static void ExibirTabuleiro(String nomeDoJogador, int[][] tabuleiro, boolean seutabuleiro) {
        // |~|agua no bloco |*|tiro dado |X|havia um navio

        System.out.println("|-----" + nomeDoJogador + "-----|");
        ExibirNumerosDasColunasDoTabuleiro();
        String linhadotabuleiro = "";
        char letradalinha = 64;
        linhadotabuleiro = (letradalinha++) + " |";
        for (int[] linha : tabuleiro) {
            linhadotabuleiro = (letradalinha++) + " |";

            for (int coluna : linha) {
                switch (coluna) {
                    case 0: //Vazio
                        linhadotabuleiro += "~|";
                        break;
                    case 1: //Navio
                        if (seutabuleiro) {
                            linhadotabuleiro += "N|";
                        } else {
                            linhadotabuleiro += "~|";
                        }
                        break;
                    case 2: //Erro
                        linhadotabuleiro += "*|";
                        break;
                    case 3://Acertou
                        linhadotabuleiro += "X|";
                        break;
                }

            }
            System.out.println(linhadotabuleiro);
        }

    }

    public static void ExibirTabuleiroDosJogadores() {
        if (opcao == 1) {
            ExibirTabuleiro(nomeJogador1, tabuleirojogador1, true);
            ExibirTabuleiro(nomeJogador2, tabuleirojogador2, false);
        } else {
            ExibirTabuleiro(nomeJogador1, tabuleirojogador1, false);
            ExibirTabuleiro(nomeJogador2, tabuleirojogador2, false);
        }

    }

    public static String ReceberValorDigitadoPeloJogador() {
        System.out.print("Digite a posição do seu tiro: ");
        return entrada.next();
    }

    public static boolean ValidarTiroDoJogador(String tirodoJogador) {
        int quantidadedenumeros = (largura > 10) ? 2 : 1;
        String expressaodeverificacao = "^[A-za-z]{1}[0-9]{" + quantidadedenumeros + "}$";
        return tirodoJogador.matches(expressaodeverificacao);
    }

    public static int[] RetornarPosicoesDigitadasPeloJogador(String tirodoJogador) {
        String tiro = tirodoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[0] = tiro.charAt(0) - 97;
        retorno[1] = Integer.parseInt(tiro.substring(1)) - 1;
        return retorno;
    }
        public static int[] RetornarPosicoesDigitadasPeloJogadorBomba(String tirodoJogador) {
        String tiro = tirodoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[0] = tiro.charAt(1) - 97;
        retorno[1] = Integer.parseInt(tiro.substring(2)) - 1;
        return retorno;
    }

    public static void InserirValoresDaAcaoNoTabuleiro(int[] posicoes, int numerodojogador) {
       if(!ValidarPosicoesInseridasPeloJogador(posicoes)){
           System.out.println("Posição invalida");
           return;
       }
        if (numerodojogador == 1) {
            if (tabuleirojogador2[posicoes[0]][posicoes[1]] == 1) {
                tabuleirojogador2[posicoes[0]][posicoes[1]] = 3;
                System.out.println(nomeJogador1 + " acertou o navio");
                naviosjogador2--;
            } else {
                tabuleirojogador2[posicoes[0]][posicoes[1]] = 2;
                System.out.println(nomeJogador1 + " errou o tiro");
            }
        } else {
            if (tabuleirojogador1[posicoes[0]][posicoes[1]] == 1) {
                tabuleirojogador1[posicoes[0]][posicoes[1]] = 3;
                System.out.println(nomeJogador2 + " acertou o navio");
                naviosjogador1--;
            } else {
                tabuleirojogador1[posicoes[0]][posicoes[1]] = 2;
                System.out.println(nomeJogador2 + " errou o tiro");
            }
        }
    }
    
        public static void InserirValoresDaAcaoNoTabuleiroBomba(int[] posicoes, int numerodojogador) {
        InserirValoresDaAcaoNoTabuleiro(posicoes,numerodojogador);
            
            int[] aux = new int[2];
             
            aux[0] = posicoes[0]+1;
            aux[1] = posicoes[1];
            
        InserirValoresDaAcaoNoTabuleiro(aux, numerodojogador);
            aux[0] = posicoes[0]-1;
            aux[1] = posicoes[1];
            
            InserirValoresDaAcaoNoTabuleiro(aux, numerodojogador);
            aux[0] = posicoes[0];
            aux[1] = posicoes[1]+1;
            
            InserirValoresDaAcaoNoTabuleiro(aux, numerodojogador);
            aux[0] = posicoes[0];
            aux[1] = posicoes[1]-1;
            
            InserirValoresDaAcaoNoTabuleiro(aux, numerodojogador);
            
        }
        

    

    public static boolean ValidarBombaDoJogador(String bombadoJogador) {
        int quantidadedenumeros = (largura > 10) ? 2 : 1;
        String expressaodeverificacao = "^0[A-za-z]{1}[0-9]{" + quantidadedenumeros + "}$";
        return bombadoJogador.matches(expressaodeverificacao);
    }

    public static int[] RetornarPosicoesDigitadasPelaBombaDoJogador(String bombadoJogador) {
        String bomba;
        bomba = bombadoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[0] = bombadoJogador.charAt(1) - 97;
        retorno[1] = Integer.parseInt(bomba.substring(2)) - 1;
        return retorno;
    }

    public static boolean AcaoDoJogador1() {
        boolean acaovalida = true;
        String tirodoJogador;
        tirodoJogador = ReceberValorDigitadoPeloJogador();
        if (ValidarTiroDoJogador(tirodoJogador)) {
            System.out.println("test");
       
                 int[] posicoes = RetornarPosicoesDigitadasPeloJogador(tirodoJogador);
            if (ValidarPosicoesInseridasPeloJogador(posicoes)) {

                InserirValoresDaAcaoNoTabuleiro(posicoes, 1);

            } else {
                acaovalida = false;

            }
        } else if (ValidarBombaDoJogador(tirodoJogador)) {
            int[] posicoes = RetornarPosicoesDigitadasPeloJogadorBomba(tirodoJogador);
            if (ValidarPosicoesInseridasPeloJogador(posicoes)) {

                InserirValoresDaAcaoNoTabuleiroBomba(posicoes, 1);

            } else {
                System.out.println("Posição Inválida");
                acaovalida = false;

            }

        } else {
            System.out.println("Posição Inválida");
            acaovalida = false;

        }
        return acaovalida;
    }

    public static boolean AcaoDoJogador2() {
        boolean acaovalida = true;
        String tirodoJogador = ReceberValorDigitadoPeloJogador();
        if (ValidarTiroDoJogador(tirodoJogador)) {

            int[] posicoes = RetornarPosicoesDigitadasPeloJogador(tirodoJogador);
            if (ValidarPosicoesInseridasPeloJogador(posicoes)) {

                InserirValoresDaAcaoNoTabuleiro(posicoes, 2);

            } else {
                acaovalida = false;
            }

        } else {
            System.out.println("Posição Inválida");
            acaovalida = false;
        }
        return acaovalida;
    }

    public static boolean ValidarPosicoesInseridasPeloJogador(int[] posicoes) {
        boolean retorno = true;
        if (posicoes[0] > altura - 1 || posicoes[0] < 0  ) {
            retorno = false;
            System.out.println("A posição das letras não podem ser maiores que " + (char) altura + 64);
        }
        if (posicoes[1] > largura || posicoes[1] < 0) {
            retorno = false;
            System.out.println("A posição numérica não pode ser maior que " + (char) largura);
        }
        return retorno;
    }

    public static void AcaoDoComputador() {
        int[] posicoes = RetornarJogadaDoComputador();
        InserirValoresDaAcaoNoTabuleiro(posicoes, 2);
    }

    public static int[] RetornarJogadaDoComputador() {
        int[] posicoes = new int[2];
        posicoes[0] = RetornarJogadaAleatoriaDoComputador(altura);
        posicoes[1] = RetornarJogadaAleatoriaDoComputador(largura);
        return posicoes;
    }

    public static int RetornarJogadaAleatoriaDoComputador(int limite) {
        Random jogadadocomputador = new Random();
        int numerogerado = jogadadocomputador.nextInt(limite);
        return (numerogerado == limite) ? --numerogerado : numerogerado;
    }

    public static void ContinuarJogando() {
        int S_N;
        System.out.print("Deseja Continuar o Jogo? (0 Para Não / 1 Para Sim): ");
        S_N = entrada.nextInt();
        while (S_N < 0 || S_N > 1) {
            System.out.print("Digite Novamente(0 para Não / 1 Para Sim): ");
            S_N = entrada.nextInt();
        }
        if (S_N == 1) {
            inicio = 1;
        } else if (S_N == 0) {
            inicio = 0;
        }

    }

    public void SaveGame() {
        {
            try {
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

                File arquivo = new File(currentPath + "/log.txt");

                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void LoadGame() throws FileNotFoundException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader("/log.txt"));
    }

    public static void main(String[] args) {

        inicio = 1;
        while (inicio == 1) {
            ObterNomesDosJogadores();
            ObterTamanhoDosTabuleiros();
            CalcularQuantidadeMaximaDeNavios();
            IniciandoOsTabuleiros();
            ObiterQuantidadeDeNaviosNoJogo();
            IniciarContadordeNavios();
            InserirOsNaviosNosTabuleirosDosJogadores();
            ExibirTabuleiroDosJogadores();

            while (inicio == 1) {

                if (opcao == 1) {
                    if (AcaoDoJogador1()) {
                        if (naviosjogador2 <= 0) {
                            System.out.println(nomeJogador1 + " venceu o jogo");
                            break;
                        }
                        AcaoDoComputador();
                        if (naviosjogador1 <= 0) {
                            System.out.println(nomeJogador2 + " venceu o jogo");
                            break;

                        }

                    }
                } else {
                    if (AcaoDoJogador1()) {
                        if (naviosjogador2 <= 0) {
                            System.out.println(nomeJogador1 + " venceu o jogo");
                            break;
                        }
                        AcaoDoJogador2();
                        if (naviosjogador1 <= 0) {
                            System.out.println(nomeJogador2 + " venceu o jogo");
                            break;
                        }
                    }

                }
                ExibirTabuleiroDosJogadores();

            }
            ContinuarJogando();
        }

    }
}
