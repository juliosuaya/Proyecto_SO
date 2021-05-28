package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ListaEspera extends Thread {
    public ArrayList<Usuario> listaNuevosAgendados = new ArrayList<>();
    public Queue<Usuario>[] cola = new Queue[]{ new LinkedList<>(), new LinkedList<>(), new LinkedList<>()};
    public Semaphore semaforoListaEspera = new Semaphore(0, true);
    public Semaphore semaforoListaNuevosAgendados = new Semaphore(1);


    @Override
    public void run() {
        while(true) {
        try {
            semaforoListaEspera.acquire();
            semaforoListaNuevosAgendados.acquire();

            clasificar();
          //  System.out.println("tuve aka "+Reloj.getTiempo());
            synchronized (this){
                wait(1000);
            }

            semaforoListaNuevosAgendados.release();
            semaforoListaEspera.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    private void clasificar() {
        for (Usuario agendado : listaNuevosAgendados) {
            cola[agendado.prioridad].add(agendado);
        }
        listaNuevosAgendados.clear();
    }

    public synchronized Usuario getAgendado() {
        if(!cola[0].isEmpty()) {
            return cola[0].remove();
        }
        else if(!cola[1].isEmpty()) {
            return cola[1].remove();
        }
        else if(!cola[2].isEmpty()) {
            return cola[2].remove();
        }
        else {
            return null;
        }

    }

}