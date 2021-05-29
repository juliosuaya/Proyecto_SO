package com;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
    public static Semaphore imprimir = new Semaphore(1);
    public static void main(String[] args) {


        ListaEspera listaDeEspera= new ListaEspera();
        Recursos[] recursos = {
                new Recursos("Española","Juan carlos","Sinovac", listaDeEspera),
                new Recursos("Hospital de Clinicas","Pedro Martinez","Pfizer", listaDeEspera),
                new Recursos("Britanico","Jose Martinez","Pfizer", listaDeEspera)};
        Agenda agenda1 = new Agenda(listaDeEspera);
        Reloj rel = new Reloj(recursos, listaDeEspera, agenda1);

        listaDeEspera.start();
        agenda1.start();
        for (Recursos recurso : recursos) {
            recurso.start();
        }
        rel.start();
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
