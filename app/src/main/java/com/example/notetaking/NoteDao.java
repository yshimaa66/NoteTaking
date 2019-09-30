package com.example.notetaking;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    public List<Note> getAll();

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM note WHERE title LIKE :title AND " +
            "time LIKE :time AND "+"description LIKE :description")
    Note findByName(String title, String time, String description);

    @Insert
    void insert(Note...note);

    @Delete
    void delete(Note...note);

    //Delete one item by id
    @Query("DELETE FROM Note WHERE id = :itemId")
    void deleteByItemId(long itemId);


    @Update
    void update(Note...note);

    @Query("DELETE FROM Note")
    public void deleteall();





}
