package com.proquest.interview.phonebook

interface PersistentStorage {
    fun addPersonAsync(newPerson: Person)
    fun findPerson(firstName: String, lastName: String): Person?
    fun getAllPersons(): List<Person>
}