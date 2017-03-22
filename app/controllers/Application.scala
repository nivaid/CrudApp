package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.Play.current
import play.api.i18n.Messages.Implicits._


@Singleton
class Application @Inject()(val messagesApi: MessagesApi) extends Controller {

	def userForm = Form(
		mapping("First Name" -> nonEmptyText,
			"Last Name" -> nonEmptyText,
			"Phone Number" -> text,
			"Address" -> nonEmptyText)(UsersForm.apply)(UsersForm.unapply))

	def loginForm = Form(
		mapping("First Name" -> nonEmptyText,
			"Last Name" -> nonEmptyText,
			"Password" -> text
			)(LoginForm.apply)(LoginForm.unapply))
	
	def index = Action {
				Ok(views.html.index(userForm))
			}
	
	
	def createUser = Action { implicit request =>
		userForm.bindFromRequest().fold(
			formWithErrors => BadRequest("INSERT FAILED"),
			ok => {
				val userObj = Users(0, ok.fname, ok.lname, ok.pno, ok.address)
				User.insert(userObj)
				Ok(views.html.usercreated(userObj))
			}

		)
	}


	def delete(id: Long) = Action {
    	User.delete(id)
		Redirect("http://localhost:9000/disp")
	}

	def disp = Action{
		Ok(views.html.disp(User.getAllWithParser))
	}

	def login = Action{
		Ok(views.html.login(loginForm))
	}

}
	

	 





