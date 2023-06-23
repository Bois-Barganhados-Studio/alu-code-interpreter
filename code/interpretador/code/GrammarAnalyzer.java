/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.InputMismatchException;

class GrammarAnalyzer {

    public boolean isSafe = true;
    private int rows;

    // Construtor para o analizador gramatical
    public GrammarAnalyzer(String instruction, int rows) {
        if (rows == 1 && instruction.compareTo("inicio:") != 0) {
            throw new InputMismatchException("Erro! Falta do operador de \"inicio\" na linha " + rows + "!");
        }
        this.rows = rows;
        if (instruction.compareTo("inicio:") == 0 || instruction.compareTo("fim.") == 0) {
            isSafe = true;
        } else {
            isSafe = LexicalAnalyzer(instruction) && SyntaxAnalyzer(instruction);
        }
    }

    // Análise léxica dos principais símbolos que fazem parte da gramática da
    // linguagem
    public boolean LexicalAnalyzer(String instruction) {
        if (instruction.length() > 0) {
            if (instruction.charAt(instruction.length() - 1) != ';') {
                throw new InputMismatchException("Erro! Insira ';' para completar a instrução na linha " + rows + "!");
            }
            for (int i = 0; i < instruction.length(); i++) {
                if (!isHex(instruction.charAt(i)) && isVariable(instruction.charAt(i))) {
                    isSafe = false;
                    i = instruction.length();
                    throw new InputMismatchException("Erro! Caracter não suportado na linha " + rows + ".");
                }
            }
        } else {
            isSafe = false;
        }
        return isSafe;
    }

    // Análise sintáxica da lógica de operações e instruções da linguagem
    public boolean SyntaxAnalyzer(String instruction) {
        if (instruction.length() > 0) {
            String[] members = instruction.split("=");
            if (members.length > 2) {
                isSafe = false;
                throw new InputMismatchException("Erro! Mais de um operador de atribuição na linha " + rows + ".");
            } else if (members.length < 2) {
                isSafe = false;
                throw new InputMismatchException("Erro! Sem operador de atribuição na linha " + rows + ".");
            } else {
                if (!isVariable(members[0].charAt(0))) {
                    isSafe = false;
                    throw new InputMismatchException("Erro! Variável desconhecida na linha " + rows + ".");
                }
                if (members[0].charAt(0) == 'W' && !isOperation(members[1].substring(0, members[1].length() - 1))) {
                    isSafe = false;
                    throw new InputMismatchException("Erro! Operação desconhecida na linha " + rows + ".");
                }
                if ((members[0].charAt(0) == 'X' || members[0].charAt(0) == 'Y') && isHex(members[1].charAt(0))) {
                    isSafe = false;
                    throw new InputMismatchException("Erro! Valor HEXADECIMAL desconhecido na linha " + rows + ".");
                }
            }
        } else {
            isSafe = false;
        }
        return isSafe;
    }

    // Checa se os numeros inseridos estão entre os valores de hexadecimais
    boolean isHex(char caracter) {
        return (caracter < '0' || caracter > '9') && (caracter < 'A' || caracter > 'F');
    }

    // Checa as variaveis informadas pelo arquivo ula
    boolean isVariable(char caracter) {
        return (caracter == 'X' || caracter == 'Y' || caracter == 'W');
    }

    // Checa se uma entrada é considerada como operação
    boolean isOperation(String operation) {
        return (operation.compareTo("An") == 0 || operation.compareTo("nAoB") == 0 || operation.compareTo("AnB") == 0
                || operation.compareTo("zeroL") == 0 || operation.compareTo("nAeB") == 0
                || operation.compareTo("AxB") == 0
                || operation.compareTo("ABn") == 0 || operation.compareTo("AnoB") == 0
                || operation.compareTo("nAxB") == 0
                || operation.compareTo("Bn") == 0 || operation.compareTo("copiaB") == 0
                || operation.compareTo("AB") == 0
                || operation.compareTo("umL") == 0 || operation.compareTo("AoBn") == 0
                || operation.compareTo("AoB") == 0
                || operation.compareTo("copiaA") == 0);
    }
}
