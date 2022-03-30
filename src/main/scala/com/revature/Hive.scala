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

        spark.sql("DROP TABLE IF EXISTS BESTBOOKS_DATA")
        spark.sql("CREATE TABLE IF NOT EXISTS BESTBOOKS_DATA()")
    }

    def displayAPIQueries(selectedQuery: String): Unit = {
        //DONE: Use something similar to a switch statement to run/ set up each query
        selectedQuery match {
            case "1" => spark.sql().show()
            case "2" => spark.sql().show()
            case "3" => spark.sql().show()
            case "4" => spark.sql().show()
            case "5" => spark.sql().show()
            case "6" => spark.sql().show()

        }
    }
}
