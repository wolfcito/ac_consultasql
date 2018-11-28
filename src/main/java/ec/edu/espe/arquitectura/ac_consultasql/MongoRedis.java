/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ac_consultasql;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author user
 */
public class MongoRedis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int limite = 1000000;
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("registro-civil");
        DBCollection collection = db.getCollection("persona");
        DBCursor cursorDocMap = collection.find();
        DBObject dbobject;
        Jedis jedis = new Jedis();
        String valor;
        try {
            while (cursorDocMap.hasNext() && limite-- > 0) {
                dbobject = cursorDocMap.next();
                valor = dbobject.get("apellidos").toString();
                valor += ",";
                valor += dbobject.get("nombres");
                valor += ",";
                valor += dbobject.get("fecha_nacimiento");
                valor += ",";
                valor += dbobject.get("provincia_nacimiento");
                valor += ",";
                valor += dbobject.get("genero");
                valor += ",";
                valor += dbobject.get("estado_civil");
                jedis.set(dbobject.get("cedula").toString(), valor);
            }
        } catch (Exception e) {
            Logger.getLogger(MongoRedis.class.getName()).log(Level.SEVERE, null, e);
        }
        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (end - start) + " milisegundos.");
    }
}

