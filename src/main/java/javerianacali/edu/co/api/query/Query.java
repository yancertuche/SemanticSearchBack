/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javerianacali.edu.co.api.query;

import javerianacali.edu.co.api.beans.CrcMessage;

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
    
    public static String buildQuery(String class1, String relation, String class2){
        return getPREFIX() + "SELECT DISTINCT ?Instance ?NameTool ?Description ?NameAutor ?Amount ?Title  ?Year ?url"+
        "?ObjectRelated ?NameToolObjRelated ?DescripcionObjRelated  ?NameCompany ?TitleObjRelated  ?YearObjRelated ?UrlObjRelated " +
        "WHERE { ?instance a ?class. "+
	"?class a owl:Class. "+
	"?instance uri:"+relation +" ?objeto. "+
        "?instance rdfs:label ?Instance. "+
        "?objeto rdfs:label ?ObjectRelated. "+
	"OPTIONAL{?objeto uri:Description ?DescripcionObjRelated.} "+
	"OPTIONAL{?instance uri:Description ?Description} "+
	"OPTIONAL{?objeto uri:Name ?NameCompany.} "+
	"OPTIONAL{?instance uri:Name ?NameAutor} "+
	"OPTIONAL{?objeto uri:toolName ?NameToolObjRelated.} "+
	"OPTIONAL{?instance uri:toolName ?NameTool} "+
	"OPTIONAL{?objeto uri:Amount ?AmountObjRelated.} "+
	"OPTIONAL{?instance uri:Amount ?Amount} "+
        "OPTIONAL{?objeto uri:Title ?TitleObjRelated.} "+
	"OPTIONAL{?instance uri:Title ?Title} "+
        "OPTIONAL{?objeto uri:Url ?UrlObjRelated.} "+
	"OPTIONAL{?instance uri:Url ?url} "+
        "OPTIONAL{?objeto uri:Year ?YearObjRelated.} "+
	"OPTIONAL{?instance uri:Year ?Year} "+
	"}"  ;
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
        return getPREFIX() + "SELECT DISTINCT  ?Instance " +
            "?NameTool ?Description ?Amount ?Categories ?CompanyName ?Context ?Metric "+
            "?Name ?ProductName ?Size ?Title ?Type ?Year ?url  "+
            "WHERE {?instance a ?class . "+
            "?class a owl:Class. "+
            "?instance rdfs:label ?Instance " +
            "OPTIONAL{?instance uri:Description ?Description} "+
            "OPTIONAL{?instance uri:Amount ?Amount} "+
            "OPTIONAL{?instance uri:Categories ?Categories} "+
            "OPTIONAL{?instance uri:companyName ?CompanyName} "+
            "OPTIONAL{?instance uri:Context ?Context} "+
            "OPTIONAL{?instance uri:metric ?Metric} "+
            "OPTIONAL{?instance uri:Name ?Name} "+
            "OPTIONAL{?instance uri:productName ?ProductName} "+
            "OPTIONAL{?instance uri:toolName ?NameTool} "+
            "OPTIONAL{?instance uri:Size ?Size} "+
            "OPTIONAL{?instance uri:Title ?Title} "+
            "OPTIONAL{?instance uri:Type ?Type} "+
            "OPTIONAL{?instance uri:Url ?url} "+
            "OPTIONAL{?instance uri:Year ?Year} "+
            "FILTER(regex(str(?class),\""+getURI()+classIn+"\", \"i\" )). "+
            " FILTER (lang(?Instance) = \"en\" ) } "   ;
    }
    
    public static String getRelationsByClass(String classIn){
        return getPREFIX() + "SELECT DISTINCT ?property ?Domain ?Range " +
            "WHERE { "+
            "?Property rdfs:domain ?domain. "+
            "?Property rdfs:range ?range. "+
            "?Property rdfs:label ?property. "+
            "?domain rdfs:label ?Domain. "+
            "?range rdfs:label ?Range. "+
            "FILTER(regex(str(?domain),\""+getURI()+classIn+"\", \"i\" )) " +
            "FILTER(regex(str(?Property),\""+getURI()+"*\", \"i\" )). " +
            "FILTER(regex(str(?range),\""+getURI()+"*\", \"i\" )). " +
            "} "  ;  
    }
    
    public static String getDonaQuery(){
        return getPREFIX() + "SELECT (count( distinct ?instance)  as ?counter)  ?NameClass "+
            "WHERE {?instance a ?class . "+
            "?class a owl:Class. "+
            "?class rdfs:label ?NameClass} "+ 
            "GROUP BY ?NameClass "+ 
            "ORDER BY DESC(?counter) " +
            "LIMIT 3 ";
    }
}
