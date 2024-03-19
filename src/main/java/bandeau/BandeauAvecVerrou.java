package bandeau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BandeauAvecVerrou extends Bandeau {

    //On déclare le verrou à utiliser
    private final Lock VerrouAUtiliser = new ReentrantLock();

    //méthode pour mettre notre verrou
    public void placer_verrou() {
        VerrouAUtiliser.lock();
    }

    //méthode pour enlever notre verrou
    public void enlever_verrou() {
        VerrouAUtiliser.unlock();
    }
}
