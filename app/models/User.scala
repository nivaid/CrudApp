package models

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import anorm._
import anorm.SqlParser._
import play.api.db.DB

case class Users(id: Long, fname: String , lname: String , pno: String , address: String)

case class UsersForm(fname: String , lname: String , pno: String , address: String)

case class LoginForm(fname: String,lname: String,pwd: String)

object User{
	/***This will be used to insert data into the database***/
	def insert(user: Users): Boolean =
	DB.withConnection { implicit connection =>
		val addedRows = SQL("""insert
			into UserTable(fname,lname,pno,address)
			values ({fname}, {lname}, {pno}, {address})""").on(
			"fname" -> user.fname,
			"lname" -> user.lname,
			"pno" -> user.pno,
			"address" -> user.address
			).executeUpdate()
			addedRows == 1
		}
	/*************End of Insert Code*********************/

	/***This will be used to update data into the database***/
		def update(user: Users): Boolean =
		DB.withConnection { implicit connection =>
			val updatedRows = SQL("""update UserTable
				set name = {fname},
				lname = {lname},
				description = {description}
				where id = {id}
				""").on(
				"id" -> user.fname,
				"name" -> user.lname,
				"ean" -> user.pno,
				"description" -> user.address).
				executeUpdate()
				updatedRows == 1
			}
		/*************End of Update Code*********************/

		/***This will be used to delete data into the database***/
			def delete(id: Long) =
			DB.withConnection { implicit connection =>
				val updatedRows = SQL("delete from UserTable where uid = {id}").
				on("id" -> id).executeUpdate()
				
			}
		/*************End of Delete Code*********************/

		/*****This Will be used to display and fetch SQL Records*****/
			val sql: SqlQuery = SQL("select * from UserTable")

			val usersParser: RowParser[Users] = {
				long("uid") ~
				str("fname") ~
				str("lname") ~
				str("pno") ~
				str("address") map {
					case id ~ fname ~ lname ~ pno ~ address =>
					Users(id, fname, lname, pno, address)
				}
			}
			
			val resultParser: ResultSetParser[List[Users]] = {
				usersParser *
			}

			def getAllWithParser: List[Users] = DB.withConnection {
				implicit connection =>
				sql.as(resultParser)
			}
			
		/*************End of Display Code*********************/


		}