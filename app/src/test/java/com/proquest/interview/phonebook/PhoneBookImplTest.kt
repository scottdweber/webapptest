package com.proquest.interview.phonebook

import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Test

class PhoneBookImplTest {
    @Test
    fun shouldAddFindPerson() {
        val phonebook = PhoneBookImpl(mock())
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        phonebook.addPerson(person)

        val actualPerson = phonebook.findPerson("John", "Doe")
        assertEquals(actualPerson, person)
    }

    @Test
    fun shouldNotFindPerson_whenPersonNotAdded() {
        val phonebook = PhoneBookImpl(mock())
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        phonebook.addPerson(person)

        val actualPerson = phonebook.findPerson("Jane", "Doe")
        assertNull(actualPerson)
    }

    @Test
    fun shouldFindCorrectPerson_whenMultipleAdded() {
        val phonebook = PhoneBookImpl(mock())
        val expectedPerson = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        val anotherPerson = Person("Johannes Doe", "321-456-7890", "321 Evergreen Terrace")
        phonebook.addPerson(expectedPerson)
        phonebook.addPerson(anotherPerson)

        val actualPerson = phonebook.findPerson("John", "Doe")
        assertEquals(actualPerson, expectedPerson)
    }

    @Test
    fun shouldAddToDatabase() {
        val database = mock<PersistentStorage>()
        val phonebook = PhoneBookImpl(database)
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")

        phonebook.addPerson(person)

        verify(database).addPersonAsync(person)
    }

    @Test
    fun shouldNotLookInDatabase_ifPersonIsInMemory() {
        val database = mock<PersistentStorage>()
        val phonebook = PhoneBookImpl(database)
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")

        phonebook.addPerson(person)

        val actualPerson = phonebook.findPerson("John", "Doe")
        assertEquals(actualPerson, person)

        verify(database, never()).findPerson(any(), any())
    }

    @Test
    fun shouldRetrieveFromDatabase_ifPersonIsNotInMemory() {
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        val database = mock<PersistentStorage> {
            on { findPerson(any(), any()) } doReturn person
        }
        val phonebook = PhoneBookImpl(database)

        val actualPerson = phonebook.findPerson("John", "Doe")
        verify(database).findPerson("John", "Doe")
        assertEquals(actualPerson, person)
    }

    @Test
    fun getAllPersons_getsDataFromDatabase() {
        val persons = listOf(Person("", "", ""))
        val database = mock<PersistentStorage> {
            on { getAllPersons() } doReturn persons
        }
        val phonebook = PhoneBookImpl(database)

        val actualList = phonebook.getAllPersons()
        assertSame(persons, actualList)
    }
}