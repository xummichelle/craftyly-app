package com.craftylyteam.craftylyapp1.utils;

public class Constants {


//    EXTRAs
    public static final String EXTRA_USER =
        "com.craftylyteam.craftylyapp1.EXTRA_USER";
    public static final String EXTRA_NOTE_ID =
            "com.craftylyteam.craftylyapp1.utils.EXTRA_ID";

//    settings fragment tag
    public static final String SETTINGS_FRAGMENT = "com.craftylyteam.craftylyapp1.main.prompt.settings.SettingsFragment";

    //    reference to names of firestore database/collections/documents
    public static final String USERS = "Users";
    public static final String HISTORY = "History";
    public static final String HISTORY_ACCEPTED = "accepted";
    public static final String HISTORY_DESCRIPTION = "description";
    public static final String HISTORY_TIMESTAMP = "timestamp";

    public static final String NOTES = "Notes";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_DESCRIPTION = "description";
    public static final String NOTE_LIGHTBULB = "bulbTag";
    public static final String NOTE_TIMESTAMP = "timestamp";

    public static final String FILTERS = "Filters";
    public static final String FILTERS_CHARACTERS = "Characters";
    public static final String FILTERS_FEELINGS = "Feelings";
    public static final String FILTERS_ENVIRONMENTS = "Environments";
    public static final String FILTERS_PROMPTS = "prompts";

    public static final String[] ALL_FILTERS_ARRAY = {FILTERS_CHARACTERS, FILTERS_FEELINGS, FILTERS_ENVIRONMENTS};


//    request codes
    public static final int SIGN_IN_REQUEST = 123;

//    lightbulb references
    public static final String CRAFTYLY_BULB = "1";
    public static final String CALICO_BULB = "2";
    public static final String CRAFTYLY_CALICO_BULB = "3";
    public static final String LEMON_BULB = "4";
    public static final String SUNSET_BULB = "5";
    public static final String CAMO_BULB = "6";
    public static final String[] ALL_BULBS_ARRAY = {CRAFTYLY_BULB, CALICO_BULB, CRAFTYLY_CALICO_BULB, LEMON_BULB, SUNSET_BULB, CAMO_BULB};

//    shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FILTERS_UNCHECKED = "filtersUnchecked";
    public static final String CURRENT_THEME = "currentTheme";
    public static final String THEME_NEW = "themeNew";
    public static final String LAST_LAUNCH_DATE = "lastLaunchData";
    public static final String FIRST_RUN = "firstRun";
    public static final String FIRST_RUN_NOTES = "firstRunNotes";

//    bundle keys
    public static final String FIRST_BULB_TAG_KEY = "firstBulbTagKey";
    public static final String SECOND_BULB_TAG_KEY = "secondBulbTagKey";
    public static final String FIRST_NOTE_TITLE_KEY = "firstNoteTitleKey";
    public static final String FIRST_NOTE_DESC_KEY = "firstNoteDescKey";
    public static final String SECOND_NOTE_TITLE_KEY = "secondNoteTitleKey";
    public static final String SECOND_NOTE_DESC_KEY = "secondNoteDescKey";







}
