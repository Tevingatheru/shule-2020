package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.TopicContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class TopicPresenterTest {
    @Mock
    private TopicContract.View view;
    private TopicPresenter presenter;

    @Before
    public void setUp() {
        openMocks(this);
        presenter = new TopicPresenter(view);
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
    public void shouldStartExamActivity() {
        presenter.startExamActivity();
        verify(view).shouldTopicActivity();
    }
}