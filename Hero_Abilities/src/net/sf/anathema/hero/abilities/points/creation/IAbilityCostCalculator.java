package net.sf.anathema.hero.abilities.points.creation;

import net.sf.anathema.hero.points.HeroBonusPointCalculator;

public interface IAbilityCostCalculator extends HeroBonusPointCalculator {

  int getFreePointsSpent(boolean favored);

  int getFavoredPicksSpent();
}