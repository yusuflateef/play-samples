package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc._
import services.session.SessionService

@Singleton
class LogoutController @Inject()(sessionService: SessionService,
                                 cc: ControllerComponents) extends AbstractController(cc) {


  def logout = Action {  implicit request: Request[AnyContent] =>
    // When we delete the session id, removing the secret key is enough to render the
    // user info cookie unusable.
    request.session.get("sessionId").foreach { sessionId =>
      sessionService.delete(sessionId)
    }

    CookieStripper.logout {
      Redirect(routes.HomeController.index())
    }
  }

}
