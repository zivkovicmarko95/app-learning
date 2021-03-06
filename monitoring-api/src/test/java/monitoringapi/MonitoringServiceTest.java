package monitoringapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import monitoringapi.models.Monitoring;
import monitoringapi.repositories.MonitoringRepository;
import monitoringapi.services.MonitoringServiceImpl;


@DataMongoTest
public class MonitoringServiceTest {
    
    private static final String TEST_USER_API = "test-user-api";
    private static final String TEST_PRODUCT_API = "test-product-api";
    private static final String TEST_ORDER_BACKUP_API = "test-order-backup-api";
    
    private static final String MONITORING_MESSAGE_USER_API = "JUnit user-api - monitoring1 object saved to the database";
    private static final String MONITORING_MESSAGE_PRODUCT_API = "JUnit product-api - monitoring2 object saved to the database";
    private static final String MONITORING_MESSAGE_ORDER_BACKUP_API = "JUnit order-backup-api - monitoring3 object saved to the database";

    @Autowired
    private MonitoringRepository monitoringRepository;
    private MonitoringServiceImpl monitoringService;

    @BeforeEach
    public void setUp() {
        monitoringService = new MonitoringServiceImpl(monitoringRepository);

        if (monitoringService.findByApi(TEST_USER_API).size() > 0) {
            monitoringService.deleteByApi(TEST_USER_API);
        }

        if (monitoringService.findByApi(TEST_PRODUCT_API).size() > 0) {
            monitoringService.deleteByApi(TEST_PRODUCT_API);
        }

        if (monitoringService.findByApi(TEST_ORDER_BACKUP_API).size() > 0) {
            monitoringService.deleteByApi(TEST_ORDER_BACKUP_API);
        }

        Monitoring monitoring1 = new Monitoring(MONITORING_MESSAGE_USER_API, TEST_USER_API);
        Monitoring monitoring2 = new Monitoring(MONITORING_MESSAGE_PRODUCT_API, TEST_PRODUCT_API);
        Monitoring monitoring3 = new Monitoring(MONITORING_MESSAGE_ORDER_BACKUP_API, TEST_ORDER_BACKUP_API);

        monitoringService.save(monitoring1);
        monitoringService.save(monitoring2);
        monitoringService.save(monitoring3); 
    }

    @Test
    public void checkIfObjectIsSavedInDbTest() {
        Monitoring testUserApiMonitoring = monitoringService.findByApi(TEST_USER_API).get(0);
        Monitoring testProductApiMonitoring = monitoringService.findByApi(TEST_PRODUCT_API).get(0);
        Monitoring testOrderBackupApiMonitoring = monitoringService.findByApi(TEST_ORDER_BACKUP_API).get(0);

        assertEquals(MONITORING_MESSAGE_USER_API, testUserApiMonitoring.getMessage());
        assertEquals(TEST_USER_API, testUserApiMonitoring.getApi());

        assertEquals(MONITORING_MESSAGE_PRODUCT_API, testProductApiMonitoring.getMessage());
        assertEquals(TEST_PRODUCT_API, testProductApiMonitoring.getApi());

        assertEquals(MONITORING_MESSAGE_ORDER_BACKUP_API, testOrderBackupApiMonitoring.getMessage());
        assertEquals(TEST_ORDER_BACKUP_API, testOrderBackupApiMonitoring.getApi());

        assertNotNull(testUserApiMonitoring.getId());
        assertNotNull(testProductApiMonitoring.getId());
        assertNotNull(testOrderBackupApiMonitoring.getId());
    }

    @Test
    public void checkIfObjectIsDeletedFromDb() {
        monitoringService.deleteByApi(TEST_USER_API);
        monitoringService.deleteByApi(TEST_PRODUCT_API);
        monitoringService.deleteByApi(TEST_ORDER_BACKUP_API);

        assertTrue(monitoringService.findByApi(TEST_USER_API).isEmpty());
        assertTrue(monitoringService.findByApi(TEST_PRODUCT_API).isEmpty());
        assertTrue(monitoringService.findByApi(TEST_ORDER_BACKUP_API).isEmpty());
    }

    @Test
    public void checkIfObjectIsDeletedByIdFromDb() {
        Monitoring monitoring = monitoringService.findAll().get(0);
        String id = monitoring.getId();

        monitoringService.deleteById(id);

        assertNull(monitoringService.findById(id));
    }

}
