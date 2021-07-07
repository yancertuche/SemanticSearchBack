/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javerianacali.edu.co.api.query;

/**
 *
 * @author jeank
 */
public class Query {
    static String PREFIX =   "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                                    "PREFIX uri:<http://www.semanticweb.org/jeank/ontologies/2021/2/untitled-ontology-13#> ";

    public static String getPREFIX() {
        return PREFIX;
    }
    
    public static String bulidQuery(String query){
        /*
        return getPREFIX() +  "SELECT  ?x (STR(?lab) as ?label) " +
                                    "where {" +
                                    " ?x rdfs:label ?lab." +
                                    " FILTER regex(?lab, \""+(query)+"\", \"i\")" +
                                    "}" ;
        */
        /*
        return getPREFIX() + "SELECT  distinct ?class" +
                               "WHERE { " +
                                        "?class a owl:Class  ."+
                                "}" ;
        */
        /*
        return getPREFIX() + "SELECT ?x (STR(?lab) as ?label)" + 
                            " where { ?x rdf:type owl:NamedIndividual ." +
                            " optional { ?x rdf:ensayo ?lab} }" ;
        */
        /*
        return getPREFIX() + " SELECT ?anyAutor (STR(?name) as ?nameAutor)" +
                               " WHERE {?anyAutor a uri:AUTOR." +
                            " OPTIONAL {?anyAutor uri:Name ?name}" +
                            " FILTER regex(?name, \""+(query)+"\", \"i\")" +
                                "}"; 
        return getPREFIX() + " SELECT (STR(?x) as ?Name) (STR(?description) as ?Report) " +
                            "WHERE { " +
                            "?x  a uri:"+(query)+".  " +
                            "?x  uri:Description ?description " +
                            "}";
       
        
        return getPREFIX() + " SELECT (STR(?x) as ?Uri) (STR(?description) as ?Report) (STR(?name) as ?NameAutor) " +
                        "(STR(?title) as ?Title)  (STR(?url) as ?Url) (STR(?year) as ?Year)   (STR(?paper) as ?Paper) "+
                        "WHERE { ?x  a uri:" +(query)+ ". " + 
                        "OPTIONAL ?x  uri:Description ?description. " +
                        "?x uri:showA ?paper. " +
                        "?paper uri:Year ?year. " +
                        "?paper uri:Title ?title. "+
                        "?paper uri:Url ?url. "+
                        "?paper uri:Title ?title. "+
                        "?paper uri:writeBy ?uriName. "+
                        "?uriName uri:Name ?name. "+
                        "}";

        */
        
        return getPREFIX() + "SELECT * " +
                        "WHERE { " +
                                "?paperType rdfs:label \""+(query)+"\"@en. "+
                                "?papers a ?paperType . " +
                        "optional{ ?papers uri:writeBy ?autor . " +
                                "?papers uri:Title ?Title. "+
                                "?autor uri:Name ?NameAutor " +
                                "}. " +
                        "optional{ ?papers uri:hasCompany ?comp. " +
                                "?comp uri:CompanyName ?nameCompany} } ";
    } 
    
    public static String getClassesQuery(){
        return getPREFIX() + "SELECT ?subject "+ 
            "WHERE { ?subject rdfs:subClassOf ?object } " +
            "ORDER BY ?subject ";
    }
    
}
