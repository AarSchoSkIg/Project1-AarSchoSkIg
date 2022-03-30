package com.revature

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.sql.{Connection, DriverManager}
import java.util.Scanner
import scala.annotation.tailrec
import scala.sys.exit


object BestBooksMain {
    private var existingUser = ""
    private var selectedUser = ""
    private var amAdmin = 0

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
            case "1" => signUp()
            case "2" => signIn()
            case "3" => exitApp()
            case _ => mainStartUpMenu()
        }
    }

    @tailrec
    def signIn(): Unit = {
        println("You have chosen option 1, to sign in. To start Please Enter in your username")
        println("Enter in username now.")
        var usersUserName = scala.io.StdIn.readLine()
        println("Now enter in the password for your account")
        var usersPassword = scala.io.StdIn.readLine()
        var validateLogin = UserDataBase.validateLogin(usersUserName, usersPassword)
        if (validateLogin) {
            amAdmin = UserDataBase.checkIsAdmin(usersUserName)
            if (amAdmin == 1) {
                adminMenu()
            } else if (amAdmin == 0) {
                basicUserMenu()
                existingUser = usersUserName
            } else {
                println("Something went wrong please contact an admin, going back to start up menu")
                mainStartUpMenu()
            }
        }
        signIn()
    }

    @tailrec
    def signUp(): Unit = {
        println("YOu have chosen option 2, create an account to access database")
        println("This is the beginning of the form you will have ot fill out to create an account.")
        println("Please enter in your first name")
        var firstName = scala.io.StdIn.readLine()
        println("Please enter in your last name")
        var lastName = scala.io.StdIn.readLine()
        println("Please enter in your username")
        var usersUserName = scala.io.StdIn.readLine()
        println("Please enter in your password")
        var usersPassword = scala.io.StdIn.readLine()
        var adminAuth = 0
        var newAccount = UserDataBase.creationOfUsers(firstName, lastName, usersUserName, usersPassword, adminAuth)
        if (newAccount == 1) {
            println("You have successfully created an account")
            existingUser = usersUserName
            mainStartUpMenu()
        } else (mainStartUpMenu())
        signUp()
    }

    @tailrec
    def adminMenu(): Unit = {
        println("YOu have arrived at the Admin menu, Admin, welcome master")
        println("Master what would oyu like me to do? Please select an option 1-5")
        println {
            "Option 1: Delete a bad user's account" +
                "Option 2: Go to menu to show username" +
                "Option 3: elevate User to admin status" +
                "Option 4: Go to menu to change Password" +
                "Option 5: Go to data for books menu (the real version)" +
                "Option 6: Logout (Awww don't leave master, I will miss you. Logging out and going back to start menu)" +
                "Option 7: exit app"
        }
        val option = scala.io.StdIn.readLine()
        option match {
            case "1" => deleteUser()
            case "2" => showUser()
            case "3" => newAdmin()
            case "4" => changeLastName()
            case "5" => RealBooksMenu()
            case "6" => existingUser = ""
                mainStartUpMenu()
            case "7" => exitApp()
            case _ => mainStartUpMenu()
        }
        adminMenu()
    }

    def deleteUser(): Unit = {

        println("Master you have selected the option to delete a user. Enter in the username of the user to delete")
        var userToDelete = scala.io.StdIn.readLine()
        UserDataBase.deleteUser(userToDelete)
        println("User has been deleted from Best Books. Now Going back to admin menu once you Press Enter")
        adminMenu()
    }

    def changeLastName(): Unit = {
        println("You have selected the option to change your last name. Enter in the new last name now")
        var newLastname = scala.io.StdIn.readLine()
        UserDataBase.updateLastName(newLastname, existingUser)
        println("Last name has been successfully updated")
        basicUserMenu()

    }

    def newAdmin(): Unit = {
        println("Master you have selected the option to elevate a user to admin. Enter in the username of the user to elevate to admin")
        selectedUser = scala.io.StdIn.readLine()
        UserDataBase.elevateUser2Admin(selectedUser)
        println("Returning to adminMenu")
        adminMenu()
    }

    def showUser(): Unit = {
        println("Welcome to the menu to check your username")
        UserDataBase.showUser(existingUser)
        basicUserMenu()
    }

    @tailrec
    def basicUserMenu(): Unit = {
        println("Welcome for real to the best app for Books: Best Books")
        println("Below is the menu options to choose from. You will need to select an option 1-5 to proceed to that option")
        println {
            "Option 1: Show your username" +
                "Option 2: Go to menu to change Password" +
                "Option 3: Go to the books Menu to view the first two options" +
                "Option 4: logout" +
                "Option 5: exit app"
        }
        val option = scala.io.StdIn.readLine()
        option match {
            case "1" => showUser()
            case "2" => changeLastName()
            case "3" => booksMenu()
            case "4" => existingUser = ""
                mainStartUpMenu()
            case "5" => exitApp()
            case _ => mainStartUpMenu()
        }
        basicUserMenu()
    }

    def RealBooksMenu(): Unit = {
        println("Choice 1: What are the total number of books for each genre in the years 2009-2019?")
        println("Choice 2: What are the Top 50 highest rated books from 2009-2019?")
        println("Choice 3 :What are the top 20 books based on user ratings in 2015")
        println("Choice 4: What is the total number of Authors from 2009-2019?")
        println("Choice 5: What are the highest Priced books from which Authors during the year 2012?")
        println("Choice 6: What are the 40 lowest reviewed books from 2009-2019")
        var choiceQuery = scala.io.StdIn.readLine()
        choiceQuery match {
            case "1" => Hive.displayAPIQueries("1")
            case "2" => Hive.displayAPIQueries("2")
            case "3" => Hive.displayAPIQueries("3")
            case "4" => Hive.displayAPIQueries("4")
            case "5" => Hive.displayAPIQueries("5")
            case "6" => Hive.displayAPIQueries("6")
        }
    }

    def booksMenu(): Unit = {
        println("Choice 1: What are the total number of books for each genre in the years 2009-2019?")
        println("Choice 2: What are the Top 50 highest rated books from 2009-2019?")
        var choiceQuery = scala.io.StdIn.readLine()
        choiceQuery match {
            case "1" => Hive.displayAPIQueries("1")
            case "2" => Hive.displayAPIQueries("2")

        }
    }


        def exitApp(): Unit = {
            println("Thank you for using Best Books!! Now Exiting the program")
            UserDataBase.disconnectFromDB()
            exit(0)
        }
 }
