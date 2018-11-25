
package ec.edu.espe.arquitectura.ac_consultasql;

/**
 *
 * @author guffenix
 */
public class Procesar {
    public static void main(String[] args) {
        //aqui se unen los tres procesos
        System.out.println("Procesando txt y guardando en mariadb...");
        System.out.println("consultando mariadb y guardando en mongodb...");
        System.out.println("consultando mongodb y guardando en redis...");
    }
}
