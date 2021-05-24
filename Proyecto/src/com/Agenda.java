package com;

public class Agenda extends Thread {
    //toma los datos del usuario y lo mete en la lista de espera
    public Usuario usuario;
    public ListaEspera listaEspera;

    public Agenda(ListaEspera listaEspera, Usuario usuario){
        this.listaEspera=listaEspera;
        this.usuario=usuario;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semAgenda.acquire(); // Espero por usuario para poder entrar
            //con los datos le doy una prioridad
            this.listaEspera.listaEspera.add(usuario);
            System.out.println("El usuario: "+this.usuario.getNombre()+ " fue agregado a la lista de espera");
            Main.semListaDeEspera.release(); // una vez obtenida la prioridad permito entrar a ListaDeEspera()

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }
}