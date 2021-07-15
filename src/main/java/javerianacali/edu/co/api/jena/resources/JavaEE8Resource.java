package javerianacali.edu.co.api.jena.resources;

import javerianacali.edu.co.api.ontology.Ontology;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javerianacali.edu.co.api.beans.ClassMessage;
import javerianacali.edu.co.api.query.Query;
import static javerianacali.edu.co.api.query.Query.*;

/**
 *
 * @author jeank
 */
@Path("ontology")
public class JavaEE8Resource {
 
    static String q ="select (count( distinct ?instance)  as ?cou) ?class " +
    " where {?instance a ?class . ?class a owl:Class} " +
    "group by ?class " +
    "order by ?cou";
    static String PREFIX =  "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                            "PREFIX uri:<http://www.semanticweb.org/jeank/ontologies/2021/2/untitled-ontology-13#>";
 
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    //Principal Service
    @GET
    @Path("search/q={q}")
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
               .entity(result)
               .build();
    }  
    
    // Service for dona graphic
    @GET
    @Path("/dona")
    @Produces("application/json")
    public void doresDO(@Suspended
    final AsyncResponse asyncResponse, final String query) {
        executorService.submit(() -> {
            asyncResponse.resume(doDoresDO(query));
        });
    }  

    private Response doDoresDO(String query) {
        System.out.println(q);
        String result = Ontology.GetResultAsString( PREFIX + q);
        return Response
                .ok("ok")
                .entity(result)
                .build();
    }
    
    //Service for get all classes from ontology
    @GET
    @Path("/class")
    @Produces("application/json")
    public void doGetClasses(@Suspended final AsyncResponse asyncResponse){
            executorService.submit(() -> {
                asyncResponse.resume(getClasses());
            });
    }

    private Response getClasses() {
        String qfinal = Query.getClassesQuery();
        System.out.println(qfinal);
        String result = Ontology.GetResultAsString(qfinal);
        return Response
               .ok("ok")
               .entity(result)
               .build();
    } 
    
    //Service for get all instances by Class
    @POST
    @Path("/instance")
    @Produces("application/json")
    @Consumes("application/json")
    public void doGetInstancesClass(@Suspended final AsyncResponse asyncResponse,
            ClassMessage bodyRequest ){
            executorService.submit(() -> {
                asyncResponse.resume(getInstancesClass(bodyRequest));
            });
    }

    private Response getInstancesClass(ClassMessage classIn) {
        String qfinal = Query.getInstancesClass(classIn.getClassIn());
        String result = Ontology.GetResultAsString(qfinal);
        return Response
               .ok("ok")
               .entity(result)
               .build();
    }
    
    //Service for get relations by class
    @POST
    @Path("/relation")
    @Produces("application/json")
    @Consumes("application/json")
    public void doGetRelationByClass(@Suspended final AsyncResponse asyncResponse,
            ClassMessage bodyRequest ){
            executorService.submit(() -> {
                asyncResponse.resume(getRelationByClass(bodyRequest));
            });
    }

    private Response getRelationByClass(ClassMessage classIn) {
        System.out.println(classIn.getClassIn());
        String qfinal = Query.getRelationsByClass(classIn.getClassIn());
        System.out.println(qfinal);
        String result = Ontology.GetResultAsString(qfinal);
        return Response
               .ok("ok")
               .entity(result)
               .build();
    }
    
    
} 
