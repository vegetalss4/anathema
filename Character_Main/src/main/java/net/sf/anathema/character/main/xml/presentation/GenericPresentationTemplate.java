package net.sf.anathema.character.main.xml.presentation;

import net.sf.anathema.character.main.template.HeroTemplate;
import net.sf.anathema.character.main.template.ITemplateType;
import net.sf.anathema.character.main.template.presentation.IPresentationProperties;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.lang.clone.ReflectionCloneableObject;

public class GenericPresentationTemplate extends ReflectionCloneableObject<GenericPresentationTemplate> implements IPresentationProperties {

  private ITemplateType templateType;

  @Override
  public RelativePath getSmallCasteIconResource(String casteId) {
    return new RelativePath("icons/" + getCharacterTypeId() + "Button" + casteId + "SecondEdition16.png");
  }

  @Override
  public RelativePath getLargeCasteIconResource(String casteId) {
    return new RelativePath("icons/" + getCharacterTypeId() + "Button" + casteId + "SecondEdition100.png");
  }

  @Override
  public String getNewActionResource() {
    return "CharacterGenerator.Templates." + getCharacterTypeId() + "." + getSubTypeId();
  }

  @Override
  public String getCasteLabelResource() {
    return getCharacterTypeId() + ".Caste.Label";
  }

  public void setParentTemplate(ITemplateType templateType) {
    this.templateType = templateType;
  }

  public void setParentTemplate(HeroTemplate template) {
    this.templateType = template.getTemplateType();
  }

  private String getCharacterTypeId() {
    return templateType.getCharacterType().getId();
  }

  private String getSubTypeId() {
    return templateType.getSubType().getId();
  }
}