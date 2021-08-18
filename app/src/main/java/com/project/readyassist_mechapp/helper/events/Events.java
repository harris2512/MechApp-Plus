package com.project.readyassist_mechapp.helper.events;


public class Events {


    // Event used to send message from fragment to activity.
    public static class FragmentActivityMessage {
        private final String message;

        public FragmentActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to fragment.
    public static class ActivityFragmentMessage {
        private final String message;

        public ActivityFragmentMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

    // Event used to send message from activity to activity.
    public static class ActivityActivityMessage {
        private final String message;

        public ActivityActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


}
