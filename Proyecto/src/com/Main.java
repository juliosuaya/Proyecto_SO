package com;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
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
        
        ListaEspera listaDeEspera= new ListaEspera();
        
        Agenda agenda1 = new Agenda(listaDeEspera, usuario1); // un hilo de agenda por cada usuario?
        Recursos recursos = new Recursos("Hospital de Clinicas","Pedro Martinez","Pfizer");
        Comunicacion comunicacion = new Comunicacion(usuario1); // un hilo de agenda por cada usuario?
        Vacunacion vacunacion = new Vacunacion(usuario1,listaDeEspera);

        usuario1.start();
        listaDeEspera.start();
        agenda1.start();
        recursos.start();
        comunicacion.start();
        vacunacion.start();
    }
}
