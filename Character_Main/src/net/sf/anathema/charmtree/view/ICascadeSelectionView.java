package net.sf.anathema.charmtree.view;

import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.platform.tree.presenter.view.CascadeLoadedListener;
import net.sf.anathema.platform.tree.presenter.view.NodeProperties;
import net.sf.anathema.platform.tree.presenter.view.ToolTipProperties;
import net.sf.anathema.framework.ui.Area;

import javax.swing.JComponent;
import javax.swing.ListCellRenderer;

public interface ICascadeSelectionView {

  CharmTreeRenderer getCharmTreeRenderer();

  void addCascadeLoadedListener(CascadeLoadedListener listener);

  //todo: Renderer
  void addCharmTypeSelector(String title, Identifier[] types, ListCellRenderer renderer);

  void addCharmTypeSelectionListener(ObjectValueListener<Identifier> selectionListener);

  //todo: Action
  void addCharmFilterButton(SmartAction action, String titleText);

  void fillCharmGroupBox(Identifier[] charmGroups);

  void fillCharmTypeBox(Identifier[] cascadeTypes);

  //todo: Renderer
  void addCharmGroupSelector(String title, ListCellRenderer renderer, ICharmGroupChangeListener selectionListener, Area preferredSize);

  void addCharmCascadeHelp(String helpText);

  //todo: Component
  JComponent getCharmComponent();

  void initGui(ToolTipProperties treeProperties, NodeProperties properties);
}