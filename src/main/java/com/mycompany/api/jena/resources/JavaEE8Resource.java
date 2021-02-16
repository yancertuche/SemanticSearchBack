package com.mycompany.api.jena.resources;

import com.mycompany.api.ontology.Ontology;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
        static String prefix =   "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
        
        static String queryTest2 =   "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
                                    "SELECT distinct ?x ( STR(?lab) as ?label ) WHERE {?x rdfs:label ?lab " +
                                    "} ORDER BY ?label";
        static String q ="";
  
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
    @Path("response/search/q={q}")
    @Produces("application/json")
    public void res(@Suspended
    final AsyncResponse asyncResponse, @PathParam("q") String query){
            Future<?> submit = executorService.submit(() -> {
                asyncResponse.resume(doRes(query));
            });
    }

    private Response doRes(String query) {
        System.out.println(query);
        String qfinal = prefix + "SELECT  ?x (STR(?lab) as ?label) " +
                                    "where {" +
                                    " ?x rdfs:label ?lab." +
                                    " FILTER regex(?lab, \""+(query)+"\", \"i\")" +
                                    "}" ;
        System.out.println(qfinal);
        String a = Ontology.GetResultAsString(qfinal);
        if("nada".equals(a)){
            a = "{results : { bindings:[{\"nada\"}]}}";
        }
        return Response
               .ok("ok")
               .header("Access-Control-Allow-Origin", "*")
               .entity(a)
               .build();
    }

    
} 
