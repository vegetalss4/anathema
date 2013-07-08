package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.character.main.magic.model.charm.ICharmLearnStrategy;
import net.sf.anathema.character.main.magic.model.charm.CharmGroup;
import net.sf.anathema.character.main.magic.model.charmtree.CharmTree;
import net.sf.anathema.character.main.magic.model.charm.ICharm;
import net.sf.anathema.character.main.magic.model.charmtree.ICharmTree;
import net.sf.anathema.character.main.traits.ValuedTraitType;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.character.main.dummy.DummyCharm;
import net.sf.anathema.character.main.testing.dummy.DummyExaltCharacterType;
import net.sf.anathema.character.main.testing.dummy.magic.DummyLearnableArbitrator;
import net.sf.anathema.character.main.testing.dummy.magic.DummyLearningCharmGroupContainer;
import net.sf.anathema.character.main.testing.dummy.template.DummyCharmTemplate;
import net.sf.anathema.character.main.magic.model.charms.IExtendedCharmLearnableArbitrator;
import net.sf.anathema.character.main.magic.model.charms.ILearningCharmGroup;
import net.sf.anathema.character.main.magic.model.charms.LearningCharmGroup;
import net.sf.anathema.hero.magic.model.charms.context.CreationCharmLearnStrategy;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LearningCharmGroupTest {

  private DummyLearningCharmGroupContainer container = new DummyLearningCharmGroupContainer();

  private LearningCharmGroup createSolarMeleeGroup(IExtendedCharmLearnableArbitrator learnableArbitrator) {
    return createSolarGroup(learnableArbitrator, AbilityType.Melee.getId());
  }

  private LearningCharmGroup createSolarGroup(IExtendedCharmLearnableArbitrator learnableArbitrator, String groupId) {
    ICharmLearnStrategy learnStrategy = new CreationCharmLearnStrategy();
    CharmTree charmTree = new CharmTree(new DummyCharmTemplate());
    CharmGroup group = new CharmGroup(new DummyExaltCharacterType(), groupId,
            charmTree.getAllCharmsForGroup(groupId).toArray(new ICharm[charmTree.getAllCharmsForGroup(groupId).size()]), false);
    return new LearningCharmGroup(learnStrategy, group, learnableArbitrator, container);
  }

  private LearningCharmGroup createSolarGroup(IExtendedCharmLearnableArbitrator learnableArbitrator, ICharmTree charmTree, String groupId) {
    ICharmLearnStrategy learnSrategy = new CreationCharmLearnStrategy();
    CharmGroup group = new CharmGroup(new DummyExaltCharacterType(), groupId,
            charmTree.getAllCharmsForGroup(groupId).toArray(new ICharm[charmTree.getAllCharmsForGroup(groupId).size()]), false);
    return new LearningCharmGroup(learnSrategy, group, learnableArbitrator, container);
  }

  @Test
  public void testIsLearnedCreationCharmOnCreation() throws Exception {
    ICharm learnableCharm = new DummyCharm("learnableDummyCharm");
    IExtendedCharmLearnableArbitrator learnableArbitrator = new DummyLearnableArbitrator(learnableCharm.getId());
    LearningCharmGroup learningCharmGroup = createSolarMeleeGroup(learnableArbitrator);
    container.setLearningCharmGroup(learningCharmGroup);
    assertFalse(learningCharmGroup.isLearned(learnableCharm));
    learningCharmGroup.toggleLearned(learnableCharm);
    assertTrue(learningCharmGroup.isLearned(learnableCharm));
  }

  @Test
  public void testLearnedCreationCharmsUnlearnableOnCreation() throws Exception {
    ICharm learnableCharm = new DummyCharm("learnableDummyCharm");
    IExtendedCharmLearnableArbitrator learnableArbitrator = new DummyLearnableArbitrator(learnableCharm.getId());
    LearningCharmGroup learningCharmGroup = createSolarMeleeGroup(learnableArbitrator);
    container.setLearningCharmGroup(learningCharmGroup);
    assertFalse(learningCharmGroup.isUnlearnable(learnableCharm));
    learningCharmGroup.toggleLearned(learnableCharm);
    assertTrue(learningCharmGroup.isUnlearnable(learnableCharm));
  }

  @Test
  public void testMultipleGroupsPrerequisiteCharms() throws Exception {
    String internalPrerequisiteId = "internalPrerquisite";
    String externalPrerequisiteId = "externalPrerquisite";
    String learCharmID = "learnCharm";
    DummyCharm internalPrerequisite =
            new DummyCharm(internalPrerequisiteId, new ICharm[0], new ValuedTraitType[]{new net.sf.anathema.character.main.traits.types.ValuedTraitType(AbilityType.Melee, 1)});
    DummyCharm externalPrerequisite =
            new DummyCharm(externalPrerequisiteId, new ICharm[0], new ValuedTraitType[]{new net.sf.anathema.character.main.traits.types.ValuedTraitType(AbilityType.Archery, 1)});
    DummyCharm learnCharm = new DummyCharm(learCharmID, new ICharm[]{internalPrerequisite, externalPrerequisite},
            new ValuedTraitType[]{new net.sf.anathema.character.main.traits.types.ValuedTraitType(AbilityType.Melee, 1)});
    ICharmTree charmTree = new CharmTree(new ICharm[]{internalPrerequisite, externalPrerequisite, learnCharm});
    externalPrerequisite.addLearnFollowUpCharm(learnCharm);
    IExtendedCharmLearnableArbitrator learnableArbitrator =
            new DummyLearnableArbitrator(externalPrerequisiteId, internalPrerequisiteId, learCharmID);
    LearningCharmGroup internalGroup = createSolarGroup(learnableArbitrator, charmTree, AbilityType.Melee.getId());
    LearningCharmGroup externalGroup = createSolarGroup(learnableArbitrator, charmTree, AbilityType.Archery.getId());
    container.setLearningCharmGroups(new ILearningCharmGroup[]{internalGroup, externalGroup});
    assertFalse(externalGroup.isLearned(externalPrerequisite));
    assertFalse(internalGroup.isLearned(internalPrerequisite));
    assertFalse(internalGroup.isLearned(learnCharm));
    internalGroup.learnCharm(learnCharm, false);
    assertTrue(externalGroup.isLearned(externalPrerequisite));
    assertTrue(internalGroup.isLearned(internalPrerequisite));
    assertTrue(internalGroup.isLearned(learnCharm));
    externalGroup.forgetCharm(externalPrerequisite, false);
    assertFalse(externalGroup.isLearned(externalPrerequisite));
    assertTrue(internalGroup.isLearned(internalPrerequisite));
    assertFalse(internalGroup.isLearned(learnCharm));
  }
}