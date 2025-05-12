package com.swayni.marvelheroescompose.domain.model


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CharacterModel(): RealmObject {

    @PrimaryKey
    var id : Int = 0
    var name : String = ""
    var description : String = ""
    var imageUrl : String = ""

    constructor(id: Int, name: String, description: String, imageUrl: String) : this() {
        this.id = id
        this.name = name
        this.description = description
        this.imageUrl = imageUrl
    }
}
