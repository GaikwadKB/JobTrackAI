package com.jobtrackai.core.database.dummy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dummy")
data class DummyEntity(
    @PrimaryKey val id: Int = 1
)
