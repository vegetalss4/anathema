package net.sf.anathema.fx.character.perspective;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.anathema.character.perspective.Selector;
import net.sf.anathema.character.perspective.model.model.CharacterIdentifier;

import javax.swing.SwingUtilities;

public class CharacterSelected implements EventHandler<ActionEvent> {
  private final Selector<CharacterIdentifier> characterSelector;
  private final CharacterIdentifier identifier;

  public CharacterSelected(Selector<CharacterIdentifier> characterSelector, CharacterIdentifier identifier) {
    this.characterSelector = characterSelector;
    this.identifier = identifier;
  }

  @Override
  public void handle(ActionEvent actionEvent) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        characterSelector.selected(identifier);
      }
    });
  }
}
