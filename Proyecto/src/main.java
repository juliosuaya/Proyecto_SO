/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication46;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
/**
 *
 * @author estudiante.fit
 */
public class JavaApplication46 {
    public static Semaphore semComunicacion = new Semaphore(0);
    public static Semaphore semAgenda = new Semaphore(0);
    public static Semaphore semVacunandose = new Semaphore(0);
    public static Semaphore semListaDeEspera = new Semaphore(0);
    public static Semaphore semRecursos = new Semaphore(0);
    public static Semaphore semVacunacion = new Semaphore(0);
    public static Semaphore semAgendandose = new Semaphore(0);

    public static void main(String[] args) {
        
        Usuario usuario1 = new Usuario("42312", "Juan", "Perez", "LB", 23);
        LinkedList<Usuario> linkedEspera = new LinkedList<>();
        
        ListaDeEspera listaDeEspera= new ListaDeEspera(linkedEspera);
        
        Agenda agenda1 = new Agenda(listaDeEspera, usuario1);
        Recursos recursos = new Recursos("Hospital de Clinicas","Pedro Martinez","Pfizer");
        Comunicacion comunicacion = new Comunicacion(usuario1);
        Vacunacion vacunacion = new Vacunacion(usuario1,listaDeEspera);

        
        usuario1.start();
        listaDeEspera.start();
        agenda1.start();
        recursos.start();
        comunicacion.start();
        vacunacion.start();
        
        
        
        
    }


    public static class Usuario extends Thread {

        private String cedula;
        private String nombre;
        private String apellido;
        private String barrio;
        private int edad;
        public String mensaje;
        public boolean vacunado;

        public Usuario(String cedula, String nombre, String apellido, String barrio, int edad) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.barrio = barrio;
            this.edad = edad;
            this.vacunado=false;
            this.mensaje="";
        }
        
        
        public String getNombre(){
            return this.nombre + " " +this.apellido;
        }

        @Override
        public void run() {
            //while(true) {
                try {
                    semAgenda.release();
                    System.out.println("El usuario: "+this.getNombre()+ " esta iniciando el proceso");
                    semVacunandose.acquire();
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            //}
        }

    }


    public static class Agenda extends Thread{
//toma los datos del usuario y lo mete en la lista de espera
        public Usuario usuario;
        public ListaDeEspera listaEspera;
        
        public Agenda(ListaDeEspera listaEspera, Usuario usuario){
            this.listaEspera=listaEspera;
            this.usuario=usuario;
        }
        
        @Override
        public void run() {
            //while(true) {
                try {
                    semAgenda.acquire(); // Espero por usuario para poder entrar
                    //con los datos le doy una prioridad
                    this.listaEspera.listaEspera.add(usuario);
                    System.out.println("El usuario: "+this.usuario.getNombre()+ " fue agregado a la lista de espera");
                    semListaDeEspera.release(); // una vez obtenida la prioridad permito entrar a ListaDeEspera()
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            //}
        }
    }




    public static class ListaDeEspera extends Thread {
//Gestiona la lista (es unica)
        public LinkedList<Usuario> listaEspera = new LinkedList();
        
        public ListaDeEspera(LinkedList<Usuario> listaEspera){
            this.listaEspera=listaEspera;
        }

        @Override
        public void run() {
            //while(true) {
                try {
                    semListaDeEspera.acquire();
                    System.out.println("lista de espera se fija si hay recursos");
                    semRecursos.release();
                    

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //}
        }

    }

    public static class Recursos extends Thread {
        
        public String centro;
        public String vacunador;
        public String vacuna;
        
        public Recursos(String centro, String vacunador, String vacuna){
            this.centro=centro;
            this.vacunador=vacunador;
            this.vacuna=vacuna;
        }

        @Override
        public void run() {
            //while(true) {
                try {
                    semRecursos.acquire();
                    System.out.println("El centro: "+this.centro+" esta disponible, con el vacunador: "+this.vacunador+" y la vacuna: "+this.vacuna);
                    semComunicacion.release();
                    
                } catch (Exception e) {
                }
                     //Espero a que se agende a la lista
            //}
        }

    }


    public static class Comunicacion extends Thread {
        
        public Usuario usuario;
        
        public Comunicacion(Usuario usuario){
            this.usuario=usuario;
        }

        @Override
        public void run() {
            //while(true) {
                try {
                    semComunicacion.acquire(); // Espero a que hayan recursos
                    this.usuario.mensaje="Vaya a vacunarse a ";
                    System.out.println("Se comunica al usuario que puede ir a vacunarse");
                    semVacunacion.release();                  
                             
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //}
        }
    }


    public static class Vacunacion extends Thread {
        public Usuario usuario;
        public ListaDeEspera listaDeEspera;
       public Vacunacion (Usuario usuario, ListaDeEspera listaDeEspera){
           this.usuario=usuario;
           this.listaDeEspera=listaDeEspera;
       }

        @Override
        public void run() {
            //while(true) {
                try {
                    semVacunacion.acquire(); //Espero a que se le asigne fecha y lugar a un usuario para vacunarse
                    this.usuario.vacunado=true;
                    System.out.println("El usuario: "+this.usuario.getNombre()+ " fue vacunado");
                    this.listaDeEspera.listaEspera.remove(this.usuario);
                    semVacunandose.release(); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            //}
        }

    }
    
}
