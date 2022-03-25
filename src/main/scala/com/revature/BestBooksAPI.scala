package com.revature

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.sql.{Connection, DriverManager}
import java.util.Scanner


object BestBooksAPI {

    //global variables
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/bestbooksproject1db"
    val username = "root"
    val password = "L0nkL1nkl3!"
    var scanner = new Scanner(System.in)
    var connection: Connection = null
    System.setSecurityManager(null)
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\")
    var Quit = false
    var menuAdminQuit = false
    var menuUserQuit = false
    var accurateChoice = false
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    var statement = connection.createStatement()
    var statement1 = connection.createStatement()
    val spark = SparkSession
        .builder()
        .appName("Project1 BestBooks API HIVE")
        .config("spark.master", "local")
        .enableHiveSupport()
        .getOrCreate()
    println("Creating spark session...")
    spark.sparkContext.setLogLevel("ERROR")

    //creating Users method
    def elevateUsertoAdmin(): Unit = {
        menuAdminQuit = false
        accurateChoice = false
        println("Welcome to the option of granting a user admin rights. This guide will go step by step for an easy process")
        var legitimateUser = false
        println("First step is to get the username of the user you want to promote to admin. Enter it now")
        while (legitimateUser == false) {
            scanner.useDelimiter(System.lineSeparator())
            var users1 = scanner.next().toString().toUpperCase()
            var users = users1
            println("Step 2 is to retrieve the username from the database")
            var userRslt = statement.executeQuery("SELECT COUNT(USERNAME) FROM USERS WHERE USERNAME = \"" + users1 + "\";")
            while (userRslt.next()) {
                var userRst1 = userRslt.getString(1)
                if (userRst1 == "1") {
                    print(s" $users1 user has been retrieved from the database; Step 2 complete")
                    legitimateUser = true
                    accurateChoice = true
                    menuAdminQuit = false
                    println("Third step is to change type of user to admin by changing permission in database. Dont worry this is all done for you")
                    println("Step 3 (final step) complete: Admin access has been given to " + users1 + "Congrats admin on a good job.")
                    var userUpdate = statement1.executeUpdate("UPDATE USERS SET user_type = \"ADMIN\" where userName = \"" + users + "\";")
                } else if (users == "0") {
                    println("Returning to Admin Menu")
                    accurateChoice = true
                    menuAdminQuit = false
                    legitimateUser = true
                } else {
                    accurateChoice = false
                }
            }
        }
    }

    //creating login method for users
    def loginUsers(): Unit = {
        var userNamValid = false
        var passwordValid = false
        var finishLogIn = false
        var signedIn = false
        var username1 = ""
        var password1 = ""
        while (finishLogIn == false) {
            println("Welcome to the Best Sellers API!! Provide your username and password to log in.")
            while (userNamValid == false && signedIn == false) {
                print("Enter Username now.")
                scanner.useDelimiter(System.lineSeparator())
                username1 = scanner.next().toString.toUpperCase()
                if (username1 == "0") {
                    println("Returning to Main Menu")
                    println("")
                    userNamValid = true
                    finishLogIn = true
                    signedIn = true
                    passwordValid = true
                } else {
                    val userRes = statement.executeQuery("Select userName from Users;")
                    while (userRes.next() && signedIn == false) {
                        var userRes1 = userRes.getString(1)
                        if (userRes1 == username1) {
                            userNamValid = true
                            signedIn = true
                        } else {
                            userNamValid = false
                            signedIn = false
                        }
                    }
                    if (signedIn == false) {
                        println("Username enter does not match existing username, could be mistyped, to try again go to main menu")
                        println("To return to Main Menu press \"0\" ")
                    } else {}
                }
            }
        }
        while (passwordValid == false) {
            println("")
            println("Please enter your passsword")
            scanner.useDelimiter(System.lineSeparator())
            password1 = scanner.next().toString().toUpperCase()
            if (password1 == "0") {
                println("Returning to Main Menu")
                passwordValid = true
                finishLogIn = true
            } else {
                val pwRes = statement.executeQuery("Select password from Users where userName =\"" + username1 + "\";")
                while (pwRes.next()) {
                    var pwRes1 = pwRes.getString(1)
                    if (pwRes1 == password1) {
                        passwordValid = true
                        signedIn = true
                    } else {
                        println("")
                        println("Invalid Password return to main menu to try again.")
                        println("To return to Main Menu press \"0\" ")
                        passwordValid = false
                    }
                }
            }
        }
        if (userNamValid == true && passwordValid == true && signedIn == true) {
            finishLogIn = true
            println("You have successfully logged in, you may browse the Best Books API" + username1)
            basicUsersMenu(username1)
        } else if (username1 == "0" || password1 == "0") {
            passwordValid = true
            finishLogIn = true
            userNamValid = true
            signedIn = true
        }
    }

    //method for creating new Users
    //method for basicUserMenu
    def basicUsersMenu(username1: String): Unit = {
        ???
    }

    //creating Main Menu & options
    def main(args: Array[String]): Unit = {

        mainMenu()
    }
}
