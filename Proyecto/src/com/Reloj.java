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

    /*Cada iteracion del ciclo while, permite correr una iteracion
    a cada recurso, a la agenda y a la lista de espera cuando corresponde */
    @Override
    public void run() {
        soltarTodosRecursos();//Libero sem1 que es el semaforo de los recursos tanta veces como recursos
        while (!this.listaEspera.seTerminoListaEspera()) { // Mientras que no se termine la lista de espera

            tomarTodosRecursos(); // Tomo sem2 de recursos tanta veces como recursos
            if(!agenda.termino){
                agenda.soltarSemaforo();
                agenda.tomarSemaforo();
            }
            tiempo++;
            if(tiempo % 3 == 0) { // Se planifica una vez cada 3 ciclos.
                try {
                    listaEspera.soltarSemaforo();
                    listaEspera.tomarSemaforo();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            soltarTodosRecursos();

        }
        terminarEjecucionRecursos(); //Termino cada uno de los recursos y Libero sem1 tantas veces como recursos
    }

    private void terminarEjecucionRecursos() {
        for (Recursos recurso : recursos) {
            recurso.terminoEjecucionRecurso(); // Termino cada uno de los recursos
            recurso.reanudarRecurso(); // Libero sem1 por cada recurso
        }
    }

    private void soltarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.reanudarRecurso(); // Libero sem1 por cada recurso
        }
    }

    private void tomarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.pausarRecurso(); //Ocupo sem1 por cada recurso
        }
    }

    public static synchronized int getTiempo() {
        return tiempo;
    }
}
