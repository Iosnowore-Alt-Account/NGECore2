import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('rock_beetle')
	mobileTemplate.setLevel(18)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(3)
	mobileTemplate.setMeatType("Insect Meat")
	mobileTemplate.setMeatAmount(6)
	mobileTemplate.setHideType("Scaley Hide")
	mobileTemplate.setHideAmount(8)	
	mobileTemplate.setSocialGroup("rock beetle")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_rock_beetle.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_small_toxicgreen.iff', WeaponType.UNARMED, 1.0, 24, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rock_beetle_1', mobileTemplate)
	return