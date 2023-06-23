package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Decodificador {

    private final Mnemonicos mnemonicos;

    public Decodificador() {
        mnemonicos = new Mnemonicos();
    }

    public String compileCode(String nomeArq, File arqUla) {
        //Verificando se o nome existe ou esta vazio
        if (nomeArq == null || nomeArq.equals("")) {
            nomeArq = arqUla.getName().replace(".ula", "");
        }
        // Iniciando variaveis do sistema para leitura e escrita dos arquivos (hex/ula)
        // leitor = Buffer de leitura do arquivo .ula
        // escrita = Buffer de escrita do arquivo .hex
        BufferedReader leitor;
        BufferedWriter escrita;
        String entX = "", entY = "", saidaW = "", linha = "", status = "";
        int rows = 1;
        // Iniciando classe de Mnemonicos (operações que a ULA consegue executar)
        try {
            // Carregando o codigo em .ula para o sistema de leitura do Java
            leitor = new BufferedReader(new FileReader(arqUla));
            // Iniciando o sistema de escrita do codigo interpretado .hex
            escrita = new BufferedWriter(new FileWriter(new File(nomeArq.replace(".ula", "") + ".hex")));
            // Declarando analisador de gramática
            GrammarAnalyzer grammarAnalyzer;
            try {
                do {
                    // leitura das linhas do arquivo .ula para que o sistema reconheça X, Y e W
                    linha = leitor.readLine();
                    if (linha != null) {
                        // Checando gramática da instrução
                        grammarAnalyzer = new GrammarAnalyzer(linha, rows);
                        if (!grammarAnalyzer.isSafe) {
                            System.err.println("Código problemático com exceção não rastreada!");
                        }
                        // Verificação se a linha contem o comando X= para atribuir valor a variavel X
                        if (linha.contains("X=")) {
                            entX = linha.substring(2, linha.length() - 1);
                        } // Verificação se a linha contem o comando Y= para atribuir valor a variavel Y
                        else if (linha.contains("Y=")) {
                            entY = linha.substring(2, linha.length() - 1);
                        } // Verificação se a linha contem o comando W= para iniciar o processo de escrita
                        // do codigo
                        // interpretado pelo sistema, caso encontre o 'W=' o sistema vai escrever no
                        // arquivo a junção
                        // entre os valores A,B e codigo hexadecimal da operação
                        else if (linha.contains("W=")) {
                            saidaW = linha.substring(2, linha.length() - 1);
                            escrita.write(entX + entY + mnemonicos.getHex(saidaW) + "\n");
                        }
                    } else {
                        throw new Exception("Erro de compilação: O arquivo não possuí fim declarado no código!");
                    }
                    rows++;
                    // Loop de verificação enquanto não encontra o fim do arquivo com a palavra de
                    // parada "fim"
                } while (!linha.equalsIgnoreCase("fim."));
                status = "O arquivo .hex foi gerado com sucesso. \n"
                        + "Arquivo: " + nomeArq.replace(".ula", "") + ".hex na raiz de execução.";
            } catch (Exception e) {
                status = e.getMessage();
                e.printStackTrace();
            }
            // Fechando canais de leitura e escrita dos arquivos
            leitor.close();
            escrita.close();
        } catch (IOException io) {
            status = "Arquivo desconhecido na pasta ou falha ao escrever HEX \n" + io.getMessage();
            io.printStackTrace();
        } catch (NullPointerException np) {
            status = "Falha ao referenciar objeto nulo desconhecido \n" + np.getMessage();
            np.printStackTrace();
        } catch (Exception e) {
            status = "Erro inesperado na execução principal \n" + e.getMessage();
            e.printStackTrace();
        }
        if (!status.contains("sucesso")) {
            File f = new File(nomeArq + ".hex");
            f.delete();
            if (f.exists()) {
                f.delete();
            }
        }
        return status;
    }
}
