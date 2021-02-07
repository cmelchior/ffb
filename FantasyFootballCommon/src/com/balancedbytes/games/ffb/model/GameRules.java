package com.balancedbytes.games.ffb.model;

import java.util.Map;

import com.balancedbytes.games.ffb.FactoryManager;
import com.balancedbytes.games.ffb.FactoryType.Factory;
import com.balancedbytes.games.ffb.FactoryType.FactoryContext;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.factory.INamedObjectFactory;
import com.balancedbytes.games.ffb.factory.SkillFactory;

public class GameRules implements IFactorySource {

	private Map<Factory, INamedObjectFactory> factories;
	private FactoryManager manager;
	private IFactorySource applicationSource;
	
	public GameRules(IFactorySource applicationSource, FactoryManager manager) {
		this.manager = manager;
		this.applicationSource = applicationSource;
		factories = manager.getFactoriesForContext(getContext());
	}
	
	public void initialize(Game game) {
		for (INamedObjectFactory factory : factories.values()) {
			factory.initialize(game);
		}
	}

	public SkillFactory getSkillFactory() {
		return this.<SkillFactory>getFactory(Factory.SKILL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends INamedObjectFactory> T getFactory(Factory factory) {
		return (T) factories.get(factory);
	}
	
	@Override
	public FactoryContext getContext() {
		return FactoryContext.GAME;
	}

	@Override
	public FactoryManager getFactoryManager() {
		return manager;
	}

	@Override
	public IFactorySource forContext(FactoryContext context) {
		if (context == getContext()) {
			return this;
		}
		return applicationSource;
	}
}