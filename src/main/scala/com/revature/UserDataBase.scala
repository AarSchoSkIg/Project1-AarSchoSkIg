package com.revature

import java.sql.{Connection, DriverManager}

object UserDataBase {

    private var connection:Connection = _
    def connect(): Unit = {
        val url = "jdbc:mysql://localhost:3306/bestbookapidb"
        val driver = "com.mysql.cj.jdbc.Driver"
        val username = "root"
        val password = "L0nkL1nkl3!"
        try {
            Class.forName(driver)
            connection = DriverManager.getConnection(url, username, password)
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }

    def creationOfUsers(firstName: String, lastName: String, usersUserName: String, usersPassword: String, adminAuth: Int): Unit = {
        val statement =  connection.createStatement()
        val rsSet = statement.executeUpdate(s"Insert into users(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, ADMIN values = ('$firstName', '$lastName', '$usersUserName', '$usersPassword', $adminAuth")
        if (rsSet == 0){
            println("User no exist, enter existing user.")
        }else{
            println("User updated to Admin, good job!")
        }
    }


    def updateUsername(newUsersUsername: String, usersUserName: String): Unit = {
        val statement =  connection.createStatement()
        val rsSet = statement.executeUpdate(s"UPDATE users USERNAME SET USERNAME = '$newUsersUsername' where username = '$usersUserName'")
        if (rsSet == 0){
            println("An Error has occurred please try again.")
        }else{
            println("Username is updated to: " + newUsersUsername)
        }
    }
    def updatePassword(newPassword: String, usersUsername: String): Unit = {
        val statement =  connection.createStatement()
        val rsSet = statement.executeUpdate(s"UPDATE users SET Password = '$newPassword' where username = '$usersUsername'")
        if (rsSet == 0){
            println("An Error has occurred please try again.")
        }else{
            println("Password successfully updated")
        }
    }
    def elevateUser2Admin(id: Int): Unit = {
        val statement =  connection.createStatement()
        val rsSet = statement.executeUpdate(s"UPDATE users SET ADMIN = 1 where UsersId = '$id'")
        if (rsSet == 0){
            println("User no exist, enter existing user.")
        }else{
            println("User updated to Admin, good job!")
        }
    }
    def deleteUser(usersUserName: String): Unit = {
        val statement =  connection.createStatement()
        val rsSet = statement.executeUpdate(s"Delete From users where Username = '$usersUserName'")
        if (rsSet == 0){
            println("User no exist, enter existing user.")
        }else{
            println("User deleted, good bye, thanks for using Database!")
        }
    }
    def validateLogin(usersUserName: String, usersPassword: String): Boolean = {
        //check username in database against passed in variable that user entered same with pass word
        var psmt = connection.prepareStatement(s"SELECT 1 FROM USERS WHERE USERNAME = [$usersUserName] AND PASSWORD = [$usersPassword]")
        var validUsername = psmt.executeQuery()
        try{
            if (validUsername.getInt(1) == 1){
                println("Username and Password are correct. YOu have succesfully Logged In to Best Books. WELCOME!!!!")
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
        var prsmt = connection.prepareStatement(s"Select AmAadmin from USERS WHERE username = '$usersUserName'")
        var adminAuth = prsmt.executeQuery()
        if (adminAuth.getInt(1) == 1){
            1

        }else{
            0
        }
    }

    def closeConnection(): Unit = {
        connection.close()
    }

}
