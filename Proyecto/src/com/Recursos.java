package com;

import java.util.concurrent.Semaphore;

public class Recursos extends Thread {

    private final ListaEspera listaEspera;
    public String centro;
    public String vacunador;
    public String vacuna;
    public Semaphore sem = new Semaphore(0);
    public Usuario usuario= null;

    public Recursos(String centro, String vacunador, String vacuna, ListaEspera listaEspera){
        this.centro = centro;
        this.vacunador = vacunador;
        this.vacuna = vacuna;
        this.listaEspera = listaEspera;
    }

    @Override
    public void run() {
        while(true) {
        try {
            sem.acquire();

            usuario = listaEspera.getAgendado();
            if(usuario != null) {
                System.out.println("El centro: "+this.centro+" esta disponible, con el vacunador: "+this.vacunador+" y la vacuna: "+this.vacuna);
                System.out.println("Se vacuna a"+ usuario+ " En el momento "+ Reloj.getTiempo());
            }

            sem.release();

        } catch (Exception e) {
        }
        //Espero a que se agende a la lista
        }
    }

    public void soltarSemaforo() {
        sem.release();
    }

    public void tomarSemaforo() {
        try {
            sem.acquire();
        }
        catch (Exception e) {}
    }
}