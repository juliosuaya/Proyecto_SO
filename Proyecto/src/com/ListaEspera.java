package com;

import java.util.LinkedList;

public class ListaEspera extends Thread {
    //Gestiona la lista (es unica)
    public LinkedList<Usuario> listaEspera = new LinkedList();

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semListaDeEspera.acquire();
            System.out.println("lista de espera se fija si hay recursos");
            Main.semRecursos.release();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //}
    }

}