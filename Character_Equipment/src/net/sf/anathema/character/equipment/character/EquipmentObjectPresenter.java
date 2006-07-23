package net.sf.anathema.character.equipment.character;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.disy.commons.core.model.BooleanModel;
import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.character.view.IEquipmentObjectView;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.lib.gui.IPresenter;

public class EquipmentObjectPresenter implements IPresenter {

  private final IEquipmentItem model;
  private final IEquipmentObjectView view;
  private final IEquipmentStringBuilder stringBuilder;

  public EquipmentObjectPresenter(
      IEquipmentItem model,
      IEquipmentObjectView view,
      IEquipmentStringBuilder stringBuilder) {
    this.model = model;
    this.view = view;
    this.stringBuilder = stringBuilder;
  }

  public void initPresentation() {
    view.setItemTitle(model.getName());
    String description = model.getDescription();
    if (description != null) {
      view.setItemDescription(description);
    }
    for (final IEquipmentStats equipment : model.getEquipments()) {
      final BooleanModel booleanModel = view.addStats(createEquipmentDescription(equipment));
      booleanModel.setValue(model.isPrintEnabled(equipment));
      booleanModel.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          model.setPrintEnabled(equipment, booleanModel.getValue());
        }
      });
    }
  }

  private String createEquipmentDescription(IEquipmentStats equipment) {
    return stringBuilder.createString(equipment);
  }
}