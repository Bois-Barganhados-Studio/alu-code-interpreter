import processing.serial.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JOptionPane;

private String portName, resp = "", code, dump = "";
private Serial myPort;
private BufferedReader buff,buffConfs;
private FileReader fileReader,readerConfs;
private boolean sended = false;
private int totdumps = 0;

void setup(){
  size(600,600);
  try{
     //buscando configurações iniciais
    readerConfs = new FileReader(new File("ini.cfg"));
    //buffConfs = new BufferedReader(fileReader);
    buffConfs = new BufferedReader(readerConfs);
    portName = buffConfs.readLine().replace("port=","").trim();
    buffConfs.close();
    readerConfs.close();
    //iniciando leitura do arquivo hex
    fileReader = new FileReader(new File(args[0]));
    buff = new BufferedReader(fileReader);
    myPort = new Serial(this,portName, 9600);
  }catch(IOException io){
    System.err.println(io.getMessage());
         System.exit(0);
  }catch(Exception e){
    JOptionPane.showMessageDialog(null, "Porta não localizada no sistema: "+ e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
         e.printStackTrace();
         System.exit(0);
  }
}

void draw(){
  background(0,160,200);
  fill(20,20,20);
  rect(0,0,600,80);
  fill(255,255,255);
  textSize(24);
  text("DUMP",280,50);
  fill(0,0,0);
  line(0,81,600,81);
  stroke(0);
  try{
    Thread.sleep(1000);
  }catch(Exception e){
    System.exit(0);
  }
  //myPort.write("Debug");
  try{
    if(!sended){
      code = buff.readLine();
     if(code!=null){
       resp += code + " ";
     }else{
       resp += "\n";
       myPort.write(resp);
       JOptionPane.showMessageDialog(null, "Dados enviados com sucesso para o arduino\n Verifique as respostas no sistema físico.", "Transmissão completa", JOptionPane.INFORMATION_MESSAGE);
       sended = true;
     }
   }else{
      try{
         Thread.sleep(100);
       }catch(Exception e){
         JOptionPane.showMessageDialog(null, "Falha ao realizar o dump de memoria.\nErro: "+ e.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
         System.exit(0);
       }
       if ( myPort.available() > 0) 
       { 
         String textReceived = myPort.readStringUntil('\n');
         if(textReceived != null && textReceived.length() >0 && textReceived.charAt(0) == '-'){
           if(totdumps >= 8){
             dump = dump.substring(textReceived.length(),dump.length());
           }
           dump += textReceived + "\n";
           totdumps++;
         }
       }
       textSize(13);
       text(dump,70,120);
       fill(0,0,0);
     }
  }catch(IOException io){
   JOptionPane.showMessageDialog(null, io.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
  }catch(Exception e){
    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
         System.exit(0);
  }
}
