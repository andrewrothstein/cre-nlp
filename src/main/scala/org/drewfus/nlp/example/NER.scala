package org.drewfus.nlp.example

import java.util.Properties
import scala.collection.JavaConversions._
import edu.stanford.nlp.pipeline._
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations
import edu.stanford.nlp.trees.TreeCoreAnnotations
import edu.stanford.nlp.dcoref.CorefCoreAnnotations

object NER extends App {
  import CoreAnnotations._
  import SemanticGraphCoreAnnotations._
  import TreeCoreAnnotations._
  import CorefCoreAnnotations._
  
  val props = new Properties
  props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref")
  
  val pipeline = new StanfordCoreNLP(props)

  val textToAnalyze = "In the beginning, God created the heaven and the earth. Andrew lives at 57 Franklin Street, Englewood, NJ 07631"
  val document = new Annotation(textToAnalyze)

  pipeline.annotate(document)
  
  val sentences = document.get(classOf[SentencesAnnotation])
  for (sentence <- sentences) {
    for (token <- sentence.get(classOf[TokensAnnotation])) {
      val word = token.get(classOf[TextAnnotation])
      val pos = token.get(classOf[PartOfSpeechAnnotation])
      val ner = token.get(classOf[NamedEntityTagAnnotation])
      
      println(s"word: $word, pos : $pos, ner : $ner")
    }

    val tree = sentence.get(classOf[TreeAnnotation])
  
    val dependencies = sentence.get(classOf[CollapsedCCProcessedDependenciesAnnotation])
    
    println(s"tree: $tree")
    println(s"dependencies: $dependencies")
  }

  val graph = document.get(classOf[CorefChainAnnotation])
  println(s"graph: $graph")
}