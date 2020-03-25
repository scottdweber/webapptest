package com.proquest.interview.phonebook

import com.nhaarman.mockitokotlin2.*
import com.proquest.interview.util.DatabaseConnectionFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class PhoneBookPersistentStorageTest {
    @Test
    fun addExecutesCorrectInsertStatement() {
        val mockStatement = mock<Statement>()
        val mockConnection = mock<Connection> {
            on { createStatement() } doReturn mockStatement
        }
        val factory = mock<DatabaseConnectionFactory> {
            on { getConnection() } doReturn mockConnection
        }
        val database = PhoneBookPersistentStorage(factory)

        val person = Person("John Doe", "123-456-7890", "123 Main Street")
        database.addPersonAsync(person)

        verify(mockStatement).execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES ('${person.name}', '${person.phoneNumber}', '${person.address}')")
    }

    @Test
    fun findReturnsPersonFromDatabase() {
        val person = Person("John Doe", "123-456-7890", "123 Main Street")
        val mockResultSet = mock<ResultSet> {
            on { next() } doReturn true
            on { getString(1) } doReturn person.name
            on { getString(2) } doReturn person.phoneNumber
            on { getString(3) } doReturn person.address
        }
        val mockStatement = mock<Statement> {
            on { executeQuery(any()) } doReturn mockResultSet
        }
        val mockConnection = mock<Connection> {
            on { createStatement() } doReturn mockStatement
        }
        val factory = mock<DatabaseConnectionFactory> {
            on { getConnection() } doReturn mockConnection
        }
        val database = PhoneBookPersistentStorage(factory)

        val actualPerson = database.findPerson("John", "Doe")
        assertEquals(person, actualPerson)
    }

    @Test
    fun findReturnsNullWhenPersonNotInDatabase() {
        val mockResultSet = mock<ResultSet> {
            on { next() } doReturn false
        }
        val mockStatement = mock<Statement> {
            on { executeQuery(any()) } doReturn mockResultSet
        }
        val mockConnection = mock<Connection> {
            on { createStatement() } doReturn mockStatement
        }
        val factory = mock<DatabaseConnectionFactory> {
            on { getConnection() } doReturn mockConnection
        }
        val database = PhoneBookPersistentStorage(factory)

        val actualPerson = database.findPerson("John", "Doe")
        assertNull(actualPerson)
    }

    @Test
    fun getAllReturnsListFromDatabase() {
        val mockResultSet = mock<ResultSet> {
            on { next() } doReturnConsecutively listOf(true, true, false)
            on { getString(1) } doReturnConsecutively listOf("John Smith", "Jane Doe")
            on { getString(2) } doReturnConsecutively listOf("123", "456")
            on { getString(3) } doReturnConsecutively listOf("Main Street", "Somewhere Else")
        }
        val mockStatement = mock<Statement> {
            on { executeQuery(any()) } doReturn mockResultSet
        }
        val mockConnection = mock<Connection> {
            on { createStatement() } doReturn mockStatement
        }
        val factory = mock<DatabaseConnectionFactory> {
            on { getConnection() } doReturn mockConnection
        }
        val database = PhoneBookPersistentStorage(factory)

        val persons = database.getAllPersons()

        assertEquals(2, persons.size)
    }
}