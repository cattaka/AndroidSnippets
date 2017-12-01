package net.cattaka.android.snippets.example.tracker;

/**
 * NODE: Follow the limitation of Firebase Analytics, such as num of actions, don't use space in action name.
 */
public enum TrackAction {
    ACTION_CLICK("action_click"),
    ACTION_LONG_CLICK("action_long_click"),
    CHECK_CHANGE("check_change"),
    SELECTED("selected"),
    //
    ;
    public final String code;

    TrackAction(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
