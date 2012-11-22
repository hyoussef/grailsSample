package com.sample

import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclSid;

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
		ManageAclSid.addSid(username , true)
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
		User usr = User.get(this.id)
		if (usr.isDirty('username')) {
			def currentName = usr.username
			def originalName = usr.getPersistentValue('username')
			if (currentName != originalName) {
				ManageAclSid.updateSid(currentName , originalName , true);
			}
		}
	}
	

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	



}
