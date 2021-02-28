package monitoringapi.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import monitoringapi.constants.MonitoringMessage;
import monitoringapi.models.Monitoring;
import monitoringapi.repositories.MonitoringRepository;

@Service
@Transactional
public class MonitoringServiceImpl implements MonitoringService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MonitoringRepository userMonitoringRepository;

    @Autowired
    public MonitoringServiceImpl(MonitoringRepository userMonitoringRepository) {
        this.userMonitoringRepository = userMonitoringRepository;
    }

    /*
     * Method findById returns Monitoring object or null which depends on if the
     * monitoring object exists in the database with the passed id.
     */
    @Override
    public Monitoring findById(String id) {
        Optional<Monitoring> optional = userMonitoringRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

    /*
     * Method findAll returns list of Monitoring objects which exists in the
     * database
     */
    @Override
    public List<Monitoring> findAll() {
        return userMonitoringRepository.findAll();
    }

    /*
     * Method save is used for saving Monitoring object to the database
     */
    @Override
    public Monitoring save(Monitoring object) {
        return userMonitoringRepository.save(object);
    }

    /*
     * Method deleteById is used for deleting Monitoring object by passing id If
     * Monitoring object exists in the database with passed id, it will be found and
     * deleted
     */
    @Override
    public void deleteById(String id) {
        logger.warn(MonitoringMessage.DELETING_LOG_BY_ID + id);
        userMonitoringRepository.deleteById(id);
    }

    /*
     * Method deleteAll is used for deleting whole database monitoring collection
     */
    @Override
    public void deleteAll() {
        logger.warn(MonitoringMessage.DELETING_LOG_DATABASE);
        userMonitoringRepository.deleteAll();
    }

    /*
     * This method is used if user has not valid credentials and Monitoring object
     * will be created and saved to the database
     */
    @Override
    public void notValidCredentialsLog(String message, String api, String id) {
        Monitoring monitoring = null;

        if (id != null) {
            monitoring = new Monitoring(message + id, api);
            logger.warn(message + id);
        } else {
            monitoring = new Monitoring(message, api);
            logger.warn(message);
        }

        userMonitoringRepository.save(monitoring);
    }

}
