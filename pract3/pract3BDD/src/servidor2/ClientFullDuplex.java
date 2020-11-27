package servidor2;
import java.net.*; 
import java.io.*; 

public class ClientFullDuplex { 

    private static String ip;
    private static int puertoS;
    private static String DatosEnviados = null; 
    private static String maquina; 
    private static int puerto; 
    private static String line;
    
    public ClientFullDuplex(){
        ip = "localhost";
        puertoS = Integer.parseInt("13777");
        
    }
    public ClientFullDuplex(String ipS, String puerto, String lineIn){
        ip = "localhost";
        puertoS = Integer.parseInt(puerto);
        DatosEnviados = lineIn; 
    }
	public static void main (String[] argumentos)throws IOException{ 
		Socket cliente = null; 
		PrintWriter escritor = null; 
		BufferedReader entrada=null;
		
		String maquina; 
		int puerto; 
		BufferedReader DatosTeclado = new BufferedReader ( new InputStreamReader (System.in)); 

		if (argumentos.length != 2){ 
			maquina = "localhost"; 
			puerto = 13777; 
			System.out.println ("Establecidos valores por defecto:\nEQUIPO = localhost\nPORT = 13777"); 
		} 
		else{ 
			maquina = argumentos[0]; 
			Integer pasarela = new Integer (argumentos[1]); 
			puerto = pasarela.parseInt(pasarela.toString()); 
			System.out.println ("Conectado a " + maquina + " en puerto: " + puerto); 
		} 
		try{ 
			cliente = new Socket (maquina,puerto); 
		}catch (Exception e){ 
			System.out.println ("Fallo : "+ e.toString()); 
			System.exit (0); 
		}
		try{ 
			escritor = new PrintWriter(cliente.getOutputStream(), true);
			entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
 
		}catch (Exception e){ 
			System.out.println ("Fallo : "+ e.toString()); 
			cliente.close(); 
			System.exit (0); 
		} 
		
		
		System.out.println("Conectado con el Servidor. Listo para enviar datos...");
		
		do{ 
                        
			DatosEnviados = DatosTeclado.readLine(); 
			escritor.println (DatosEnviados); 
			line = entrada.readLine();
			System.out.println(line);
		}while (!DatosEnviados.equals("FIN")); 
                
	
		System.out.println ("Finalizada conexion con el servidor"); 
		try{ 
			escritor.close(); 
		}catch (Exception e){}
	}

        
          public void conectarServidor(String trama){
                Socket cliente = null; 
		PrintWriter escritor = null; 
                //System.out.println ("Conectado a " + maquina + " en puerto: " + puerto); 
		
		try{ 
			cliente = new Socket (ip,puertoS); 
		}catch (Exception e){ 
			System.out.println ("Fallo : "+ e.toString()); 
			System.exit (0); 
		}
            
			
		/*try{ 
			escritor = new PrintWriter(cliente.getOutputStream(), true);
 
		}catch (Exception e){ 
			System.out.println ("Fallo : "+ e.toString()); 
			//cliente.close(); 
			System.exit (0); 
		} 
                */
		System.out.println("Conectado con el Servidor " + ip + " en puerto: " + puertoS);
		DatosEnviados = trama; 
                line = trama;
                
                escritor.println (DatosEnviados); 
                
		System.out.println(line);
                        
		DatosEnviados = "FIN"; 
	
		System.out.println ("Finalizada conexion con el servidor"); 
		try{ 
			escritor.close(); 
		}catch (Exception e){}
        
          }            
                
                
        
} 