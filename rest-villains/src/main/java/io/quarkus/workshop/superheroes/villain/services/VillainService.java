package io.quarkus.workshop.superheroes.villain.services;

import java.util.List;

import io.quarkus.workshop.superheroes.villain.model.Villain;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

public class VillainService {

    @Transactional(SUPPORTS)
    public List<Villain> findAllVillains(long id) {
        return Villain.listAll();
    }

    @Transactional(SUPPORTS)
    public Villain findVillainById(long id) {
        return Villain.findById(id);
    }

    @Transactional(SUPPORTS)
    public Villain findRandoVillain() {
        Villain randomVillain = null;
        while (randomVillain == null) {
            randomVillain = Villain.findRandom();
        }
        return randomVillain;
    }

    public Villain persistVillain(@Valid Villain villain) {
        villain.persist();
        return villain;
    }

    public Villain updateVillain(@Valid Villain villain) {
        Villain entity = Villain.findById(villain.id);
        entity.name = villain.name;
        entity.otherName = villain.otherName;
        entity.level = villain.level;
        entity.picture = villain.picture;
        entity.powers = villain.powers;

        return entity;
    }

    public void deleteVillain(long id) {
        Villain.deleteById(id);
    }
}
