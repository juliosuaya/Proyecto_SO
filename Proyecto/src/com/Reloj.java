package com;

public class Reloj extends Thread {
    private static volatile int tiempo = 0;
    private Recursos[] recursos;
    private ListaEspera listaEspera;

    public Reloj(Recursos[] rec, ListaEspera listaEspera) {
        this.recursos = rec;
        this.listaEspera = listaEspera;
    }

    @Override
    public void run() {
        soltarTodosRecursos();
        while (tiempo < 200) {
            tomarTodosRecursos();
            tiempo++;
            if(tiempo % 5 == 0) {
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

    }

    private void soltarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.soltarSemaforo();
        }
    }

    private void tomarTodosRecursos() {
        for (Recursos recurso : recursos) {
            recurso.tomarSemaforo();
        }
    }

    public static synchronized int getTiempo() {
        return tiempo;
    }
}
