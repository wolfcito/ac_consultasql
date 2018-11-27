
package ec.edu.espe.arquitectura.ac_consultasql;

import com.mongodb.MongoClient;
import ec.edu.espe.arquitectura.ac_consultasql.model.Persona;
import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author guffenix
 */
public class ProcMariaToMongo {
    public static void main(String[] args) {
        System.out.println("Conectandose a mongo...");
        
        final Morphia morphia = new Morphia();
        morphia.mapPackage("ec.edu.espe.arquitectura.ac_consultasql.model");
        
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "Personas");
        //datastore.ensureIndexes();
        System.out.println("Conexion establecida");
        //recibe la consulta desde mysql
        List<Persona> personas = new ArrayList<>();
        
        personas.forEach(p -> { 
            datastore.save(p);
                });
        
        
    }
}
