package repositories;

import models.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TasksRepository extends BaseRepository<Task> {

    public TasksRepository() {
        super();
        this.tClass = Task.class;
    }

    public ArrayList<Task> getByUserId(int userId) {
        return this.getAll().stream().filter(t -> t.getUser().getId() == userId).collect(Collectors.toCollection(ArrayList<Task>::new));
    }
}