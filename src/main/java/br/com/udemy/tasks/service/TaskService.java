package br.com.udemy.tasks.service;

import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.repository.TaskCustomRepository;
import br.com.udemy.tasks.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;

    private final TaskCustomRepository taskCustomRepository;

    public TaskService(TaskRepository repository, TaskCustomRepository taskCustomRepository){
        this.repository = repository;
        this.taskCustomRepository = taskCustomRepository;
    }

    public Mono<Task> insert(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save)
//                .flatMap(it -> doError())
                .doOnError(error -> LOG.error("Error during save task, Title: {}", task.getTitle(), error));
    }

//    public Mono<Task> doError(){
//        return Mono.error(RuntimeException::new);
//    }

    public Page<Task> findPaginated(Task task, int pageNumber, int pageSize){
        return taskCustomRepository.findPaginated(task, pageNumber, pageSize);
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .doOnNext(t -> LOG.info("Saving task with title {}", t.getTitle()))
                .map(repository::save);
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(()-> repository.deleteById(id));
    }
}
