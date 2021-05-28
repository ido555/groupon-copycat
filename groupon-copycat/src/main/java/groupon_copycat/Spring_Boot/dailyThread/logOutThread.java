package groupon_copycat.Spring_Boot.dailyThread;

import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.web.controllers.LoginController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class logOutThread extends Thread {
    private boolean quit = false;
    private final Map<String, ClientWrapper> sessions = LoginController.sessions;

    @Override
    public void run() {
        try {
            while (!(quit)) {
                long now = new Date().getTime();
                for (Map.Entry<String, ClientWrapper> wrapper : sessions.entrySet()) {
                    if (now > (wrapper.getValue().getLastAccess() + TimeUnit.MINUTES.toMillis(30)))
                        sessions.remove(wrapper.getKey());
                }

                // if 30 minutes passed
                sleep(TimeUnit.MINUTES.toMillis(30));
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public void stopJob() {
        quit = true;
        interrupt();
    }
}
