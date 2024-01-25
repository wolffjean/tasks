package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.TaskController;
import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskDTOConverter {



    public TaskDTO convert(Task task) {
        return Optional.ofNullable(task)
                .map(source -> {
                        TaskDTO dto = new TaskDTO();
                        dto.setId(source.getId());
                        dto.setTitle(source.getTitle());
                        dto.setDescription(source.getDescription());
                        dto.setPriority(source.getPriority());
                        dto.setState(source.getState());
                        dto.setAddress(source.getAddress());
                        return dto;
                        }
                )
                .orElse(null);
    }

    public Task convert(TaskDTO dto){
        return Optional.ofNullable(dto)
                .map(source ->
                    Task.builder()
                            .withId(source.getId())
                            .withDescription(source.getDescription())
                            .withPriority(source.getPriority())
                            .withState(source.getState())
                            .withTitle(source.getTitle()).build())
                .orElse(null);
    }


    public Task convert(String id, String title, String description, int priority, TaskState taskState){
        return Task.builder()
                .withId(id)
                .withTitle(title)
                .withState(taskState)
                .withDescription(description)
                .withPriority(priority).build();
    }



}
