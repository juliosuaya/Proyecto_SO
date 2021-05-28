package com;

import java.util.concurrent.Semaphore;

public class Agenda extends Thread {
    //toma los datos del usuario y lo mete en la lista de espera
    public Usuario usuario = new Usuario("42312", "Juan Perez", 5);;
    public ListaEspera listaEspera;

    public Agenda(ListaEspera listaEspera){
        this.listaEspera=listaEspera;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            listaEspera.semaforoListaNuevosAgendados.acquire(); // Espero por usuario para poder entrar
            //con los datos le doy una prioridad
            this.listaEspera.listaNuevosAgendados.add(usuario);
            System.out.println("El usuario: "+this.usuario.getNombre()+ " fue agregado a la lista de espera");
            listaEspera.semaforoListaNuevosAgendados.release(); // una vez obtenida la prioridad permito entrar a ListaDeEspera()

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }
}