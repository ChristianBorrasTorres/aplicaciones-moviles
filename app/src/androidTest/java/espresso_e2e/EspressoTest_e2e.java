package espresso_e2e;

import static android.os.SystemClock.sleep;
import static  androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static  androidx.test.espresso.assertion.ViewAssertions.matches;
import static  androidx.test.espresso.matcher.ViewMatchers.*;


import static org.hamcrest.core.AllOf.allOf;


import androidx.test.InstrumentationRegistry;
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
        //click on button "visitantes"
        onView(allOf(withId(R.id.button_visitor), isDisplayed())).perform(click());

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


    @Test
    public void visualizarMusicoDetalle() {

        // check whether album names and pictures are displayed
        /*onView(withRecyclerView(R.id.albumsRv)
                .atPositionOnView(1, R.id.album_name))
                .check(matches(isDisplayed()));*/

        sleep(1000);
        //click on menu button and "Artistas"
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.action_artistas)).perform(click());

        sleep(1000);
        // click on first artist card
        onView(allOf(withId(R.id.musiciansRv), isDisplayed())).perform(new ViewAction[]{
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        });

        // check if view artist detail is displayed
        onView(withId(R.id.musiciandetailRv)).check(matches(isDisplayed()));

        // check if artist name is displayed
        onView(withId(R.id.detail_musician_name_placeholder)).check(matches(withText("Rubén Blades Bellido de Luna")));

        // check if artist description is displayed
        onView(withId(R.id.detail_musician_description)).check(matches(isDisplayed()));

        // check if artist image is displayed
        onView(withId(R.id.detail_musician_image)).check(matches(isDisplayed()));

        // check if artist birthdate is displayed
        onView(withId(R.id.detail_musician_birthDate)).check(matches(isDisplayed()));

        // return to album catalog and check if album catalog is displayed
        pressBack();
        onView(allOf(withId(R.id.musiciansRv), isDisplayed()));

    }


    @Test
    public void visualizarColeccionistaLista() {

        // check whether album names and pictures are displayed
        /*onView(withRecyclerView(R.id.albumsRv)
                .atPositionOnView(1, R.id.album_name))
                .check(matches(isDisplayed()));*/

        sleep(1000);
        //click on menu button and "Coleccionistas"
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.action_collectors)).perform(click());

        sleep(1000);
        // click on first collector card
        onView(allOf(withId(R.id.collectorsRv), isDisplayed())).perform(new ViewAction[]{
                RecyclerViewActions.actionOnItemAtPosition(0, click())
        });

        // check if view collector detail is displayed
        onView(withId(R.id.collectorDetailRv)).check(matches(isDisplayed()));

        // check if collector name is displayed
        onView(withId(R.id.collector_name_detail_placeholder)).check(matches(withText("Manolo Bellon")));

        // check if collector telephone number is displayed
        onView(withId(R.id.collector_detail_telephone)).check(matches(isDisplayed()));

        // check if collector e-mail is displayed
        onView(withId(R.id.collector_detail_email)).check(matches(isDisplayed()));

        // return to album catalog and check if album catalog is displayed
        pressBack();
        onView(allOf(withId(R.id.collectorsRv), isDisplayed()));

    }

    @Test
    public void crearAlbum() {

        sleep(1000);
        //click on button "coleccionistas"
        onView(allOf(withId(R.id.button_collector), isDisplayed())).perform(click());

        sleep(1000);
        //click on button "crear album"
        onView(allOf(withId(R.id.button_create_album), isDisplayed())).perform(click());

        sleep(1000);
        //click on input "record label"
        onView(allOf(withId(R.id.spinner_record_label), isDisplayed())).perform(click());

        sleep(1000);
        // click on record label
        onView(allOf(withText("EMI"), isDisplayed())).perform(click());

        sleep(1000);
        //click on input "genre"
        onView(allOf(withId(R.id.spinner_genre), isDisplayed())).perform(click());

        sleep(1000);
        // click on genre
        onView(allOf(withText("Rock"), isDisplayed())).perform(click());

        sleep(1000);
        //click on input "description"
        onView(allOf(withId(R.id.album_description), isDisplayed())).perform(click());

        sleep(1000);
        // type description
        onView(withId(R.id.album_description_id)).perform(typeText("American Idiot es el album mas popular de Green Day"));
        pressBack();

        sleep(1000);
        //click on input "release date"
        onView(allOf(withId(R.id.album_releaseDate), isDisplayed())).perform(click());

        sleep(1000);
        // type release date
        onView(withId(R.id.album_releaseDate_id)).perform(typeText("2004-08-01T00:00:00-05:00"));
        pressBack();

        sleep(1000);
        //click on input "cover"
        onView(allOf(withId(R.id.album_cover), isDisplayed())).perform(click());

        sleep(1000);
        // type cover URL
        onView(withId(R.id.album_cover_id)).perform(typeText("https://upload.wikimedia.org/wikipedia/en/e/ed/Green_Day_-_American_Idiot_album_cover.png"));
        pressBack();

        sleep(1000);
        //click on input "name"
        onView(allOf(withId(R.id.album_name), isDisplayed())).perform(click());

        sleep(1000);
        // type album name
        onView(withId(R.id.album_name_id)).perform(typeText("American Idiot"));
        pressBack();

        sleep(1000);
        //click on button "create album"
        onView(allOf(withId(R.id.button_create_album), isDisplayed())).perform(click());

    }

}
