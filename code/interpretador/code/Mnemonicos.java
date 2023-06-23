/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.HashMap;

class Mnemonicos {

    // Mapa com as operações que a ULA executa e seu respectivo código em
    // hexadecimal
    // Usar um hashMap garante baixo custo de pesquisa para encontrar o hexadecimal
    // de cada
    // operação (custo O{1})
    private final HashMap<String, String> mapaOperacoes = new HashMap<>();

    // Construtor do objeto de mnemonicos do sistema no qual preenche o mapa de hash
    // com a operação e seu respectivo valor em hexadecimal
    public Mnemonicos() {
        mapaOperacoes.put("An", "0");
        mapaOperacoes.put("nAoB", "1");
        mapaOperacoes.put("AnB", "2");
        mapaOperacoes.put("zeroL", "3");
        mapaOperacoes.put("nAeB", "4");
        mapaOperacoes.put("Bn", "5");
        mapaOperacoes.put("AxB", "6");
        mapaOperacoes.put("ABn", "7");
        mapaOperacoes.put("AnoB", "8");
        mapaOperacoes.put("nAxB", "9");
        mapaOperacoes.put("copiaB", "A");
        mapaOperacoes.put("AB", "B");
        mapaOperacoes.put("umL", "C");
        mapaOperacoes.put("AoBn", "D");
        mapaOperacoes.put("AoB", "E");
        mapaOperacoes.put("copiaA", "F");
    }

    // Função que retorna o valor em hexadecimal com base em uma chave de pesquisa
    // (operação)
    public String getHex(String chave) {
        try {
            return mapaOperacoes.get(chave);
        } catch (Exception e) {
            System.err.println(chave + " operacao desconhecida\n" + e.getMessage());
            return null;
        }
    }

    // Função que retorna uma operação com base na pesquisa por um numero em
    // hexadecimal
    public String getOp(String hexa) {
        String resp = "";
        try {
            for (String key : mapaOperacoes.keySet()) {
                if (mapaOperacoes.get(key).equals(hexa)) {
                    resp = mapaOperacoes.get(key);
                }
            }
        } catch (Exception e) {
            System.err.println(hexa + " desconhecido localizado\n" + e.getMessage());
        }
        return resp;
    }
}