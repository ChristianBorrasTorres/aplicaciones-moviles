package espresso_e2e;

import static android.os.SystemClock.sleep;
import static  androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static  androidx.test.espresso.assertion.ViewAssertions.matches;
import static  androidx.test.espresso.matcher.ViewMatchers.*;


import static org.hamcrest.core.AllOf.allOf;


import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.vinilosgrupo3.ui.MainActivity;
import com.example.vinilosgrupo3.R;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest_e2e {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void visualizarAlbumDetalle() {

        // check whether album names and pictures are displayed
        /*onView(withRecyclerView(R.id.albumsRv)
                .atPositionOnView(1, R.id.album_name))
                .check(matches(isDisplayed()));*/

        sleep(1000);
        // click on album in list
        onView(allOf(withId(R.id.albumsRv), isDisplayed())).perform(new ViewAction[]{
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        });

        // check if view album detail is displayed
        onView(withId(R.id.detailRv)).check(matches(isDisplayed()));

        // check if album name is displayed
        onView(withId(R.id.detail_album_name_placeholder)).check(matches(withText("Buscando América")));

        // check if album detail is displayed
        onView(withId(R.id.detail_album_description)).check(matches(isDisplayed()));

        // check if album image is displayed
        onView(withId(R.id.detail_album_cover_image)).check(matches(isDisplayed()));

        // return to album catalog and check if album catalog is displayed
        pressBack();
        onView(allOf(withId(R.id.albumsRv), isDisplayed()));

    }

}
