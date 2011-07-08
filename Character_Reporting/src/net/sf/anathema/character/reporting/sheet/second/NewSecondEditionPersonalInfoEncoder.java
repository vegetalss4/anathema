package net.sf.anathema.character.reporting.sheet.second;

import static net.sf.anathema.character.reporting.sheet.pageformat.IVoidStateFormatConstants.TEXT_PADDING;
import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.reporting.sheet.util.AbstractPdfEncoder;
import net.sf.anathema.character.reporting.util.Bounds;
import net.sf.anathema.character.reporting.util.Position;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

public class NewSecondEditionPersonalInfoEncoder extends AbstractPdfEncoder {

  private final BaseFont baseFont;
  private final IResources resources;

  public NewSecondEditionPersonalInfoEncoder(BaseFont baseFont, IResources resources) {
    this.baseFont = baseFont;
    this.resources = resources;
  }

  public void encodePersonalInfos(
      PdfContentByte directContent,
      IGenericCharacter character,
      IGenericDescription description,
      Bounds infoBounds) {
    ICharacterType characterType = character.getTemplate().getTemplateType().getCharacterType();
    
    int lines;
    if (characterType.isExaltType()) {
      lines = 4;
    }
    else {
      lines = 3;
    }
    float lineHeight = (infoBounds.height - TEXT_PADDING) / lines;
    float entryWidth = (infoBounds.width - 2*TEXT_PADDING) / 3;
    float shortEntryWidth = (infoBounds.width - 4*TEXT_PADDING) / 5;
    float firstColumnX = infoBounds.x;
    float secondColumnX = firstColumnX + entryWidth + TEXT_PADDING;
    float thirdColumnX = secondColumnX + entryWidth + TEXT_PADDING;

    float firstRowY = (int) (infoBounds.getMaxY() - lineHeight);
    String conceptContent = description.getConceptText();
    String conceptLabel = getLabel("Concept"); //$NON-NLS-1$
    if (characterType.isExaltType()) {
      drawLabelledContent(directContent, conceptLabel, conceptContent, new Position(firstColumnX, firstRowY), entryWidth);
      String casteContent = getCasteString(character.getConcept().getCasteType());
      drawLabelledContent(
          directContent,
          getLabel("Caste." + characterType.getId()), casteContent, new Position(secondColumnX, firstRowY), entryWidth); //$NON-NLS-1$
    }
    else {
      drawLabelledContent(directContent, conceptLabel, conceptContent, new Position(firstColumnX, firstRowY), 2*entryWidth + TEXT_PADDING);
    }
    IExaltedRuleSet rules = character.getRules();
    String rulesContent = rules == null ? null : resources.getString("Ruleset." + rules.getId()); //$NON-NLS-1$
    drawLabelledContent(directContent,
                        getLabel("Rules"), rulesContent, new Position(thirdColumnX, firstRowY), entryWidth); //$NON-NLS-1$
    /*drawLabelledContent(
        directContent,
        getLabel("Player"), description.getPlayer(), new Position(secondColumnX, firstRowY), entryWidth); //$NON-NLS-1$*/

    float secondRowY = firstRowY - lineHeight;
    String motivationContent = character.getConcept().getWillpowerRegainingComment(resources);
    String motivationLabel = character.getRules().getEdition() == ExaltedEdition.SecondEdition
        ? getLabel("Motivation") : getLabel("Nature"); //$NON-NLS-1$ //$NON-NLS-2$
    drawLabelledContent(directContent,
                        motivationLabel, motivationContent, new Position(firstColumnX, secondRowY), infoBounds.width);

    float thirdRowY = secondRowY - lineHeight;
    float[] shortColumnX = new float[5];
    for (int i = 0; i < 5; i++) {
      shortColumnX[i] = infoBounds.x + i*(shortEntryWidth + TEXT_PADDING);
    }
    String ageContent = null;
    drawLabelledContent(directContent,
                        getLabel("Age"), ageContent, new Position(shortColumnX[0], thirdRowY), shortEntryWidth);
    String sexContent = null;
    drawLabelledContent(directContent,
                        getLabel("Sex"), sexContent, new Position(shortColumnX[1], thirdRowY), shortEntryWidth);
    String hairContent = null;
    drawLabelledContent(directContent,
                        getLabel("Hair"), hairContent, new Position(shortColumnX[2], thirdRowY), shortEntryWidth);
    String skinContent = null;
    drawLabelledContent(directContent,
                        getLabel("Skin"), skinContent, new Position(shortColumnX[3], thirdRowY), shortEntryWidth);
    String eyesContent = null;
    drawLabelledContent(directContent,
                        getLabel("Eyes"), eyesContent, new Position(shortColumnX[4], thirdRowY), shortEntryWidth);

    if (characterType.isExaltType()) {
      float fourthRowY = thirdRowY - lineHeight;
      String animaContent = null;
      drawLabelledContent(directContent,
                          getLabel("Anima"), animaContent, new Position(firstColumnX, fourthRowY), infoBounds.width); //$NON-NLS-1$
    }
  }

  private String getCasteString(ICasteType casteType) {
    if (casteType == null || casteType == ICasteType.NULL_CASTE_TYPE) {
      return null;
    }
    return resources.getString("Caste." + casteType.getId()); //$NON-NLS-1$
  }

  @Override
  protected BaseFont getBaseFont() {
    return baseFont;
  }

  protected final String getLabel(String key) {
    return resources.getString("Sheet.Label." + key) + ":"; //$NON-NLS-1$ //$NON-NLS-2$
  }
}