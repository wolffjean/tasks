package br.com.udemy.tasks.controller;

import br.com.udemy.tasks.controller.converter.TaskDTOConverter;
import br.com.udemy.tasks.controller.converter.TaskInsertDTOConverter;
import br.com.udemy.tasks.controller.converter.TaskUpdateDTOConverter;
import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.controller.dto.TaskInsertDTO;
import br.com.udemy.tasks.controller.dto.TaskUpdateDTO;
import br.com.udemy.tasks.model.TaskState;
import br.com.udemy.tasks.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    private final TaskService service;
    private final TaskDTOConverter converter;

    private final TaskInsertDTOConverter insertConverter;

    private final TaskUpdateDTOConverter updateConverter;

    public TaskController(TaskService service, TaskDTOConverter converter, TaskInsertDTOConverter insertConverter, TaskUpdateDTOConverter updateConverter) {
        this.service = service;
        this.converter = converter;
        this.insertConverter = insertConverter;
        this.updateConverter = updateConverter;
    }

    @GetMapping
    public Mono<Page<TaskDTO>> geTasks(@RequestParam(required = false) String id,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false, defaultValue = "0") int priority,
                                       @RequestParam(required = false)TaskState taskState,
                                       @RequestParam(value="pageNumber", defaultValue = "0") Integer pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return service.findPaginated(converter.convert(id, title, description, priority,taskState), pageNumber, pageSize).map(it -> it.map(converter::convert));
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody TaskInsertDTO taskInsertDTO){
        return service.insert(insertConverter.convert(taskInsertDTO))
                 .doOnNext(it -> LOG.info("Saved task with id {}", it.getId()))
                 .map(converter::convert);
    }


    @PutMapping
    public Mono<TaskDTO> updateTask(@RequestBody TaskUpdateDTO updateDTO){
        return service.update(updateConverter.convert(updateDTO))
                .doOnNext(it -> LOG.info("Update task with id {}", it.getId()))
                .map(converter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTask(@PathVariable String id){

        return Mono.just(id).doOnNext(it -> LOG.info("Deleting task with id {}", id)).flatMap(service::deleteById);
    }
}
