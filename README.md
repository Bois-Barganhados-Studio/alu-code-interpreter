# alu-code-interpreter
Sistema de interpretação de arquivos .HEX para .ULA para serem executados por um arduino programado com um sistema de ULA com operaçõs básicas. Intepreter system to decode .HEX files to .ULA for running in an arduino with ALU basic operations. 
# Autores
- [@Leon Junio Martins Ferreira](https://www.github.com/leon-junio)
- [@Pedro Pampolini](https://github.com/PedroPampolini)
- [@Leon Junio Martins Ferreira](https://github.com/GustavGomes)
# Descrição
## ULA 4 bits (programa montador) + Arduino
Neste exercício 2 programas vão ser desenvolvidos. Um no hardware externo (Arduino) e outro no PC, que será a interface com o usuário. A ideia é ler um programa escrito pelo usuário, transformá-lo em mnemônicos gerando outro programa e finalmente passá-lo ao Hardware externo através da porta serial e realizar algum processamento nesse Hardware. O resultado será observado nos 4 Leds conectados no Hardware externo.
## Arquitetura proposta
Você deverá projetar uma ULA com 4 bits para um dado A, 4 bits para um dado B e 4 bits para a instrução desejada. O funcionamento é similar à ULA anteriormente estudada.
Uma arquitetura do sistema proposto pode ser vista abaixo:
![image](https://github.com/Bois-Barganhados-Studio/alu-code-interpreter/assets/59715756/c8be4413-07b1-459a-bab8-6151277b9dc5)
## Software no PC
O software no PC poderá ser escrito em C, C++, C# , Java ou Python.
Deverá ser criado um programa que transforme um texto lido de um arquivo nas instruções a serem executadas e permita a sua execução linha a linha através do console. Para isso, o programa deverá inicialmente ler um arquivo contendo um texto original com os mnemônicos (instruções a serem executadas) e gerar um segundo texto, onde cada linha seja transformada nos valores que serão disponibilizados para a porta USB/serial (ou digitados) no Arduino. Esse segundo texto deverá ser um arquivo gravado com os respectivos valores a serem enviados para a porta USB/serial (ou digitados) porém no formato hexadecimal.
Você deverá utilizar o conjunto de instruções que a ULA possui ilustrado na Figura 2 a seguir:
![image](https://github.com/Bois-Barganhados-Studio/alu-code-interpreter/assets/59715756/f1f8a2c8-39c0-43b1-9d54-5a397ce3e7a8)
## Exemplo de execução
![image](https://github.com/Bois-Barganhados-Studio/alu-code-interpreter/assets/59715756/afe17a56-1aa5-4843-a15d-66c3ee354a09)
O ciclo de execução da máquina pode ser entendido através da Figura 5 a seguir e que é executado sobre o programa hex. Imagine que cada linha do programa hex esteja em uma posição da memória e que aconteça a busca de uma instrução de cada vez.
![image](https://github.com/Bois-Barganhados-Studio/alu-code-interpreter/assets/59715756/aa26efd8-26c4-40bf-b735-b09c68714c8b)

## O Programa no Arduino
A ideia é executar exatamente o ciclo de instruções proposto na figura 5 dentro do Arduino.
Você deverá elaborar um programa no Arduino que utilize a entrada serial para receber as entradas necessárias ao funcionamento da ULA (dados e instruções) e as saídas deverão ser 4 Leds ligados aos pinos 13, 12, 11 e 10 (o bit mais significativo no pino 13 e o menos significativo no pino 10).
Seu programa no arduino deverá ser capaz de receber 3 dados da seguinte forma:
Um primeiro valor representando a entrada X (X0, X1, X2 e X3).
Um segundo valor representando a entrada Y (Y0, Y1, Y2 e Y3).
Um terceiro valor representando a instrução desejada S (S0, S1, S2 e S3).
Assim, se fornecermos pela comunicação serial na IDE do Arduino os seguintes 3 valores:
124, estaremos passando para a ULA as seguintes informações:
Valor de X=1, valor de Y=2 e a instrução desejada=4 ou S=4. A ULA projetada no arduino deverá então realizar, conforme o conjunto de instruções da ULA (de acordo com a Fig. 2), a instrução (nAeB), ou seja (AB)’ que sobre as variáveis X e Y ficaria (XY)’.
Observe que as operações sempre serão realizadas sobre as variáveis X e Y e o resultado sempre será em W.
Para não haver confusão nos valores, deveremos usar os números em Hexadecimal, assim, se passarmos ao Arduino os seguintes dados AAA, o significado será:
Valor de X = 10, valor de Y=10 e a instrução desejada ou S=10. A ULA projetada no arduino deverá então realizar, conforme o conjunto de instruções da ULA (e de acordo com a Fig. 2) a instrução B, atenção que a instrução B apenas coloca o valor da entrada Y na saída (não confunda a instrução B com a entrada tendo o valor de B).
Deverá existir internamente no Arduino um vetor que será a memória (e também os registradores) da Unidade.
Este vetor deverá conter nos quatro primeiros campos (que serão os registradores da máquina) os seguintes valores:
Primeira posição = o índice do vetor onde a instrução está armazenada, que chamaremos de PC
Segunda posição = o conteúdo da variável W ( que contém os resultados das operações)
Terceira posição = o conteúdo da variável X
Quarta posição = o conteúdo da variável Y
Vamos considerar que iremos utilizar um espaço relativo a 100 posições (esta será a nossa área de memória) onde as quatro primeiras posições serão as variáveis e as 96 restantes o programa a ser executado.
![image](https://github.com/Bois-Barganhados-Studio/alu-code-interpreter/assets/59715756/aa164101-9b1e-41a1-b68a-7f776131de14)
Se, durante o programa a ser executado, houver uma alteração no valor das variáveis X, Y ou W este valor deverá ser alterado na memória ( nas respectivas posições do vetor). A alteração se dá através da atribuição de valores no programa fonte original.
