package net.sf.anathema.character.generic.framework.xml.trait.alternate.test;

import net.sf.anathema.character.generic.impl.testing.DummyGenericTrait;
import net.sf.anathema.character.generic.traits.IFavorableGenericTrait;
import net.sf.anathema.character.generic.traits.ITraitType;

public class DummyFavorableGenericTrait extends DummyGenericTrait implements IFavorableGenericTrait {

  private final boolean isFavored;

  public DummyFavorableGenericTrait(ITraitType type, int currentValue) {
    this(type, currentValue, false);
  }

  public DummyFavorableGenericTrait(ITraitType type, int currentValue, boolean isFavored) {
    super(type, currentValue);
    this.isFavored = isFavored;
  }

  public boolean isCasteOrFavored() {
    return isFavored;
  }
}