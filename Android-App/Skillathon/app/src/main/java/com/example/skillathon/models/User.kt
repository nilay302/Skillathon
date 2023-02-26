package com.example.skillathon.models

data class User(
    var _id : String,
    var name: String,
    var email: String,
    var mobile: String,
    var profilePicLink: String,
    var resumeLink: String,
    var collegeName: String,
    var course: String,
    var yearOfStudy: String,
    var branch: String,
    var gender: String,
    var category: String
)
