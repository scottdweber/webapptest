package com.proquest.interview.phonebook

interface PhoneBook {
    fun findPerson(firstName: String, lastName: String): Person?
    fun getAllPersons(): List<Person>
    fun addPerson(newPerson: Person)
}