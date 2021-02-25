package userapi.bootstrap;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import userapi.constants.MessagesConstants;
import userapi.models.User;
import userapi.repositories.UserRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /*
        Bootstrap class is used only for importing dummy data in the database
    */

    @Autowired
    public Bootstrap(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }

    private void createUsers() {
        if (userRepository.count() > 0) {
            logger.info(MessagesConstants.PREPERING_USER_COLLECTION_DELETING);
            userRepository.deleteAll();
        }

        logger.info(MessagesConstants.PREPERING_USER_COLLECTION_ADDING);

        User user1 = new User(UUID.randomUUID().toString(), "Marko", "Markovic", "markomarkovic",
                bCryptPasswordEncoder.encode("markovic1234"), "marko.markovic@email.com", true, true);
        User user2 = new User(UUID.randomUUID().toString(), "Petar", "Petrovic", "petarpetrovic",
                bCryptPasswordEncoder.encode("petrovic1234"), "petar.petrovic@email.com", true, true);
        User user3 = new User(UUID.randomUUID().toString(), "Maja", "Marinkovic", "majamarinkovic",
                bCryptPasswordEncoder.encode("marinkovic1234"), "maja.marinkovic@email.com", true, true);
        User user4 = new User(UUID.randomUUID().toString(), "Tamara", "Trninic", "tamaratrninic",
                bCryptPasswordEncoder.encode("trninic1234"), "tamara.trninic@email.com", true, true);
        User user5 = new User(UUID.randomUUID().toString(), "Milos", "Milosevic", "milosmilosevic",
                bCryptPasswordEncoder.encode("milosevic1234"), "milos.milosevic@email.com", true, true);
        User user6 = new User(UUID.randomUUID().toString(), "Jelena", "Pantic", "jelenapantic",
                bCryptPasswordEncoder.encode("pantic1234"), "jelena.pantic@email.com", true, true);
        User user7 = new User(UUID.randomUUID().toString(), "Luka", "Jovanovic", "lukajovanic",
                bCryptPasswordEncoder.encode("jovanic1234"), "luka.jovanovic@email.com", true, true);
        User user8 = new User(UUID.randomUUID().toString(), "Bojana", "Majkic", "bojanamajkic",
                bCryptPasswordEncoder.encode("majkic1234"), "bojana.majkic@email.com", true, true);
        User user9 = new User(UUID.randomUUID().toString(), "Anastasija", "Anastasijevic", "anastasijaanastasijevic",
                bCryptPasswordEncoder.encode("anastasijevic1234"), "anastasija.anastasijevic@email.com", true, true);
        User user10 = new User(UUID.randomUUID().toString(), "Sergej", "Boric", "sergejboric",
                bCryptPasswordEncoder.encode("boric1234"), "sergej.boric@email.com", true, true);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
        userRepository.save(user8);
        userRepository.save(user9);
        userRepository.save(user10);
    }

}
