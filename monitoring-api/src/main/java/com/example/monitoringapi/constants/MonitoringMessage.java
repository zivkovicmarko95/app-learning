package com.example.monitoringapi.constants;

import java.util.Date;

public class MonitoringMessage {

    public final static String DELETING_LOG_BY_ID = "Deleting log by id: ";
    public final static String DELETING_LOG_BY_API = "Deleting log by api: ";
    public final static String DELETING_LOG_DATABASE = "Deleting logs database, database name is: ";

    public final static String MONITORING_API = "monitoring-api";
    public final static String WARNING_DELETING_DATABASE = "WARNING: At: " + new Date() + " user deleted database. User id is: ";
    public final static String WARNING_DELETING_DATABASE_FAILED = "WARNING: User tried to access the database and delete all the logs";
    public final static String WARNING_DELETING_LOG_BY_ID = "Log with id has been deleted. Log id is: ";
    public final static String WARNING_DELETING_LOG_BY_ID_FAILED = "WARNING: User without valid credentials tries to delete log. Log id is: ";
    public final static String USER_FETCH_ALL_THE_DATA = "User fetch all the logs";
    public final static String USER_FETCH_ALL_DATA_FAILED = "User without valid credentials wants to fetch all the monitoring data";
    public final static String USER_FETCH_USER_MONITORING_DATA_BY_ID = "User without valid credentials wants to fetch the monitoring object with id: ";
    public final static String USER_FETCHED_USER_MONITORING_DATA = "User fetched user monitoring data with id: ";

    public final static String SAVING_OBJECT_TO_DB_INFO_MSG = "Saving monitoring object to the database";
    
}
