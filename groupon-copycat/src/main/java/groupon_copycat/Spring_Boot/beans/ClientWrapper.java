package groupon_copycat.Spring_Boot.beans;

import groupon_copycat.Spring_Boot.clientFacades.ClientFacade;


public class ClientWrapper {
    //    @Autowired already autowired?
    private ClientFacade clientFacade;
    private long lastAccess;

    public ClientWrapper(ClientFacade clientFacade, long lastAccess) {
        this.clientFacade = clientFacade;
        this.lastAccess = lastAccess;
    }

    public ClientFacade getClientFacade() {
        return clientFacade;
    }

    public void setClientFacade(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }
}
