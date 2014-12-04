package controllers

import java.util.concurrent.TimeUnit

import models._
import org.specs2.mutable._
import play.api.libs.json._
import play.api.test.Helpers._
import play.api.test._
import reactivemongo.bson.BSONObjectID

import scala.concurrent._
import scala.concurrent.duration.FiniteDuration

/**
 * Created by Andrew on 11/30/14.
 */
class EventsControllerIntTest extends Specification {

  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

  val events = List(Event("Game Night", "December 4 - 5:30pm", "SPAC",
      "Have games you want to play? Bring them with you!" +
      " Don't have a game? Come by anyway and play some of ours!", "kmontpetit@thespac.com"),
      Event("Holiday Sing-A-Long", "December 4 - 5:30pm", "SPAC",
      "Stop by and go ah-carroling with your friends at SPAC!", "kmontpetit@thespac.com"))
  val eventSchedule = Events(Option(BSONObjectID.apply("35181f15e0f8477d21a5859a")),
    "events", events)
  val eventsAsJson = Json.toJson[Events](eventSchedule)

  "Events" should {

    "insert a valid json schedule" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/events/create")
          .withJsonBody(eventsAsJson)
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        result.header.status must equalTo(CREATED)
      }
    }

    "update a valid json schedule" in {
      running(FakeApplication()) {
        val updateRequest = FakeRequest.apply(PUT, "/events/35181f15e0f8477d21a5859a")
          .withJsonBody(eventsAsJson)
        val updateResponse = route(updateRequest)
        updateResponse.isDefined mustEqual true
        val updateResult = Await.result(updateResponse.get, timeout)
        updateResult.header.status must equalTo(CREATED)
      }
    }

  }
}
