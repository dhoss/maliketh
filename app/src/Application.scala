package app
object Application extends cask.MainRoutes{
  @cask.get("/")
  def hello() = {
    "Hello World!"
  }

  override def host = "0.0.0.0"

  initialize()
}
