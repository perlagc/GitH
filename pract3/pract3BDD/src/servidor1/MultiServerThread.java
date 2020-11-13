package servidor1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.Base64;
import java.util.Random;
import java.lang.annotation.Native;


public class MultiServerThread extends Thread {
   private Socket socket = null;
   private int c;

   private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    private static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
   
    private String info(char cad[], int in){
        char idM[] = new char[30];
        c = 0;
        for( int i = in; i <= (cad.length - 2); i++){
            if(cad[i] == '#'){
                break;
            }else if(cad[i] != '#'){
                idM[c] = cad[i];
                c++;
            }
        }
        String idMs = String.copyValueOf(idM).trim();
        return idMs;
    }
   private String infocad(char cad[], int in, int num){
        char p[] = new char[40];
        int parametro = 0;
        c = 0;
        for(int i = in; i <= (cad.length - 2); i++){
            if(cad[i] == '#'){
                p[c] = 32;
                parametro++;
            }else if(cad[i] != '#'){
                p[c] = cad[i];
                c++;
            }
            if(parametro == num){break;}
        }      
        String idMs = String.copyValueOf(p).trim();
        return idMs;
    }
   public MultiServerThread(Socket socket) {
      super("MultiServerThread");
      this.socket = socket;
      ServerMultiClient.NoClients++;
   }
   
//Autor: P. ARIADNA GALINDO C.
   
//#comando#númerodeparámetros#parámetro1#parámetro2#...#parámetron#
   
//#noc#-#-#                                                                      convierte una cadena a mayúsculas
   //#R-noc#ServerMultiClient.NoClients#
//#may#númerodeparámetros#parámetro1#parámetro2#...#parámetron#             convierte una cadena a mayúsculas
   //#R-may#1#HOLA#
//#min#númerodeparámetros#parámetro1#parámetro2#...#parámetron#             min: convierte una cadena a minúsculas
   //#min#1#hola#
//#inv#númerodeparámetros#parámetro1#parámetro2#...#parámetron#             inv: invierte una cadena
   //#inv#1#ALOH#
//#len#númerodeparámetros#parámetro1#parámetro2#...#parámetron#             len: cuenta el número de caracteres de una cadena
   //#len#1#4#
//#ale#númerodeparámetros#parámetro1#parámetro2#...#parámetron#            alea: genera un número aleatorio entre 0 y el parámetro enviado
   //#R-ale#1#13#
   

   
      public void run() {

          
      try {
         PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String lineIn, lineOut;
             while((lineIn = entrada.readLine()) != null){
                System.out.println("Received: "+lineIn);
                escritor.flush();
                if(lineIn.equals("FIN")){
                    ServerMultiClient.NoClients--;
                    break;
		}else{
                    if((String.valueOf(lineIn.charAt(0)).equals("#")) && (String.valueOf(lineIn.charAt(lineIn.length()-1)).equals("#"))  ){
                        char cad[] = lineIn.toCharArray();         //mensaje de cliente
                        String p;           //mensaje tipo char[]
                        int position = 0;
                        String idMs = info (cad, 1);                //servicio
                        String numv = info(cad, (c+2));
                        int num = Integer.parseInt (String.valueOf(numv));      //no. parametros
                        
                        //escritor.println("buscando servicio: "+ idMs );
                        DirS directorio = new DirS();
                        String[] servidor = directorio.buscarServidor(idMs);
                        //escritor.println(servidor[0] + "... " + servidor[1]);
                        if(servidor[1].compareTo("12345") == 0){
                            position = idMs.length() + numv.length() + 3;
                            switch(idMs){
                                case "cuantos":
                                    escritor.println("#R-cuantos#" + ServerMultiClient.NoClients+ "#");
                                    escritor.flush();
                                    break;
                                case "may":
                                    p = infocad(cad, position, num);
                                    escritor.println("#R-may#" + num+ "#" + p.toUpperCase()+ "#");
                                    escritor.flush();
                                    break;
                                case "min":
                                    p = infocad(cad, position, num);
                                    escritor.println("#R-min#" + num+ "#" + p.toLowerCase()+ "#");
                                    escritor.flush();
                                    break;
                                case "inv":
                                    String invertida = "";
                                    p = infocad(cad, position, num);
                                    for (int indice = p.length() - 1; indice >= 0; indice--) {
                                        invertida += p.charAt(indice);
                                    }
                                    escritor.println("#R-inv#" + num+ "#" + invertida + "#");
                                    escritor.flush();
                                    break;
                                case "len":
                                    p = infocad(cad, position, num);
                                    escritor.println("#R-len#" + num+ "#" + p.length() + "#");
                                    escritor.flush();
                                    break;
                                case "alea":
                                    p = info(cad, position);
                                    int nAle = num = Integer.parseInt (String.valueOf(p));
                                    Random aleatorio = new Random(System.currentTimeMillis());
                                    int intAletorio = aleatorio.nextInt(nAle);
                                    
                                    escritor.println("#R-alea#" + num+ "#" + intAletorio + "#");
                                    break;
                                /*case "num2text":
                                    p = info(cad, position);
                                    escritor.println("#R-num2text#" + num+ "#" + p + "#");
                                    break;
                                case "concat":
                                    p = infocad(cad, position, num);
                                    escritor.println("#R-concat#" + num+ "#" + p + "#");
                                    break;
                                case "crypt":
                                    p = infocad(cad, position, num);
                                    p = encriptar(p);
                                    escritor.println("#R-crypt#" + num+ "#" + p + "#");
                                    break;
                                case "decrypt":
                                    p = infocad(cad, position, num);
                                    p = desencriptar(p);
                                    escritor.println("#R-decrypt#" + num+ "#" + p + "#");
                                    break;
                                case "suma_0_n":
                                    p = info(cad, position);
                                    int nTot = 0;
                                    int nSum = Integer.parseInt (String.valueOf(p));
                                    for(int i = 0; i <= nSum;i++){
                                        nTot = i + nTot;
                                    }
                                    escritor.println("#R-suma_0_n#" + num+ "#" + nTot + "#");
                                    break;*/
                            }
                        }else if(servidor != null){
                            //escritor.println("Client FullDuplex... ");
                            ClientFullDuplex clienteN = new ClientFullDuplex(servidor[0], servidor[1]);
                            clienteN.conectarServidor(lineIn);
                            escritor.println(servidor[0]+"... "+servidor[1]);
                            escritor.flush();
                        }else{
                            escritor.println("Echo... "+lineIn);
                            escritor.flush();
                        }
                    }        
                }
             }
         try{		
            entrada.close();
            escritor.close();
            socket.close();
         }catch(Exception e){ 
            System.out.println ("Error : " + e.toString()); 
            socket.close();
            System.exit (0); 
   	   } 
      }catch (IOException e) {
         e.printStackTrace();
      }
   }
   }

