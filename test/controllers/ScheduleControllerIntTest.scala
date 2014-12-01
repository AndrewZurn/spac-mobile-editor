package controllers

import java.util.concurrent.TimeUnit

import models.{FitnessClass, FitnessClassDay, FitnessClassWeek}
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
class ScheduleControllerIntTest extends Specification {

  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

  val classes = List(FitnessClass("BarBell Strength", "6:15am", "Jason", "9A"), FitnessClass("Cycle", "11:15am", "Jason", "5C"))
  val classDays = List(FitnessClassDay("Monday", classes), FitnessClassDay("Tuesday", classes),
    FitnessClassDay("Wednesday", classes), FitnessClassDay("Thursday", classes),
    FitnessClassDay("Friday", classes), FitnessClassDay("Saturday", classes),
    FitnessClassDay("Sunday", classes))
  val classWeek = FitnessClassWeek(Option(BSONObjectID.apply("50181f15e0f8477d00a5859e")), "group", classDays)
  val classWeekAsJson = Json.toJson[FitnessClassWeek](classWeek)

  "Schedules" should {

    "insert a valid json schedule" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/schedule/pilates/create")
          .withJsonBody(classWeekAsJson)
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        result.header.status must equalTo(CREATED)
      }
    }

    "update a valid json schedule" in {
      running(FakeApplication()) {
        val updateRequest = FakeRequest.apply(PUT, "/schedule/pilates/50181f15e0f8477d00a5859e")
          .withJsonBody(classWeekAsJson)
        val updateResponse = route(updateRequest)
        updateResponse.isDefined mustEqual true
        val updateResult = Await.result(updateResponse.get, timeout)
        updateResult.header.status must equalTo(CREATED)
      }
    }


  }
}
