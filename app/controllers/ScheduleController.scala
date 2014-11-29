package controllers

import java.util.concurrent.TimeoutException

import models.{FitnessClass, FitnessClassDay, FitnessClassWeek}
import org.slf4j.{LoggerFactory, Logger}
import play.api.data.Form
import play.api.data.Forms.ignored
import play.api.data.Forms.mapping
import play.api.data.Forms.list
import play.api.data.Forms.nonEmptyText
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson._
import views.html

import scala.concurrent.Future

/**
 * Created by Andrew on 11/28/14.
 */
class ScheduleController extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ScheduleController])

  val scheduleForm: Form[FitnessClassWeek] = Form(
    mapping(
      "_id" -> ignored(BSONObjectID.generate: BSONObjectID),
      "name" -> nonEmptyText,
      "classSchedule" -> list(mapping(
        "day" -> nonEmptyText,
        "classes" -> list(mapping(
          "name" -> nonEmptyText,
          "time" -> nonEmptyText,
          "instructor" -> nonEmptyText,
          "room" -> nonEmptyText
        )(FitnessClass.apply)(FitnessClass.unapply))
      )(FitnessClassDay.apply)(FitnessClassDay.unapply))
    )(FitnessClassWeek.apply)(FitnessClassWeek.unapply)
  )

  def findSchedule(classType: String) = Action.async {
    val collection: JSONCollection = getFitnessDatabase(classType)

    val futureSchedule = collection.find(Json.obj("name" -> classType)).one[FitnessClassWeek]
    futureSchedule.map { schedule =>
        Ok(html.schedule(schedule.get._id.toString(), classType, scheduleForm.fill(schedule.get)))
    }.recover {
      case t: TimeoutException =>
        logger.error("Problem in finding schedules, timeout")
        InternalServerError(t.getMessage)
    }
  }

  def create(classType: String) = Action.async { implicit request =>
    val collection: JSONCollection = getFitnessDatabase(classType)

    scheduleForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.createGroupSchedule(scheduleForm))),
      schedule => {
        val futureUpdateSchedule = collection.insert(schedule.copy(_id = BSONObjectID.generate))
        futureUpdateSchedule.map { result =>
          Ok(html.index)
        }.recover {
          case t: TimeoutException =>
            logger.error("Problem in updating schedule, timeout")
            InternalServerError(t.getMessage)
        }
      })
  }

  def update(id: String, classType: String) = Action.async { implicit request =>
    val collection: JSONCollection = getFitnessDatabase(classType)

    scheduleForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.schedule(id, classType, formWithErrors))),
      schedule => {
        val futureUpdateSchedule = collection
          .update(Json.obj("_id" -> Json.obj("$oid" -> id)), schedule.copy(_id = BSONObjectID(id)))
        futureUpdateSchedule.map { result =>
          Ok(html.index)
        }.recover {
          case t: TimeoutException =>
            logger.error("Problem in updating schedule, timeout")
            InternalServerError(t.getMessage)
        }
      })
  }

  def getFitnessDatabase(classType: String): JSONCollection = {
    if (classType == "GROUP") {
      db.collection[JSONCollection]("group_fitness")
    } else if (classType == "SMALL_GROUP") {
      db.collection[JSONCollection]("small_group_fitness")
    } else if (classType == "PILATES") {
      db.collection[JSONCollection]("pilaties_fitness")
    } else {
      throw new IllegalArgumentException("Illegal Class Type Argument, legal values are (GROUP|SMALL_GROUP|PILATES)");
    }
  }

}
