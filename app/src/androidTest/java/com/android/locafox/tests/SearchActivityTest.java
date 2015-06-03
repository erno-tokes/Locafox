package com.android.locafox.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.locafox.R;
import com.android.locafox.SearchActivity;

/**
 * Created by Erno Tokes on 6/3/2015.
 */
public class SearchActivityTest extends ActivityInstrumentationTestCase2<SearchActivity>{

    private SearchActivity searchActivity;
    private ProgressBar searchProgress;
    private AutoCompleteTextView searchText;

    public SearchActivityTest(Class<SearchActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        searchActivity = getActivity();
        searchProgress = (ProgressBar)searchActivity.findViewById(R.id.search_progr);
        searchText = (AutoCompleteTextView)searchActivity.findViewById(R.id.search_text);
    }

    public void testPreconditions() {
        assertNotNull("activity is null", searchActivity);
        assertNotNull("progressBar is null", searchProgress);
        assertNotNull("textView is null", searchText);
    }

    public void testViews(){
        testPreconditions();
    }
}
