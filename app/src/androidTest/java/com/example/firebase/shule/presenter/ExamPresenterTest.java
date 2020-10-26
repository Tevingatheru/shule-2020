package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.ExamContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class ExamPresenterTest {
    @Mock
    private ExamContract.View view;

    private ExamPresenter presenter;

    @Before
    public void setUp() {
        openMocks(this);
        presenter = new ExamPresenter(view);
    }

    @Test
    public void shouldAddExam() {
        presenter.addExam();
        verify(view).shouldAddExam();
    }

    @Test
    public void shouldRemoveExam() {
        presenter.removeExam();
        verify(view).shouldRemoveExam();
    }

    @Test
    public void shouldEditExam() {
        presenter.editExam();
        verify(view).shouldEditExam();
    }

    @Test
    public void shouldOpenReference() {
        presenter.openReference();
        verify(view).shouldOpenReference();
    }
}