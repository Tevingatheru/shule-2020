package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.SubjectContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class SubjectPresenterTest {
    @Mock
    private SubjectContract.View view;

    private  SubjectPresenter presenter;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        presenter = new SubjectPresenter(view);
    }

    @Test
    public void shouldOpenReference() {
        presenter.openReference();
        verify(view).shouldOpenReference();
    }

    @Test
    public void shouldSetView() {
        presenter.setView();
        verify(view).shouldSetView();
    }

    @Test
    public void shouldStartTopicActivity() {
        presenter.startTopicActivity();
        verify(view).shouldTopicActivity();
    }
}