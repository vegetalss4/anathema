package net.sf.anathema.character.generic.framework.xml;

import net.sf.anathema.character.generic.dummy.DummyMundaneCharacterType;
import net.sf.anathema.character.generic.framework.xml.presentation.GenericPresentationTemplate;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.lib.util.Identifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenericPresentationPropertiesTest {

  private GenericPresentationTemplate template;

  @Before
  public void setUp() throws Exception {
    template = new GenericPresentationTemplate();
  }

  @Test
  public void testAutoGeneratedNewResource() throws Exception {
    GenericCharacterTemplate dummyCharacterTemplate = new GenericCharacterTemplate();
    dummyCharacterTemplate.setTemplateType(new TemplateType(new DummyMundaneCharacterType()));
    template.setParentTemplate(dummyCharacterTemplate);
    Assert.assertEquals("CharacterGenerator.Templates.Dummy.TemplateType.Default", template.getNewActionResource()); //$NON-NLS-1$
    dummyCharacterTemplate.setTemplateType(new TemplateType(new DummyMundaneCharacterType(), new Identifier("HeroicSubtype"))); //$NON-NLS-1$
    Assert.assertEquals("CharacterGenerator.Templates.Dummy.HeroicSubtype", template.getNewActionResource()); //$NON-NLS-1$
  }
}
