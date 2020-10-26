package com.example.firebase.shule.presenter;

import com.example.firebase.shule.contract.MainContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit test for the main presenter
 * @author tevin
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    private MainContract.View view;

    private MainPresenter presenter;


    @Before
    public void setUp() {
        openMocks(this);
        presenter = new MainPresenter(view);
    }

    @Test
    public void shouldSayHello() {
        presenter.sayHello();
        verify(view).shouldSayHello();
    }

    @Test
    public void shouldStartSubjectActivity() {
        presenter.startSubjectActivity();
        verify(view).shouldStartSubjectActivity();
    }
}