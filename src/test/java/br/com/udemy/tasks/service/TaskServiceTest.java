package br.com.udemy.tasks.service;

import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.repository.TaskCustomRepository;
import br.com.udemy.tasks.repository.TaskRepository;
import br.com.udemy.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @InjectMocks
    private TaskService service;
    @Mock
    private TaskRepository repository;
    @Mock
    private TaskCustomRepository taskCustomRepository;


    @Test
    void serviceMustReturnTaskWhenInsertSuccessfully(){
        Task task = TestUtils.buildValidTask();

        when(repository.save(any())).thenReturn(task);

        StepVerifier.create(service.insert(task))
                .then(()-> verify(repository, times(1)).save(any()))
                .expectNext(task)
                .expectComplete();
    }


    @Test
    void serviceMustDeleteTaskWhenDeleteSuccessfully(){
        String id="ID";

        StepVerifier.create(service.deleteById(id))
                .then(()-> verify(repository, times(1)).deleteById(any()))
                .verifyComplete();
                // quando nao tiver retorno, usar verificar complete
    }

    @Test
    void serviceMustReturnTaskPageWhenFindPaginated(){
        Task task = TestUtils.buildValidTask();

        when(taskCustomRepository.findPaginated(any(), anyInt(), anyInt())).thenReturn(Page.empty());
        Page<Task> result= service.findPaginated(task, 0, 10);

        Assertions.assertNotNull(result);
        verify(taskCustomRepository, times(1)).findPaginated(any(), anyInt(), anyInt());
    }
}
