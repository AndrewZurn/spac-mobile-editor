package models

import play.modules.reactivemongo.json.BSONFormats._

/**
 * Created by Andrew on 11/28/14.
 */
case class FitnessClass( name: String,
                  time: String,
                  instructor: String,
                  room: String)