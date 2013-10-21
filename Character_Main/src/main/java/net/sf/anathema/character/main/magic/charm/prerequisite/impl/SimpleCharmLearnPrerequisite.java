package net.sf.anathema.character.main.magic.charm.prerequisite.impl;

import com.google.common.base.Preconditions;
import net.sf.anathema.character.main.magic.charm.Charm;
import net.sf.anathema.character.main.magic.charm.CharmImpl;
import net.sf.anathema.character.main.magic.charm.ICharmLearnArbitrator;
import net.sf.anathema.character.main.magic.charm.ICharmLearnableArbitrator;
<<<<<<< HEAD
import net.sf.anathema.character.main.magic.charm.prerequisite.CharmLearnPrerequisite;
import net.sf.anathema.character.main.magic.charm.prerequisite.CharmLearnPrerequisiteVisitor;
=======
>>>>>>> fecc285840fd34c25557ca7295ed31d502d2dcd5
import net.sf.anathema.character.main.magic.charm.prerequisite.DirectCharmLearnPrerequisite;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleCharmLearnPrerequisite implements DirectCharmLearnPrerequisite {
    private static final Charm PREREQUISITE_NOT_SET = null;
    private final String prerequisiteId;
	private Charm prerequisite;
	
	public SimpleCharmLearnPrerequisite(String charm) {
		this.prerequisiteId = charm;
	}
	
	public SimpleCharmLearnPrerequisite(Charm charm) {
		this.prerequisite = charm;
		this.prerequisiteId = charm.getId();
	}

	@Override
	public Charm[] getDirectPredecessors() {
		return new Charm[] { prerequisite };
	}

	@Override
	public boolean isSatisfied(ICharmLearnArbitrator arbitrator) {
		return arbitrator.isLearned(prerequisite);
	}

	@Override
	public void visitCharmLearnPrerequisite(CharmLearnPrerequisiteVisitor visitor) {
		visitor.visitSimpleCharmPrerequisite(this);		
	}
	
	@Override
	public boolean isAutoSatisfiable(ICharmLearnableArbitrator arbitrator) {
		return arbitrator.isLearnable(prerequisite);
	}
	
	@Override
	public Charm[] getLearnPrerequisites(ICharmLearnArbitrator arbitrator) {
        if (prerequisite == PREREQUISITE_NOT_SET){
           throw new IllegalStateException("The prerequisite Charm isn't linked yet. Please call ``link(Map)`` prior to using this object.");
        }
		Set<Charm> prerequisiteCharms = new HashSet<>();
	    prerequisiteCharms.addAll(prerequisite.getLearnPrerequisitesCharms(arbitrator));
	    prerequisiteCharms.add(prerequisite);
	    return prerequisiteCharms.toArray(new Charm[prerequisiteCharms.size()]);
	}

	@Override
	public void link(Map<String, CharmImpl> charmsById) {
		if (prerequisite != PREREQUISITE_NOT_SET) {
			return;
		}
		prerequisite = charmsById.get(prerequisiteId);
		Preconditions.checkNotNull(prerequisite, "Parent Charm " + prerequisiteId + " not defined." );
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleCharmLearnPrerequisite) {
			SimpleCharmLearnPrerequisite prerequisite = (SimpleCharmLearnPrerequisite) obj;
			return prerequisite.prerequisite.equals(prerequisite);
		}
		return false;
	}
}
