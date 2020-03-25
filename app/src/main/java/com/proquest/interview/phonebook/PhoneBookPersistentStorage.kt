package com.proquest.interview.phonebook

import com.proquest.interview.util.DatabaseConnectionFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhoneBookPersistentStorage(
    private val connectionFactory: DatabaseConnectionFactory,
    private val coroutineScope: CoroutineScope
) : PersistentStorage {
    override fun addPersonAsync(newPerson: Person) {
        coroutineScope.launch {
            try {
                val connection = connectionFactory.getConnection()
                connection.createStatement()
                    .execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES ('${newPerson.name}', '${newPerson.phoneNumber}', '${newPerson.address}')")
                connection.commit()
                connection.close()
            } catch (e: Exception) {
                // TODO: retry?
                e.printStackTrace()
                // TODO: move cleanup to finally block
            }
        }
    }

    override fun findPerson(firstName: String, lastName: String): Person? {
        try {
            val connection = connectionFactory.getConnection()
            val stmt = connection.createStatement()
            val rs = stmt.executeQuery("""
                SELECT NAME, PHONENUMBER, ADDRESS
                FROM PHONEBOOK
                WHERE NAME='$firstName $lastName'
                LIMIT 1
                """)

            var person: Person? = null
            if (rs.next()) {
                person = Person(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
                )
            }

            rs.close()
            stmt.close()
            connection.close()

            return person
        } catch (e: Exception) {
            // TODO: retry?
            e.printStackTrace()
            return null
        }
    }

    override fun getAllPersons(): List<Person> {
        try {
            val connection = connectionFactory.getConnection()
            val stmt = connection.createStatement()
            val rs = stmt.executeQuery("""
                SELECT NAME, PHONENUMBER, ADDRESS
                FROM PHONEBOOK
                """)

            val persons = mutableListOf<Person>()
            while (rs.next()) {
                persons.add(
                    Person(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                    )
                )
            }

            rs.close()
            stmt.close()
            connection.close()

            return persons
        } catch (e: Exception) {
            // TODO: retry?
            e.printStackTrace()
            return emptyList()
        }
    }
}