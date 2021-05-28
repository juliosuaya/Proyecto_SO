package com;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {

        ListaEspera listaDeEspera= new ListaEspera();
        Recursos[] recursos = { new Recursos("Hospital de Clinicas","Pedro Martinez","Pfizer", listaDeEspera),
                new Recursos("Española","Carlos Martinez","Pfizer", listaDeEspera),
                new Recursos("Britanico","Jose Martinez","Pfizer", listaDeEspera)};
        Agenda agenda1 = new Agenda(listaDeEspera);
        Reloj rel = new Reloj(recursos, listaDeEspera);

        listaDeEspera.start();
        agenda1.start();
        for (Recursos recurso : recursos) {
            recurso.start();
        }
        rel.start();
    }
}
