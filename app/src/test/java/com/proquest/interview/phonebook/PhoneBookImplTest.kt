package com.proquest.interview.phonebook

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PhoneBookImplTest {
    @Test
    fun shouldAddFindPerson() {
        val phonebook = PhoneBookImpl()
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        phonebook.addPerson(person)

        val actualPerson = phonebook.findPerson("John", "Doe")
        assertEquals(actualPerson, person)
    }

    @Test
    fun shouldNotFindPerson_whenPersonNotAdded() {
        val phonebook = PhoneBookImpl()
        val person = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        phonebook.addPerson(person)

        val actualPerson = phonebook.findPerson("Jane", "Doe")
        assertNull(actualPerson)
    }

    @Test
    fun shouldFindCorrectPerson_whenMultipleAdded() {
        val phonebook = PhoneBookImpl()
        val expectedPerson = Person("John Doe", "123-456-7890", "123 Evergreen Terrace")
        val anotherPerson = Person("Johannes Doe", "321-456-7890", "321 Evergreen Terrace")
        phonebook.addPerson(expectedPerson)
        phonebook.addPerson(anotherPerson)

        val actualPerson = phonebook.findPerson("John", "Doe")
        assertEquals(actualPerson, expectedPerson)
    }
}