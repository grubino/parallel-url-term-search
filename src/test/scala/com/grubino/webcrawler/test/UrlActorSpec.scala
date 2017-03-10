package com.grubino.webcrawler.test

import akka.actor.{Actor, Props}
import akka.testkit.{TestActorRef, TestActors}
import com.grubino.webcrawler.UrlActor
import com.ning.http.client.{AsyncHandler, Request}
import org.scalatest.mockito.MockitoSugar
import org.scalamock.scalatest.MockFactory
import dispatch.Defaults

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong
import scala.concurrent.{ExecutionContext, Future}



/**
  * Created by gregrubino on 3/10/17.
  */
class UrlActorSpec extends IntegrationSpec with MockFactory {

  "A url actor" should "not pass along urls that contain no terms" in {

    val httpMock = mock[TestHttpExecutor]
    (httpMock.apply(_: (Request, AsyncHandler[String]))(_: ExecutionContext))
      .expects(*, *)
      .returning(Future[String] {
        "cash me oussah, howbowdeh?"
      })

    val actor = system.actorOf(
      Props(classOf[UrlActor], testActor, "nomatch", httpMock))
    actor ! "http://fakeyMcNotAURL.com"
    expectNoMsg(1 second)

  }
  "A url actor" should "pass along urls that contain terms" in {

    val httpMock = mock[TestHttpExecutor]
    (httpMock.apply(_: (Request, AsyncHandler[String]))(_: ExecutionContext))
      .expects(*, *)
      .returning(Future[String] {
        "Lorem ipsum ..."
      })

    val actor = system.actorOf(
      Props(classOf[UrlActor], testActor, "ipsum", httpMock))
    actor ! "http://fakeyMcNotAURL.com"
    expectMsg(1 second, "http://fakeyMcNotAURL.com")

  }
}
