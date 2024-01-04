package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.persistence.UnitOfWork;

public class BattleRepositoryImpl implements BattleRepository{

    private UnitOfWork unitOfWork;

    public BattleRepositoryImpl(UnitOfWork unitOfWork) { this.unitOfWork = unitOfWork; }
}
