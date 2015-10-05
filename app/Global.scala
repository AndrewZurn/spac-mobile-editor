import com.google.inject.{Guice, AbstractModule}
import play.api.GlobalSettings
import services.{SimpleUUIDGenerator, UUIDGenerator}

/**
 * Set up the Guice injector and provide the mechanism for return objects from the dependency graph.
 */
object Global extends GlobalSettings {

  /**
   * Bind types such that whenever UUIDGenerator is required, an instance of SimpleUUIDGenerator will be used.
   */
//  val injector = Guice.createInjector(new AbstractModule {
//    protected def configure() {
//      bind(classOf[UUIDGenerator]).to(classOf[SimpleUUIDGenerator])
//    }
//  })
}
