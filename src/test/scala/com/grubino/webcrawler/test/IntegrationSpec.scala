package com.grubino.webcrawler.test

import akka.actor.ActorSystem
import akka.testkit.TestKit
import dispatch.HttpExecutor
import org.scalatest._

/**
  * Created by gregrubino on 3/10/17.
  */
abstract class IntegrationSpec
  extends TestKit(ActorSystem("MySpec"))
    with FlatSpecLike with Matchers with OptionValues with Inside with Inspectors with BeforeAndAfterAll {

  trait TestHttpExecutor extends HttpExecutor {
    type HttpPackage[T] = T
  }
  override def afterAll = {
    TestKit.shutdownActorSystem(system)
  }
}
