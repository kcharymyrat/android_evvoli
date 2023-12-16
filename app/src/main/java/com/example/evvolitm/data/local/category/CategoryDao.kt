package com.example.evvolitm.data.local.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryList(categoryList: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryItem(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategoryItem(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categoryentity")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categoryentity WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Query("SELECT * FROM categoryentity WHERE slug = :slug")
    suspend fun getCategoryBySlug(slug: String): CategoryEntity

    @Query("DELETE FROM categoryentity")
    suspend fun deleteCategoryList()

    @Query("DELETE FROM categoryentity WHERE id = :id OR slug = :slug")
    suspend fun deleteCategoryByIdOrSlug(id: String, slug: String = "")
}