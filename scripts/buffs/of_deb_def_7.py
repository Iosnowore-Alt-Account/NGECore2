import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', -6)
	core.skillModService.addSkillMod(actor, 'expertise_parry', -6)
	core.skillModService.addSkillMod(actor, 'critical_hit_vulnerable', 7)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', -6)
	core.skillModService.deductSkillMod(actor, 'expertise_parry', -6)
	core.skillModService.deductSkillMod(actor, 'critical_hit_vulnerable', 7)
	return