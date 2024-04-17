package com.example.mytodoapp.model

import java.io.Serializable

data class Task(var name: String) : Serializable {
    var id: Int? = null
    var title: String? = null
    var description: String? = null


    constructor(id: Int, title: String, description: String) : this(title) {
        this.id = id
        this.title = title
        this.description = description

    }
}