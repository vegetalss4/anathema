package net.sf.anathema.character.abyssal.reporting.extended;

import net.sf.anathema.character.reporting.common.encoder.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.extended.ExtendedEncodingRegistry;
import net.sf.anathema.character.reporting.extended.page.AbstractSecondEditionExaltPdfPartEncoder;
import net.sf.anathema.lib.resources.IResources;

public class SecondEditionAbyssalPartEncoder extends AbstractSecondEditionExaltPdfPartEncoder {

  public SecondEditionAbyssalPartEncoder(IResources resources, ExtendedEncodingRegistry registry, int essenceMax) {
    super(resources, registry, essenceMax);
  }

  public IPdfContentBoxEncoder getGreatCurseEncoder() {
    return new Abyssal2ndResonanceEncoder(getBaseFont(), getResources());
  }

  @Override
  public IPdfContentBoxEncoder getAnimaEncoder() {
    return new Abyssal2ndAnimaEncoderFactory(getResources(), getBaseFont(), getBaseFont()).createAnimaEncoder();
  }
}
