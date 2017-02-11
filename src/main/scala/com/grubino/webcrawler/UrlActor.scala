package com.grubino.webcrawler

import java.io._

import akka.actor.{Actor, ActorRef}
import dispatch._
import Defaults._
import akka.event.Logging

class WriteResultsActor(file: File) extends Actor {
  val log = Logging(context.system, this)
  val output = new FileWriter(file, true)
  override def receive: Receive = {
    case u: String =>
      log.info(s"appending ${u} to ${file}...")
      output.append(u + "\n")
      output.flush()
  }
}

class UrlActor(writeFileActor: ActorRef, term: String) extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case u: String =>

      log.info(s"fetching ${u}...")
      val svc = url(u)
      for { s <- Http(svc OK as.String) } {
        if(s.contains(term)) {
          log.info(s"found ${term} in result, sending to output writing actor...")
          writeFileActor ! u
        }
      }

  }
}
