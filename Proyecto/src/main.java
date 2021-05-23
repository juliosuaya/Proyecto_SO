import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class main {
    public static List<Agenda> agenda = new ArrayList<>();
    public static Semaphore semComunicacion = new Semaphore(1);
    public static Semaphore semAgenda = new Semaphore(1);
    public static Semaphore semListaDeEspera = new Semaphore(1);
    public static Semaphore semRecursos = new Semaphore(1);
    public static Semaphore semVacunar = new Semaphore(1);
    public static Semaphore semVacunacion = new Semaphore(1);
    public static Semaphore agregar = new Semaphore(0);
    public static Semaphore eliminar = new Semaphore(0);

    public static void main(String[] args) {
    }


    public static class Usuario implements Runnable {

        private String cedula;
        private String nombre;
        private String apellido;
        private String barrio;
        private int edad;
        private boolean patologias;

        public Usuario(String cedula, String nombre, String apellido, String barrio, int edad, boolean patologias) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.barrio = barrio;
            this.edad = edad;
            this.patologias = patologias;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    semAgenda.release(); // una vez que pasa por usuario le permite pasar a Agenda
                    semVacunar.acquire(); // hasta que no le llegue el mensaje no se va a vacunar
                    semVacunacion.release(); //Permito entrar a Vacunacion()


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public static class Agenda implements Runnable{
//toma los datos del usuario y lo mete en la lista de espera


        @Override
        public void run() {
            while(true) {
                try {
                    semAgenda.acquire(); // Espero por usuario para poder entrar
                    //con los datos le doy una prioridad
                    semListaDeEspera.release(); // una vez obtenida la prioridad permito entrar a ListaDeEspera()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }




    public static class ListaDeEspera implements Runnable {
//Gestiona la lista (es unica)

        @Override
        public void run() {
            while(true) {
                try {
                    semListaDeEspera.acquire(); // Espero por Agenda para poder entrar
                    agregar.release();
                    //Agrego al usuario a listaDeEspera(de forma ordenada por prioridad)
                    //lo hago en un semaforo porque es una zona critica
                    agregar.acquire();
                    semRecursos.release(); // permito acceder a Recursos()

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class Recursos implements Runnable {


        @Override
        public void run() {
            while(true) {
                try {
                    semRecursos.acquire(); //Espero a que se agende a la lista
                    //Compruebo que hayan los recursos
                    semComunicacion.release(); // permito entrar a Comunicacion()

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static class Comunicacion implements Runnable {


        @Override
        public void run() {
            while(true) {
                try {
                    semComunicacion.acquire(); // Espero a que hayan recursos
                    eliminar.release();
                    //MANDO MENSAJE
                    // MANDO INFORMACION AL VACUNATORIO CON DATOS DEL USUARIO QUE VA
                    // A IR A VACUNARSE
                    // ELIMINO DE LA LISTA Y LO HAGO CON SEMAFOROS PORQUE
                    //ES UNA ZONA CRITICA
                    eliminar.acquire();
                    semVacunar.release(); // Permito vacunar
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static class Vacunacion implements Runnable {


        @Override
        public void run() {
            while(true) {
                try {
                    semVacunacion.acquire(); //Espero a que se le asigne fecha y lugar a un usuario para vacunarse
                    //SE VACUNA USUARIO
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }

    }

}
