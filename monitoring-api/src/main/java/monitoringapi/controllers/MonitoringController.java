package monitoringapi.controllers;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import monitoringapi.constants.MonitoringMessage;
import monitoringapi.models.Monitoring;
import monitoringapi.services.MonitoringService;
import monitoringapi.util.JwtHelper;

@RestController
@RequestMapping(MonitoringController.BASE_URL)
public class MonitoringController {

    /*
        MonitoringController is used only for GET and DELETE requests. It doesn't have POST request
        because something will be saved in the database only if some component throws an exception
        and it is not allowed to user to create a Monitoring object
    */

    public static final String BASE_URL = "/api/monitorings";
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MonitoringService userMonitoringService;
    private final JwtHelper jwtHelper;

    @Autowired
    public MonitoringController(MonitoringService userMonitoringService, JwtHelper jwtHelper) {
        this.userMonitoringService = userMonitoringService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monitoring> fetchMonitoringById(@PathVariable String id) {
        logger.info(MonitoringMessage.USER_FETCHED_USER_MONITORING_DATA + id);

        return new ResponseEntity<>(userMonitoringService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search/{api}")
    public ResponseEntity<List<Monitoring>> fetchMonitoringByApi(@PathVariable String api) {
        logger.info(MonitoringMessage.USER_FETCHED_USER_MONITORING_DATA + api);

        return new ResponseEntity<>(userMonitoringService.findByApi(api), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Monitoring>> fetchAllMonitoringLogs() {
        logger.info(MonitoringMessage.USER_FETCH_ALL_THE_DATA);

        return new ResponseEntity<>(userMonitoringService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUserCollection(@RequestHeader HashMap<String, String> params) {
        userMonitoringService.deleteAll();

        logger.warn(MonitoringMessage.WARNING_DELETING_DATABASE);

        Monitoring monitoring = new Monitoring(
                MonitoringMessage.WARNING_DELETING_DATABASE
                        + jwtHelper.getSubject(params.get("authorization")).toString(),
                MonitoringMessage.MONITORING_API);
        userMonitoringService.save(monitoring);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLogById(@PathVariable String id,
            @RequestBody HashMap<String, String> params) {
        userMonitoringService.deleteById(id);
        logger.warn(MonitoringMessage.WARNING_DELETING_LOG_BY_ID + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}