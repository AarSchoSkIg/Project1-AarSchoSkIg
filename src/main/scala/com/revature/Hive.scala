package com.revature

import com.revature.UserDataBase.connect
import org.apache.spark.sql.SparkSession

object Hive {

    def main(args: Array[String]): Unit = {
        connect()
        displayAPIQueries("1")
    }
    private var spark:SparkSession = _
    def connect() : Unit = {
        System.setProperty("hadoop.home.dir", "C:\\hadoop")
        spark = SparkSession
            .builder()
            .appName("Best Books API")
            .config("spark.master", "local")
            .enableHiveSupport()
            .getOrCreate()

        spark.sparkContext.setLogLevel("ERROR")
    }

    def displayAPIQueries(str: String): Unit = {
        //To DO: Use something simiair to a switch statement to run/ set up each query
        //Query 1

        //Query 2

        //Query 3

        //Query 4
        //Query 5
        //Query 6


    }
}
