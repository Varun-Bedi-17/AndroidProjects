package com.example.goodspacesample.domain.models

data class JobsModel(
    var userId : Int,
    var jobTitle : String,
    var companyName : String,
    var jobLocation : String,
    var displayYearlyCompensation : String,
    var expLowerWork : Int,
    var expUpperWork : Int,
    var jobType : String,
    var jobPosterName : String,
    var jobPosterImageUrl : String
)