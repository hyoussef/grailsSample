package com.sample

class Role {

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
	
	def beforeInsert() {
		ManageAclSid.addSid(authority , false)
	}

	def beforeUpdate() {
		
		Role role = Role.get(this.id)
		if (usr.isDirty('authority')) {
			def currentName = role.authority
			def originalName = role.getPersistentValue('authority')
			if (currentName != originalName) {
				ManageAclSid.updateSid(currentName , originalName , false);
			}
		}
	}
}
