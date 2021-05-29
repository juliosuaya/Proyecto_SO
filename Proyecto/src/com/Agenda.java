package com;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Agenda extends Thread {

    private Usuario[][] usuarios = {
            {},
            {new Usuario("1", "1Juan Perez", 3),
                    new Usuario("2", "1carlos Perez", 4),
                    new Usuario("3", "1Juan carlos", 2)},
            {new Usuario("21", "2Juan Perez", 3),
                    new Usuario("22", "2carlos Perez", 1),
                    new Usuario("23", "2Juan carlos", 2)},
            {new Usuario("31", "3Juan Perez", 4),
                    new Usuario("32", "3carlos Perez", 3),
                    new Usuario("33", "3Juan carlos", 4)},
            {new Usuario("1", "4Juan Perez", 1),
                    new Usuario("2", "4carlos Perez", 3),
                    new Usuario("3", "4Juan carlos", 1)},
            {new Usuario("21", "5Juan Perez", 2),
                    new Usuario("22", "5carlos Perez", 3),
                    new Usuario("23", "5Juan carlos", 2)},
            {new Usuario("31", "6Juan Perez", 4),
                    new Usuario("32", "6carlos Perez", 3),
                    new Usuario("33", "6Juan carlos", 3)},
            {},
            {new Usuario("1", "7Juan Perez", 2),
                    new Usuario("2", "7carlos Perez", 1),
                    new Usuario("3", "7Juan carlos", 4)},
            {new Usuario("21", "8Juan Perez", 2),
                    new Usuario("22", "8carlos Perez", 2),
                    new Usuario("23", "8Juan carlos", 1)},
            {new Usuario("31", "9Juan Perez", 3),
                    new Usuario("32", "9carlos Perez", 2),
                    new Usuario("33", "9Juan carlos", 3)}
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
                semaforoAgenda.acquire();
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
            listaEspera.semaforoListaNuevosAgendados.acquire();
            this.listaEspera.listaNuevosAgendados.addAll(Arrays.asList(usuarios[i]));
            for(Usuario us : usuarios[i]){us.iniciarVacunacion();}

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            listaEspera.semaforoListaNuevosAgendados.release();
        }
        return true;
    }

    public void tomarSemaforo() {
        try {
            semaforoAgenda.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soltarSemaforo() {
        semaforoAgenda.release();
    }
}