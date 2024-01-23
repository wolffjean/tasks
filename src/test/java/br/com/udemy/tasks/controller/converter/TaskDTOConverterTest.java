package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;
import br.com.udemy.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskDTOConverterTest {

   @InjectMocks
    private TaskDTOConverter converter;

    @Test
    void converterMustConvertTask2TaskDTO(){
        Task task = TestUtils.buildValidTask();

        TaskDTO dto = converter.convert(task);

        Assertions.assertEquals(dto.getId(), task.getId());
        Assertions.assertEquals(dto.getTitle(), task.getTitle());
        Assertions.assertEquals(dto.getDescription(), task.getDescription());
        Assertions.assertEquals(dto.getPriority(), task.getPriority());
        Assertions.assertEquals(dto.getState(), task.getState());
    }

    @Test
    void converterMustConvertTaskDTO2Task(){
        TaskDTO dto = TestUtils.buildValidTaskDTO();

        Task task = converter.convert(dto);

        Assertions.assertEquals(dto.getId(), task.getId());
        Assertions.assertEquals(dto.getTitle(), task.getTitle());
        Assertions.assertEquals(dto.getDescription(), task.getDescription());
        Assertions.assertEquals(dto.getPriority(), task.getPriority());
        Assertions.assertEquals(dto.getState(), task.getState());

    }

    @Test
    void converterMustConvert2Task(){
        String id = "123";
        String title = "title";
        String description = "desc";
        int priority = 1;
        TaskState state = TaskState.INSERT;

        Task task = converter.convert(id, title, description, priority, state);

        Assertions.assertEquals(task.getId(),id);
        Assertions.assertEquals(task.getTitle(), title);
        Assertions.assertEquals(task.getDescription(), description);
        Assertions.assertEquals(task.getPriority(), priority);
        Assertions.assertEquals(task.getState(), state);


    }

}
