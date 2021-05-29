package com;

import java.util.concurrent.Semaphore;

public class Recursos extends Thread {

    private final ListaEspera listaEspera;
    public String centro;
    public String vacunador;
    public String vacuna;
    public Semaphore sem1 = new Semaphore(0);
    public Semaphore sem2 = new Semaphore(0);
    public Usuario usuario= null;
    private boolean fin = false;

    public Recursos(String centro, String vacunador, String vacuna, ListaEspera listaEspera){
        this.centro = centro;
        this.vacunador = vacunador;
        this.vacuna = vacuna;
        this.listaEspera = listaEspera;
    }

    @Override
    public void run() {
        while(!this.fin) {
            try {
                sem1.acquire();

                usuario = listaEspera.getAgendado();
                if(usuario != null) {
                    Main.imprimir.acquire();
                    System.out.println("El centro: "+this.centro+" esta disponible, con el vacunador: "+this.vacunador+" y la vacuna: "+this.vacuna);
                    System.out.println("Se vacuna a el usuario: "+ usuario+ " En el momento "+ Reloj.getTiempo());
                    Main.imprimir.release();
                }

                sem2.release();

            } catch (Exception e) {
        }
        //Espero a que se agende a la lista
        }
    }

    public void reanudarRecurso() {
        sem1.release();
    }

    public void terminoEjecucionRecurso() {
       fin = true;
    }

    public void pausarRecurso() {
        try {
            sem2.acquire();
        }
        catch (Exception e) {}
    }
}