package com.marina.shoppinglist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.marina.shoppinglist.entities.NoteItem
import com.marina.shoppinglist.entities.ShopListNameItem
import com.marina.shoppinglist.entities.ShopListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    @Query("SELECT * FROM shop_list_item WHERE list_id LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>

    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Query("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)

    @Insert
    suspend fun insertShopListName(nameItem: ShopListNameItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateListName(shopListNameItem: ShopListNameItem)
}