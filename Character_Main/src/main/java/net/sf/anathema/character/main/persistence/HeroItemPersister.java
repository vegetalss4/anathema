package net.sf.anathema.character.main.persistence;

import net.sf.anathema.character.main.ExaltedCharacter;
import net.sf.anathema.character.main.framework.item.CharacterItem;
import net.sf.anathema.character.main.framework.item.HeroNameFetcher;
import net.sf.anathema.character.main.framework.item.Item;
import net.sf.anathema.character.main.template.HeroTemplate;
import net.sf.anathema.character.main.template.TemplateType;
import net.sf.anathema.character.main.type.CharacterType;
import net.sf.anathema.framework.messaging.IMessaging;
import net.sf.anathema.framework.persistence.RepositoryItemPersister;
import net.sf.anathema.framework.repository.access.RepositoryReadAccess;
import net.sf.anathema.framework.repository.access.RepositoryWriteAccess;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.persistence.HeroModelPersister;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.message.MessageType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class HeroItemPersister implements RepositoryItemPersister {

  private final HeroEnvironment generics;
  private final IMessaging messaging;
  private final HeroPersisterList persisterList;

  public HeroItemPersister(HeroEnvironment generics, IMessaging messaging) {
    this.generics = generics;
    this.messaging = messaging;
    this.persisterList = new HeroPersisterList(generics.getObjectFactory());
  }

  @Override
  public Item createNew(HeroTemplate template) throws PersistenceException {
    return createCharacterInItem(template, new NewCharacterInitializer());
  }

  @Override
  public void save(RepositoryWriteAccess writeAccess, Item item) throws PersistenceException {
    Hero hero = (Hero) item.getItemData();
    String name = new HeroNameFetcher().getName(hero);
    messaging.addMessage("CharacterPersistence.SavingCharacter", MessageType.INFORMATION, name);
    saveModels(writeAccess, hero);
    new HeroMainFilePersister().save(writeAccess, item);
    messaging.addMessage("CharacterPersistence.SavingCharacterDone", MessageType.INFORMATION, name);
  }

  @Override
  public Item load(RepositoryReadAccess readAccess) throws PersistenceException {
    HeroMainFileDto mainFileDto = new HeroMainFilePersister().load(readAccess);
    HeroTemplate template = loadHeroTemplate(mainFileDto);
    CharacterInitializer initializer = new LoadingCharacterInitializer(readAccess, persisterList, messaging);
    Item item = createCharacterInItem(template, initializer);
    item.getRepositoryLocation().setId(mainFileDto.repositoryId);
    return item;
  }

  private HeroTemplate loadHeroTemplate(HeroMainFileDto mainFileDto) {
    CharacterType characterType = generics.getCharacterTypes().findById(mainFileDto.characterType.characterType);
    String subTypeValue = mainFileDto.characterType.subType;
    Identifier subtype = subTypeValue == null ? TemplateType.DEFAULT_SUB_TYPE : new SimpleIdentifier(subTypeValue);
    TemplateType templateType = new TemplateType(characterType, subtype);
    return generics.getTemplateRegistry().getTemplate(templateType);
  }

  private void saveModels(RepositoryWriteAccess writeAccess, Hero hero) {
    for (HeroModelPersister persister : persisterList.iterator(hero)) {
      HeroModel heroModel = hero.getModel(persister.getModelId());
      if (heroModel != null) {
        persister.setMessaging(messaging);
        persister.save(heroModel, new HeroModelSaverImpl(writeAccess));
      }
    }
  }

  private Item createCharacterInItem(HeroTemplate template, CharacterInitializer initializer) {
    ExaltedCharacter character = new ExaltedCharacter(template, generics);
    initializer.initialize(character);
    return initItem(character);
  }

  private Item initItem(ExaltedCharacter character) {
    character.markReadyForWork();
    return new CharacterItem(character);
  }
}