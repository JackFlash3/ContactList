package com.example.prodigalson7.contactlist.business;

/**
 * Created by ProdigaLsON7 on 11/01/2018.
 */

public class ConnectionDetails {
    private static ConnectionDetails connectionDetails = null;
    private boolean connection;
    private boolean result;
    private long time;

    private ConnectionDetails()
    {
        this.connection = false;
        this.time = 0;
        this.result = true;
    }

    public static ConnectionDetails getInstance()
    {
        if (connectionDetails == null)
        {
            connectionDetails = new ConnectionDetails();
        }
        return connectionDetails;
    }

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public boolean isConnection() {
        return connection;
    }
    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
