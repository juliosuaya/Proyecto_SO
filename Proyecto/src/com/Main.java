package com;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
    public static Semaphore imprimir = new Semaphore(1);
    public static void main(String[] args) {


        ListaEspera listaDeEspera= new ListaEspera();
        Recursos[] recursos = { // Se crea lista de recursos
                new Recursos("Española","Juan carlos","Sinovac", listaDeEspera),
                new Recursos("Hospital de Clinicas","Pedro Martinez","Pfizer", listaDeEspera),
                new Recursos("Britanico","Jose Martinez","Pfizer", listaDeEspera)};
        Agenda agenda1 = new Agenda(listaDeEspera); //Se instancia agenda con una lista de espera
        Reloj rel = new Reloj(recursos, listaDeEspera, agenda1); // Se instancia Reloj con los recursos, la lista de espera y la agenda

        listaDeEspera.start(); //Corro el hilo de lista de espera
        agenda1.start(); // Corro el hilo de Agenda
        for (Recursos recurso : recursos) { // Corro los hilos de recursos
            recurso.start();
        }
        rel.start(); // Corro hilo del reloj
    }
    public static void imprimir(String s){
        try {
            imprimir.acquire();
            System.out.println(s);
            imprimir.release();
        }
        catch (Exception e) {}
    }
}
