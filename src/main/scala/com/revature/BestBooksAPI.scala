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
    val sprk = SparkSession
        .builder()
        .appName("Project1 BestBooks API HIVE")
        .config("spark.master", "local")
        .enableHiveSupport()
        .getOrCreate()
    println("Creating spark session...")
    sprk.sparkContext.setLogLevel("ERROR")

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
                    var userUpdate = statement1.executeUpdate( "UPDATE USERS SET user_type = \"ADMIN\" where userName = \""+users+"\";")
                }else if (users == "0"){
                    println("Returning to Admin Menu")
                    accurateChoice = true
                    menuAdminQuit = false
                    legitimateUser = true
                } else {
                    accurateChoice = false
                }
            }
        }
        //creating Main Menu & options


        def main(args: Array[String]): Unit = {

            mainMenu()
        }
    }
}