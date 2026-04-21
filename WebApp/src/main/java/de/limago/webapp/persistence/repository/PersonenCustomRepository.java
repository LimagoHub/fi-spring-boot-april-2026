package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.PersonEntity;

public interface PersonenCustomRepository {

    void onlySave(PersonEntity personEntity) ;
}
