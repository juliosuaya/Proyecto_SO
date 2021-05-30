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
        while(!seTermino) { // hasta que no se setee seTermino en true
            try {
                semaforoListaEspera.acquire(); // Tomo semaforo de lista de espera
                semaforoListaNuevosAgendados.acquire(); // tomo semaforo de lista de nuevos agendados

                clasificar(); // clasifica a los usuarios segun su prioridad
                Main.imprimir("Se vuelve a planificar "+Reloj.getTiempo());

                semaforoListaNuevosAgendados.release(); // libero semaforo de lista de nuevos agendados
                semaforoListaEspera.release(); // Libero semaforo de lista de espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clasificar() {
        try {
            semaforoColaListaEspera.acquire();  // tomo semaforo de la cola de lista de espera
            for (Usuario agendado : listaNuevosAgendados) {
                cola[agendado.prioridad].add(agendado); // agrego a cada uno a su cola respecto su prioridad
            }
            listaNuevosAgendados.clear(); // listo la lista de nuevos agendados
            semaforoColaListaEspera.release(); // libero semaforo de la cola de lista de espera
        } catch (Exception e) {e.printStackTrace();}
    }

    //Mientras estoy clasificando no puedo obtener un agendado y viceversa

    public synchronized Usuario getAgendado() { //Retorna un agendado basandose en la prioridad y FCFS
        try {
            semaforoColaListaEspera.acquire(); // Tomo semaforo de la cola de lista de espera
            Usuario res;
            if(!cola[0].isEmpty()) { //Si la cola de prioridad 0 no esta vacia
                res = cola[0].remove();
            }
            else if(!cola[1].isEmpty()) { //Si la cola de prioridad 1 no esta vacia
                res = cola[1].remove();
            }
            else if(!cola[2].isEmpty()) { //Si la cola de prioridad 2 no esta vacia
                res = cola[2].remove();
            }
            else if(!cola[3].isEmpty()) { //Si la cola de prioridad 3 no esta vacia
                res = cola[3].remove();
            }
            else { // Si todas estan vacias es porque no hay mas agendados
                res = null;
                if(noMasAgendados) {
                    seTermino = true;
                }
            }
            semaforoColaListaEspera.release(); // Libero semaforo de la cola de lista de espera
            return res;
        }
        catch (Exception e){}
        return null;

    }

    public void noHayMasAgenda() { // Seteo que no hay mas agendados
        this.noMasAgendados = true;
    }

    public boolean seTerminoListaEspera() {
        return this.seTermino;
    }

}