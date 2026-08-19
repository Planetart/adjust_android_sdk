package com.adjust.sdk;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * First-write-wins timestamps for the expensive stages on the first
 * {@code /session} path. Merged into the host app {@code adjust_track_time}
 * event so GAID, package build, signing, and network cost can be compared.
 */
public final class AdjustFirstSessionTiming {

    public static final String INIT_I_START_TS = "init_i_start_ts";
    public static final String INIT_I_END_TS = "init_i_end_ts";
    public static final String PLAY_IDS_RELOAD_INIT_START_TS = "play_ids_reload_init_start_ts";
    public static final String PLAY_IDS_RELOAD_INIT_END_TS = "play_ids_reload_init_end_ts";
    public static final String PLAY_IDS_RELOAD_PACKAGE_START_TS = "play_ids_reload_package_start_ts";
    public static final String PLAY_IDS_RELOAD_PACKAGE_END_TS = "play_ids_reload_package_end_ts";
    public static final String PLAY_IDS_SOURCE = "play_ids_source";
    public static final String ON_RESUME_I_TS = "on_resume_i_ts";
    public static final String SESSION_PACKAGE_BUILD_START_TS = "session_package_build_start_ts";
    public static final String SESSION_PACKAGE_BUILD_END_TS = "session_package_build_end_ts";
    public static final String SESSION_SIGN_START_TS = "session_sign_start_ts";
    public static final String SESSION_SIGN_END_TS = "session_sign_end_ts";
    public static final String SESSION_NETWORK_START_TS = "session_network_start_ts";
    public static final String SESSION_NETWORK_END_TS = "session_network_end_ts";

    private static final ConcurrentHashMap<String, Object> values = new ConcurrentHashMap<>();

    private AdjustFirstSessionTiming() {}

    public static boolean has(final String key) {
        return values.containsKey(key);
    }

    public static void mark(final String step) {
        values.putIfAbsent(step, System.currentTimeMillis());
    }

    public static void markIfSession(final ActivityPackage activityPackage, final String step) {
        if (activityPackage != null && activityPackage.getActivityKind() == ActivityKind.SESSION) {
            mark(step);
        }
    }

    public static void putExtra(final String key, final Object value) {
        if (value == null) {
            return;
        }
        values.putIfAbsent(key, value);
    }

    public static Map<String, Object> snapshot() {
        return new LinkedHashMap<>(values);
    }
}
