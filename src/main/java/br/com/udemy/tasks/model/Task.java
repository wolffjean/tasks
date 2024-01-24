package br.com.udemy.tasks.model;


import br.com.udemy.tasks.service.TaskService;
import org.springframework.data.annotation.Id;

public class Task {

    @Id
    private String id;

    private String title;

    private String description;

    private int priority;

    private TaskState state;

    public Task(){

    }
    public Task(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.state = builder.state;
        this.id = builder.id;
    }

    public Task insert(){
        return builderFrom(this)
                          .withState(TaskState.INSERT)
                          .build();
    }

    public Task update(Task oldTask){
        return builderFrom(this)
                .withState(oldTask.getState())
                .build();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public int getPriority() {
        return priority;
    }

    public TaskState getState() {
        return state;
    }


    public static Builder builder(){
        return new Builder();
    }
    public static Builder builderFrom(Task task){
        return new Builder(task);
    }

    public static class Builder {

        private String id;

        private String title;

        private String description;

        private int priority;

        private TaskState state;

        public Builder(){

        }

        public Builder(Task task) {
            this.state = task.state;
            this.priority = task.priority;
            this.description = task.description;
            this.title = task.title;
            this.id = task.id;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public Builder withPriority(int priority){
            this.priority = priority;
            return this;
        }

        public Builder withState(TaskState state){
            this.state = state;
            return this;
        }

        public Task build(){
            return new Task(this);
        }

    }
}
