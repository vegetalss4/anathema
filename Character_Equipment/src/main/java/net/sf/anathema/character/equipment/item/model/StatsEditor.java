package net.sf.anathema.character.equipment.item.model;

import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IEquipmentStats;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.util.Closure;

public interface StatsEditor {
  void editStats(Resources resources, String[] nameArray, IEquipmentStats selectedStats);

  void whenChangesAreConfirmed(Closure<IEquipmentStats> action);
}