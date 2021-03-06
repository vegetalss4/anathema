package net.sf.anathema.character.equipment.item.model;

import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IEquipmentStats;
import net.sf.anathema.equipment.core.MaterialComposition;

public interface EquipmentStatsFactory {

  IEquipmentStats createNewStats(String[] definedNames, String nameProposal, EquipmentStatisticsType type);

  boolean canHaveThisKindOfStats(EquipmentStatisticsType type, MaterialComposition materialComposition);
}
