package javerianacali.edu.co.api.jena.resources;

import javerianacali.edu.co.api.ontology.Ontology;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import static javerianacali.edu.co.api.query.Query.bulidQuery;
/**
 *
 * @author jeank
 */
@Path("javaee8")
public class JavaEE8Resource {
    
        
        static String queryTest2 =   "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
                                    "SELECT distinct ?x ( STR(?lab) as ?label ) WHERE {?x rdfs:label ?lab " +
                                    "} ORDER BY ?label";
        static String q ="";
 
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
        //System.out.println(query);
        String qfinal = bulidQuery(query);
        System.out.println(qfinal);
        String result = Ontology.GetResultAsString(qfinal);
        return Response
               .ok("ok")
               .header("Access-Control-Allow-Origin", "*")
               .entity(result)
               .build();
    }  
} 
