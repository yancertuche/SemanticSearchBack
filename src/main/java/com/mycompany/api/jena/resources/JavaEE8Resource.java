package com.mycompany.api.jena.resources;

import com.mycompany.api.ontology.Ontology;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
/**
 *
 * @author jeank
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    //  String with a query in sparql formart t
        static String queryTest =   "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
                                    "SELECT ?x ( STR(?lab) as ?label ) WHERE {?x rdf:type owl:Class. " +
                                    "OPTIONAL{?x rdfs:label ?lab}} ORDER BY ?label";
  
    @GET
    @Path("json")
    @Produces("application/json")
    public String json(){
        return "{\n" +
               "   \"eBooks\":[\n" +
               "      {\n" +
                "         \"language\":\"Pascal\",\n" +
                "         \"edition\":\"third\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"language\":\"Python\",\n" +
                "         \"edition\":\"four\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"language\":\"SQL\",\n" +
                "         \"edition\":\"second\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";
        /*return Response
                .ok("ping")
                .build(); */
    }
    @GET
    @Path("xml")
    @Produces("text/xml")
    public String xml(){
        return "<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>";
        /*return Response
                .ok("ping")
                .build(); */
    }
    
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    @GET
    @Path("response")
    @Produces("application/json")
    public void res(@Suspended
    final AsyncResponse asyncResponse){
            Future<?> submit = executorService.submit(() -> {
                asyncResponse.resume(doRes());
            });
    }

    private Response doRes() {
        String a = Ontology.GetResultAsString(queryTest);
        
        return Response
               .ok("ok")
               .header("Access-Control-Allow-Origin", "*")
               .entity(a)
               .build();
    }

    
} 
