package com.example.goodspacesample.data.models

import com.example.goodspacesample.domain.models.JobsModel

data class Data(
    val cardData: CardData,
    val cardType: String,
    val cardVariant: String,
    val type: String
)

fun Data.toDomain() : JobsModel {
    return JobsModel(
        userId = this.cardData.userId,
        jobTitle = this.cardData.title,
        companyName = this.cardData.companyName,
        jobLocation =  this.cardData.location_city,
        displayYearlyCompensation = this.cardData.displayCompensation,
        expLowerWork = this.cardData.lowerworkex,
        expUpperWork = this.cardData.upperworkex,
        jobType = this.cardData.jobType[0].job_type,
        jobPosterName = this.cardData.userInfo.name,
        jobPosterImageUrl = this.cardData.userInfo.image_id
    )
}