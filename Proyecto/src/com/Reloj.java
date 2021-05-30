package com;

public class Reloj extends Thread {
    private static int tiempo = 0;
    private Agenda agenda;
    private Recursos[] recursos;
    private ListaEspera listaEspera;

    public Reloj(Recursos[] rec, ListaEspera listaEspera, Agenda agenda) {
        this.recursos = rec;
        this.listaEspera = listaEspera;
        this.agenda = agenda;
    }

    @Override
    public void run() {
        soltarTodosRecursos();//Libero sem1 que es el semaforo de los recursos ???
        while (!this.listaEspera.seTerminoListaEspera()) { // Mientras que no se termine la lista de espera

            tomarTodosRecursos(); // Tranco sem1 que es el semaforo de los recursos ???
            agenda.soltarSemaforo();
            agenda.tomarSemaforo();
            tiempo++;
            if(tiempo % 3 == 0) {
                try {
                    listaEspera.semaforoListaEspera.release();
                    listaEspera.semaforoListaEspera.acquire();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            soltarTodosRecursos();

        }
        terminarEjecucionRecursos();
    }

    private void terminarEjecucionRecursos() {
        for (Recursos recurso : recursos) {
            recurso.terminoEjecucionRecurso();
            recurso.reanudarRecurso();
        }
    }

    private void soltarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.reanudarRecurso();
        }
    }

    private void tomarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.pausarRecurso();
        }
    }

    public static synchronized int getTiempo() {
        return tiempo;
    }
}
