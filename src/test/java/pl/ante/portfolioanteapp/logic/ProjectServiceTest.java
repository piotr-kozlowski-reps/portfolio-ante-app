package pl.ante.portfolioanteapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.ante.portfolioanteapp.exceptions.NoSuchTypeException;
import pl.ante.portfolioanteapp.model.TypeRepository;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw NoSuchTypeException when couldn't find Type by desired id")
    void createProjectFromWriteModel__badIdType__throwsNoSuchTypeException() {
        //given
        var mockProjectWriteModel = mock(ProjectWriteModel.class);
        when(mockProjectWriteModel.getMonth()).thenReturn(10);
        when(mockProjectWriteModel.getTypes()).thenReturn(Set.of(1, 3));
        //and
        var mockTypeRepository = mock(TypeRepository.class);
        when(mockTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new ProjectService(null, mockTypeRepository);

        //when
        var exception = catchThrowable(() -> {
            toTest.createProjectFromWriteModel(mockProjectWriteModel);
        });

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class);

    }
}