package com.practiceapp.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Feedback(
        @Id var feedbackId: Long = 0,
        var id: String? = null,
        var upVote: Int = 0,
        var downVote: Int = 0,
        var isRead: Boolean = false
)