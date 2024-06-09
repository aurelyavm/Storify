package com.example.submissionawalstoryapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionawalstoryapp.data.response.DetailStoryList

@Dao
interface DetailStoryListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<DetailStoryList>)

    @Query("SELECT * FROM stories")
    fun getAllStories(): PagingSource<Int, DetailStoryList>

    @Query("SELECT * FROM stories")
    fun getAllListStories(): List<DetailStoryList>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}