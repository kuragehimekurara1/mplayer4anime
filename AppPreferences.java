package mplayer4anime;

import java.util.prefs.Preferences;

// Rule application settings
public class AppPreferences {

    private Preferences preferences;

    public AppPreferences(){
        preferences = Preferences.userRoot().node("mplayer4anime");
    }

    public void setPath(String path){
        preferences.put("PATH", path);
    }

    public String getPath(){
        return preferences.get("PATH", "mplayer");
    }

    /** Return subtitles priority to show
    * @return
     * TRUE - Subtitles tab opens first
     * FALSE - Subtitles tab opens as usual
    */
    public boolean getSubtilesFirst(){
        return preferences.getBoolean("SUBS_TAB_FIRST", false);
    }

    public void setSubtilesFirst(boolean set){
        preferences.putBoolean("SUBS_TAB_FIRST", set);
    }

    /** Convert strings array to singls string.
     * Used in:
     * setSubsExtensionsList
     * setSubsEncodingList
     */
    private void storeSingleStringList(String whichList, String[] strArr){
        StringBuilder collect = new StringBuilder();
        for (String e : strArr) {
            collect.append(e);
            collect.append(" ");
        }
        String strToStore = collect.toString();
        preferences.put(whichList, strToStore);
    }
    /** Handle lists of the video files extensions */
    public void setVideoExtensionsList(String[] videoExtensionsList){ storeSingleStringList("VIDEO_EXTENSIONS_LIST", videoExtensionsList); }
    public String[] getVideoExtensionsList(){ return preferences.get("VIDEO_EXTENSIONS_LIST", "*.mkv *.avi *.mp4").split(" "); }
    /** Handle lists of the audio files extensions */
    public void setAudioExtensionsList(String[] audioExtensionsList){ storeSingleStringList("AUDIO_EXTENSIONS_LIST", audioExtensionsList); }
    public String[] getAudioExtensionsList(){ return preferences.get("AUDIO_EXTENSIONS_LIST", "*.mka *.ac3").split(" "); }

     /** Handle lists of the subtitles extensions selector */
    public void setSubsExtensionsList(String[] subsList){ storeSingleStringList("SUBS_EXTENSIONS_LIST", subsList); }
    public String[] getSubsExtensionsList(){ return preferences.get("SUBS_EXTENSIONS_LIST", "*.ass *.srt ").split(" "); }

    /** Handle lists of the subtitles encodings selector */
    public void setSubsEncodingList(String[] subsEncodingList){ storeSingleStringList("SUBS_ENCODINGS_LIST", subsEncodingList); }
    public String[] getSubsEncodingList(){ return preferences.get("SUBS_ENCODINGS_LIST", "default utf8 cp1251 koi8-r").split(" "); }

    /** Save & recover selected by user Subtitles encoding */
    public void setLastTimeUsedSubsEncoding(String selected){ preferences.put("SUBS_ENCODING_LAST_TIME_SELECTED", selected); }
    public String getLastTimeUsedSubsEncoding(){ return preferences.get("SUBS_ENCODING_LAST_TIME_SELECTED", ""); }

    /** Save & recover Full Screen checkbox, if selected */
    public boolean getFullScreenSelected(){
        return preferences.getBoolean("FULL_SCREEN_SELECTED", false);
    }
    public void setFullScreenSelected(boolean set){ preferences.putBoolean("FULL_SCREEN_SELECTED", set); }

    /** Save & recover Subtitles checkbox, if selected */
    public boolean getSubtitlesHideSelected(){
        return preferences.getBoolean("FULL_SUBTITLES_HIDE_SELECTED", false);
    }
    public void setSubtitlesHideSelected(boolean set){ preferences.putBoolean("FULL_SUBTITLES_HIDE_SELECTED", set); }

    /** Return recently opened elements */
    public String[] getRecentPlaylists(){
        String[] recentPlaylists = new String[10];
        for (int i=0; i<10; i++)
            recentPlaylists[i] = preferences.get("RECENT_PLS_" + i, "");
        return recentPlaylists;
    }
    /** Store recently opened elements */
    public void setRecentPlaylists(String[] recentPlaylists){
        if (recentPlaylists != null && recentPlaylists.length > 0) {
            int i;
            for (i = 0; i < recentPlaylists.length && !(i > 10); i++)
                if (recentPlaylists[i] != null && !recentPlaylists[i].isEmpty())
                    preferences.put("RECENT_PLS_" + i, recentPlaylists[i]);
                else
                    preferences.put("RECENT_PLS_" + i, "");
            for (;i<10;i++)                                             // Not needed. Logic may handle received String to be less or greater then String[10], but it never happened.
                preferences.put("RECENT_PLS_" + i, "");
        }
    }

}
