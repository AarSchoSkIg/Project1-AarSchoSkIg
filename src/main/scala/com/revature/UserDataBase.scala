package com.revature

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

object UserDataBase {

    val url = "jdbc:mysql://localhost:3306/bestbooksproject1db"
    val driver = "com.mysql.cj.jdbc.Driver"
    val username = "root"
    val password = "L0nkL1nkl3!"
    private var connection: Connection = null
    var statmnt: Statement = null
    var resultSet: ResultSet = null

    def connect(): Unit = {
        if (connection == null) {
            try {
                Class.forName(driver)
                connection = DriverManager.getConnection(url, "root", "L0nkL1nkl3!")

            } catch {
                case e: Exception => e.printStackTrace()
            }
        } else if (connection.isClosed) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, "root", "L0nkL1nkl3!");
            }catch {
                case e: SQLException => e.printStackTrace();
            }
        }
    }
        def creationOfUsers(firstName: String, lastName: String, usersUserName: String, usersPassword: String, adminAuth: Int): Int = {
        var rsSet = 0
        var pstmt =  connection.prepareStatement(s"Insert into users (FIRSTNAME, LASTNAME, USERNAME, PSWD, AMADMIN) Values(?, ?, ?, ?, ?)")
        try {
            pstmt.setString(1, firstName)
            pstmt.setString(2, lastName)
            pstmt.setString(3, usersUserName)
            pstmt.setString(4, usersPassword)
            pstmt.setInt(5, adminAuth)
            rsSet = pstmt.executeUpdate()
            println("The user account has been created. Exuberant!!!!")
            //showAllUsers()
            rsSet
        }catch {
            case e: SQLException => e.printStackTrace()
                rsSet
        }
    }

    def showAllUsers(): Unit = {
        val pstmt = connection.prepareStatement("SELECT DISTINCT USERNAME FROM USERS")
        try {
            val rsSet = pstmt.executeQuery()
            println("Users")
            while (rsSet.next()) {
                println(rsSet.getString("USERNAME"))
            }
        }  catch {
            case e: Exception => e.printStackTrace()
        }
    }

    def showUser(existingUser: String): Unit = {
        val pstmt = connection.prepareStatement(s"SELECT 1 USERNAME FROM USERS where USERNAME = '$existingUser'")
        try {
            val rsSet = pstmt.executeQuery()
            println("User")
            while (rsSet.next()) {
                println(rsSet.getString("USERNAME"))
            }
        }  catch {
            case e: Exception => e.printStackTrace()
        }
    }


    def updateUsername(newUsersUsername: String, usersUserName: String): Unit = {
        val statemnt =  connection.createStatement()
        val rsSet = statemnt.executeUpdate(s"UPDATE users USERNAME SET USERNAME = '$newUsersUsername' where username = '$usersUserName'")
        if (rsSet == 0){
            println("An Error has occurred please try again.")
        }else{
            println("Username is updated to: " + newUsersUsername)
        }
    }
    def updateLastName(newLastName: String, existingUser: String): Unit = {
        val statemnt =  connection.createStatement()
        val rsSet = statemnt.executeUpdate(s"UPDATE users SET LASTNAME = '$newLastName' where username = '$existingUser'")
        if (rsSet == 0){
            println("An Error has occurred please try again.")
        }else{
            println("Last name successfully updated")
        }
    }
    def elevateUser2Admin(selectedUser: String): Unit = {
        val statemnt =  connection.createStatement()
        val rsSet = statemnt.executeUpdate(s"UPDATE users SET AMADMN = 1 where username = '$selectedUser'")
        if (rsSet == 0){
            println("User no exist, enter existing user.")
        }else{
            println("User updated to Admin, good job!")
        }
    }
    def deleteUser(selectedUser: String): Unit = {
        val statemnt =  connection.createStatement()
        val rsSet = statemnt.executeUpdate(s"Delete From users where username = '$selectedUser'")
        if (rsSet == 0){
            println("User no exist, enter existing user.")
        }else{
            println("User deleted, good bye, thanks for using Database!")
        }
    }
    def validateLogin(usersUserName: String, usersPassword: String): Boolean = {
        //check username in database against passed in variable that user entered same with pass word
        var pstmt = connection.prepareStatement(s"SELECT 1 FROM USERS WHERE USERNAME = [$usersUserName] AND PSWD = [$usersPassword]")
        var validUsername = pstmt.executeQuery()
        try{
            if (validUsername.getInt(1) == 1){
                println("Username and Password are correct. YOu have successfully Logged In to Best Books. WELCOME!!!!")
                print("")
                true

            }else {
                println("Username and Password are incorrect")
                print("")
                false
               }

            }
        catch {
            case sql: java.sql.SQLException =>
                sql.printStackTrace()
                false
        }
    }

    def checkIsAdmin(usersUserName: String): Int = {
        var pstmt = connection.prepareStatement(s"Select AmAdmin from USERS WHERE username = '$usersUserName'")
        var adminAuth = pstmt.executeQuery()
        if (adminAuth.getInt(1) == 1){
            1

        }else{
            0
        }
    }
    /*def masterMenu: Unit = {

    }
    //TO DO add a method for creating the master which supersedes all admins and users*/

    def disconnectFromDB(): Unit = {
        if (!connection.isClosed)
            connection.close()
        if (resultSet != null)
            if (!resultSet.isClosed)
                resultSet.close()
        if (statmnt != null)
            if (!statmnt.isClosed)
                statmnt.close()
    }

}
