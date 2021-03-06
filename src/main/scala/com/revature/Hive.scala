package com.revature

import org.apache.spark.sql.SparkSession

object Hive {

    def main(args: Array[String]): Unit = {
        connect()
        displayAPIQueries("1")
    }

    private var spark: SparkSession = _

    def connect(): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\hadoop")
        spark = SparkSession
            .builder()
            .appName("Best Books API")
            .config("spark.master", "local")
            .enableHiveSupport()
            .getOrCreate()
        println("created spark session")

        spark.sparkContext.setLogLevel("ERROR")
        spark.sql("Set hive.exec.dynamic.partition.mode=nonstrict")

        spark.sql("DROP TABLE IF EXISTS bestbooks_table")
        spark.sql("CREATE TABLE IF NOT EXISTS bestbooks_table(Name String, Author String, UserRating double, Reviews Int, Price Int, Year Int, Genre String) Row format delimited fields terminated by ',' stored as textfile")
        //spark.sql("CREATE TABLE IF NOT EXISTS bestbooks_table_parti(Name String, Author String, UserRating double, Reviews Int, Price Int) Row format delimited fields terminated by ',' stored as textfile  tblproperties(\"skip.header.line.count\"=\"1\") partitioned by (Year) clustered by (Genre) into 2 buckets")

        /*spark.sql("Load data Local Inpath 'C:/Users/aaron/Documents/revature-trainingFeb282022/ProjectsBigData/githubrepositoriesforProjects/Project1/bestbooks_table_data.csv' into table bestbooks_table")
    }*/
        spark.sql("Load data Local Inpath 'bestbooks_table_data.csv' into table bestbooks_table")
    }

    def displayAPIQueries(selectedQuery: String): Unit = {
        //DONE: Use something similar to a switch statement to run/ set up each query
        selectedQuery match {
            case "1" => spark.sql("Select count (Distinct genre) as Genre_Types from bestbooks_table").show()
            case "2" => spark.sql("Select Name, UserRating as User_Rating from bestbooks_table order by UserRating desc").show(50)
            case "3" => spark.sql("Select Name as Book, UserRating as User_Rating from bestbooks_table where year = 2015 order by UserRating desc").show(20)
            case "4" => spark.sql("Select Distinct count(Author) as Unique_Authors From bestbooks_table order by Author Desc").show()
            case "5" => spark.sql("Select Name as Book_Title, Price as Price, Author as Author From bestbook_table order by Price desc").show()
            case "6" => spark.sql("Select Name as Book_Title, Reviews as Reviews from bestbooks_table order by Reviews asc").show(40)
            case "7" => spark.sql("Select * From bestbooks_table").show(50)

        }
    }
}
