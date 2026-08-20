package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jobtrackai.core.database.entity.InterviewAnswerEntity
import com.jobtrackai.core.database.entity.InterviewQuestionEntity
import com.jobtrackai.core.database.entity.InterviewSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AIDao {

    @Insert
    suspend fun insertSession(session: InterviewSessionEntity)

    @Insert
    suspend fun insertQuestions(questions: List<InterviewQuestionEntity>)

    @Insert
    suspend fun insertAnswer(answer: InterviewAnswerEntity)

    @Query("SELECT * FROM ai_sessions WHERE userId = :userId ORDER BY date DESC")
    fun getSessions(userId: String): Flow<List<InterviewSessionEntity>>

    @Query("SELECT * FROM ai_sessions WHERE sessionId = :sessionId")
    suspend fun getSessionById(sessionId: String): InterviewSessionEntity?

    @Query("SELECT * FROM ai_questions WHERE sessionId = :sessionId ORDER BY `order` ASC")
    suspend fun getQuestionsForSession(sessionId: String): List<InterviewQuestionEntity>

    @Query("SELECT * FROM ai_answers WHERE sessionId = :sessionId")
    suspend fun getAnswersForSession(sessionId: String): List<InterviewAnswerEntity>
}
