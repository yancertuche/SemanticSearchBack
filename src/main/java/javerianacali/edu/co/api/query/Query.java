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
    static String URI = "http://www.semanticweb.org/jeank/ontologies/2021/2/untitled-ontology-13#";
    
    public static String getURI(){
        return URI;
    }

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
        return getPREFIX() + "SELECT DISTINCT ?labelClass ?labelSubClass " + 
            "WHERE { ?subClass rdfs:subClassOf ?class. ?class a owl:Class. " +
            "?class rdfs:label ?labelClass. "+
            "?subClass rdfs:label ?labelSubClass. "+
            "FILTER(regex(str(?subClass),\""+getURI()+"*\", \"i\" )). " +
            "FILTER(regex(str(?class),\""+getURI()+"*\", \"i\" )) " +
            "FILTER(?subClass != ?class). "+
            "FILTER (lang(?labelSubClass) = \"en\" )}"+
            "ORDER BY ?class " ;
    }
    
    public static String getInstancesClass(String classIn){
        return getPREFIX() + "SELECT DISTINCT  ?labelInstance " +
            "?description ?amount ?categories ?companyName ?context ?metric "+
            "?name ?productName ?size ?title ?type ?url ?year "+
            "WHERE {?instance a ?class . "+
            "?class a owl:Class. "+
            "?instance rdfs:label ?labelInstance " +
            "OPTIONAL{?instance uri:Description ?description} "+
            "OPTIONAL{?instance uri:Amount ?amount} "+
            "OPTIONAL{?instance uri:Categories ?categories} "+
            "OPTIONAL{?instance uri:companyName ?companyName} "+
            "OPTIONAL{?instance uri:Context ?context} "+
            "OPTIONAL{?instance uri:metric ?metric} "+
            "OPTIONAL{?instance uri:Name ?name} "+
            "OPTIONAL{?instance uri:productName ?productName} "+
            "OPTIONAL{?instance uri:Size ?size} "+
            "OPTIONAL{?instance uri:Title ?title} "+
            "OPTIONAL{?instance uri:Type ?type} "+
            "OPTIONAL{?instance uri:Url ?url} "+
            "OPTIONAL{?instance uri:Year ?year} "+
            "FILTER(regex(str(?class),\""+getURI()+classIn+"\", \"i\" )). "+
            " FILTER (lang(?labelInstance) = \"en\" ) } "   ;
    }
    
    public static String getRelationsByClass(String classIn){
        return getPREFIX() + "SELECT ?domain ?Property  ?range " +
            "WHERE { "+
            "?Property rdfs:domain ?domain. "+
            "?Property rdfs:range ?range. "+
            "FILTER(regex(str(?domain),\""+getURI()+classIn+"\", \"i\" )) " +
            "} "  ;  
    }
}
