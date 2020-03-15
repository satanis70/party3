package nikita.party3;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.DrawerActions;

import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.io.Closeable;
import java.nio.channels.AsynchronousFileChannel;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class Main2ActivityTest {

    @Rule
    public IntentsTestRule<Main2Activity> activityRule = new IntentsTestRule<>(Main2Activity.class);


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void TestPersonalArea() throws Exception{

        Thread.sleep(1000);
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.facebook));
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressBack();

        Thread.sleep(5000);
        onView(withId(R.id.buttonstartbanner)).check(matches(isEnabled())).perform(click());
        Thread.sleep(6000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        //onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.Out));



    }

    @After
    public void tearDown() throws Exception {

    }
}