package net.cattaka.android.snippets.example.tracker_v2;

/**
 * Created by cattaka on 18/02/06.
 */

public enum Event {
    SCREEN_SHOW("screen_show"),
    SELECT_CONTENT("select_content"),
    CLICK("click"),
    // TODO: イベントはここに追加していく
    ;

    public final String key;

    Event(String key) {
        this.key = key;
    }
}
