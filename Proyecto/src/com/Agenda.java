package com;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Agenda extends Thread {

    private Usuario[][] usuarios = {
            {},
            {new Usuario("1", "Juan Rodriguez", 3),
                    new Usuario("2", "Carlos Perez", 4),
                    new Usuario("3", "Teodoro Fernandez", 2)},
            {new Usuario("4", "Ricardo Arjona", 3),
                    new Usuario("5", "Roberto Carlos", 1),
                    new Usuario("6", "Cecilia Paredes", 2)},
            {new Usuario("7", "Pedro Fontes", 4),
                    new Usuario("8", "Diego Perez", 3),
                    new Usuario("9", "Nicolas Nicolas", 4)},
            {new Usuario("10", "Agustina Pereiria", 1),
                    new Usuario("11", "Osvaldo Blanco", 3),
                    new Usuario("12", "Paola Paola", 1)},
            {new Usuario("13", "Ximena Pereira", 2),
                    new Usuario("14", "Gonzalo Gonzalo", 3),
                    new Usuario("15", "Silvana Rodriguez", 2)},
            {new Usuario("16", "Oscar Tabarez", 4),
                    new Usuario("17", "Luis Miguel", 3),
                    new Usuario("18", "Gabriel Gabriel", 3)},
            {},
            {new Usuario("19", "Carlos Rodriguez", 2),
                    new Usuario("20", "Nicole Neumann", 1),
                    new Usuario("21", "Marcelo Tinelli", 4)},
            {new Usuario("22", "David David", 2),
                    new Usuario("23", "Diego Rodriguez", 2),
                    new Usuario("24", "Juan Rodriguez", 1)},
            {new Usuario("25", "Nicolas Baldez", 3),
                    new Usuario("26", "Luis Suarez", 2),
                    new Usuario("27", "Edison Cavani", 3)}
    };

    public ListaEspera listaEspera;
    public Semaphore semaforoAgenda = new Semaphore(0, true);

    public Agenda(ListaEspera listaEspera){
        this.listaEspera=listaEspera;
    }

    @Override
    public void run() {
        while(true) {
            try {
                semaforoAgenda.acquire(); //Una vez que entra, tranco el semaforo de Agenda
                if(!agregarAgendados(Reloj.getTiempo())) {
                    semaforoAgenda.release();
                    break;
                }
                semaforoAgenda.release();
            }
            catch (Exception e) {}
        }
        listaEspera.noHayMasAgenda();
    }

    private boolean agregarAgendados(int i) {
        if(usuarios.length <= i) return false;
        try {
            listaEspera.semaforoListaNuevosAgendados.acquire(); //Tranco semaforo de lista de espera para asignar
            this.listaEspera.listaNuevosAgendados.addAll(Arrays.asList(usuarios[i]));//Agrego todos los usuarios de una fila i de la matriz a listaNuevosAgendados
            for(Usuario us : usuarios[i]){us.iniciarVacunacion();} //Inicio la vacunacion de cada usuario de la fila i

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            listaEspera.semaforoListaNuevosAgendados.release();// Una vez asignados e iniciada sus vacunaciones, libero semaforo
        }
        return true;
    }

    public void tomarSemaforo() {  //Tranca semaforo de Agenda
        try {
            semaforoAgenda.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soltarSemaforo() {
        semaforoAgenda.release();
    } //Libera semaforo de Agenda
}