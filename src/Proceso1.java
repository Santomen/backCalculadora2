import com.example.calculadoracliente.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class Proceso1 extends Thread {
    int puerto_e;
    int puerto_r;

    @Override // para usar polimorfism
    public void run(){
        //Server socket pondra a la app a la escucha de un puerto
        boolean ban=true;
        try{
            ServerSocket servidor=new ServerSocket(15002);
            //ahora que acepte cualquier conexion que venga del exterior con el metodo accept

            while(ban){
                Socket misocket=servidor.accept();//aceptara las conexiones que vengan del exterior
                ObjectInputStream flujo_entrada=new ObjectInputStream(misocket.getInputStream());
                Paquete mensaje=(Paquete)flujo_entrada.readObject();
                flujo_entrada.close();
                //dentro de la funcion calculo se asigna el codigo 1 que indica que se manda un mensaje que se puede mostrar en pantalla
               if(mensaje.getCodigo()==0){
                   System.out.println(mensaje);
                   //Reenviar informacion a un cierto puerto
                   //Socket enviaReceptor=new Socket("127.0.0.1",puerto_middle);
                   Socket enviaReceptor=new Socket("127.0.0.1",14504);
                   ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
                   paqueteReenvio.writeObject(calculo(mensaje));
                   paqueteReenvio.close();
                   enviaReceptor.close();
                   misocket.close();
               }
                else{
                   System.out.println("Mnesaje que el servidor no puede procesar");
               }
            }

        }
        catch(IOException e){
            System.out.println(e);

        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    public void puertos(int emisor,int receptor){
        this.puerto_e=emisor;
        this.puerto_r=receptor;
    }
    public Paquete calculo(Paquete datos){
        datos.setCodigo(1);
        String cadena=datos.getCadena();
        float suma=0f,resta=0f,div=0f,mul=0f;
        float n1=0f,n2=0f;
        char operacion='a';
        int cont=0;
        boolean ban=true;
        while(ban){
            if(cadena.charAt(cont)=='/'|cadena.charAt(cont)=='-'|cadena.charAt(cont)=='+'|cadena.charAt(cont)=='*'){
                operacion=cadena.charAt(cont);
                n1=Float.parseFloat(cadena.substring(0,cont));
                ban=false;

            }else{
                System.out.println(cadena.substring(cont,cont+1));
                cont=cont+1;
                if(cont==cadena.length()){
                    ban=false;
                }
            }
        }
        n2=Float.parseFloat(cadena.substring(cont+1,cadena.length()));
        System.out.println("n1: "+n1+" n2:"+n2);
        System.out.println(operacion);
        switch(operacion) {
            case '+':
                suma=n1+n2;
                System.out.println("Suma "+suma);
                datos.setCadena(Float.toString(suma));
                return datos;

            case '-':
                resta=n1-n2;
                System.out.println("Resta "+resta);
                datos.setCadena(Float.toString(resta));
                return datos;

            case '*':
                mul=n1*n2;
                System.out.println("Multiplicacion "+mul);
                datos.setCadena(Float.toString(mul));
                return datos;

            case '/':
                div=n1/n2;
                System.out.println("Division "+div);
                datos.setCadena(Float.toString(div));
                return datos;

            default:
                System.out.println("Habitación 5");
                datos.setCadena(Float.toString(0.0f));
                return datos;

        }

    }

}
