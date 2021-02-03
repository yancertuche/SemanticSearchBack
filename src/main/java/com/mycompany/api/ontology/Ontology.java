/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.api.ontology;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

/**
 *
 * @author jeank
 */
public class Ontology {
   
    /**
     * @param args the command line arguments
     */
    
    static  String  s;
    
    //Method than load de OWL file of SPL and return a Ontology Model 
    public static OntModel onto() {
        // TODO code application logic here
        
        OntModel mode = null;
        mode = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
        java.io.InputStream in = FileManager.get().open("C:\\Users\\jeank\\Documents\\"
                                            + "spl-ontology-ontologies-owl-REVISION-HEAD\\spl.owl" ); 
        if (in == null) {
            throw new IllegalArgumentException("Archivo de ontología no encontrado");
        }
            System.out.println("Cargó");
            return  (OntModel) mode.read(in, "");
    }
    // method that execute a Query in SPARQL format and return the result like a string in Json format 
    public  static  String GetResultAsString(String Query){
        try {
            org.apache.jena.query.Query query = QueryFactory.create(Query);
            QueryExecution qe = QueryExecutionFactory.create(query, onto());
            org.apache.jena.query.ResultSet results = qe.execSelect();
                if(results.hasNext()){
                    ByteArrayOutputStream go = new ByteArrayOutputStream ();
                    //ResultSetFormatter.out((OutputStream)go ,results, query);
                    ResultSetFormatter.outputAsJSON(go, results);
                    //  String s = go.toString();
                    // retouner les resultat de recherche (String ) ;)
                    s = new String(go.toByteArray(), "UTF-8");
                   }
                else{
                      // no encuentra nada 
                      s = "nada";
                  }
        }
         catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Ontology.class.getName()).log(Level.SEVERE, null, ex);
        }
         return s;
      }
}
