// C++ code
//
#include <math.h>
#include <LiquidCrystal.h>
//Pinos Binários-----
#define A0 10
#define A1 11
#define A2 12
#define A3 13
#define MEMSIZE 100
//------------------

String memory[MEMSIZE];
LiquidCrystal lcd(7, 6, 5, 4, 3, 2);

void setup()
{
  pinMode(A0, OUTPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, OUTPUT);
  
  memory[0] = "4";
  memory[1] = "0";
  memory[2] = "0";
  memory[3] = "0";
  dumbMemory();
  Serial.begin(9600);
  lcd.begin(16,2);
  lcd.clear();
}

void loop()
{
  if(Serial.available()){
    setMemory();
    memory[0] = "4";
    memory[1] = "0";
    memory[2] = "0";
    dumbMemory();
    while(memory[PC()] != "NULL"){
      lcd.clear();
      lcd.print(memory[PC()]);
      lcd.print("=");
      operation(memory[PC()]);
      lcd.print(memory[1]);
      dumbMemory();
      incPC();
      delay(5000);
    }
  }
  
  
  
}

int PC(){
  return memory[0].toInt();
}

void writeOut(int n){
  digitalWrite(A0,LOW);
  digitalWrite(A1,LOW);
  digitalWrite(A2,LOW);
  digitalWrite(A3,LOW);
  
  digitalWrite(A0, (n & 1) == 1);
  digitalWrite(A1, (n & 2) == 2);
  digitalWrite(A2, (n & 4) == 4);
  digitalWrite(A3, (n & 8) == 8);
}

void setMemory(){
  int i = 4;
  String s = Serial.readString();
  s+= " NULL";
  s.replace("\n","");
  while(i < MEMSIZE && s != "NULL" && s != " NULL"){
    memory[i] = "";
    for(int j = 0; j < s.length(); j++){
      if(s[j] != ' '){
        memory[i] += s[j];
      }
      else{
        s = s.substring(j+1,s.length());
        j = s.length();
      }
    }
    i++;
    
  }
  memory[i] = "NULL";
}


void dumbMemory(){
  Serial.print("->|");
  int pc = PC();
  for(int i = 0; i < MEMSIZE && memory[i] != "NULL"; i++){
    if(i == pc && pc > 3){
      Serial.print(">  ");
      Serial.print(memory[i]);
      Serial.print("  <");
    }
    else{
      Serial.print(memory[i]);
    }
    Serial.print("|");
  }
  Serial.println();
}

void incPC(){
  int pos = PC();
  pos++;
  memory[0] = String(pos);
}

int hexToInt(char c){
  int rsp = 0;
  if(c >= '0' && c <= '9'){
    rsp = c - 48;
  }
  else{
    rsp = c - 55;
  }
  return rsp;
}

void operation(String op){
  memory[2] = op[0];
  memory[3] = op[1];
  int num = 0;
  switch (op[2])
  {
  case '0':
    /* not A */
    num = hexToInt(op[0]);
    setW(num ^ 15);
    writeOut(num ^ 15);
    break;
  //1100 1011 1111 0000
  case '1':
    /* not(or(A,B)) */
    num = (hexToInt(op[0]) | hexToInt(op[1])) ^ 15;
    setW(num);
    writeOut(num);
    break;
  
  case '2':
    /* and(not(A),B) */
    num = (hexToInt(op[0]) ^ 15) & hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case '3':
    /* saida = 0000 */
    //setW(0);
    writeOut(0);
    break;
  
  case '4':
    /* not(and(A,B)) */
    num = (hexToInt(op[0]) & hexToInt(op[1])) ^ 15;
    setW(num);
    writeOut(num);
    break;
  
  case '5':
    /* not(B) */
    num = hexToInt(op[1]);
    setW(num ^ 15);
    writeOut(num ^ 15);
    break;
  
  case '6':
    /* xor(A,B) */
    num = hexToInt(op[0]) ^ hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case '7':
    /* and(A,not(B)) */
    num = hexToInt(op[0]) & (hexToInt(op[1]) ^ 15);
    setW(num);
    writeOut(num);
    break;
  
  case '8':
    /* or(not(A),B) */
    num = (hexToInt(op[0]) ^ 15) | hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case '9':
    /* not(xor(A,B)) */
    num = (hexToInt(op[0]) ^ hexToInt(op[1])) ^ 15;
    setW(num);
    writeOut(num);
    break;
  
  case 'A':
    /* saida = B */
    num = hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case 'B':
    /* and(A,B) */
    num = hexToInt(op[0]) & hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case 'C':
    /* saida = 1111 */
    setW(15);
    writeOut(15);
    break;
  
  case 'D':
    /* or(A,not(B)) */
    num =  hexToInt(op[0]) | (hexToInt(op[1]) ^ 15);
    setW(num);
    writeOut(num);
    break;
  
  case 'E':
    /* or(A,B) */
    num = hexToInt(op[0]) | hexToInt(op[1]);
    setW(num);
    writeOut(num);
    break;
  
  case 'F':
    /* saida = A */
    num = hexToInt(op[0]);
    setW(num);
    writeOut(num);
    break;

  }
}

void setW(int n){
  if(n >= 0 && n <= 9){
    memory[1] = (char)(n + 48);
  }
  else{
    memory[1] = (char)(n + 55);
  }
}
