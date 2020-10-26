package servidortarea1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.Base64;
import java.util.Random;
import java.lang.annotation.Native;


public class MultiServerThread extends Thread {
   private Socket socket = null;

   private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    private static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
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
                    
		}else if((String.valueOf(lineIn.charAt(0)).equals("#")) && (String.valueOf(lineIn.charAt(4)).equals("#")) && (String.valueOf(lineIn.charAt(6)).equals("#")) && (String.valueOf(lineIn.charAt(lineIn.length()-1)).equals("#"))  ){
                    char idM[] = {lineIn.charAt(1), lineIn.charAt(2), lineIn.charAt(3)};
                    String idMs = String.copyValueOf(idM);
                    
                            char cad[] = lineIn.toCharArray();         //mensaje de cliente
                            char p[] = new char[cad.length];           //mensaje tipo char[]
                            int num = Integer.parseInt (String.valueOf(lineIn.charAt(5)));
                            int parametro;
                            int c;
                    
                    switch(idMs){
                        case "noc":
                            escritor.println("#R-noc#" + ServerMultiClient.NoClients+ "#");
                            escritor.flush();
                            break;
                        case "may":
                            parametro = 0;
                            c = 0;
                            for(int i = 7; i <= (cad.length - 2); i++){
                                if(cad[i] == '#'){
                                    p[c] = 32;
                                    parametro++;
                                }else if(cad[i] != '#'){
                                    p[c] = cad[i];
                                }
                                c++;
                                if(parametro == num){break;}
                            }      
                            escritor.println("#R-may#" + num+ "#" + String.valueOf(p).toUpperCase()+ "#");
                            escritor.flush();
                            break;
                        case "min":
                            parametro = 0;
                            c = 0;
                            for(int i = 7; i <= (cad.length - 2); i++){
                                if(cad[i] == '#'){
                                    p[c] = 32;
                                    parametro++;
                                }else if(cad[i] != '#'){
                                    p[c] = cad[i];
                                }
                                c++;
                                if(parametro == num){break;}
                            }      
                            escritor.println("#R-min#" + num+ "#" + String.valueOf(p).toLowerCase()+ "#");
                            escritor.flush();
                            break;
                        case "inv":
                            String invertida = "";
                            parametro = 0;
                            c = 0;
                            for(int i = 7; i <= (cad.length - 2); i++){
                                if(cad[i] == '#'){
                                    p[c] = 32;
                                    parametro++;
                                }else if(cad[i] != '#'){
                                    p[c] = cad[i];
                                }
                                c++;
                                if(parametro == num){break;}
                            }      
                            for (int indice = String.valueOf(p).length() - 1; indice >= 0; indice--) {
                                invertida += String.valueOf(p).charAt(indice);
                            }
                            escritor.println("#R-inv#" + num+ "#" + invertida + "#");
                            escritor.flush();
                            break;
                        case "len":
                            parametro = 0;
                            c = 0;
                            for(int i = 7; i <= (cad.length - 2); i++){
                                if(cad[i] == '#'){
                                    p[c] = 32;
                                    parametro++;
                                }else if(cad[i] != '#'){
                                    p[c] = cad[i];
                                }
                                c++;
                                if(parametro == num){break;}
                            }      
                            
                            escritor.println("#R-len#" + num+ "#" + c + "#");
                            escritor.flush();
                            break;
                        case "ale":
                            int valor=0;
                            Random rand = new Random();
                            c = 0;
                            for(int i = 7; i <= (cad.length - 2); i++){
                                if(cad[i] == '#'){
                                    break;
                                }else if(cad[i] != '#'){
                                    p[c] = cad[i];
                                    //escritor.println(p[c]);
                                    //valor = (p[c]-48)*(c+1)+valor;
                                }
                                c++;
                            }
                            
                        //String number = String.valueOf(p);
                        //int h=Integer.parseInt(number);
                        //int h=Integer.valueOf(number);
                            char j[] = {'1', '7'};
                            escritor.println(Integer.parseUnsignedInt(String.copyValueOf(p)));
                            //escritor.println("#R-ale#" + num + "#" + Math.random()*num + "#");
                            escritor.flush();
                            break;
                        default:
                            escritor.println("buscando servicio: "+ idMs );
                            DirS directorio = new DirS();
                            String[] servidor = directorio.buscarServidor(idMs);
                            escritor.println(servidor[0]+"... "+servidor[1]);
                            if(servidor != null){
                                escritor.println("Client FullDuplex... ");
                                ClientFullDuplex clienteN = new ClientFullDuplex(servidor[0], servidor[1]);
                                clienteN.conectarServidor(lineIn);
                                escritor.println(servidor[0]+"... "+servidor[1]);
                                escritor.flush();
                            }else{
                                escritor.println("Echo... "+lineIn);
                                escritor.flush();
                            }
                            break;
                    }
                }else{
                escritor.println("Echo...... "+lineIn);
                escritor.flush();
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

