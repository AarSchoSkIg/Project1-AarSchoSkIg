package com.revature

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.sql.{Connection, DriverManager}
import java.util.Scanner


object BestBooksMain {

    def main(args: Array[String]): Unit = {

        UserDataBase.connect()
        Hive.connect()
        mainStartUpMenu()
    }




    def mainStartUpMenu(): Unit = {
        println("Welcome one and all to a fantastic place to get all the best news about your favorite books, all in one place!")
        println("")
        println("Welcome to the Best Books Application Interface")
        println("")
        println("To begin please select from the following options: ")
        println("")
        println{"Option 1: Create New Account" +
                "Option 2: Login as an existing User" +
                "Option 3: Exit the app (We will be sad to see you go, but we get it)"
        }
        val option = scala.io.StdIn.readLine()
        option match {
            case "1" =>  signIn()
            case "2" =>  signUp()
            case "3" =>  exitApp()
            case _   => mainStartUpMenu()
        }
    }

    def signIn(): Unit = {
        println("You have chosen to sign in. To start Please Enter in your username")
        println("Enter in username now.")
        val usersUserName = scala.io.StdIn.readLine()
        println("Now enter in the password for your account")
        val usersPassword = scala.io.StdIn.readLine()
        val validateLogin = UserDataBase.validateLogin(usersUserName, usersPassword)
        if (validateLogin) {
             var amAdmin = UserDataBase.checkIsAdmin(usersUserName)
                if(amAdmin == 1){
                    adminMenu()
                }else if (amAdmin == 0){
                    basicUserMenu()
                }else{
                    println("Something went wrong please contact an admin, going back to start up menu")
                    mainStartUpMenu()
                }
        }
    }

    def signUp(): Unit = ???

    def adminMenu() = ???

    def basicUserMenu() = ???

    def exitApp(): Unit = ???

}
