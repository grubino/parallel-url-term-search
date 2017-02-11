package com.grubino.webcrawler

import dispatch._
import Defaults._

import scala.io.Source
import java.io.{File, PrintWriter}

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by greg on 2/8/17.
  */
object TermCrawler extends App {

  val customConf = ConfigFactory.parseString("""
      akka.actor.deployment {
        /url-search-service {
          router = round-robin-pool
          nr-of-instances = 20
        }
        /file-writing-service {
          nr-of-instances = 1
        }
      }
      """)
  val actorSystem = ActorSystem("url-search-system", ConfigFactory.load(customConf))

  val urls = Source.fromFile(args(0)).getLines().toList.drop(1).map(
    "http://"+_.split(",")(1).stripSuffix("\"").stripPrefix("\""))
  val term = args(1)

  val urlActor = actorSystem.actorOf(Props(new UrlActor(
    actorSystem.actorOf(Props(new WriteResultsActor(
      new File("out.txt"))),
      "file-writing-service"), term)),
    "url-search-service")

  urls.foreach(urlActor ! _)

}

