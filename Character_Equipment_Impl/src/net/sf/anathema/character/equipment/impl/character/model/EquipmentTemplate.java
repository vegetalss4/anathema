package net.sf.anathema.character.equipment.impl.character.model;

import net.sf.anathema.character.equipment.character.model.IEquipmentTemplate;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;

public class EquipmentTemplate implements IEquipmentTemplate {

  private final IEquipmentStats[] equipments;
  private final String description;
  private final String name;

  public EquipmentTemplate(IEquipmentStats[] equipments, String name, String description) {
    this.equipments = equipments;
    this.name = name;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public IEquipmentStats[] getEquipmentStats() {
    return equipments;
  }

  public String getName() {
    return name;
  }
}