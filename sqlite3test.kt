@file:DependsOn("org.xerial:sqlite-jdbc:3.21.0.1")

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

fun main(args: Array<String>) {
    Class.forName("org.sqlite.JDBC")
    var connection: Connection? = null
    try {
        // create a database connection
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db")
        val statement = connection!!.createStatement()
        statement.queryTimeout = 30  // set timeout to 30 sec.

        statement.executeUpdate("drop table if exists person")
        statement.executeUpdate("create table person (id integer, name string)")
        statement.executeUpdate("insert into person values(1, 'leo')")
        statement.executeUpdate("insert into person values(2, 'yui')")
        val rs = statement.executeQuery("select * from person")
        while (rs.next()) {
            // read the result set
            println("name = " + rs.getString("name"))
            println("id = " + rs.getInt("id"))
        }
    } catch (e: SQLException) {
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.message)
    } finally {
        try {
            if (connection != null)
                connection.close()
        } catch (e: SQLException) {
            // connection close failed.
            System.err.println(e)
        }
    }
}

