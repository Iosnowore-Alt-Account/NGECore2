import sys

def setup(core, object):
	object.setStringAttribute('condition', '40000/40000')
	object.setIntAttribute('volume', 1)
	object.setStringAttribute('required_faction', 'Imperial')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_assault')
	return	