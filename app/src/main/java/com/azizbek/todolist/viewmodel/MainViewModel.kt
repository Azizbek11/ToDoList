package com.azizbek.todolist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.azizbek.todolist.App;
import com.azizbek.todolist.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().allLiveData();
    private final LiveData<List<Note>> completedTasks = App.getInstance().getNoteDao().allCompletedTasks();
    private final LiveData<List<Note>> progressedTasks = App.getInstance().getNoteDao().allProgressTasks();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
    public LiveData<List<Note>> getCompletedTasks() {
        return completedTasks;
    }
    public LiveData<List<Note>> getProgressedTasks() {
        return progressedTasks;
    }
}
