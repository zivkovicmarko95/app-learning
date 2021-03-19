package monitoringapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    private MonitoringRepository monitoringRepository;
    private MonitoringServiceImpl monitoringService;

    @BeforeEach
    public void setUp() {
        monitoringService = new MonitoringServiceImpl(monitoringRepository);
    }

    @Test
    public void checkIfObjectIsSavedInDbTest() {
        Monitoring monitoring1 = new Monitoring(MONITORING_MESSAGE_USER_API, TEST_USER_API);
        Monitoring monitoring2 = new Monitoring(MONITORING_MESSAGE_PRODUCT_API, TEST_PRODUCT_API);
        Monitoring monitoring3 = new Monitoring(MONITORING_MESSAGE_ORDER_BACKUP_API, TEST_ORDER_BACKUP_API);
        
        when(monitoringService.findByApi(TEST_USER_API)).thenReturn(List.of(monitoring1));
        Monitoring testUserApiMonitoring = monitoringService.findByApi(TEST_USER_API).get(0);

        when(monitoringService.findByApi(TEST_PRODUCT_API)).thenReturn(List.of(monitoring2));
        Monitoring testProductApiMonitoring = monitoringService.findByApi(TEST_PRODUCT_API).get(0);

        when(monitoringService.findByApi(TEST_ORDER_BACKUP_API)).thenReturn(List.of(monitoring3));
        Monitoring testOrderBackupApiMonitoring = monitoringService.findByApi(TEST_ORDER_BACKUP_API).get(0);

        assertEquals(MONITORING_MESSAGE_USER_API, testUserApiMonitoring.getMessage());
        assertEquals(TEST_USER_API, testUserApiMonitoring.getApi());

        assertEquals(MONITORING_MESSAGE_PRODUCT_API, testProductApiMonitoring.getMessage());
        assertEquals(TEST_PRODUCT_API, testProductApiMonitoring.getApi());

        assertEquals(MONITORING_MESSAGE_ORDER_BACKUP_API, testOrderBackupApiMonitoring.getMessage());
        assertEquals(TEST_ORDER_BACKUP_API, testOrderBackupApiMonitoring.getApi());
    }

    @Test
    public void checkIfObjectIsDeletedFromDb() {
        Monitoring monitoring1 = new Monitoring(MONITORING_MESSAGE_USER_API, TEST_USER_API);

        when(monitoringService.findByApi(TEST_USER_API)).thenReturn(List.of(monitoring1));
        Monitoring testUserApiMonitoring = monitoringService.findByApi(TEST_USER_API).get(0);

        monitoringService.deleteByApi(testUserApiMonitoring.getApi());

        verify(monitoringRepository).deleteByApi(testUserApiMonitoring.getApi());
    }

    @Test
    public void checkIfObjectIsDeletedByIdFromDb() {
        Monitoring monitoring1 = new Monitoring(MONITORING_MESSAGE_USER_API, TEST_USER_API);
        monitoring1.setId(UUID.randomUUID().toString());

        monitoringService.deleteById(monitoring1.getId());

        verify(monitoringRepository).deleteById(monitoring1.getId());
    }

}
