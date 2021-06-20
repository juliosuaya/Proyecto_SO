package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Agenda extends Thread {

    private ArrayList<ArrayList<Usuario>> usuarios;
    public ListaEspera listaEspera;
    public Semaphore semaforo1Agenda = new Semaphore(0);
    public Semaphore semaforo2Agenda = new Semaphore(0);
    public boolean termino = false;

    public Agenda(ListaEspera listaEspera){
        this.listaEspera=listaEspera;
        usuarios = Utils.cargarUsuarios();
    }

    @Override
    public void run() {
        while(!termino) {
            try {
                semaforo1Agenda.acquire(); //Una vez que entra, tomo semaforo de Agenda
                if(!agregarAgendados(Reloj.getTiempo())) { // si no se va a anotar mas gente
                    termino = true;
                }
                semaforo2Agenda.release(); // Libero semaforo de agenda
            }
            catch (Exception e) {}
        }
        listaEspera.noHayMasAgenda(); // Seteo que no hay mas agenda
    }

    private boolean agregarAgendados(int i) {
        if(usuarios.size() <= i) return false;
        listaEspera.agregarAgendados(usuarios.get(i));
        return true;
    }

    public void tomarSemaforo() {  //Toma semaforo de Agenda
        try {
            semaforo2Agenda.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soltarSemaforo() {
        semaforo1Agenda.release();
    } //Libera semaforo de Agenda
}