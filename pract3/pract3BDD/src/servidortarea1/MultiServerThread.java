package servidortarea1;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Base64;


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
// #302|3|hola|como|estas&
// #c3c|3|hola|como|estas&
   
// #cry|2|llave|cadena&
// #cry|1|cadena&
// #R-cry|1|cadenaencriptada&
   
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
                    
		}else if((String.valueOf(lineIn.charAt(0)).equals("#")) && (String.valueOf(lineIn.charAt(4)).equals("|")) && (String.valueOf(lineIn.charAt(6)).equals("|")) && (String.valueOf(lineIn.charAt(lineIn.length()-1)).equals("&"))  ){
                    char idM[] = {lineIn.charAt(1), lineIn.charAt(2), lineIn.charAt(3)};
                    String idMs = String.copyValueOf(idM);
                    switch(idMs){
                        case "c3c":
                            int l = 0;
                            char cadM[] = lineIn.toCharArray();
                            char p[] = new char[cadM.length];
                            if(String.valueOf(lineIn.charAt(5)).equals("3")){
                                int c = 0;
                                for(int i = 7; i <= (cadM.length - 2); i++){
                                    if(cadM[i] == '|'){
                                        p[c] = 32;
                                        l++;
                                    }else if(cadM[i] != '|'){
                                        p[c] = cadM[i];
                                    }
                                    c++;
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
                        case "cry":
                            int ll = 0;
                            char cad[] = lineIn.toCharArray();
                            char pm[] = new char[cad.length];
                            if(String.valueOf(lineIn.charAt(5)).equals("1")){
                                int c = 0;
                                for(int i = 7; i <= (cad.length - 2); i++){
                                    if(cad[i] == '|'){
                                        ll++;
                                    }else if(cad[i] != '|'){
                                        pm[c] = cad[i];
                                    }
                                    c++;
                                }
                            }
                            
                            //cadenaEncriptada = encriptar(cadenaDeTexto);
                            //String cadenaDesencriptada = desencriptar(cadenaEncriptada);
                            
                            if(ll == 0){
                                escritor.println("#R-cry|1|" + encriptar(String.valueOf(pm))+ "&");
                                escritor.flush();
                            }else{
                                escritor.println("Echo... "+lineIn);
                                escritor.flush();
                            }
                            break;
                        default:
                            escritor.println("Echo... "+lineIn);
                            escritor.flush();
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

