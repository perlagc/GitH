/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor2;
import servidor1.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
/**
 *
 * @author hsmha
 */

public class DirS {
    private Map<String, String> directorio = new HashMap<>();
    private Map<String, String> servidorA = new HashMap<>();
    String[] datos = new String[2];
    
    
    public void leerDirectorio(String archivo, Map var) throws IOException{
        var.clear();
        String delimiter = ";";
        try(Stream<String> lines = Files.lines(Paths.get(archivo))){
            lines.filter(line -> line.contains(delimiter)).forEach(line -> var.putIfAbsent(line.split(delimiter)[0], line.split(delimiter)[1]));
        }
        //System.out.println(var);  
    }
    public String[] buscarServidor(String servicio) throws IOException{
        leerDirectorio("D:/Documentos/GitHub/GitH/servidores.txt", directorio);
        Iterator it = directorio.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();  
            leerDirectorio(e.getValue().toString(), servidorA);
            //System.out.println(servidorA);
            Iterator it2 = servidorA.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry a = (Map.Entry)it2.next();
                if(a.getKey().toString().compareTo(servicio)==0){
                    datos[0] = e.getKey().toString();
                    datos[1] = a.getValue().toString();
                    //System.out.println(datos[0]+"... "+datos[1]);
                    return datos;
                }
            }
        }
        return null;
    }
}
