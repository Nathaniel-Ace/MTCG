package at.fhtw.sampleapp.service;

import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.BattleRepository;
import at.fhtw.sampleapp.persistence.repository.BattleRepositoryImpl;

public class BattleService extends AbstractService {

    private BattleRepository battleRepository;

    public BattleService() { battleRepository = new BattleRepositoryImpl(new UnitOfWork()); }



}
