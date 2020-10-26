package servidortarea2;

import servidortarea1.*;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Base64;


public class MultiServerThread extends Thread {
   private Socket socket = null;
   public static PrintWriter escritor;
   public static String analizar;
   
   private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    private static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
   
   
   public MultiServerThread(Socket socket) throws IOException {
      super("MultiServerThread");
      this.socket = socket;
      ServerMultiClient.NoClients++;
      escritor = new PrintWriter(socket.getOutputStream(), true);
   }
      public MultiServerThread(String palabra) throws FileNotFoundException {
      super("MultiServerThread");
      this.socket = socket;
      ServerMultiClient.NoClients++;
      escritor = new PrintWriter(palabra);
   }
// #302|3|hola|como|estas&
// #c3c|3|hola|como|estas&
   
// #cry|2|llave|cadena&
// #cry|1|cadena&
// #R-cry|1|cadenaencriptada&
      
      
      
       public void run() {

      try {
         //PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String lineIn, lineOut;
             while((lineIn = entrada.readLine()) != null){
                System.out.println("Received: "+lineIn);
                escritor.flush();
                System.out.println("Funcionando");
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
                        case "c3c":
                            int l = 0;
                            char cadM[] = lineIn.toCharArray();
                            if(String.valueOf(lineIn.charAt(5)).equals("3")){
                                int j = 0;
                                for(int i = 7; i <= (cadM.length - 2); i++){
                                    if(cadM[i] == '|'){
                                        p[j] = 32;
                                        l++;
                                    }else if(cadM[i] != '|'){
                                        p[j] = cadM[i];
                                    }
                                    j++;
                                }
                            }
                            if(l == 2){
                                escritor.println("#R-c3c|1|" + String.valueOf(p)+ "&");
                                escritor.flush();
                            }else{
                                escritor.println("Echo... "+lineIn);
                                escritor.flush();
                            }
                            break;
                        case "fbp":
                            //fb publicar
                            break;
                        case "icw":
                            //info clima
                            break;
                        case "p4p":
                            //publicar digito con autentificacion a dos pasos
                            break;
                        case "cad":
                            //conversion de divisas
                            break;
                        case "ccc":
                            escritor.println("ccc... "+lineIn);
                            escritor.flush();
                            break;
                        default:
                            escritor.println("buscando servicio: "+ idMs );
                            /*DirS directorio = new DirS();
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
                            }*/
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


      
    