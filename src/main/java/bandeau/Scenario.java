package bandeau;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}

/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour
 * chaque effet Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final ReentrantReadWriteLock reentrantlock = new ReentrantReadWriteLock();
    private final Lock verrouLecture = reentrantlock.readLock();
    private final Lock verrouEcriture = reentrantlock.writeLock();

    private final List<ScenarioElement> myElements = new LinkedList<>();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e       l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {


        verrouEcriture.lock();

        try {

                 myElements.add(new ScenarioElement(e, repeats));
        } finally
        {
                verrouEcriture.unlock();
        }
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param bandeauAvecVerrou le bandeau ou s'afficher.
     */
    public void playOn(BandeauAvecVerrou bandeauAvecVerrou) {
        new Thread(
            // "lambda-expression"
            () -> {
                bandeauAvecVerrou.placer_verrou();
                try {
                    play(bandeauAvecVerrou);
                } finally {
                    bandeauAvecVerrou.enlever_verrou();
                }
            }).start();
    }
//bandeau simple
    private void play(Bandeau bandeau) {
        verrouLecture.lock();
        for (ScenarioElement element : myElements) {
            for (int nbreRepet = 0; nbreRepet < element.repeats; nbreRepet++) {
                element.effect.playOn(bandeau);
            }
        }
        verrouLecture.unlock();
    }
}
