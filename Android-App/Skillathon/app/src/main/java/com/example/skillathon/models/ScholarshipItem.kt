package com.example.skillathon.models

import java.io.Serializable

data class ScholarshipItem(
    val id:String,
    val name:String,
    val categories:MutableList<String>,
    val description:String,
    val gender:String,
    val course:String,
    val branch:String,
    val deadline:String,
    val income:Long,
    val amount:String,
    val provider:String
): Serializable
