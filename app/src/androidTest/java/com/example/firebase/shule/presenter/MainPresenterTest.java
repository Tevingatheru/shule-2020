package com.example.firebase.shule.presenter;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.firebase.shule.activity.MainActivity;
import com.example.firebase.shule.contract.MainContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test for the main presenter
 * @author tevin
 */
@RunWith(MockitoJUnitRunner.class)
class MainPresenterTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Mock
    private MainContract.View view;

    @Before
    void setUp() {

    }

    @Test
    void sayHello() {
    }

    @Test
    void startSubjectActivity() {
    }
}