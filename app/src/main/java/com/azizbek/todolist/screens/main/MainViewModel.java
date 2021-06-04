package com.azizbek.todolist.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.azizbek.todolist.App;
import com.azizbek.todolist.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }

    private LiveData<List<Note>> progressTasks = App.getInstance().getNoteDao().getAllProgressTasks();

    public LiveData<List<Note>> getProgressTasks() {
        return progressTasks;
    }

    private LiveData<List<Note>> completedTasks = App.getInstance().getNoteDao().getAllCompletedTasks();

    public LiveData<List<Note>> getCompletedTasks() {
        return completedTasks;
    }

}
