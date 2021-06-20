package com;

import java.util.concurrent.Semaphore;

public class Recursos extends Thread {

    private final ListaEspera listaEspera;
    public String centro;
    public String vacunador;
    public String vacuna;
    public Semaphore sem1 = new Semaphore(0);//Empieza Trancado
    public Semaphore sem2 = new Semaphore(0);//Empieza Trancado
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
                sem1.acquire(); // Tomo sem1, para pausar los recursos

                usuario = listaEspera.getAgendado(); //Obtengo agendado por prioridad y despues por FCFS
                if(usuario != null) { // Si la agenda no estaba vacia
                    Main.imprimir("El centro: "+this.centro+" esta disponible, con el vacunador: "+this.vacunador+
                            " y la vacuna: "+this.vacuna + ". Se vacuna a el usuario: "+ usuario+ " En el momento "+ Reloj.getTiempo());
                }

                sem2.release(); //le avisa al reloj que termino
            } catch (Exception e) {
        }
        }
    }

    public void reanudarRecurso() { // Libero sem1 para reanudar los recursos
        sem1.release();
    }

    public void terminoEjecucionRecurso() {
       fin = true;
    }

    public void pausarRecurso() { //Tomo sem2 para pausar los recursos
        try {
            sem2.acquire();
        }
        catch (Exception e) {}
    }
}