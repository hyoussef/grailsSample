package com.sample

import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclSid;

class ManageAclSid {
	static AclSid addSid( String newSid, Boolean isPrincipal){
		AclSid sid = new AclSid()
		sid.setSid(newSid)
		sid.setPrincipal(isPrincipal);
		sid.save(insert: true)
	}
	
	static AclSid updateSid( String newSid , String originalSid, Boolean isPrincipal){
		AclSid sid = AclSid.find {sid == originalSid}
		sid.setSid(newSid)
		sid.setPrincipal(isPrincipal);
		sid.save()
	}
    static constraints = {
    }
}
