package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ListaEspera extends Thread {
    public ArrayList<Usuario> listaNuevosAgendados = new ArrayList<>();
    public Queue<Usuario>[] cola = new Queue[]{ new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>()};
    public Semaphore semaforoListaEspera = new Semaphore(0, true);
    public Semaphore semaforoColaListaEspera = new Semaphore(1, true);
    public Semaphore semaforoListaNuevosAgendados = new Semaphore(1);
    private boolean noMasAgendados = false;
    private boolean seTermino = false;

    @Override
    public void run() {
        while(!seTermino) {
            try {
                semaforoListaEspera.acquire();
                semaforoListaNuevosAgendados.acquire();

                clasificar();
                Main.imprimir("Se vuelve a planificar "+Reloj.getTiempo());
                semaforoListaNuevosAgendados.release();
                semaforoListaEspera.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clasificar() {
        try {
            semaforoColaListaEspera.acquire();
            for (Usuario agendado : listaNuevosAgendados) {
                cola[agendado.prioridad].add(agendado);
            }
            listaNuevosAgendados.clear();
            semaforoColaListaEspera.release();
        } catch (Exception e) {e.printStackTrace();}
    }

    public synchronized Usuario getAgendado() {
        try {
            semaforoColaListaEspera.acquire();
            Usuario res;
            if(!cola[0].isEmpty()) {
                res = cola[0].remove();
            }
            else if(!cola[1].isEmpty()) {
                res = cola[1].remove();
            }
            else if(!cola[2].isEmpty()) {
                res = cola[2].remove();
            }
            else if(!cola[3].isEmpty()) {
                res = cola[3].remove();
            }
            else {
                res = null;
                if(noMasAgendados) {
                    seTermino = true;
                }
            }
            semaforoColaListaEspera.release();
            return res;
        }
        catch (Exception e){}
        return null;

    }

    public void noHayMasAgenda() {
        this.noMasAgendados = true;
    }

    public boolean seTerminoListaEspera() {
        return this.seTermino;
    }

}