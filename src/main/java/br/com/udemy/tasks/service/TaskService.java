package br.com.udemy.tasks.service;

import br.com.udemy.tasks.exception.TaskNotFoundException;
import br.com.udemy.tasks.model.Address;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.repository.TaskCustomRepository;
import br.com.udemy.tasks.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;

    private final TaskCustomRepository taskCustomRepository;

    private final AddressService addressService;

    public TaskService(TaskRepository repository, TaskCustomRepository taskCustomRepository, AddressService addressService){
        this.repository = repository;
        this.taskCustomRepository = taskCustomRepository;
        this.addressService = addressService;
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

    public Mono<Page<Task>> findPaginated(Task task, int pageNumber, int pageSize){
        return taskCustomRepository.findPaginated(task, pageNumber, pageSize);
    }


    public Mono<Void> deleteById(String id) {

        return repository.deleteById(id);
    }

    public Mono<Task> update(Task task) {
        return repository.findById(task.getId())
                .map(task::update)
                .flatMap(repository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error-> LOG.error("Error during update task with id: {}. Message: {}",
                        task.getId(), error.getMessage()));
    }


    public Mono<Task> start(String id, String zipCode){
        return repository.findById(id)
                .zipWhen(it -> addressService.getAddress(zipCode))
                .flatMap(it -> updateAddress(it.getT1(), it.getT2()))
                .map(Task::start)
                .flatMap(repository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error -> LOG.error("Error on start task id {}", id, error));
    }

    private Mono<Task> updateAddress(Task task, Address address){
        return Mono.just(task)
                .map(it -> task.updateAddress(address));
    }

    private Mono<Task> save(Task task){
        return Mono.just(task)
                .doOnNext(t -> LOG.info("Saving task with title {}", t.getTitle()))
                .flatMap(repository::save);
    }

}
