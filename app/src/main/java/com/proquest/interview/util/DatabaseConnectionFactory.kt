package com.proquest.interview.util

open class DatabaseConnectionFactory {
    open fun getConnection() = DatabaseUtil.getConnection()
}