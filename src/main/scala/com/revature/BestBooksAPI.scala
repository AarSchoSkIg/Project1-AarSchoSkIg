package com.revature

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.sql.{Connection, DriverManager}
import java.util.Scanner


object BestBooksAPI {

  //global variables
  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/bestbooksAPIproject1"
  val username = "root"
  val password = "L0nkL1nkl3!"
  var scanner = new Scanner(System.in)
  var connection: Connection = null
  System.setSecurityManager(null)
  System.setProperty("hadoop.home.dir", "C:\\hadoop\\")
  var Quit = false
  var menuAdminQuit = false
  var menuUserQuit = false
  var correctChoice = false
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

  def main(args: Array[String]): Unit = {

    mainMenu()
  }
}
