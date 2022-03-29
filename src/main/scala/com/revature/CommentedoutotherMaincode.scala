package com.revature

/*
class CommentedoutotherMaincode {
    /* //global variables
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
       def createNewUser(): Unit = {
           var username1 = ""
           var password1 = ""
           var userNameUnique = false
           var passwordEntered = false
           println("This is the menu to create a new User. Enter it now.")
           scanner.useDelimiter(System.lineSeparator())
           println("What is yoou first name")
           var userFirstName1 = scanner.next().toString().toUpperCase()
           var userFirstName = userFirstName1
           println("What is your last name?. Enter it now.")
           var userLastName1 = scanner.next().toString().toUpperCase()
           var userLastName = userLastName1
           println("Enter in a username to use for your account in the database. The username must be unique for the database.")
           username1 = scanner.next().toString().toUpperCase()
           while (userNameUnique == false) {
               val userRes = statement.executeQuery(" Select count(userName) from Users where username= \"" + username1 + "\" ;")
               while (userRes.next()) {
                   var userRes1 = userRes.getString(1)
                   if (userRes1 == "1") {
                       println("The username oyu typed in is not unique and is taken please try a different one: ")
                       username1 = scanner.next().toString().toUpperCase()
                       userNameUnique = false
                   } else if (username1 == "" || username1 == " ") {
                       println("Username cannot be blank try again.")
                   } else {
                       userNameUnique = true
                   }
               }
           }

           var Newusername = username1
           println("Enter in your password oyu are going ot use for your account")
           password1 = scala.io.StdIn.readLine()
           while (passwordEntered == false) {
               if (password1 == "" || password1 == " ") {
                   println("Username cannot be blank try again.")
                   }else {
                   passwordEntered = true
               }
           }
           var Newpassword = password1
           val addedUser = statement.executeUpdate("Insert Into Users(first_name,last_name,username,password,user_type) values (\"" + userFirstName + "\", \"" + userLastName + "\", \"" + Newusername + "\",\"" +Newpassword+ "\",\"BASIC\");"
           )
           println("Welcome to the NewBookAPI " + Newusername + "!!!" )
           println("Returning to Main Menu")
       }
       //method for basicUserMenu
       def checkLogin(username: String, password: String): Boolean = {
           val driver = "com.mysql.cj.jdbc.Driver"
           val url = "jdbc:mysql://localhost:3306/users"
           val u = "root"
           val p = ""

           Class.forName(driver)
           val connection: Connection = DriverManager.getConnection(url, u, p)
           val statement = connection.createStatement()
           val resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"'")
           resultSet.next()
       }

       //If login info is present, user will be given the option to update or continue.
       //If the user wishes to update, prompt user for new login info and update MySQL database.
       def checkOptions(username: String, password: String) {
           println("Type 'Update' to update your information or 'Continue' to proceed.")
           val infoCheck = scala.io.StdIn.readLine()
           if (infoCheck == "Update") {
               println("What would you like your new username to be? ")
               val newUser = scala.io.StdIn.readLine()
               println("What would you like your new password to be? ")
               val newPass = scala.io.StdIn.readLine()
               updateLogin(username, password, newUser, newPass)
               println("Changes saved.")
               checkPermissions(newUser, newPass)
           }
           else{
               checkPermissions(username, password)
           }
       }

       update Login method
       def updateInfoforLogin(): Unit = {
           var ChangePassword = ""
           var correctChoice = false
           println("This is the menu to change your password for your account")
           while (correctChoice == false){

           }

       }

       //creating Main Menu & options
       def main(args: Array[String]): Unit = {

           mainMenu()
       }

       */
}
*/
