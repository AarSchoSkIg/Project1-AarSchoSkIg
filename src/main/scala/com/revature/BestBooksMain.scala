package com.revature

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.sql.{Connection, DriverManager}
import java.util.Scanner
import scala.annotation.tailrec


object BestBooksMain {

    def main(args: Array[String]): Unit = {

        UserDataBase.connect()
        Hive.connect()
        mainStartUpMenu()
    }


    @tailrec
    def mainStartUpMenu(): Unit = {
        println("Welcome one and all to a fantastic place to get all the best news about your favorite books, all in one place!")
        println("")
        println("Welcome to the Best Books Application Interface")
        println("")
        println("To begin please select from the following options: ")
        println("")
        println {
            "Option 1: Create New Account" +
                "Option 2: Login as an existing User" +
                "Option 3: Exit the app (We will be sad to see you go, but we get it)"
        }
        val option = scala.io.StdIn.readLine()
        option match {
            case "1" => signIn()
            case "2" => signUp()
            case "3" => exitApp()
            case _ => mainStartUpMenu()
        }
    }

    def signIn(): Unit = {
        println("You have chosen option 1, to sign in. To start Please Enter in your username")
        println("Enter in username now.")
        val usersUserName = scala.io.StdIn.readLine()
        println("Now enter in the password for your account")
        val usersPassword = scala.io.StdIn.readLine()
        val validateLogin = UserDataBase.validateLogin(usersUserName, usersPassword)
        if (validateLogin) {
            var amAdmin = UserDataBase.checkIsAdmin(usersUserName)
            if (amAdmin == 1) {
                adminMenu()
            } else if (amAdmin == 0) {
                basicUserMenu()
            } else {
                println("Something went wrong please contact an admin, going back to start up menu")
                mainStartUpMenu()
            }
        }
    }

    def signUp(): Unit = {
        println("YOu have chosen option 2, create an account to access database")
        println("This is the beginning of the form you will have ot fill out to create an account.")
        println("Please enter in your first name")
        val firstName = scala.io.StdIn.readLine()
        println("Please enter in your last name")
        val lastName = scala.io.StdIn.readLine()
        println("Please enter in your username")
        val userUserName = scala.io.StdIn.readLine()
        println("Please enter in your password")
        val usersPassword = scala.io.StdIn.readLine()
        val adminAuth = 0
        val newAccount = UserDataBase.creationOfUsers(firstName, lastName, userUserName, usersPassword, adminAuth)
        if (newAccount == 1){
            println("You have successfully created an account")
            var newAccountUser = userUserName
            basicUserMenu()
        }else mainStartUpMenu()

    }

    def adminMenu(): Unit = {

    }

    def basicUserMenu() = ???

    def exitApp(): Unit = ???

}
